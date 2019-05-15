package com.adaxiom.criccoo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.pixplicity.easyprefs.library.Prefs;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.adaxiom.utils.Constants.PREF_BLOCK_ID;

public class SelectBlock extends AppCompatActivity {


    @BindView(R.id.tvSelectOver_SelectBlock)
    TextView tvSelectOverSelectBlock;
    @BindView(R.id.ivBlockOne)
    ImageView ivBlockOne;
    @BindView(R.id.ivBlockTwo)
    ImageView ivBlockTwo;
    @BindView(R.id.ivBlockThree)
    ImageView ivBlockThree;
    @BindView(R.id.ivBlockFour)
    ImageView ivBlockFour;
    @BindView(R.id.ivBlockFive)
    ImageView ivBlockFive;
    @BindView(R.id.tvOver_1)
    TextView tvOver1;
    @BindView(R.id.tvOver_2)
    TextView tvOver2;
    @BindView(R.id.tvOver_3)
    TextView tvOver3;
    @BindView(R.id.tvOver_4)
    TextView tvOver4;
    @BindView(R.id.tvOver_5)
    TextView tvOver5;
    @BindView(R.id.tvOver_6)
    TextView tvOver6;
    @BindView(R.id.tvOver_7)
    TextView tvOver7;
    @BindView(R.id.tvOver_8)
    TextView tvOver8;
    @BindView(R.id.tvOver_9)
    TextView tvOver9;
    @BindView(R.id.tvOver_10)
    TextView tvOver10;


