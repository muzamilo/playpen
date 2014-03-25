package com.appedia.bassat.domain;

/**
 * Created with IntelliJ IDEA.
 * User: muz
 * Date: 3/21/14
 * Time: 2:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class Account {

    private AccountType type;
    private long accountNumber;

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }
}
