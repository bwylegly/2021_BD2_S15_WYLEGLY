package pl.polsl.s15.library.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "managers")
@NoArgsConstructor
public class Manager extends Employee {
    public Manager(User user) {
        super(user);
    }
}
