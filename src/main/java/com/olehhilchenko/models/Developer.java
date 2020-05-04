package com.olehhilchenko.models;

import javax.persistence.*;
import java.util.Set;
@Entity
@Table(name = "developers")
public class Developer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "developer_account",
            joinColumns =
                    {@JoinColumn(name = "developer_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "account_id", referencedColumnName = "id")})
    private Account account;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "developer_skill",
            joinColumns =
                    {@JoinColumn(name = "developer_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "skill_id", referencedColumnName = "id")})
    private Set<Skill> skills;

    public Developer() {
    }

    public Developer(long id, String firstName, String lastName, Account account, Set<Skill> skills) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.account = account;
        this.skills = skills;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Developer)) return false;

        Developer developer = (Developer) o;

        if (getId() != developer.getId()) return false;
        if (getFirstName() != null ? !getFirstName().equals(developer.getFirstName()) : developer.getFirstName() != null)
            return false;
        if (getLastName() != null ? !getLastName().equals(developer.getLastName()) : developer.getLastName() != null)
            return false;
        if (getAccount() != null ? !getAccount().equals(developer.getAccount()) : developer.getAccount() != null)
            return false;
        return getSkills() != null ? getSkills().equals(developer.getSkills()) : developer.getSkills() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getAccount() != null ? getAccount().hashCode() : 0);
        result = 31 * result + (getSkills() != null ? getSkills().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", account=" + account +
                ", skills=" + skills +
                '}';
    }
}
