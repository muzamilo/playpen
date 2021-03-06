package com.appedia.bassat.job.statementio;

import com.appedia.bassat.domain.*;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author muz
 */
public class TransactionAccountStatementParser implements StatementParser {

    private static final String HEADER_START_LINE = "## These fees are inclusive of VAT";
    private static final String TRANSACTION_START_LINE = "BALANCE BROUGHT FORWARD";

    private static final SimpleDateFormat formatFor_ddMMMMMyyyy = new SimpleDateFormat("dd MMMMM yyyy");
    private static final SimpleDateFormat formatFor_MMddyyyy = new SimpleDateFormat("MM dd yyyy");

    public enum Segment { HEADER, TRANSACTIONS }

    @Override
    public StatementComposite parse(List<String> lines) throws ParseException {

        Statement statement = new Statement();
        List<Transaction> transactionLines = new ArrayList<Transaction>();
        Segment nextSegment = Segment.HEADER;
        boolean hasHeader = false;
        Date lastTxDate = null;
        int txYear = 0;

        for (Iterator i = lines.iterator(); i.hasNext(); ) {
            String dataline = (String) i.next();

            if (dataline.contains(TRANSACTION_START_LINE)) {
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
                    Calendar cal = GregorianCalendar.getInstance();
                    cal.setTime(statement.getFromDate());
                    txYear = cal.get(Calendar.YEAR);
                    hasHeader = true;
                }

            } else if (!hasHeader) {
                try {
                    if (dataline.contains("Statement No")) {
                        statement.setSourceReference(dataline.substring(dataline.indexOf("Statement No") + 13, dataline.length()));
                        if (StringUtils.isEmpty(statement.getSourceReference())) {
                            throw new ParseException("Missing statement number or source reference");
                        }
                    }
                    if (dataline.startsWith("Statement Frequency")) {
                        statement.setFrequency(StatementFrequency.valueOf(dataline.substring(20).toUpperCase()));
                    }
                    if (dataline.startsWith("Statement from")) {
                        statement.setFromDate(formatFor_ddMMMMMyyyy.parse(dataline.substring(14, dataline.indexOf(" to "))));
                        statement.setToDate(formatFor_ddMMMMMyyyy.parse(dataline.substring(dataline.indexOf(" to ") + 4, dataline.length())));
                    }
                    if (dataline.contains("Account Number")) {
                        statement.setAccountIdentifier(dataline.substring(dataline.indexOf("Account Number") + 15).replaceAll("\\s+", "").trim());
                        if (statement.getAccountIdentifier() == null || !StringUtils.isNumeric(statement.getAccountIdentifier())) {
                            throw new ParseException("Invalid account number " + statement.getAccountIdentifier());
                        }
                    }
                } catch (ParseException e) {
                    System.out.println(dataline);
                    throw e;
                } catch (Exception e) {
                    System.out.println(dataline);
                    throw new ParseException("Unable to parse statement header", e);
                }

            } else if (dataline.startsWith(HEADER_START_LINE)) {
                nextSegment = Segment.HEADER;

            } else if (nextSegment == Segment.TRANSACTIONS) {
                try {
                    String description = dataline + " " + i.next();
                    String[] amountDateBalance = ((String) i.next()).split(" ");
                    int n = (amountDateBalance[0].equals("##") ? 1 : 0);
                    String amountStr = amountDateBalance[n].replaceAll("\\.", " ").replaceAll(",", ".").replaceAll("\\s+", "");
                    double amount;
                    if (amountStr.endsWith("-")) {
                        amount = Double.parseDouble(amountStr.substring(0, amountStr.length() - 1));
                    } else {
                        amount = Double.parseDouble(amountStr);
                    }
                    String txDayMonth = amountDateBalance[++n] + " " + amountDateBalance[++n];
                    Date txDate = formatFor_MMddyyyy.parse(txDayMonth + " " + txYear);
                    if (lastTxDate != null && lastTxDate.getTime() > txDate.getTime()) {
                        txYear = txYear + 1;
                        txDate = formatFor_MMddyyyy.parse(txDayMonth + " " + txYear);
                    }
                    lastTxDate = txDate;
                    transactionLines.add(new Transaction(AccountType.TRANSACTIONAL, txDate, description, description.toUpperCase(), amount));
                } catch (Exception e) {
                    System.out.println(dataline);
                    throw new ParseException("Unable to parse transaction", e);
                }
            }
        }
        return new StatementComposite(statement, transactionLines.toArray(new Transaction[transactionLines.size()]));


    }
}
