package com.adaxiom.criccoo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Signup extends AppCompatActivity {

    @BindView(R.id.etName_Signup)
    EditText etNameSignup;
    @BindView(R.id.etEmail_Signup)
    EditText etEmailSignup;
    @BindView(R.id.etPassword_Signup)
    EditText etPasswordSignup;
    @BindView(R.id.etConfirmPassword_Signup)
    EditText etConfirmPasswordSignup;
    @BindView(R.id.btnLogin_Signup)
    Button btnLoginSignup;


    public static void startSignUpActivity(Context context) {
        Intent intent = new Intent(context, Signup.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.btnLogin_Signup)
    public void onViewClicked() {
    }
}
