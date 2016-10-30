package com.itcast.zbc.customwidge.Widge;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Icon;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Zbc on 2016/9/16
 * 这个是我自定的滑动按钮.
 */
public class MyToggleButton extends View {

    private Bitmap backgroud;
    private Bitmap icon;
    private OnClickListener mOnClick;
    private float IconLeft;
    private float iconMax;
    private boolean isHandUp;
    private int iconbackgroud;
    private int icon1;
    private boolean togglestate;

    //代码中new的时候调用的
    public MyToggleButton(Context context) {
        this(context, null);
    }

    //在布局文件中调用的，所有的属性都会保存在AttributeSet中
    public MyToggleButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    // 在布局文件中使用，多了一个样式
    public MyToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        String namespace = "http://schemas.android.com/apk/res-auto";
        //1. 根据名字获取对应属性的值
        //获取属性的值并设置  参数1： 命名空间 参数2 ：属性的名称  参数3： 默认值
        iconbackgroud = attrs.getAttributeResourceValue(namespace, "iconbackgroud", -1);
        icon1 = attrs.getAttributeResourceValue(namespace, "iconfort", -1);
        togglestate = attrs.getAttributeBooleanValue(namespace, "togglestate", false);

        //2. 设置属性对应的值
        if (iconbackgroud != -1 && icon1 != -1) {    //真正的代码逻辑中一定要增加对应健壮性判断
            setBackgroundAndrIcon(iconbackgroud,icon1);
        }

        setState(togglestate);  //设置开关的初始状态




    }

    /**
     * 接收传递过来的显示图片
     *
     * @param backgroudID 传递过来的图片的id
     */
    public void setBackgroundAndrIcon(int backgroudID, int iconID) {
        //根据传递过来的id显示背景图片
        backgroud = BitmapFactory.decodeResource(getResources(), backgroudID);
        //图标
        icon = BitmapFactory.decodeResource(getResources(), iconID);

        //获取图片差的最大值
        iconMax = backgroud.getWidth() - icon.getWidth();
    }

    /**
     * 因为开关是需要设置开关是开启还是关闭的，
     * 需要一个对应的方法
     */
    public void setState(boolean Open) {
        if (mOnClick != null) {
            mOnClick.onClickListener(true);
        }
        isHandUp = true;
        if (Open) {
            IconLeft = iconMax;
        } else {
            IconLeft = 0;
        }
        //调用方法一起重绘
        invalidate();
    }

    /**
     * 因为滑动的状态可以通过吐司显示，因此需要有一个滑动状态的监听
     */
    public void setOnToggleClickListener(OnClickListener click) {
        mOnClick = click;
    }

    /**
     * 显示操作；控件的渲染问题
     * 1. 测量控件的宽高
     * 2. 排版： 控制控件的显示位置  ，可以使用布局文件的形式亦可以使用new的形式
     * 3. 绘制控件
     * 只有前两部完成才会执行第三步
     * 在activity的oncreate方法中是无法回去控件的宽高的 ，因为只有当oncreate方法走完了才会间渲染的步骤走完，
     * 才会获得空间的宽高。
     *
     * 1. 测量控件是有着自己的方法的onmeasure方法
     * 2. 摆放控件由layout来实现的
     * 3. 排版使用布局文件来完成
     * 4. 绘制的操作
     *
     */


    /**
     * 1. 控件宽高测量的方法
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //根据自己的控件测量自己控件的宽高
        setMeasuredDimension(backgroud.getWidth(), backgroud.getHeight());

    }

    /**
     * 2. 重新摆放控件的位置
     *
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    /**
     * 3. 绘制控件
     *
     * @param canvas：画布 实际的测试过程中发现，坐标的值是从屏幕的右上角去除状态栏和actionbar开始的，x,y是多少绘制的位置就是多少
     */
    @Override
    protected void onDraw(Canvas canvas) {
        //控制按钮滑动的位置
        if (IconLeft < 0) {
            IconLeft = 0;
        } else if (IconLeft > iconMax) {
            IconLeft = iconMax;
        }

        //将抬起的状态交给回调监听进行状态显示
        //1.确定已经抬起
        //2.将开关的状态床底给回调监听
        if (isHandUp) {  //判断抬起
            isHandUp = false;  //及时的修正状态
            if (mOnClick != null) {
                mOnClick.onClickListener(IconLeft == 0 ? false : true);
            }

        }


        //背景
        //参数1：绘制的原图片 参数2： 绘制的X坐标 参数3： 绘制的Y坐标  因为绘制的是原来图片，相当于复制，传入null使用默认的画笔
        canvas.drawBitmap(backgroud, 0, 0, null);
        //绘制按钮
        canvas.drawBitmap(icon, 0 + IconLeft, 0, null);


        super.onDraw(canvas);
    }


    /**
     * 执行滑动事件的操作
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //移动的距离=按住的位置-图片宽度的一半
                IconLeft = event.getX() - icon.getWidth() / 2;
                break;
            case MotionEvent.ACTION_MOVE:
                //移动的距离=按住的位置-图片宽度的一半
                IconLeft = event.getX() - icon.getWidth() / 2;
                break;
            case MotionEvent.ACTION_UP:
                isHandUp = true;
                //当手抬起的时候设置图片偏向一方
                if (event.getX() < backgroud.getWidth() / 2) {  //抬起的位置小于图片的一半，滑向左边
                    IconLeft = 0;
                } else {
                    IconLeft = iconMax;
                }
                break;
        }
        //引起控件的重绘，需要调用onDraw方法，但是在Android中这个方法是不可以直接的调用的
        invalidate();  //引起重绘，间接调用ondraw()

        return true;  //只有返回true的时候才可以相应事件
    }


    public interface OnClickListener {
        public void onClickListener(boolean open);


    }


}
