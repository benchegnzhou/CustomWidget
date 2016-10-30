package com.itcast.zbc.customwidge.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.itcast.zbc.customwidge.R;
import com.itcast.zbc.customwidge.Widge.MyToggleButton;
import com.itcast.zbc.customwidge.toastutils.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 自定义的滑动按钮的使用
 */
public class ButtonCustomActivity extends AppCompatActivity {


    @Bind(R.id.view_toggle_custom)
    MyToggleButton viewToggleCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_custom);
        ButterKnife.bind(this);
        intView();
    }

    /**
     * 初始化按钮的背景图片
     */
    private void intView() {
//        MyToggleButton myToggleButton = new MyToggleButton(ButtonCustomActivity.this);
        //传入背景： 暂时可以采用自定义属性的方式进行，暂时将这里注释掉
        //viewToggleCustom.setBackgroundAndrIcon(R.drawable.slide_background, R.drawable.slide_icon);
        //设置开关的初始状态:关闭  暂时可以采用自定义属性的方式进行，暂时将这里注释掉
       // viewToggleCustom.setState(false);

        //监听开关的状态，在实际的开当中，状态的监听会比广播有更广的使用
        viewToggleCustom.setOnToggleClickListener(new MyToggleButton.OnClickListener() {
            @Override
            public void onClickListener(boolean open) {
                ToastUtil.showToast_Short(open ? "开关打开" : "开关关闭");
            }
        });


    }
}
