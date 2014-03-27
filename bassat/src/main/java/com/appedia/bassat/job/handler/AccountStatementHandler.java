package com.appedia.bassat.job.handler;

import com.appedia.bassat.domain.Statement;

import java.io.File;

/**
 * @author Muz Omar
 */
public interface AccountStatementHandler {

    /**
     *
     * @param statementFile
     * @return
     */
    Statement parseStatement(File statementFile) throws Exception;

}
