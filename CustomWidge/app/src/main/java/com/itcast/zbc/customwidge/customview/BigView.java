package com.itcast.zbc.customwidge.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Zbc on 2016/10/30.
 * 自定义view类实现大图片加载
 */
public class BigView extends View {

    private int imageWidth;
    private int imageHeight;
    private BitmapRegionDecoder decoder;
    private int measuredWidth;
    private int measuredHeight;
    private static BitmapFactory.Options options = new BitmapFactory.Options();

    //静态代码块
    static {
        options.inPreferredConfig = Bitmap.Config.RGB_565;
    }

    private Rect currentRect;
    private int downX;
    private int downY;

    public BigView(Context context) {
        this(context, null);
    }

    public BigView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BigView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    /**
     * 要绘制图片就要获取屏幕的宽高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取屏幕的宽
        measuredWidth = getMeasuredWidth();
        //获取屏幕的高
        measuredHeight = getMeasuredHeight();


        //top=原图片高度的一般 - 屏幕高的一般
        int top = imageHeight / 2 - measuredHeight / 2;
        int bottom = imageHeight / 2 + measuredHeight / 2;
        int left = imageWidth / 2 - measuredWidth / 2;
        int right = imageWidth / 2 + measuredWidth / 2;
        //传递局部大小的矩形
        currentRect = new Rect(left, top, right, bottom);
    }

    /**
     * view自身无法显示布局，这能借助ondraw方法
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //加载图片  ：设置矩形框和加载的图片的质量
        Bitmap bitmap = decoder.decodeRegion(currentRect, options);
        //绘制图片  //参数1: bitmap 参数2:pisionLeft 参数3：pisionRight 参数4：画笔
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    /**
     * 一张大图不可以直接的读取并加载渲染，会造成oom，应该实现分块加载
     *
     * @param inputStream 读入的超大图片
     */
    public void setInput(InputStream inputStream) {
        //1. 获取图片的宽和高
        BitmapFactory.Options options = new BitmapFactory.Options();
        //1.1 设置图片只加载边界，不进行渲染
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);


        //2. 获取图片的宽和高
        imageWidth = options.outWidth;
        imageHeight = options.outHeight;

        //3. 加载这张图片   BitmapRegionDecoder进行局部加载某个类  参数1： imageview的输入流，参数2： 是不是原图片和局部加载的图片共享一个inputStream为true有问题 true在原来图片的基础上进行修改 false 拷贝原图片进行加载
        try {
            decoder = BitmapRegionDecoder.newInstance(inputStream, false);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 处理图片的拖拽功能
     *
     * @param event
     * @return true  表示自己处理
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            //记录下当前的x轴和y轴的坐标
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                downY = (int) event.getY();

                break;
            //获取移动后的x和y
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getX();
                int moveY = (int) event.getY();

                //获取移动的距离
                int diffX =  downX-moveX ;
                int diffY =  downY-moveY ;
                refreshRect(diffX, diffY);
                //刷新之后重新记录位置，因此这里需要更正一下参考的位置
                downX=moveX;
                downY=moveY;
                break;
            case MotionEvent.ACTION_UP:
                break;


        }
        return true;
    }

    /**
     * 刷新当前的矩形图块的位置
     * @param diffX  x方向的移动
     * @param diffY  y方向的移动
     */
    private void refreshRect(int diffX, int diffY) {
        //解决边界溢出的问题，所用的控件移动都会有这样的问题
        if(currentRect.left<0){  //左边界解决
            currentRect.left=0;
            currentRect.right=currentRect.left+measuredWidth;

        }else if(currentRect.right>imageWidth){//右边界解决
            currentRect.right=imageWidth;
            currentRect.left=currentRect.right-measuredWidth;
        }
        if(currentRect.top<0){  //上边界解决
            currentRect.top=0;
            currentRect.bottom=currentRect.top+measuredHeight;

        }else if(currentRect.bottom>imageHeight){//下边界解决
            currentRect.bottom=imageHeight;
            currentRect.top=currentRect.bottom-measuredHeight;
        }



        //注意一点： 这里的传入的另是移动改变的量，每一次的都是一个差值
        currentRect.offset(diffX,diffY);
        invalidate();
    }
}
