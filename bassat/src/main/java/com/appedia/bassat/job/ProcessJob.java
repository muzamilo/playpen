package com.appedia.bassat.job;

import com.appedia.bassat.domain.ImportStatus;
import com.appedia.bassat.domain.ImportedStatement;
import com.appedia.bassat.domain.StatementComposite;
import com.appedia.bassat.domain.User;
import com.appedia.bassat.job.statementio.PDFExtractor;
import com.appedia.bassat.job.statementio.ParseException;
import com.appedia.bassat.job.statementio.StatementBuilder;
import com.appedia.bassat.service.AccountService;
import com.appedia.bassat.service.StatementService;
import com.appedia.bassat.service.UserService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

/**
 * @author muz
 */
public class ProcessJob extends QuartzJobBean {

    private UserService userService;
    private AccountService accountService;
    private StatementService statementService;
    private StatementBuilder statementBuilder;
    private PDFExtractor pdfExtractor;

    /**
     *
     * @param context
     * @throws org.quartz.JobExecutionException
     */
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        System.out.println("Checking for pending imports ...");
        List<ImportedStatement> pendingStatements = getStatementService().getImportedStatements(ImportStatus.PENDING);
        for (ImportedStatement importedStatement : pendingStatements) {
            try {
                // for each imported statement ...
                System.out.println("Found " + importedStatement);
                // find the user owner of this statement
                User user = getUserService().getUserById(importedStatement.getLinkUserId());
                // extract the statement file from PDF to text
                byte[] txtFileData = getPdfExtractor().extractToText(importedStatement.getPdfFileData(), user.getIdNumber());
                // parse the statement into internal data structures
                StatementComposite statementComposite = getStatementBuilder().build(txtFileData);
                // process the statement
                getStatementService().processImportedStatement(importedStatement, statementComposite);

            } catch (ParseException e) {
                System.err.println("Error parsing statement file - updating import as failure");
                // persist FAILED import statement record
                importedStatement.setStatus(ImportStatus.ERROR);
                getStatementService().updateImportedStatement(importedStatement);

            } catch (Exception e) {
                System.err.println("There was a problem processing imported statement " + importedStatement.getImportedStatementId() + ": " + e.toString());
                e.printStackTrace();
            }
        }

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
}



