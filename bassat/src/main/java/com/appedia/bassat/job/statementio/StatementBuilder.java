package com.appedia.bassat.job.statementio;

import com.appedia.bassat.domain.StatementComposite;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author muz
 */
public class StatementBuilder {

    private final String CARD_DIVISION = "Card Division";

    @Autowired
    public StatementParser transactionAccountStatementParser;
    @Autowired
    public StatementParser creditCardStatementParser;

    /**
     *
     */
    public StatementBuilder() {
    }

    /**
     *
     * @param pdfTxtFile
     * @return
     */
    public StatementComposite build(File pdfTxtFile) throws IOException, ParseException {

        List<String> data = loadFile(pdfTxtFile);

        StatementParser parser = getStatementParser(data);

        return parser.parse(data);
    }

    /**
     *
     * @param istream
     * @return
     */
    public StatementComposite build(InputStream istream) throws IOException, ParseException {

        List<String> data = loadFile(istream);

        StatementParser parser = getStatementParser(data);

        return parser.parse(data);
    }


    /**
     *
     * @param data
     * @return
     */
    protected final StatementParser getStatementParser(List<String> data) {
        if (CARD_DIVISION.equals(data.get(0))) { // First line
            return creditCardStatementParser;
        } else {
            return transactionAccountStatementParser;
        }
    }

    /**
     *
     * @param statementFile
     * @return  List of String lines
     * @throws java.io.IOException
     */
    protected final List<String> loadFile(File statementFile) throws IOException {
        List<String> data = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new FileReader(statementFile));
        for (String line; (line = br.readLine()) != null; ) {
            //System.out.println(line);
            data.add(line);
        }
        return data;
    }

    /**
     *
     * @param istream
     * @return  List of String lines
     * @throws java.io.IOException
     */
    protected final List<String> loadFile(InputStream istream) throws IOException {
        List<String> data = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new InputStreamReader(istream));
        for (String line; (line = br.readLine()) != null; ) {
            //System.out.println(line);
            data.add(line);
        }
        return data;
    }

    public StatementParser getTransactionAccountStatementParser() {
        return transactionAccountStatementParser;
    }

    public void setTransactionAccountStatementParser(StatementParser transactionAccountStatementParser) {
        this.transactionAccountStatementParser = transactionAccountStatementParser;
    }

    public StatementParser getCreditCardStatementParser() {
        return creditCardStatementParser;
    }

    public void setCreditCardStatementParser(StatementParser creditCardStatementParser) {
        this.creditCardStatementParser = creditCardStatementParser;
    }
}
