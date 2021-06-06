package pl.polsl.s15.library.domain.user;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.polsl.s15.library.domain.user.account.AccountCredentials;
import pl.polsl.s15.library.domain.user.account.AccountPermissions;
import pl.polsl.s15.library.dtos.users.UserDTO;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
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

    public static User of(UserDTO userDTO) {
        AccountCredentials credentials = AccountCredentials.of(userDTO.getAccountCredentialsDTO());
        AccountPermissions permissions = AccountPermissions.of(userDTO.getAccountPermissionsDTO());
        return User.builder()
                .id(userDTO.getId())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .photoUrl(userDTO.getPhotoUrl())
                .credentials(credentials)
                .permissions(permissions)
                .build();
    }

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
}
