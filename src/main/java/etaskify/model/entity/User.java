package etaskify.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;

@Entity(name = "_user")
@Getter
@NoArgsConstructor
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String surname;
    @Column(unique = true)
    private String username;
    private String email;
    private String password;
    private boolean enabled;
    @ManyToOne
    private Role role;
    @ManyToOne
    private Organization organization;
    @OneToOne(cascade = {PERSIST, REMOVE})
    private TableDetail tableDetail;

    public User(String name, String surname, String username, String email, String password, Role role, Organization organization) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.organization = organization;
        this.tableDetail = TableDetail.of();
    }

    public User(Organization organization, Role role) {
        this.tableDetail = getTableDetail();
        this.enabled = false;
        this.organization = organization;
        this.role = role;
        this.tableDetail = TableDetail.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getName()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

}
