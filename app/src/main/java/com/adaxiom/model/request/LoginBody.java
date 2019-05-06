package com.adaxiom.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginBody {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;

    public LoginBody(String name, String pass) {
        this.username = name;
        this.password = pass;
    }
}
