package com.salamlahore.criccoo;

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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.salamlahore.manager.DownloaderManager;
import com.salamlahore.model.response.RM_Login;
import com.salamlahore.model.response.RM_SignUpOther;
import com.salamlahore.utils.Utils;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
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
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

import static com.salamlahore.utils.Constants.PREF_FCM_TOKEN;
import static com.salamlahore.utils.Constants.PREF_IS_LOGIN;
import static com.salamlahore.utils.Constants.PREF_PROFILE_IMAGE;
import static com.salamlahore.utils.Constants.PREF_USER_EMAIL;
import static com.salamlahore.utils.Constants.PREF_USER_ID;
import static com.salamlahore.utils.Constants.PREF_USER_NAME;

public class Login extends AppCompatActivity {


    @BindView(R.id.etEmail_Login)
    EditText etEmailLogin;
    @BindView(R.id.etPassword_Login)
    EditText etPasswordLogin;
    @BindView(R.id.btnLogin_login)
    Button btnLoginLogin;
    @BindView(R.id.btnLoginFB_login)
    ImageView btnLoginFBLogin;
    @BindView(R.id.btnLoginGoogle_login)
    ImageView btnLoginGoogleLogin;
    @BindView(R.id.tvGoToSignUp)
    TextView tvGoToSignUp;
    @BindView(R.id.login_button)
    LoginButton loginButton;
    @BindView(R.id.signInGoogle)
    SignInButton signInGoogle;
    @BindView(R.id.avLoading)
    LinearLayout avLoading;


    private Subscription getLoginSubscription;

    private CallbackManager callbackManager;
    private static final String EMAIL = "email";

    private static GoogleSignInClient mGoogleSigninClient;
    private int RQ_SIGN_IN = 0;
    private int RQ_GOOGLE_SIGN_IN = 1;

    private static Context context;


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, Login.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);



        context = this;
        Utils.hideSoftKeyboard(this);
        initialization();

    }


    public void initialization() {

//        setTextInString();


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


//    @Override
//    protected void onResume() {
//
//        //For Google already login or not
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        if (account != null) {
////            Toast.makeText(Login.this, "Google Already sign in", Toast.LENGTH_SHORT).show();
////            displayGoogleSignInData(account);
//        }
//
//        //For Facebook if already login or not
//        if (AccessToken.getCurrentAccessToken() != null) {
////            loadFBUserProfile(AccessToken.getCurrentAccessToken());
//        }
//
//        super.onResume();
//    }

    private void googleSignIn() {
        Intent signInIntent = mGoogleSigninClient.getSignInIntent();
        startActivityForResult(signInIntent, RQ_SIGN_IN);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RQ_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            API_Login("Google User","","G");
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    String name = account.getDisplayName();
                    String id = account.getId();
//                String givenName = account.getGivenName();
//                String familyName = account.getFamilyName();
//                String email = account.getEmail();

                Uri photoUrl = account.getPhotoUrl();
                Prefs.putString(PREF_PROFILE_IMAGE, photoUrl+"");

                    if (!name.equalsIgnoreCase(""))
                        API_LoginWithOther(name, id, "G");
                    else
                        Toast.makeText(Login.this, "Error while login with Google!!!", Toast.LENGTH_SHORT).show();
                }

            } catch (ApiException e) {
                Log.e("Google Exception ", e.getMessage());
            }
        }

//        if(RQ_GOOGLE_SIGN_IN == 1) {

