package com.appedia.bassat.domain;

/**
 * Created with IntelliJ IDEA.
 * User: muz
 * Date: 3/21/14
 * Time: 2:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class User {

    private String idNumber;
    private String title;
    private String firstname;
    private String surname;
    private String password;
    private String notificationEmail;
    private String importSourceEmail;

    public String getIdNumber() {

        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNotificationEmail() {
        return notificationEmail;
    }

    public void setNotificationEmail(String notificationEmail) {
        this.notificationEmail = notificationEmail;
    }

    public String getImportSourceEmail() {
        return importSourceEmail;
    }

    public void setImportSourceEmail(String importSourceEmail) {
        this.importSourceEmail = importSourceEmail;
    }
}
