package com.mechamate.entity;

import com.mechamate.common.ApiToken;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "Tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId _id;
    private String tokenName;
    private ApiToken token;

    public Token(String tokenName, ApiToken token) {
        this.tokenName = tokenName;
        this.token = token;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public ApiToken getToken() {
        return token;
    }

    public void setToken(ApiToken token) {
        this.token = token;
    }
}
