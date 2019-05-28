package com.adaxiom.criccoo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
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
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
                String strOver = tvOver1.getText().toString().trim();
                int overId = Integer.parseInt(strOver);
                Prediction.startActivity(this,overId);
                break;
            case R.id.tvOver_2:
                String strOver2 = tvOver2.getText().toString().trim();
                int overId2 = Integer.parseInt(strOver2);
                Prediction.startActivity(this,overId2);
                break;
            case R.id.tvOver_3:
                String strOver3 = tvOver3.getText().toString().trim();
                int overId3 = Integer.parseInt(strOver3);
                Prediction.startActivity(this,overId3);
                break;
            case R.id.tvOver_4:
                String strOver4 = tvOver4.getText().toString().trim();
                int overId4 = Integer.parseInt(strOver4);
                Prediction.startActivity(this,overId4);
                break;
            case R.id.tvOver_5:
                String strOver5 = tvOver5.getText().toString().trim();
                int overId5 = Integer.parseInt(strOver5);
                Prediction.startActivity(this,overId5);
                break;
            case R.id.tvOver_6:
                String strOver6 = tvOver6.getText().toString().trim();
                int overId6 = Integer.parseInt(strOver6);
                Prediction.startActivity(this,overId6);
                break;
            case R.id.tvOver_7:
                String strOver7 = tvOver7.getText().toString().trim();
                int overId7 = Integer.parseInt(strOver7);
                Prediction.startActivity(this,overId7);
                break;
            case R.id.tvOver_8:
                String strOver8 = tvOver8.getText().toString().trim();
                int overId8 = Integer.parseInt(strOver8);
                Prediction.startActivity(this,overId8);
                break;
            case R.id.tvOver_9:
                String strOver9 = tvOver9.getText().toString().trim();
                int overId9 = Integer.parseInt(strOver9);
                Prediction.startActivity(this,overId9);
                break;
            case R.id.tvOver_10:
                String strOver10 = tvOver10.getText().toString().trim();
                int overId10 = Integer.parseInt(strOver10);
                Prediction.startActivity(this,overId10);
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
                YoYo.with(Techniques.FadeIn).playOn(tvOver2);
                YoYo.with(Techniques.Shake).playOn(tvOver3);
                YoYo.with(Techniques.FadeIn).playOn(tvOver4);
                YoYo.with(Techniques.Shake).playOn(tvOver5);

                YoYo.with(Techniques.FadeIn).playOn(tvOver6);
                YoYo.with(Techniques.Shake).playOn(tvOver7);
                YoYo.with(Techniques.FadeIn).playOn(tvOver8);
                YoYo.with(Techniques.Shake).playOn(tvOver9);
                YoYo.with(Techniques.FadeIn).playOn(tvOver10);

                mHandler.postDelayed(this, 2500);
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
            tvOver2.setText("2");
            tvOver3.setText("3");
            tvOver4.setText("4");
            tvOver5.setText("5");
            tvOver6.setText("6");
            tvOver7.setText("7");
            tvOver8.setText("8");
            tvOver9.setText("9");
            tvOver10.setText("10");
        }else if(blockId.equalsIgnoreCase("2")){
            ivBlockOne.setImageResource(R.drawable.one_disable);
            ivBlockTwo.setImageResource(R.drawable.two_enable);
            ivBlockThree.setImageResource(R.drawable.three_disable);
            ivBlockFour.setImageResource(R.drawable.four_disable);
            ivBlockFive.setImageResource(R.drawable.five_disable);
            tvOver1.setText("11");
            tvOver2.setText("12");
            tvOver3.setText("13");
            tvOver4.setText("14");
            tvOver5.setText("15");
            tvOver6.setText("16");
            tvOver7.setText("17");
            tvOver8.setText("18");
            tvOver9.setText("19");
            tvOver10.setText("20");
        }else if(blockId.equalsIgnoreCase("3")){
            ivBlockOne.setImageResource(R.drawable.one_disable);
            ivBlockTwo.setImageResource(R.drawable.two_disable);
            ivBlockThree.setImageResource(R.drawable.three_enable);
            ivBlockFour.setImageResource(R.drawable.four_disable);
            ivBlockFive.setImageResource(R.drawable.five_disable);
            tvOver1.setText("21");
            tvOver2.setText("22");
            tvOver3.setText("23");
            tvOver4.setText("24");
            tvOver5.setText("25");
            tvOver6.setText("26");
            tvOver7.setText("27");
            tvOver8.setText("28");
            tvOver9.setText("29");
            tvOver10.setText("30");
        }else if(blockId.equalsIgnoreCase("4")){
            ivBlockOne.setImageResource(R.drawable.one_disable);
            ivBlockTwo.setImageResource(R.drawable.two_disable);
            ivBlockThree.setImageResource(R.drawable.three_disable);
            ivBlockFour.setImageResource(R.drawable.four_enable);
            ivBlockFive.setImageResource(R.drawable.five_disable);
            tvOver1.setText("31");
            tvOver2.setText("32");
            tvOver3.setText("33");
            tvOver4.setText("34");
            tvOver5.setText("35");
            tvOver6.setText("36");
            tvOver7.setText("37");
            tvOver8.setText("38");
            tvOver9.setText("39");
            tvOver10.setText("40");
        }else if(blockId.equalsIgnoreCase("5")){
            ivBlockOne.setImageResource(R.drawable.one_disable);
            ivBlockTwo.setImageResource(R.drawable.two_disable);
            ivBlockThree.setImageResource(R.drawable.three_disable);
            ivBlockFour.setImageResource(R.drawable.four_disable);
            ivBlockFive.setImageResource(R.drawable.five_enable);
            tvOver1.setText("41");
            tvOver2.setText("42");
            tvOver3.setText("43");
            tvOver4.setText("44");
            tvOver5.setText("45");
            tvOver6.setText("46");
            tvOver7.setText("47");
            tvOver8.setText("48");
            tvOver9.setText("49");
            tvOver10.setText("50");
        }
    }

}
