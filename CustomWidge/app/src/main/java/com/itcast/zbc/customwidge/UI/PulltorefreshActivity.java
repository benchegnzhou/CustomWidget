package com.itcast.zbc.customwidge.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.itcast.zbc.customwidge.R;
import com.itcast.zbc.customwidge.Widge.PullToRefresh;

public class PulltorefreshActivity extends AppCompatActivity {

    private PullToRefresh pullToRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulltorefresh);
        initView();


    }

    private void initView() {
        pullToRefresh = (PullToRefresh) findViewById(R.id.lv_mycustom_weigt);
        String[] arr = new String[20];
        for (int i = 0; i < 20; i++) {
            arr[i] = "我是填充的数据" + i;
        }

        pullToRefresh.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr));


    }
}
