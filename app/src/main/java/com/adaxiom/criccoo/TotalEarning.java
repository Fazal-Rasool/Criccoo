package com.adaxiom.criccoo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adaxiom.manager.DownloaderManager;
import com.adaxiom.model.response.RM_GetYourCash;
import com.adaxiom.model.response.RM_LeaderBoard;
import com.adaxiom.utils.Utils;
import com.pixplicity.easyprefs.library.Prefs;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import static com.adaxiom.utils.Constants.PREF_BLOCK_ID;
import static com.adaxiom.utils.Constants.PREF_INNING_ID;
import static com.adaxiom.utils.Constants.PREF_IS_LOGIN;
import static com.adaxiom.utils.Constants.PREF_MATCH_ID;
import static com.adaxiom.utils.Constants.PREF_PROFILE_IMAGE;
import static com.adaxiom.utils.Constants.PREF_USER_ID;

public class TotalEarning extends AppCompatActivity {

    @BindView(R.id.tvTotalCash)
    TextView tvTotalCash;
    @BindView(R.id.ivGetYourCash)
    View ivGetYourCash;
    @BindView(R.id.avLoading)
    LinearLayout avLoading;
    @BindView(R.id.etCnicTotalEarning)
    EditText etCnicTotalEarning;
    @BindView(R.id.etPhoneTotalEarning)
    EditText etPhoneTotalEarning;

    private int totalCash=0;
    private AlertDialog alert;


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, TotalEarning.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_total_earning);
        ButterKnife.bind(this);

        if (Utils.isNetworkAvailable(this))
            API_GetTotalEarning();
        else Toast.makeText(this, R.string.internet_connectivity_msg, Toast.LENGTH_SHORT).show();

    }


    @OnClick(R.id.ivGetYourCash)
    public void onViewClicked() {
        if(Utils.isNetworkAvailable(this)){
            if(validUserData())
                API_PostUserInfo();
            else
                Toast.makeText(this, "Your Cash must be 1000/-", Toast.LENGTH_LONG).show();
        }else
            Toast.makeText(this,R.string.internet_connectivity_msg,Toast.LENGTH_SHORT).show();
    }


    public void API_GetTotalEarning() {

        int user_id = Prefs.getInt(PREF_USER_ID, 0);
        avLoading.setVisibility(View.VISIBLE);

        DownloaderManager.getGeneralDownloader().
                API_LeaderBoard(user_id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<RM_LeaderBoard>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(final Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                avLoading.setVisibility(View.GONE);
                                Toast.makeText(TotalEarning.this, e.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(RM_LeaderBoard list) {
                        updateUi(list);
                    }
                });
    }


    public void updateUi(final RM_LeaderBoard mList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                avLoading.setVisibility(View.GONE);
                if (mList.user.size() != 0) {
                    String total_score = mList.user.get(0).total_score;
                    if (total_score != null || !total_score.equalsIgnoreCase("")) {
                        tvTotalCash.setText(total_score + "/-");
                        totalCash = Integer.parseInt(total_score);
                    }
                    else tvTotalCash.setText("0.0/-");
                }

            }
        });
    }



    public void API_PostUserInfo() {

        int user_id = Prefs.getInt(PREF_USER_ID, 0);
        String phone = etPhoneTotalEarning.getText().toString().trim();
        String cnic = etCnicTotalEarning.getText().toString().trim();
        avLoading.setVisibility(View.VISIBLE);

        DownloaderManager.getGeneralDownloader().
                API_GetYourCash(261, phone, cnic)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<RM_GetYourCash>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(final Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                avLoading.setVisibility(View.GONE);
                                Toast.makeText(TotalEarning.this, e.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(final RM_GetYourCash model) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                avLoading.setVisibility(View.GONE);
                                if(!model.error){
                                    AlertUserInfo();
                                }else{
                                    Toast.makeText(TotalEarning.this,model.message,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
    }





    public boolean validUserData(){
        String cnic = etCnicTotalEarning.getText().toString();
        String phone = etPhoneTotalEarning.getText().toString();
        boolean flagCnic=false, flagPhone=false;

        if(!cnic.equalsIgnoreCase("00000-0000000-0")){
            if(cnic.length() == 15){
                flagCnic=true;
            }else{
                flagCnic=false;
                Toast.makeText(this, "Invalid CNIC number", Toast.LENGTH_SHORT).show();
            }
        }else{
            flagCnic=false;
            Toast.makeText(this, "Invalid CNIC number", Toast.LENGTH_SHORT).show();
        }

        if(!phone.equalsIgnoreCase("0000-0000000")){
            if(phone.length() == 12){
                flagPhone=true;
            }else{
                flagPhone=false;
                Toast.makeText(this, "Invalid Phone number", Toast.LENGTH_SHORT).show();
            }
        }else{
            flagPhone=false;
            Toast.makeText(this, "Invalid Phone number", Toast.LENGTH_SHORT).show();
        }



        if(totalCash >= 1000){
        if(flagCnic && flagPhone)
            return true;
        else
            return false;
        }else{
            return false;
        }


    }



    public void AlertUserInfo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Logout!");
        builder.setMessage(R.string.urdu_confirmation_text);
        builder.setCancelable(false);

        builder.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        alert.dismiss();
                        finish();
                    }
                });



        alert = builder.create();
        alert.show();
    }



}
