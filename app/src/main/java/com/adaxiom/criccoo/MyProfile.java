package com.adaxiom.criccoo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adaxiom.adapter.AdapterLeaderBoard;
import com.adaxiom.adapter.AdapterMyProfile;
import com.adaxiom.manager.DownloaderManager;
import com.adaxiom.model.response.RM_LeaderBoard;
import com.adaxiom.model.response.RM_UserResult;
import com.adaxiom.utils.BaseActivity;
import com.adaxiom.utils.Utils;
import com.bumptech.glide.Glide;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import static com.adaxiom.utils.Constants.PREF_PROFILE_IMAGE;
import static com.adaxiom.utils.Constants.PREF_USER_ID;
import static com.adaxiom.utils.Constants.PREF_USER_NAME;

public class MyProfile extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ivMyProfilePhoto)
    ImageView ivMyProfilePhoto;
    @BindView(R.id.tvMyProfileName)
    TextView tvMyProfileName;
    @BindView(R.id.tvMyProfileTotalPoints)
    TextView tvMyProfileTotalPoints;
    @BindView(R.id.rvMyProfilePoints)
    RecyclerView rvMyProfilePoints;
    @BindView(R.id.avLoading)
    LinearLayout avLoading;

    private static String myTotalPoints = "";
    public AdapterMyProfile reAdapter;



    public static void startActivity(Context context, String Points) {
        myTotalPoints=Points;
        Intent intent = new Intent(context, MyProfile.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_my_profile);
        ButterKnife.bind(this);

        setToolbar(toolbar, "My Profile");
        setValues();


        if (Utils.isNetworkAvailable(this))
            API_GetProfileData();
        else Toast.makeText(this, R.string.internet_connectivity_msg, Toast.LENGTH_SHORT).show();


    }


    public void setValues(){

        String name = Prefs.getString(PREF_USER_NAME,"User");

        tvMyProfileName.setText(name);
        tvMyProfileTotalPoints.setText("Total Points : "+myTotalPoints);

        String imageUrl=Prefs.getString(PREF_PROFILE_IMAGE, "https://d1nhio0ox7pgb.cloudfront.net/_img/o_collection_png/green_dark_grey/512x512/plain/user.png");
        Log.e("Image:",imageUrl);
        if(imageUrl.equalsIgnoreCase("null"))
            imageUrl="https://d1nhio0ox7pgb.cloudfront.net/_img/o_collection_png/green_dark_grey/512x512/plain/user.png";
        Glide.with(MyProfile.this).load(imageUrl).into(ivMyProfilePhoto);

    }


        public void setAdapter(List<RM_UserResult> mList) {



            rvMyProfilePoints.setHasFixedSize(true);
            final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            rvMyProfilePoints.setLayoutManager(layoutManager);


            reAdapter = new AdapterMyProfile(this, mList,
                    new AdapterMyProfile.RecyclerViewItemClickListener() {
                        @Override
                        public void recyclerViewListClicked(View v, int position) {

                        }
                    });

            rvMyProfilePoints.setAdapter(reAdapter);
        }

    private void API_GetProfileData() {

        int user_id = Prefs.getInt(PREF_USER_ID, 0);

        avLoading.setVisibility(View.VISIBLE);

         DownloaderManager.getGeneralDownloader().
                API_UserResult(user_id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<List<RM_UserResult>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(final Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                avLoading.setVisibility(View.GONE);
                                Toast.makeText(MyProfile.this, e.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(final List<RM_UserResult> list) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                avLoading.setVisibility(View.GONE);
                                setAdapter(list);
                            }
                        });
                    }
                });


    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }



}
