package edu.whu.ScrumPM.dao.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue
    private Long userId;

    @Column(nullable = false, unique = true,length = 32)
    private String userName;

    @Column(nullable = false,length = 32)
    private String userPassword;
}
