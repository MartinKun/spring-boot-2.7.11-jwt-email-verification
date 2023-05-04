package com.example.confirmemailregistration.security.model.entity;

import com.example.confirmemailregistration.security.model.enums.RoleEnum;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private RoleEnum name;

}
