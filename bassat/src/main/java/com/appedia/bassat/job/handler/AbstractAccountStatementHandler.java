package com.appedia.bassat.job.handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Muz Omar
 */
public abstract class AbstractAccountStatementHandler {

    /**
     *
     * @param statementFile
     * @return  List of String lines
     * @throws IOException
     */
    protected List<String> loadFile(File statementFile) throws IOException {
        List<String> data = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new FileReader(statementFile));
        for (String line; (line = br.readLine()) != null; ) {
            //System.out.println(line);
            data.add(line);
        }
        return data;
    }

}
