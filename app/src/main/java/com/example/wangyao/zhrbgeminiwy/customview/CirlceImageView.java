package com.example.wangyao.zhrbgeminiwy.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by wangyao on 2017/8/14.
 */

public class CirlceImageView extends ImageView {

    private int width;
    private int radius;
    private Matrix matrix;
    private Paint paint;

    public CirlceImageView(Context context) {
        super(context);
    }

    public CirlceImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        matrix = new Matrix();
        paint = new Paint();
        paint.setAntiAlias(true);//设置抗锯齿
    }

    public CirlceImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = Math.min(getMeasuredWidth(),getMeasuredHeight());
        radius = width/2;
        setMeasuredDimension(width,width);
    }

    /**
     * 画布canvas画一个圆，用paint画笔画，paint画笔用bitmap绑定
     * 圆的大小由xml文件里确定，直径为定义的宽和高较小的那一项
     * 图片若比定义的小时，得到图片宽和高小的那一项，除xml定义的，得到的为伸缩比
     * 设置bitmapshader渲染器，参数为x，y可伸缩，绑定Matrix矩阵（设置一个Matrix矩阵，将x，y伸缩比例放进去，相当于一个模板，帮助确认渲染器扩大放到模板上）
     * @param canvas
     */


    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable == null){return;}
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();//拿到bitmap
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);//使bitmap可以伸缩
        float scale = 1.0f;
        int drawwidth = Math.min(bitmap.getWidth(),bitmap.getHeight());//拿出图片最短的那条当做直径
        scale = width*1.0f / drawwidth;//伸缩比例
        matrix.setScale(scale,scale);//变换矩阵伸缩比例设置
        bitmapShader.setLocalMatrix(matrix);//设置好矩阵的伸缩
        paint.setShader(bitmapShader);
        canvas.drawCircle(radius,radius,radius,paint);
    }
}