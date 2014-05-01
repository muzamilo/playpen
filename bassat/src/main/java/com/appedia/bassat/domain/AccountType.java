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

    /**
     *
     * @param type
     */
    private AccountType(int type) {
        this.type = type;
    }

    /**
     *
     * @return
     */
    public int getType() {
        return type;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return name();
    }
}
