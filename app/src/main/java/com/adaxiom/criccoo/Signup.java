package com.adaxiom.criccoo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adaxiom.manager.DownloaderManager;
import com.adaxiom.model.request.SignUpBody;
import com.adaxiom.model.response.RM_SignUp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

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

    private Subscription getSignUpSubscription;
    private SignUpBody signUpBody;


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
        API_SignUp();
    }

    public void API_SignUp(){

        signUpBody = new SignUpBody("fazal","Fazal",
                "fazal@gmail.com","12345","ddd","lahore");



        if (getSignUpSubscription != null) {
            return;
        }

        getSignUpSubscription = DownloaderManager.getGeneralDownloader().API_SignUp(signUpBody)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<RM_SignUp>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(final Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Signup.this, e.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(RM_SignUp modelJobList) {

                        updateUi(modelJobList);
                    }
                });

    }


    public void updateUi(final RM_SignUp model){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String msg = model.message;
                Toast.makeText(Signup.this, msg, Toast.LENGTH_LONG).show();
            }
        });
        
    }


}
