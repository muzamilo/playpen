package com.appedia.bassat.job;

import com.appedia.bassat.domain.ImportStatement;
import com.appedia.bassat.domain.ImportStatus;
import com.appedia.bassat.service.ImportService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * @author muz
 */
public class ImportJob extends QuartzJobBean {

    private ImportService importService;

    /**
     *
     * @param importService
     */
    public void setImportService(ImportService importService) {
        this.importService = importService;
    }

    /**
     *
     * @param context
     * @throws JobExecutionException
     */
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println("## EXECUTING ImportJob ##");
        ImportStatement importStatement = new ImportStatement();
        importStatement.setStatus(ImportStatus.PENDING);
        importStatement.setMd5("md5");
        importStatement.setLinkUserEmail("linkUserEmail");
        importStatement.setLinkAccountNumber("linkAccountNumber");
        importStatement.setImportDateTime(new Date());
        importStatement.setPdfFileData("TEST FILE DATA".getBytes());
        try {
            importService.persistStatement(importStatement);
        } catch (DuplicateKeyException e) {
            System.out.println(importStatement + " already exists");
        }
        System.out.println(importService.getStatementsToImport());
    }

}
