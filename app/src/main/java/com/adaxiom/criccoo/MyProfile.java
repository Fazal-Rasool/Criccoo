package com.adaxiom.criccoo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adaxiom.adapter.AdapterLeaderBoard;
import com.adaxiom.model.response.RM_LeaderBoard;
import com.adaxiom.utils.BaseActivity;
import com.adaxiom.utils.Utils;
import com.pixplicity.easyprefs.library.Prefs;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    public static void startActivity(Context context, String Points) {
        myTotalPoints=Points;
        Intent intent = new Intent(context, MyProfile.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        ButterKnife.bind(this);

        setToolbar(toolbar, "Leader Board");
        setValues();


        if (Utils.isNetworkAvailable(this))
            API_GetProfileData();
        else Toast.makeText(this, R.string.internet_connectivity_msg, Toast.LENGTH_SHORT).show();


    }


    public void setValues(){

        String name = Prefs.getString(PREF_USER_NAME,"User");

        tvMyProfileName.setText(name);
        tvMyProfileTotalPoints.setText(myTotalPoints);
    }


//        public void setAdapter(RM_LeaderBoard mList) {
//
//            if (mList.user.size() != 0) {
//                String myRank = mList.user.get(0).rank;
//                String total = mList.user.get(0).total_score;
//                tvMyRank.setText(myRank);
//                if (total != null)
//                    tvTotalpoints.setText(total);
//                else tvTotalpoints.setText("0.0");
//            }
//            if (mList.all_users.size() != 0) {
//                String highestScore = mList.all_users.get(0).total_score;
//                tvHighestPoints.setText(highestScore);
//            }
//
//
//            rvLeaderBoard.setHasFixedSize(true);
//            final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//            rvLeaderBoard.setLayoutManager(layoutManager);
//
//
//            reAdapter = new AdapterLeaderBoard(this, mList,
//                    new AdapterLeaderBoard.RecyclerViewItemClickListener() {
//                        @Override
//                        public void recyclerViewListClicked(View v, int position) {
//
//                        }
//                    });
//
//            rvLeaderBoard.setAdapter(reAdapter);
//        }

    private void API_GetProfileData() {


    }
}
