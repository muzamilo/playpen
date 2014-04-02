package com.appedia.bassat.domain;

/**
 *
 * @author Muz Omar
 */
public enum AccountType {

    TRANSACTIONAL(1),
    CARD(2),
    LOAN(3);

    private int type;

    private AccountType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

}
