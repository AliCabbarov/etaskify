package etaskify.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;

@Entity()
@Getter
@Setter
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    private LocalDate deadLine;
    private boolean status;
    @ManyToMany
    private List<User> users;
    @OneToOne(cascade = {PERSIST, REMOVE})
    private TableDetail tableDetail;
    @ManyToOne
    private Organization organization;

    public Task(List<User> users,Organization organization) {
        this.users = users;
        this.tableDetail = TableDetail.of();
        this.status = true;
        this.organization = organization;
    }
}