    private int blockId=1;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SelectBlock.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_block);
        ButterKnife.bind(this);


        setBlockEnableOrDisable();
        setBallsAnimation();
    }




    @OnClick({R.id.tvOver_1, R.id.tvOver_2, R.id.tvOver_3,
            R.id.tvOver_4, R.id.tvOver_5, R.id.tvOver_6, R.id.tvOver_7,
            R.id.tvOver_8, R.id.tvOver_9, R.id.tvOver_10, R.id.ivBlockOne,
            R.id.ivBlockTwo, R.id.ivBlockThree, R.id.ivBlockFour, R.id.ivBlockFive})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvOver_1:
                Prediction.startActivity(this,1);
                break;
            case R.id.tvOver_2:
                Prediction.startActivity(this,2);
                break;
            case R.id.tvOver_3:
                Prediction.startActivity(this,3);
                break;
            case R.id.tvOver_4:
                Prediction.startActivity(this,4);
                break;
            case R.id.tvOver_5:
                Prediction.startActivity(this,5);
                break;
            case R.id.tvOver_6:
                Prediction.startActivity(this,6);
                break;
            case R.id.tvOver_7:
                Prediction.startActivity(this,7);
                break;
            case R.id.tvOver_8:
                Prediction.startActivity(this,8);
                break;
            case R.id.tvOver_9:
                Prediction.startActivity(this,9);
                break;
            case R.id.tvOver_10:
                Prediction.startActivity(this,10);
                break;
            case R.id.ivBlockOne:
                break;
            case R.id.ivBlockTwo:
                break;
            case R.id.ivBlockThree:
                break;
            case R.id.ivBlockFour:
                break;
            case R.id.ivBlockFive:
                break;
        }
    }


    public void setBallsAnimation() {
        final Handler mHandler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                YoYo.with(Techniques.Shake).playOn(tvOver1);
                YoYo.with(Techniques.Pulse).playOn(tvOver2);
                YoYo.with(Techniques.Shake).playOn(tvOver3);
                YoYo.with(Techniques.Pulse).playOn(tvOver4);
                YoYo.with(Techniques.Shake).playOn(tvOver5);

                YoYo.with(Techniques.Pulse).playOn(tvOver6);
                YoYo.with(Techniques.Shake).playOn(tvOver7);
                YoYo.with(Techniques.Pulse).playOn(tvOver8);
                YoYo.with(Techniques.Shake).playOn(tvOver9);
                YoYo.with(Techniques.Pulse).playOn(tvOver10);

                mHandler.postDelayed(this, 1000);
            }
        };
        runnable.run();

    }


    public void setBlockEnableOrDisable(){
        String blockId = Prefs.getInt(PREF_BLOCK_ID,0)+"";

        if(blockId.equalsIgnoreCase("1")){
            ivBlockOne.setImageResource(R.drawable.one_enable);
            ivBlockTwo.setImageResource(R.drawable.two_disable);
            ivBlockThree.setImageResource(R.drawable.three_disable);
            ivBlockFour.setImageResource(R.drawable.four_disable);
            ivBlockFive.setImageResource(R.drawable.five_disable);
            tvOver1.setText("1");
            tvOver1.setText("2");
            tvOver1.setText("3");
            tvOver1.setText("4");
            tvOver1.setText("5");
            tvOver1.setText("6");
            tvOver1.setText("7");
            tvOver1.setText("8");
            tvOver1.setText("9");
            tvOver1.setText("10");
        }else if(blockId.equalsIgnoreCase("2")){
            ivBlockOne.setImageResource(R.drawable.one_disable);
            ivBlockTwo.setImageResource(R.drawable.two_enable);
            ivBlockThree.setImageResource(R.drawable.three_disable);
            ivBlockFour.setImageResource(R.drawable.four_disable);
            ivBlockFive.setImageResource(R.drawable.five_disable);
            tvOver1.setText("11");
            tvOver1.setText("12");
            tvOver1.setText("13");
            tvOver1.setText("14");
            tvOver1.setText("15");
            tvOver1.setText("16");
            tvOver1.setText("17");
            tvOver1.setText("18");
            tvOver1.setText("19");
            tvOver1.setText("20");
        }else if(blockId.equalsIgnoreCase("3")){
            ivBlockOne.setImageResource(R.drawable.one_disable);
            ivBlockTwo.setImageResource(R.drawable.two_disable);
            ivBlockThree.setImageResource(R.drawable.three_enable);
            ivBlockFour.setImageResource(R.drawable.four_disable);
            ivBlockFive.setImageResource(R.drawable.five_disable);
            tvOver1.setText("21");
            tvOver1.setText("22");
            tvOver1.setText("23");
            tvOver1.setText("24");
            tvOver1.setText("25");
            tvOver1.setText("26");
            tvOver1.setText("27");
            tvOver1.setText("28");
            tvOver1.setText("29");
            tvOver1.setText("30");
        }else if(blockId.equalsIgnoreCase("4")){
            ivBlockOne.setImageResource(R.drawable.one_disable);
            ivBlockTwo.setImageResource(R.drawable.two_disable);
            ivBlockThree.setImageResource(R.drawable.three_disable);
            ivBlockFour.setImageResource(R.drawable.four_enable);
            ivBlockFive.setImageResource(R.drawable.five_disable);
            tvOver1.setText("31");
            tvOver1.setText("32");
            tvOver1.setText("33");
            tvOver1.setText("34");
            tvOver1.setText("35");
            tvOver1.setText("36");
            tvOver1.setText("37");
            tvOver1.setText("38");
            tvOver1.setText("39");
            tvOver1.setText("40");
        }else if(blockId.equalsIgnoreCase("5")){
            ivBlockOne.setImageResource(R.drawable.one_disable);
            ivBlockTwo.setImageResource(R.drawable.two_disable);
            ivBlockThree.setImageResource(R.drawable.three_disable);
            ivBlockFour.setImageResource(R.drawable.four_disable);
            ivBlockFive.setImageResource(R.drawable.five_enable);
            tvOver1.setText("41");
            tvOver1.setText("42");
            tvOver1.setText("43");
            tvOver1.setText("44");
            tvOver1.setText("45");
            tvOver1.setText("46");
            tvOver1.setText("47");
            tvOver1.setText("48");
            tvOver1.setText("49");
            tvOver1.setText("50");
        }
    }

}
