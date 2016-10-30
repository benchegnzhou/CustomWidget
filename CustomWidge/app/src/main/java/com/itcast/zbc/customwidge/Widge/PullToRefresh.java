package com.itcast.zbc.customwidge.Widge;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.itcast.zbc.customwidge.R;
import com.itcast.zbc.customwidge.mAppAplication;
import com.itcast.zbc.customwidge.toastutils.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Zbc on 2016/9/25.
 * 这是一个自定义的下拉刷新
 * <p/>
 * 惊讶的发现一个问题，butterknife只限使用在四大组件当中
 */
public class PullToRefresh extends ListView {


    private Context mContext;
    private View header;
    private int measuredHeight;
    /**
     * 下拉刷新
     */
    private static final int PULL_DOWN = 1;
    /**
     * 正在刷新
     */
    private static final int PULL_ING = 2;
    /**
     * 松开刷新
     */
    private static final int PULL_UP = 3;
    /**呢
     * 状态
     */
    private int state = PULL_DOWN;
    /**
     * 下拉的系数
     */
    private final float F=0.5F;
    private ImageView pulltorefreshDown;
    private ImageView pulltorefreshing;
    private TextView pulltorefresh_text;
    private TextView pulltorefreshTime;
    private RotateAnimation pulldownAnimal;
    private RotateAnimation pullingAnimation;
    private RotateAnimation pullUpAnimation;
    private SimpleDateFormat formatter;

    public PullToRefresh(Context context) {
        this(context, null);
        mContext = context;

    }

    public PullToRefresh(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefresh(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setHeader();
        formatter = new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss");
    }

    /**
     * 给listview添加下拉刷新的头
     */
    private void setHeader() {
//      View header = LayoutInflater.from(mContext).inflate(R.layout.pulltorefreshheaderview, null);
        header = View.inflate(mAppAplication.mAppcontext, R.layout.pulltorefreshheaderview, null);

        pulltorefreshDown = (ImageView) header.findViewById(R.id.pulltorefresh_down);
        pulltorefreshing = (ImageView) header.findViewById(R.id.pulltorefreshing);
        pulltorefresh_text = (TextView) header.findViewById(R.id.pulltorefresh_text);
        pulltorefreshTime = (TextView) header.findViewById(R.id.pulltorefresh_time);

        //隐藏控件
        hideWeight();
        initAnimation();
        AnimationPulling();
        AnimationPullUp();
        addHeaderView(header);  //listview 自带的添加添加头条目方法
        //隐藏头条目，让头条目距离顶边框距离-边框的高度
    }

    /**
     * 初始化箭头向上的动画
     */
    private void initAnimation() {
        //箭头向上的动画
        pulldownAnimal = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        pulldownAnimal.setDuration(500);
        pulldownAnimal.setFillAfter(true);
    }

    /**
     * 刷新中的动画
     */
    private void AnimationPulling() {
        //箭头向上的动画
        pullingAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        pullingAnimation.setDuration(700);
        pullingAnimation.setRepeatCount(10000);
        pullingAnimation.setFillAfter(true);
    }

    /**
     * 箭头转向上的动画
     */
    private void AnimationPullUp() {
        //箭头向上的动画
        pullUpAnimation = new RotateAnimation(180, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        pullUpAnimation.setDuration(500);
        pullUpAnimation.setFillAfter(true);

    }


    /**
     * 将下拉刷新的控件隐藏，这里使用的是请求测量控件的方法，  控件.measure(0,0)
     * 在view的oncreate方法中是无法获取到空间的高度的，只用当oncreate方法走完之后才可以
     * 原理：是指控件的padding值的时候，如果是top 为负数就是向上移动，为整数就是向下移动
     */
    private void hideWeight() {

        //手动请求系统测量控件的高度
        header.measure(0, 0);
        //系统帮我们测量好的控件的高度
        measuredHeight = header.getMeasuredHeight();
        header.setPadding(0, -measuredHeight, 0, 0);
    }

    private float downY;

    /**
     * 刷新的状态改变
     */
    private void stateChange() {
        switch (state) {
            case PULL_DOWN: //下拉刷新
                pulltorefreshing.clearAnimation();
                pulltorefreshing.setVisibility(View.GONE);
                pulltorefreshDown.setVisibility(View.VISIBLE);
                pulltorefresh_text.setText("下拉刷新");
                pulltorefreshDown.startAnimation(pulldownAnimal);
                break;

            case PULL_ING://正在刷新
                pulltorefreshDown.clearAnimation();
                pulltorefreshDown.setVisibility(View.GONE);
                pulltorefreshing.setVisibility(View.VISIBLE);
                pulltorefresh_text.setText("正在刷新");

                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                String str = formatter.format(curDate);
                pulltorefreshTime.setText("最后刷新时间：" + str);
                pulltorefreshing.startAnimation(pullingAnimation);
                break;
            case PULL_UP://松开刷新
                pulltorefresh_text.setText("松开刷新");
                pulltorefreshDown.startAnimation(pullUpAnimation);
                break;
        }
    }


    /**
     * 控件的触摸事件：
     * 下拉生效的条件
     * 1. listview下拉的时候
     * 2. 显示的是第一个条目
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = ev.getY();
                float diffY = moveY - downY;
                ToastUtil.showToast_Short("" + diffY);
                float paddingTop = diffY*F - measuredHeight;
                //条件满足
                if (diffY > 0 && getFirstVisiblePosition() == 0 && state != PULL_ING) {
                    header.setPadding(0, (int) (paddingTop), 0, 0);
                }
                //下拉刷新变成松开刷新,header全部显示，变为松开刷新
                if (paddingTop > 0 && state != PULL_ING&& state != PULL_UP) {
                    state = PULL_UP;
                    stateChange();
                }

                //松开刷新变成下拉刷新
                if (paddingTop < 0 && state != PULL_ING&& state != PULL_DOWN) {
                    state = PULL_DOWN;
                    stateChange();
                }


                //如果有空白的区域显示的话，不适用系统的触摸事件
                return true; //自己处理事件返回true


            case MotionEvent.ACTION_UP:
                float upY = ev.getY();
                float diffUpY = upY - downY;
                paddingTop = diffUpY*F - measuredHeight;
                //下拉刷新变成松开刷新,header全部显示，变为松开刷新
                if (paddingTop > 0 && state == PULL_UP) {
                    state = PULL_ING;
                    header.setPadding(0, 0, 0, 0);
                    stateChange();
                }else{
                    header.setPadding(0, -measuredHeight, 0, 0);
                }

                break;

        }

        return super.onTouchEvent(ev);

    }
}
