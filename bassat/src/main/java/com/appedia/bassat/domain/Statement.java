package com.appedia.bassat.domain;

import java.io.Serializable;

/**
 *
 * @author Muz Omar
 */
public class Statement implements Serializable {

    private StatementHeader header;
    Transaction[] transactions;

    public Statement(StatementHeader header, Transaction[] transactions) {
        this.header = header;
        this.transactions = transactions;
    }

    public StatementHeader getHeader() {
        return header;
    }

    public Transaction[] getTransactions() {
        return transactions;
    }

}
