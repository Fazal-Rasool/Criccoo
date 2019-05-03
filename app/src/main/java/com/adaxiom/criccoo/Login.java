package com.adaxiom.criccoo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.adaxiom.utils.Constants.PREF_IS_LOGIN;

public class Login extends AppCompatActivity {


    @BindView(R.id.etEmail_Login)
    EditText etEmailLogin;
    @BindView(R.id.etPassword_Login)
    EditText etPasswordLogin;
    @BindView(R.id.btnLogin_login)
    Button btnLoginLogin;
    @BindView(R.id.btnLoginFB_login)
    Button btnLoginFBLogin;
    @BindView(R.id.btnLoginGoogle_login)
    Button btnLoginGoogleLogin;
    @BindView(R.id.tvGoToSignUp)
    TextView tvGoToSignUp;
    @BindView(R.id.login_button)
    LoginButton loginButton;
    @BindView(R.id.signInGoogle)
    SignInButton signInGoogle;


    private CallbackManager callbackManager;
    private static final String EMAIL = "email";

    private GoogleSignInClient mGoogleSigninClient;
    private int RQ_SIGN_IN = 0;



    public static void startLoginActivity(Context context) {
        Intent intent = new Intent(context, Login.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        initialization();

    }


    public void initialization(){


        setTextInString();



        //Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSigninClient = GoogleSignIn.getClient(this, gso);


        //Facebook Sign in
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });
    }


    @Override
    protected void onResume() {

        //For Google already login or not
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            Toast.makeText(Login.this, "Google Already sign in", Toast.LENGTH_SHORT).show();
//            displayGoogleSignInData(account);
        }

        //For Facebook if already login or not
        if (AccessToken.getCurrentAccessToken() != null) {
            loadFBUserProfile(AccessToken.getCurrentAccessToken());
        }

        super.onResume();
    }

    private void googleSignIn() {
        Intent signInIntent = mGoogleSigninClient.getSignInIntent();
        startActivityForResult(signInIntent, RQ_SIGN_IN);
    }

    private void displayGoogleSignInData(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                String name = account.getDisplayName();
                String givenName = account.getGivenName();
                String familyName = account.getFamilyName();
                String email = account.getEmail();
                String id = account.getId();
                Uri photoUrl = account.getPhotoUrl();

                callNewActivity();

//            tvNameGoogle.setText(name + " " + givenName + " " + familyName);
//            tvEmailGoogle.setText(email + " " + id);

            }

        } catch (ApiException e) {
            Log.e("Google Exception ", e.getStatusMessage());
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RQ_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            displayGoogleSignInData(task);
        }
    }


    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken == null) {
//                tvName.setText("");
//                tvEmail.setText("");
//                ivProfile.setImageResource(0);
                Toast.makeText(Login.this, "User Logged out", Toast.LENGTH_LONG).show();
            } else
                loadFBUserProfile(currentAccessToken);
        }
    };


    private void loadFBUserProfile(AccessToken newAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
//                    String email = object.getString("email");
                    String id = object.getString("id");
                    String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";

//                    tvEmail.setText("");
//                    tvName.setText(first_name + " " + last_name);
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.dontAnimate();

//                    Glide.with(Login.this).load(image_url).into(ivProfile);


                    if (!first_name.equalsIgnoreCase("")) {
                        Toast.makeText(Login.this, "Login Successfully in Facebook", Toast.LENGTH_SHORT).show();
                        callNewActivity();
//                        startActivity(new Intent(Login.this, MainActivity.class));
                    } else {
                        Toast.makeText(Login.this, "Login failed", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,first_name,last_name,gender");
        request.setParameters(parameters);
        request.executeAsync();

    }



    public void setTextInString(){
        String text = "New to Criccoo? SignUp Now";
        SpannableString spannableString = new SpannableString(text);
        StyleSpan boldItalicSpan = new StyleSpan(Typeface.BOLD_ITALIC);
        spannableString.setSpan(boldItalicSpan,16,26, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvGoToSignUp.setText(spannableString);
    }


    @OnClick({R.id.btnLogin_login, R.id.btnLoginFB_login, R.id.btnLoginGoogle_login, R.id.tvGoToSignUp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLogin_login:
                break;
            case R.id.btnLoginFB_login:
                loginButton.performClick();
                break;
            case R.id.btnLoginGoogle_login:
                googleSignIn();
                break;
            case R.id.tvGoToSignUp:
                Signup.startSignUpActivity(Login.this);
                this.finish();
                break;
        }
    }


    public void callNewActivity(){
        Prefs.putInt(PREF_IS_LOGIN, 1);
        MainActivity.startMainActivity(Login.this);
        this.finish();
    }


}
