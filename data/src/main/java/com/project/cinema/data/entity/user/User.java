package com.project.cinema.data.entity.user;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "USER")
@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, name = "user_name")
    private String userName;
    private String password;
    private Boolean active;
    private String roles;
}
