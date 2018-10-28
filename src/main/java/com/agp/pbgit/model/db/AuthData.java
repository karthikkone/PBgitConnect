package com.agp.pbgit.model.db;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AuthData {

	//exclude Id field from JSON serialization
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Expose @SerializedName("access_token")
    private String accessToken;

	@Expose @SerializedName("scope")
	private String scope;

	@Expose @SerializedName("token_type")
	private String tokenType;
	
    public AuthData() {

    }

    public AuthData(String accessToken, String scope, String tokenType) {
        this.accessToken = accessToken;
        this.scope = scope;
		this.tokenType = tokenType;
    }

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String authToken) {
		this.accessToken = authToken;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	@Override
	public String toString() {
		return "AuthData [accessToken=" + accessToken + ", scope=" + scope + ", tokenType=" + tokenType + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accessToken == null) ? 0 : accessToken.hashCode());
		result = prime * result + ((scope == null) ? 0 : scope.hashCode());
		result = prime * result + ((tokenType == null) ? 0 : tokenType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuthData other = (AuthData) obj;
		if (accessToken == null) {
			if (other.accessToken != null)
				return false;
		} else if (!accessToken.equals(other.accessToken))
			return false;
		if (scope == null) {
			if (other.scope != null)
				return false;
		} else if (!scope.equals(other.scope))
			return false;
		if (tokenType == null) {
			if (other.tokenType != null)
				return false;
		} else if (!tokenType.equals(other.tokenType))
			return false;
		return true;
	}
    
	

}
