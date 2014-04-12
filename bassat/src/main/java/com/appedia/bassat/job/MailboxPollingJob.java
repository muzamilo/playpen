package com.appedia.bassat.job;

import com.appedia.bassat.domain.Account;
import com.appedia.bassat.domain.ImportStatus;
import com.appedia.bassat.domain.StatementComposite;
import com.appedia.bassat.domain.User;
import com.appedia.bassat.job.mailintegration.InvalidMessageException;
import com.appedia.bassat.job.mailintegration.MailboxMessageHandler;
import com.appedia.bassat.job.mailintegration.MailboxMessagePoller;
import com.appedia.bassat.job.statementio.PDFExtractor;
import com.appedia.bassat.job.statementio.ParseException;
import com.appedia.bassat.job.statementio.StatementBuilder;
import com.appedia.bassat.service.AccountService;
import com.appedia.bassat.service.StatementIntegrityViolationException;
import com.appedia.bassat.service.StatementService;
import com.appedia.bassat.service.UserService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.io.IOUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author muz
 */
public class MailboxPollingJob extends QuartzJobBean implements MailboxMessageHandler {

    private static final String CC_MASK = "xxxxxxxxxxxx";

    private UserService userService;
    private AccountService accountService;
    private StatementService statementService;
    private StatementBuilder statementBuilder;
    private PDFExtractor pdfExtractor;
    private MailboxMessagePoller mailboxMessagePoller;
    private String originatorSenderEmail;

    /**
     *
     * @param context
     * @throws JobExecutionException
     */
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        mailboxMessagePoller.processInbox(this);

    }

    /**
     *
     * @param message
     * @throws MessagingException
     */
    public void onMessage(final Message message, final String emailAddress) throws Exception {

        assert emailAddress != null;
        boolean isEmailStatementOriginator = emailAddress.equals(getOriginatorSenderEmail());

        // find user for this email
        User user = null;
        if (!isEmailStatementOriginator) {
            user = getUserService().getUserByEmail(emailAddress);
            if (user == null) {
                throw new InvalidMessageException("No registered users with email address " + emailAddress);
            }
        }

        Multipart part = (Multipart) message.getContent();
        int nParts = part.getCount();
        if (nParts == 0) {
            throw new InvalidMessageException("No statement PDF file attachments exists");

        } else if (nParts > 1 && isEmailStatementOriginator) {
            throw new InvalidMessageException("Multiple PDF file attachments exists");

        } else {
            for (int i = 0; i < nParts; i++) {
                // get file attachment for this message
                String fileAttachment = part.getBodyPart(i).getFileName();
                InputStream fileDataStream = part.getBodyPart(i).getInputStream();
                byte[] pdfFileData = IOUtils.toByteArray(fileDataStream);

                // check if file attachment is a PDF
                if (fileAttachment != null && fileAttachment.toLowerCase().endsWith(".pdf")) {
                    System.out.println("Found fileAttachment " + fileAttachment);

                    if (isEmailStatementOriginator && (user = deriveUserFromFileAttachment(fileAttachment, pdfFileData)) == null) {
                        throw new InvalidMessageException("No registered users found for this message");
                    }

                    try {
                        // import PDF
                        System.out.println("Importing fileAttachment " + fileAttachment);
                        try {
                            // extractToText PDF
                            System.out.println("Extracting " + fileAttachment + " to text");
                            byte[] txtFileData = getPdfExtractor().extractToText(pdfFileData, user.getIdNumber());

                            // build statement with transactions
                            StatementComposite statementComposite = getStatementBuilder().build(txtFileData);

                            // validate account belongs to this user
                            String accountIdentifier = statementComposite.getStatement().getAccountIdentifier();
                            if (!getAccountService().checkUserHasAccount(user, accountIdentifier)) {
                                throw new InvalidMessageException("Account " + accountIdentifier + " does not belong to user " + user.getEmail());
                            }

                            // persist SUCCESSFUL import statement record
                            getStatementService().uploadStatementFile(user.getUserId(), accountIdentifier, pdfFileData, ImportStatus.PENDING);

                        // we still import valid but incorrectly structured statements -- in case it can be fixed
                        } catch (ParseException e) {
                            System.err.println("Error parsing statement file - importing as failure");
                            // persist FAILED import statement record
                            getStatementService().uploadStatementFile(user.getUserId(), null, pdfFileData, ImportStatus.ERROR);
                        }
                    } catch (StatementIntegrityViolationException e) {
                        throw new InvalidMessageException(e.getMessage());
                    }
                } else if (fileAttachment != null) {
                    System.err.println("Unsupported file fileAttachment " + fileAttachment + " - skipping ...");
                }
            }
        }
    }

    /**
     *
     * @param fileAttachment
     * @param fileContent
     * @return User
     * @throws IOException
     */
    protected final User deriveUserFromFileAttachment(String fileAttachment, byte[] fileContent) throws IOException {
        List<Account> accounts =  new ArrayList<Account>();
        String accountIdentifier = FilenameUtils.removeExtension(fileAttachment);
        // test for CC number
        if (accountIdentifier.length() == 16 && accountIdentifier.startsWith(CC_MASK) && StringUtils.isNumeric(accountIdentifier.substring(12))) {
            accounts.addAll(getAccountService().getAccountsByIdentifier(accountIdentifier.substring(12), true));

        } else if (accountIdentifier.length() <= 10 && StringUtils.isNumeric(accountIdentifier)) {
            accounts.addAll(getAccountService().getAccountsByIdentifier(accountIdentifier.substring(12), false));
        }
        // test whether the user's ID unlocks the PDF statement file
        for (Account account : accounts) {
            User user = getUserService().getUserById(account.getUserId());
            if (user != null) {
                if (getPdfExtractor().checkValidPassword(fileContent, user.getIdNumber())) {
                    return user;
                }
            }
        }
        return null;
    }

    /**
     *
     * @return
     */
    public StatementService getStatementService() {
        return statementService;
    }

    /**
     *
     * @param statementService
     */
    public void setStatementService(StatementService statementService) {
        this.statementService = statementService;
    }

    /**
     *
     * @return
     */
    public UserService getUserService() {
        return userService;
    }

    /**
     *
     * @param userService
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     *
     * @return
     */
    public AccountService getAccountService() {
        return accountService;
    }

    /**
     *
     * @param accountService
     */
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     *
     * @return
     */
    public StatementBuilder getStatementBuilder() {
        return statementBuilder;
    }

    /**
     *
     * @param statementBuilder
     */
    public void setStatementBuilder(StatementBuilder statementBuilder) {
        this.statementBuilder = statementBuilder;
    }

    /**
     *
     * @return
     */
    public PDFExtractor getPdfExtractor() {
        return pdfExtractor;
    }

    /**
     *
     * @param pdfExtractor
     */
    public void setPdfExtractor(PDFExtractor pdfExtractor) {
        this.pdfExtractor = pdfExtractor;
    }

    /**
     *
     * @return
     */
    public MailboxMessagePoller getMailboxMessagePoller() {
        return mailboxMessagePoller;
    }

    /**
     *
     * @param mailboxMessagePoller
     */
    public void setMailboxMessagePoller(MailboxMessagePoller mailboxMessagePoller) {
        this.mailboxMessagePoller = mailboxMessagePoller;
    }

    /**
     *
     * @return
     */
    public String getOriginatorSenderEmail() {
        return originatorSenderEmail;
    }

    /**
     *
     * @param originatorSenderEmail
     */
    public void setOriginatorSenderEmail(String originatorSenderEmail) {
        this.originatorSenderEmail = originatorSenderEmail;
    }
}