//        }






    }


    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken == null) {
//                tvName.setText("");
//                tvEmail.setText("");
//                ivProfile.setImageResource(0);
                Toast.makeText(Login.this, "User Logged out", Toast.LENGTH_SHORT).show();
            } else
                displayFacebookSignInData(currentAccessToken);
        }
    };

    private void displayFacebookSignInData(AccessToken newAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    Profile profile = Profile.getCurrentProfile();
                    if (profile != null) {

                    }
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
//                    String email = object.getString("email");
                    String id = object.getString("id");
                    String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";
                    Prefs.putString(PREF_PROFILE_IMAGE, image_url);

//                    tvEmail.setText("");
//                    tvName.setText(first_name + " " + last_name);
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.dontAnimate();

//                    Glide.with(Login.this).load(image_url).into(ivProfile);


                    if (object != null) {
                        String userName = first_name + " " + last_name;
                        Log.e("FB", "Login with fb");
                        API_LoginWithOther(userName, id, "F");
                    } else {
                        Toast.makeText(Login.this, "Could not fetch data from Facebook!!!", Toast.LENGTH_SHORT).show();
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


    public void setTextInString() {
        String text = "New to Criccoo? Sign up Now";
        SpannableString spannableString = new SpannableString(text);
        StyleSpan boldItalicSpan = new StyleSpan(Typeface.BOLD);
        spannableString.setSpan(boldItalicSpan, 16, 27, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvGoToSignUp.setText(spannableString);
    }


    @OnClick({R.id.btnLogin_login, R.id.btnLoginFB_login, R.id.btnLoginGoogle_login, R.id.tvGoToSignUp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLogin_login:
                if (Utils.isNetworkAvailable(Login.this)) {
                    String userName = etEmailLogin.getText().toString();
                    String password = etPasswordLogin.getText().toString();
                    if (Utils.isEmailValid(userName))
                        API_Login(userName, password, "C");
                    else
                        Toast.makeText(this, "Please enter valid email address", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(Login.this, R.string.internet_connectivity_msg, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnLoginFB_login:
                if (Utils.isNetworkAvailable(Login.this)) {
                    RQ_GOOGLE_SIGN_IN=1;
                    loginButton.performClick();
                }
                else
                    Toast.makeText(Login.this, R.string.internet_connectivity_msg, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnLoginGoogle_login:
                if (Utils.isNetworkAvailable(Login.this)) {
                    RQ_GOOGLE_SIGN_IN=0;
                    googleSignIn();
                }
                else
                    Toast.makeText(Login.this, R.string.internet_connectivity_msg, Toast.LENGTH_SHORT).show();
                break;
            case R.id.tvGoToSignUp:
                Signup.startActivity(Login.this);
//                this.finish();
                break;
        }
    }


    public void callNewActivity(final int userid, final String userName, final String email) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Prefs.putInt(PREF_USER_ID, userid);
                Prefs.putString(PREF_USER_NAME, userName);
                Prefs.putString(PREF_USER_EMAIL, email);
                Prefs.putInt(PREF_IS_LOGIN, 1);
                MainActivity.startActivity(Login.this);
                Login.this.finish();
            }
        });


    }


    public void API_LoginWithOther(final String name, String userName, String from) {

        String fcmToken = Prefs.getString(PREF_FCM_TOKEN, "");
        String password = "123";
        String city = "Lahore";


        Utils.showHideLoaderView(avLoading, true);

//        if (getLoginSubscription != null) {
//            return;
//        }

        getLoginSubscription = DownloaderManager.getGeneralDownloader().API_SignUpOther(
                name,
                userName,
                password,
                fcmToken,
                city,
                from
        )
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<RM_SignUpOther>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(final Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Utils.showHideLoaderView(avLoading, false);
                                Toast.makeText(Login.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(final RM_SignUpOther model) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Utils.showHideLoaderView(avLoading, false);
                                if (model.error != true) {
                                    Toast.makeText(Login.this, model.message, Toast.LENGTH_SHORT).show();
                                    callNewActivity(model.userid, name, "");
                                } else
                                    Toast.makeText(Login.this, model.message,
                                            Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

    }


    public void API_Login(String userName, String password, String from) {

        String fcmToken = Prefs.getString(PREF_FCM_TOKEN, "");

        Utils.showHideLoaderView(avLoading, true);

//        if (getLoginSubscription != null) {
//            return;
//        }

        getLoginSubscription = DownloaderManager.getGeneralDownloader().API_LoginParam(userName, password, from, fcmToken)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<RM_Login>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(final Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Utils.showHideLoaderView(avLoading, false);
                                Toast.makeText(Login.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(final RM_Login model) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Utils.showHideLoaderView(avLoading, false);
                                if (model.error != true) {
                                    Toast.makeText(Login.this, model.message, Toast.LENGTH_SHORT).show();
                                    callNewActivity(model.userid, model.username, model.email);
                                } else
                                    Toast.makeText(Login.this, model.message,
                                            Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

    }



    public String generateRandomPassword() {
        Random r = new Random();
        return r.nextInt(999999 - 100000) + 100000 + "";
    }


    public static void logoutFacebook() {
        LoginManager.getInstance().logOut();
//        mGoogleSigninClient.signOut();

    }


//    public void hideSoftKeyboard() {
//        if(getCurrentFocus()!=null) {
//            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//        }
//    }


}
