package com.appedia.bassat.job;

import com.appedia.bassat.domain.ImportStatement;
import com.appedia.bassat.domain.ImportStatus;
import com.appedia.bassat.service.ImportException;
import com.appedia.bassat.service.ImportService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.File;
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
        try {
            importService.importStatementFile("muzamilo@gmail.com", "071153322", new File("/home/muz/workspace/test/test.pdf"));
        } catch (ImportException e) {
            System.out.println(e.toString());
        }
        System.out.println(importService.getStatementsToImport());
    }

}
