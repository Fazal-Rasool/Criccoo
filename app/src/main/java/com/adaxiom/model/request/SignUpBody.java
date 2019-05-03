package com.adaxiom.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpBody {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("fcm_token")
    @Expose
    private String fcm_token;
    @SerializedName("city")
    @Expose
    private String city;

    public SignUpBody(String name, String username, String email, String password, String fcm_token, String city) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.fcm_token = fcm_token;
        this.city = city;
    }
}
