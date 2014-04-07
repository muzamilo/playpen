package com.appedia.bassat.domain;

import java.io.Serializable;

/**
 *
 * @author Muz Omar
 */
public class StatementComposite implements Serializable {

    private Statement statement;
    private Transaction[] transactions;

    public StatementComposite(Statement statement, Transaction[] transactions) {
        this.statement = statement;
        this.transactions = transactions;
    }

    public Statement getStatement() {
        return statement;
    }

    public Transaction[] getTransactions() {
        return transactions;
    }

}
