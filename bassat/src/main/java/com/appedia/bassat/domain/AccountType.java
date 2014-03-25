package com.appedia.bassat.domain;

/**
 * Created with IntelliJ IDEA.
 * User: muz
 * Date: 3/21/14
 * Time: 2:46 PM
 * To change this template use File | Settings | File Templates.
 */
public enum AccountType {

    TRANSACTIONAL("T"),
    CARD("C"),
    LOAN("L");

    private String type;

    private AccountType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
