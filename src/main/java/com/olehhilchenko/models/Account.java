package com.olehhilchenko.models;

import javax.persistence.*;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "account_data")
    private String accountData;

    @OneToOne(mappedBy = "account")
    private Developer developer;

    public Account() {
    }

    public Account(String accountData, Developer developer) {
        this.accountData = accountData;
        this.developer = developer;
    }

    public Developer getDeveloper() {

        return developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    public Account(long id, String accountData) {
        this.id = id;
        this.accountData = accountData;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccountData() {
        return accountData;
    }

    public void setAccountData(String accountData) {
        this.accountData = accountData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;

        Account account = (Account) o;

        if (getId() != account.getId()) return false;
        return getAccountData() != null ? getAccountData().equals(account.getAccountData()) : account.getAccountData() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getAccountData() != null ? getAccountData().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountData='" + accountData + '\'' +
                '}';
    }
}
