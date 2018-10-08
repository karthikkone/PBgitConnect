package com.agp.pbgit.model.db;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AuthData {

    @Id
    private Long Id;
    private String oauthToken;

    public AuthData() {

    }

    public AuthData(Long id, String oauthToken) {
        this.Id = id;
        this.oauthToken = oauthToken;
    }

    @Override
    public String toString() {
        return "AuthData{"+"id:"+Id+","+"oauthToken:"+oauthToken+"}";
    }

}
