package com.appedia.bassat.job;

import com.appedia.bassat.domain.ImportStatus;
import com.appedia.bassat.domain.Statement;
import com.appedia.bassat.domain.User;
import com.appedia.bassat.job.mailintegration.InvalidMessageException;
import com.appedia.bassat.job.mailintegration.MailboxMessageHandler;
import com.appedia.bassat.job.mailintegration.MailboxReader;
import com.appedia.bassat.job.statementio.PDFExtractor;
import com.appedia.bassat.job.statementio.ParseException;
import com.appedia.bassat.job.statementio.StatementBuilder;
import com.appedia.bassat.service.AccountService;
import com.appedia.bassat.service.ImportService;
import com.appedia.bassat.service.UserService;
import org.apache.commons.io.IOUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.io.FileOutputStream;

/**
 * @author muz
 */
public class ImportJob extends QuartzJobBean implements StatefulJob, MailboxMessageHandler {

    private ImportService importService;
    private UserService userService;
    private AccountService accountService;
    private StatementBuilder statementBuilder;
    private MailboxReader mailboxReader;
    private PDFExtractor pdfExtractor;

    /**
     *
     * @param context
     * @throws JobExecutionException
     */
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println("###### EXECUTING ImportJob ######");

        getMailboxReader().processInbox(this);

        System.out.println("#################################");

    }

    /**
     *
     * @param message
     * @throws MessagingException
     */
    public void onMessage(final Message message) throws Exception {

        String emailAddress = message.getFrom() == null ? null : ((InternetAddress) message.getFrom()[0]).getAddress();

        // find user for this email
        User user = getUserService().getUserByEmail(emailAddress);
        if (user == null) {
            throw new InvalidMessageException("No registered users with email address " + emailAddress);

        } else {
            Multipart part = (Multipart) message.getContent();
            int nParts = part.getCount();
            if (nParts == 0) {
                throw new InvalidMessageException("No statement PDF file attachments found");

            } else {
                for (int i = 0; i < nParts; i++) {

                    // get file attachment for this message
                    String fileAttachment = part.getBodyPart(i).getFileName();

                    // check if file attachment is a PDF
                    if (fileAttachment != null && fileAttachment.trim().toLowerCase().endsWith(".pdf")) {
                        System.out.println("Found fileAttachment " + fileAttachment);
                        File tempPdfFile = new File(System.getProperty("java.io.tmpdir") + File.separator + fileAttachment);
                        IOUtils.copy(part.getBodyPart(i).getInputStream(), new FileOutputStream(tempPdfFile));

                        // import PDF
                        System.out.println("Importing fileAttachment " + tempPdfFile.getAbsolutePath() + " (" + tempPdfFile.length() + " bytes)");
                        try {

                            // extract PDF
                            File tempExtractedPdfFile = getPdfExtractor().extract(tempPdfFile, user.getIdNumber());
                            System.out.println("Extracted PDF file " + tempExtractedPdfFile.getAbsolutePath());

                            // validate statement
                            Statement statement = getStatementBuilder().build(tempExtractedPdfFile);
                            String accountIdentifier = statement.getHeader().getAccountIdentifier();
                            if (!getAccountService().hasAccount(user, accountIdentifier)) {
                                throw new InvalidMessageException("Account " + accountIdentifier + " does not belong to user " + user.getEmail());
                            }

                            // persist SUCCESSFUL import statement record
                            getImportService().importStatement(emailAddress, accountIdentifier, tempPdfFile, ImportStatus.PENDING);

                        } catch (ParseException e) { // we still import valid but incorrectly structured statements -- in case it can be fixed
                            System.err.println("Error parsing statement file - flagging as import failure");
                            // persist FAILED import statement record
                            getImportService().importStatement(emailAddress, null, tempPdfFile, ImportStatus.ERROR);
                        }
                    } else if (fileAttachment != null) {
                        System.err.println("Unsupported file fileAttachment " + fileAttachment + " - skipping ...");
                    }
                }
            }
        }
    }

    /**
     *
     * @return
     */
    public ImportService getImportService() {
        return importService;
    }

    /**
     *
     * @param importService
     */
    public void setImportService(ImportService importService) {
        this.importService = importService;
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
    public MailboxReader getMailboxReader() {
        return mailboxReader;
    }

    /**
     *
     * @param mailboxReader
     */
    public void setMailboxReader(MailboxReader mailboxReader) {
        this.mailboxReader = mailboxReader;
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
}



