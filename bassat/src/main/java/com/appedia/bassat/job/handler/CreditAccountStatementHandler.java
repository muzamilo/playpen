package com.appedia.bassat.job.handler;

import com.appedia.bassat.domain.Statement;
import com.appedia.bassat.domain.StatementFrequency;
import com.appedia.bassat.domain.StatementHeader;
import com.appedia.bassat.domain.Transaction;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author Muz Omar
 */
public class CreditAccountStatementHandler extends AbstractAccountStatementHandler implements AccountStatementHandler {

    private static SimpleDateFormat formatFor_ddMMMMMyyyy = new SimpleDateFormat("dd MMMMM yyyy");
    private static SimpleDateFormat formatFor_MMddyyyy = new SimpleDateFormat("MM dd yyyy");

    public enum Segment { HEADER, TRANSACTIONS }


    @Override
    public Statement parseStatement(File statementFile) throws Exception {

        List<String> data = loadFile(statementFile);

        StatementHeader statementHeader = new StatementHeader();
        List<Transaction> transactionLines = new ArrayList<Transaction>();
        Segment nextSegment = Segment.HEADER;
        boolean hasHeader = false;
        Date lastTxDate = null;
        int txYear = 0;
        for (Iterator i = data.iterator(); i.hasNext(); ) {
            String dataline = (String) i.next();

            if (dataline.contains("BALANCE BROUGHT FORWARD")) {
                nextSegment = Segment.TRANSACTIONS;
                if (!hasHeader) {
                    if (statementHeader.getFromDate() == null) {
                        throw new IllegalStateException("No statement from-date was set");
                    }
                    Calendar cal = GregorianCalendar.getInstance();
                    cal.setTime(statementHeader.getFromDate());
                    txYear = cal.get(Calendar.YEAR);
                    hasHeader = true;
                    System.out.println(statementHeader.toString());
                }

            } else if (!hasHeader && nextSegment == Segment.HEADER) {
                try {
                    if (dataline.contains("Statement No")) {
                        statementHeader.setSourceReference(dataline.substring(dataline.indexOf("Statement No") + 13, dataline.length()));
                    }
                    if (dataline.startsWith("Statement Frequency")) {
                        statementHeader.setFrequency(StatementFrequency.valueOf(dataline.substring(20).toUpperCase()));
                    }
                    if (dataline.startsWith("Statement from")) {
                        statementHeader.setFromDate(formatFor_ddMMMMMyyyy.parse(dataline.substring(14, dataline.indexOf(" to "))));
                        statementHeader.setToDate(formatFor_ddMMMMMyyyy.parse(dataline.substring(dataline.indexOf(" to ") + 4, dataline.length())));
                    }
                    if (dataline.contains("Account Number")) {
                        statementHeader.setAccountNo(Long.parseLong(dataline.substring(dataline.indexOf("Account Number") + 15).replaceAll("\\s+", "")));
                    }
                } catch (Exception e) {
                    System.out.println("Problem parsing " + nextSegment + " : " + dataline);
                    e.printStackTrace();
                }

            } else if (dataline.startsWith("## These fees are inclusive of VAT")) {
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
                    System.out.println(txDate + " => " + amount + " : " + description);
                    transactionLines.add(new Transaction(txDate, description, amount));
                } catch (Exception e) {
                    System.out.println("Problem parsing " + nextSegment + " : " + dataline);
                    e.printStackTrace();
                }
            }
        }
        return new Statement(statementHeader, transactionLines.toArray(new Transaction[transactionLines.size()]));
    }
}
