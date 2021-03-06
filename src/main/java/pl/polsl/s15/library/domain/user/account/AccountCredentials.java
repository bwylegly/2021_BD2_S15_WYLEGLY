package pl.polsl.s15.library.domain.user.account;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "credentials")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountCredentials {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String password;

    @Column(unique = true)
    private String emailAddress;
}
