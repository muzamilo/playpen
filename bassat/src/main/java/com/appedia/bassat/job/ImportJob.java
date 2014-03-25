package com.appedia.bassat.job;

import com.appedia.bassat.domain.ImportStatement;
import com.appedia.bassat.domain.ImportStatus;
import com.appedia.bassat.service.ImportService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * @author muz
 */
public class ImportJob extends QuartzJobBean {

    private ImportService importService;

    public void setImportService(ImportService importService) {
        this.importService = importService;
    }

    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println("## EXECUTING ImportJob ##");
        ImportStatement importStatement = new ImportStatement();
        importStatement.setStatus(ImportStatus.PENDING);
        importStatement.setMd5("md5");
        importStatement.setLinkUserEmail("linkUserEmail");
        importStatement.setLinkAccountNumber("linkAccountNumber");
        importStatement.setImportDateTime(new Date());
        //importService.insertImportStatement(importStatement);
        System.out.println(importService.getImportStatementsByStatus(ImportStatus.PENDING));
    }

}
