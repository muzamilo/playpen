package com.appedia.bassat.domain;

import java.io.Serializable;

/**
 *
 * @author Muz Omar
 */
public class StatementComposite implements Serializable {

    private Statement statement;
    private Transaction[] transactions;

    /**
     *
     * @param statement
     * @param transactions
     */
    public StatementComposite(Statement statement, Transaction[] transactions) {
        this.statement = statement;
        this.transactions = transactions;
    }

    /**
     *
     * @return
     */
    public Statement getStatement() {
        return statement;
    }

    /**
     *
     * @return
     */
    public Transaction[] getTransactions() {
        return transactions;
    }

}
