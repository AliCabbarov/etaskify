package etaskify.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.CascadeType.*;

@Entity()
@Getter
@Setter
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String phone;
    private String address;
    @OneToOne(cascade = {PERSIST, REMOVE})
    private TableDetail tableDetail;

    public Organization(String name, String phone, String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.tableDetail = TableDetail.of();
    }

    public Organization() {
        this.tableDetail = TableDetail.of();
    }
}
