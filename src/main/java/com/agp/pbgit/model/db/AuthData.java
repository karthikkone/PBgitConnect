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

    /**
     * @return the oauthToken
     */
    public String getOauthToken() {
        return oauthToken;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return Id;
    }

    @Override
    public String toString() {
        return "AuthData{"+"id:"+Id+","+"oauthToken:"+oauthToken+"}";
    }

}
