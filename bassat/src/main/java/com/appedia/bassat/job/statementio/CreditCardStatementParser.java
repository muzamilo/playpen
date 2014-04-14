package com.appedia.bassat.job.statementio;

import com.appedia.bassat.domain.Statement;
import com.appedia.bassat.domain.StatementComposite;
import com.appedia.bassat.domain.StatementFrequency;
import com.appedia.bassat.domain.Transaction;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author muz
 */
public class CreditCardStatementParser implements StatementParser {

    private static final SimpleDateFormat formatFor_dMMMyy = new SimpleDateFormat("d MMM yy");

    public enum Segment { HEADER, TRANSACTIONS }


    @Override
    public StatementComposite parse(List<String> lines) throws ParseException {

        Statement statement = new Statement();
        List<Transaction> transactionLines = new ArrayList<Transaction>();
        Segment nextSegment = Segment.HEADER;
        boolean hasHeader = false;

        for (Iterator i = lines.iterator(); i.hasNext(); ) {
            String dataline = (String) i.next();

            if (dataline.contains("Transaction details Account")) {
                i.next();
                i.next();
                nextSegment = Segment.TRANSACTIONS;
                if (!hasHeader) {
                    if (statement.getFromDate() == null) {
                        throw new ParseException("No statement from-date was set");
                    } else if (statement.getToDate() == null) {
                        throw new ParseException("No statement to-date was set");
                    } else if (statement.getFrequency() == null) {
                        throw new ParseException("No statement frequency was set");
                    } else if (statement.getSourceReference() == null) {
                        throw new ParseException("No statement number was set");
                    } else if (statement.getAccountIdentifier() == null) {
                        throw new ParseException("No account identifier was set");
                    }
                    hasHeader = true;
                }

            } else if (!hasHeader) {
                try {
                    if (dataline.startsWith("Statement Number")) {
                        statement.setSourceReference(dataline.substring(dataline.indexOf("Statement Number") + 17, dataline.length()));
                        if (StringUtils.isEmpty(statement.getSourceReference())) {
                            throw new ParseException("Missing statement number or source reference");
                        }
                    }
                    if (dataline.startsWith("Statement Frequency")) {
                        statement.setFrequency(StatementFrequency.valueOf(dataline.substring(20).toUpperCase()));
                    }
                    if (dataline.startsWith("Statement Period")) {
                        statement.setFromDate(formatFor_dMMMyy.parse(dataline.substring(16, dataline.indexOf(" to "))));
                        statement.setToDate(formatFor_dMMMyy.parse(dataline.substring(dataline.indexOf(" to ") + 4, dataline.length())));
                    }
                    if (dataline.startsWith("Tax Invoice")) {
                        i.next(); // skip a line
                        statement.setAccountIdentifier((String) i.next());
                    }
                } catch (Exception e) {
                    System.out.println(dataline);
                    throw new ParseException("Unable to parse statement header", e);
                }

            } else if (dataline.startsWith("Closing balance")) {
                nextSegment = Segment.HEADER;

            } else if (nextSegment == Segment.TRANSACTIONS) {
                if (!dataline.equalsIgnoreCase("Debits") && !dataline.equalsIgnoreCase("Credits")) {
                    try {
                        String[] values = dataline.split(" ");
                        String txDateStr = values[0] + " " + values[1] + " " + values[2];
                        String amountStr = values[values.length - 1].replaceAll(",", "");
                        String description = dataline.substring(txDateStr.length() + 1, dataline.length() - amountStr.length() - 1);
                        double amount = Double.parseDouble(amountStr);
                        Date txDate = formatFor_dMMMyy.parse(txDateStr);
                        //System.out.println(txDate + " => " + amount + " : " + description);
                        transactionLines.add(new Transaction(txDate, description, amount));
                    } catch (Exception e) {
                        System.out.println(dataline);
                        throw new ParseException("Unable to parse transaction", e);
                    }
                }
            }
        }
        return new StatementComposite(statement, transactionLines.toArray(new Transaction[transactionLines.size()]));
    }
}
