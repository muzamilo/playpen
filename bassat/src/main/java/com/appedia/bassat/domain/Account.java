package com.appedia.bassat.domain;

import java.io.Serializable;

/**
 *
 * @author Muz Omar
 */
public class Account implements Serializable {

    private long accountId;
    private long userId;
    private AccountType type;
    private String identifier;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (accountId != account.accountId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (accountId ^ (accountId >>> 32));
    }
}
