package com.example.dolang.Model;

import com.google.gson.annotations.SerializedName;

public class ModelUser {

    @SerializedName("id") private int id;
    @SerializedName("name") private String name;
    @SerializedName("email") private String email;
    @SerializedName("password") private String password;
    @SerializedName("api_token") private String api_token;


    public ModelUser() {
    }

    public ModelUser(int id, String name, String email, String password, String api_token) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.api_token = api_token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }
}
