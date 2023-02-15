package com.user.service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    private String id;
    @Column(length = 20)
    private String name;
    private String email;
    private String about;

    @Transient
    private List<Rating> ratings=new ArrayList<>();
}
