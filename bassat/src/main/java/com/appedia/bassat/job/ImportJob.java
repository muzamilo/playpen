package com.appedia.bassat.job;

import com.appedia.bassat.domain.ImportStatus;
import com.appedia.bassat.domain.Statement;
import com.appedia.bassat.service.ImportService;
import org.apache.commons.io.IOUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.mail.*;
import java.text.ParseException;

/**
 * @author muz
 */
public class ImportJob extends QuartzJobBean implements StatefulJob, MailboxMessageHandler {

    private ImportService importService;
    private MailboxReader mailboxReader;
    private PDFExtractor pdfExtractor;

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
    public ImportService getImportService() {
        return importService;
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
     */
    public MailboxReader getMailboxReader() {
        return mailboxReader;
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
    public PDFExtractor getPdfExtractor() {
        return pdfExtractor;
    }

    /**
     *
     * @param context
     * @throws JobExecutionException
     */
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println("## BEGIN EXECUTING ImportJob ##");

        getMailboxReader().processInbox(this);

        System.out.println("## END EXECUTING ImportJob ##");

    }

    /**
     *
     * @param message
     * @throws MessagingException
     */
    public void onMessage(final Message message) throws MessagingException {

        String emailAddress = message.getFrom()[0];

        if (!getUserService().verifyRegisteredUser(emailAddress)) {
            System.err.println("No registered users with email address " + emailAddress);

        } else {
            Multipart part = (Multipart) message.getContent();
            int nParts = part.getCount();
            if (nParts == 0) {
                System.err.println("No statement PDF file attachments found");

            } else {
                for (int i = 0; i < nParts; i++) {
                    String attachment = part.getBodyPart(i).getFileName();
                    if (attachment != null && attachment.trim().toLowerCase().endsWith(".pdf")) {
                        byte[] pdfContent = IOUtils.toByteArray(part.getBodyPart(i).getInputStream());
                        if (pdfContent.length > 65535) {
                            System.err.println("Unsupported file attachment " + attachment + " - skipping ...");
                        } else {
                            System.out.println("Loaded attachment " + pdfContent.length + " bytes");
                            try {
                                Statement statement = getPdfExtractor().extract(pdfContent);
                                getImportService().importStatement(emailAddress, statement.getAccountNumber(), pdfContent, ImportStatus.PENDING);
                            } catch (ParseException e) {
                                getImportService().importStatement(emailAddress, null, pdfContent, ImportStatus.ERROR);
                            }
                        }
                    } else {
                        System.err.println("Unsupported file attachment " + attachment + " - skipping ...");
                    }
                }
            }
        }
    }



