package pl.polsl.s15.library.domain.user;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.polsl.s15.library.domain.user.account.AccountCredentials;
import pl.polsl.s15.library.domain.user.account.AccountPermissions;
import pl.polsl.s15.library.domain.user.account.roles.Role;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "app_users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "photo_url")
    private String photoUrl;

    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private AccountCredentials credentials;

    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private AccountPermissions permissions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.credentials.getPassword();
    }

    @Override
    public String getUsername() {
        return this.credentials.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User(User user)
    {
        this.credentials = user.credentials;
        this.id = user.id;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.permissions = user.permissions;
        this.photoUrl = user.photoUrl;
    }

    public void overrideCurrentRoles(Set<Role> roles) {
        this.permissions.overrideRoles(roles);
    }

    public void addNewRole(Role role) {
        this.permissions.addNewRoleIfNotPresent(role);
    }

    public void deleteRole(Role role) {
        this.permissions.deleteRoleIfPresent(role);
    }
}
