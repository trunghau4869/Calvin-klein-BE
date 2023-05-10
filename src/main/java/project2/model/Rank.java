package project2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class Rank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rank")
    private Long idRank;

    @Column(name = "name_rank")
    private String nameRank;

    @OneToMany(mappedBy = "rank")
    @JsonBackReference(value = "rank_member")
    private List<Member> member;

    public Rank() {
    }

    public Rank(Long idRank, String nameRank, List<Member> member) {
        this.idRank = idRank;
        this.nameRank = nameRank;
        this.member = member;
    }

    public Long getIdRank() {
        return idRank;
    }

    public void setIdRank(Long idRank) {
        this.idRank = idRank;
    }

    public String getNameRank() {
        return nameRank;
    }

    public void setNameRank(String nameRank) {
        this.nameRank = nameRank;
    }

    public List<Member> getMember() {
        return member;
    }

    public void setMember(List<Member> member) {
        this.member = member;
    }
}
