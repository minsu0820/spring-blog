package com.cos.blog.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)//프로젝트에서 연결된 디비의 넘버링 전략을 따라감
    private int id;

    @Column(nullable = false, unique = true, length = 20)
    private String username;
    @Column(nullable = false, length = 200)
    private String password;
    @Column(nullable = true, length = 50)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;


    @OneToMany(mappedBy = "users",fetch = FetchType.EAGER)
    private List<Board> boards = new ArrayList<>();


    @CreationTimestamp
    private Timestamp createDate;
}


