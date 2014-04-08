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
     * @param fileData
     * @return
     */
    public StatementComposite build(byte[] fileData) throws IOException, ParseException {

        List<String> data = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(fileData)));
        for (String line; (line = br.readLine()) != null; ) {
            //System.out.println(line);
            data.add(line);
        }

        StatementParser parser = getStatementParser(data);
        if (parser == null) {
            throw new IllegalArgumentException("No StatementParser was set");
        }

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
