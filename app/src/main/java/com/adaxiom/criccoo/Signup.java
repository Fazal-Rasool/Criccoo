package com.adaxiom.criccoo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.adaxiom.manager.DownloaderManager;
import com.adaxiom.model.response.RM_SignUp;
import com.adaxiom.utils.Utils;
import com.pixplicity.easyprefs.library.Prefs;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

import static com.adaxiom.utils.Constants.PREF_FCM_TOKEN;

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
    @BindView(R.id.avLoading)
    LinearLayout avLoading;

    private Subscription getSignUpSubscription;


    public static void startActivity(Context context) {
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
        if (Utils.isNetworkAvailable(Signup.this))
            API_SignUp();
        else
            Toast.makeText(Signup.this, R.string.internet_connectivity_msg, Toast.LENGTH_SHORT).show();
    }

    public void API_SignUp() {


        String name = etNameSignup.getText().toString().trim();
        String uName = etNameSignup.getText().toString().trim();
        String email = etEmailSignup.getText().toString().trim();
        String password = etPasswordSignup.getText().toString().trim();
        String confirmPassword = etConfirmPasswordSignup.getText().toString().trim();
        String fcmToken = Prefs.getString(PREF_FCM_TOKEN, "");
        String city = "Lahore";

        if (name.equalsIgnoreCase("") ||
                email.equalsIgnoreCase("") ||
                password.equalsIgnoreCase("") ||
                confirmPassword.equalsIgnoreCase("")
        ) {
            Toast.makeText(this, "Please fill all fields first", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!Utils.isEmailValid(email)){
            Toast.makeText(this, "Please enter correct email adress", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!password.equalsIgnoreCase(confirmPassword)) {
            Toast.makeText(this, "Password not match", Toast.LENGTH_SHORT).show();
            return;
        }

//        if (getSignUpSubscription != null) {
//            return;
//        }

        avLoading.setVisibility(View.VISIBLE);

        getSignUpSubscription = DownloaderManager.getGeneralDownloader().
                API_SignUp(name, uName, email, password, fcmToken, city,"C")
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
                                avLoading.setVisibility(View.GONE);
//                                Login.startActivity(Signup.this);
                                Toast.makeText(Signup.this, e.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(RM_SignUp model) {
                        updateUi(model);
                    }
                });

    }


    public void updateUi(final RM_SignUp model) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                avLoading.setVisibility(View.GONE);
                String msg = model.message;
                Toast.makeText(Signup.this, msg, Toast.LENGTH_LONG).show();
                if (!model.error) {
//                    Login.startActivity(Signup.this);
                    Signup.this.finish();
                }
//                else Login.startActivity(Signup.this);
            }
        });
    }


}
