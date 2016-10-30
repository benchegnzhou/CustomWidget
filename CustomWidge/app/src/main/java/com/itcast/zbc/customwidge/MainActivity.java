package com.itcast.zbc.customwidge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.itcast.zbc.customwidge.UI.BigPictureActivity;
import com.itcast.zbc.customwidge.UI.BigPictureCustomActivity;
import com.itcast.zbc.customwidge.UI.ButtonCustomActivity;
import com.itcast.zbc.customwidge.UI.PulltorefreshActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.btn_toggle_custom)
    Button btnToggleCustom;
    @Bind(R.id.btn_pulltorefresh_custom)
    Button btnPulltorefreshCustom;
    @Bind(R.id.btn_custom_big_picture_loading)
    Button btnCustomBigPictureLoading;
    @Bind(R.id.btn_big_picture_loading)
    Button btnBigPictureLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        btnToggleCustom.setOnClickListener(this);
        btnPulltorefreshCustom.setOnClickListener(this);
        btnCustomBigPictureLoading.setOnClickListener(this);
        btnBigPictureLoading.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_toggle_custom:
                startActivity(new Intent(MainActivity.this, ButtonCustomActivity.class));
                break;
            case R.id.btn_pulltorefresh_custom:
                startActivity(new Intent(MainActivity.this, PulltorefreshActivity.class));
                break;

            case R.id.btn_custom_big_picture_loading:
                startActivity(new Intent(MainActivity.this, BigPictureCustomActivity.class));
                break;
            case R.id.btn_big_picture_loading:
                startActivity(new Intent(MainActivity.this, BigPictureActivity.class));
                break;


        }
    }
}
