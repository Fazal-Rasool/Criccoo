package com.adaxiom.criccoo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SelectBlock.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_block);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ivBlockOne, R.id.ivBlockTwo, R.id.ivBlockThree, R.id.ivBlockFour, R.id.ivBlockFive})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBlockOne:
//                SelectOver.startActivity(SelectBlock.this);
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
}
