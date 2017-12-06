package com.example.nettyserver.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @Column(name = "userid")
    private Long userid;
    @Column(name = "username")
    private String username;
    @Column(name = "loginpwd")
    private String loginpwd;

    public Long getId() {
        return this.userid;
    }

    public String getName() {
        return this.username;
    }

    public void setId(Long id) {
        this.userid = id;
    }

    public void setName(String name) {
        this.username = name;
    }

    public void setLoginpwd(String pwd){this.loginpwd = pwd;}

}
