package com.itcast.zbc.customwidge.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.itcast.zbc.customwidge.R;
import com.itcast.zbc.customwidge.customview.BigView;

import java.io.IOException;
import java.io.InputStream;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BigPictureCustomActivity extends AppCompatActivity {

    @Bind(R.id.biv_custom_bigimageview)
    BigView bivCustomBigimageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_picture_custom);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
//获取资源文件，并将其转换成输入流
        try {
            InputStream imageSteream = getAssets().open("world.jpg");
            bivCustomBigimageview.setInput(imageSteream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
