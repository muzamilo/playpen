package com.appedia.bassat.job;

import com.appedia.bassat.service.ImportException;
import com.appedia.bassat.service.ImportService;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.File;

/**
 * @author muz
 */
public class ImportJob extends QuartzJobBean implements StatefulJob {

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
     * @return
     */
    public ImportService getImportService() {
        return importService;
    }

    /**
     *
     * @param context
     * @throws JobExecutionException
     */
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println("## EXECUTING ImportJob ##");

        Integer count = (Integer) context.getMergedJobDataMap().get("count");
        if (count == null) {
            count = 0;
        }
        System.err.println("HelloJob is executing. Count: '"+count+"', and is the job stateful? "+context.getJobDetail().isStateful());
        context.getJobDetail().getJobDataMap().put("count", ++count);

        try {
            getImportService().importStatementFile("muzamilo@gmail.com", "071153322", new File("/home/muz/workspace/statement/xxxxxxxxxxxx7929.pdf"));
        } catch (ImportException e) {
            System.out.println(e.toString());
        }
        System.out.println(importService.getStatementsToImport());
    }

}
