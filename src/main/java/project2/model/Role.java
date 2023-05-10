package project2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Long idRole;

    @Column(name = "name_role")
    private String nameRole;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Account> accounts;

    public Role() {
    }

    public Role(String name) {
        this.nameRole = name;
    }

    public Role(Long idRole, String nameRole) {
        this.idRole = idRole;
        this.nameRole = nameRole;
    }

    public Role(Long idRole, String nameRole, Set<Account> accounts) {
        this.idRole = idRole;
        this.nameRole = nameRole;
        this.accounts = accounts;
    }

    public Long getIdRole() {
        return idRole;
    }

    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }

    public String getNameRole() {
        return nameRole;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
}
