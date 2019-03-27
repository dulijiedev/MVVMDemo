package com.mrdo.design.mvvmktx.glide

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Priority
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.BaseRequestOptions
import com.mrdo.design.mvvmktx.R
import com.mrdo.design.mvvmktx.glide.GlideOptions.bitmapTransform
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import jp.wasabeef.glide.transformations.gpu.ToonFilterTransformation
import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation
import java.lang.RuntimeException

/**
 * Created by dulijie on 2019/3/27.
 * 图片加载
//        CropCircleTransformation              圆形剪裁显示      .bitmapTransform(new CircleCrop())
//        CropSquareTransformation              正方形剪裁        .bitmapTransform(new CropSquareTransformation())
//        RoundedCornersTransformation          圆角剪裁          .bitmapTransform(new RoundedCornersTransformation(this, 100, 0, RoundedCornersTransformation.CornerType.ALL))
//        CropTransformation                    自定义裁剪         .bitmapTransform(new CropTransformation(600, 200, CropTransformation.CropType.CENTER))
//        ColorFilterTransformation             颜色滤镜           .bitmapTransform(new ColorFilterTransformation(0x7900CCCC))
//        ToonFilterTransformation              卡通滤波器         .bitmapTransform(new ToonFilterTransformation(this, 0.2F, 10F))
//        SepiaFilterTransformation             乌墨色滤波器       .bitmapTransform(new SepiaFilterTransformation(this, 1.0F))
//        ContrastFilterTransformation          对比度滤波器       .bitmapTransform(new ContrastFilterTransformation(this, 3F))
//        InvertFilterTransformation            反转滤波器         .bitmapTransform(new InvertFilterTransformation(this))
//        PixelationFilterTransformation        像素化滤波器(马赛克).bitmapTransform(new PixelationFilterTransformation(this, 20F))
//        SketchFilterTransformation            素描滤波器         .bitmapTransform(new SketchFilterTransformation(this))
//        SwirlFilterTransformation             旋转滤波器         .bitmapTransform(new SwirlFilterTransformation(this, 1.0F, 0.4F, new PointF(0.5F, 0.5F)))
//        BrightnessFilterTransformation        亮度滤波器         .bitmapTransform(new BrightnessFilterTransformation(this, 0.5F))
//        KuwaharaFilterTransformation          Kuwahara滤波器     .bitmapTransform(new KuwaharaFilterTransformation(this, 10))
//        VignetteFilterTransformation          装饰图滤波器       .bitmapTransform(new VignetteFilterTransformation(this, new PointF(0.5F, 0.5F), new float[]{0.0F, 0.0F, 0.0F}, 0.0F, 0.5F))

 */
object GlideUtils {

    /**
     * 设置图片
     */
    fun setImage(obj: Any, imageView: ImageView, url: String) {
        val requests: GlideRequests
        when (obj) {
            is Activity -> {
                requests = GlideApp.with(obj)
            }
            is Fragment -> {
                requests = GlideApp.with(obj)
            }
            is FragmentActivity -> {
                requests = GlideApp.with(obj)
            }
            is Context -> {
                requests = GlideApp.with(obj)
            }
            is View -> {
                requests = GlideApp.with(obj)
            }
            else -> {
                return
            }
        }

        requests.load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存
            .dontAnimate()//不显示动画
            .error(R.mipmap.ic_launcher)//出错
            .placeholder(R.mipmap.ic_launcher)//默认占位
//            .fitCenter()//取中间
//            .centerCrop()//图片centerCrop
//            .circleCrop()//圆形
            .priority(Priority.HIGH)//设置加载优先级
            .into(imageView)

    }

    //            .apply(
//                GlideOptions()
//                    .circleCrop()
//                    .optionalCircleCrop()
//                    .error(R.mipmap.ic_launcher)
//                    .placeholder(R.mipmap.ic_launcher)
//                    .fitCenter()
//            )//圆形
//            .apply(bitmapTransform(CircleCrop()))
//                //圆形裁剪
//                .apply(bitmapTransform(new CircleCrop()))
//                //正方形裁剪
//                .apply(bitmapTransform(new CropSquareTransformation()))
//                //圆角剪裁
//                .apply(bitmapTransform(new RoundedCornersTransformation(100, 0, RoundedCornersTransformation.CornerType.ALL)))
//                //自定义裁剪
//                .apply(bitmapTransform(new CropTransformation(600, 200, CropTransformation.CropType.CENTER)))
//                //颜色滤镜
//                .apply(bitmapTransform(new ColorFilterTransformation(0x7900CCCC)))
//                //灰度
//                .apply(bitmapTransform(GrayscaleTransformation())
//                        .fitCenter()
//                        .placeholder(R.mipmap.ic_launcher)
//                        .error(R.mipmap.ic_launcher))
//                //模糊
//                .apply(bitmapTransform(new BlurTransformation(25)).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher))
//                //遮盖
//                .apply(bitmapTransform(new MaskTransformation(R.mipmap.ic_launcher)))
//                //卡通滤波器
//                .apply(bitmapTransform(new ToonFilterTransformation()))//bitmapTransform(new ToonFilterTransformation(0.2f,10f)
//                //乌墨色滤波器
//                .apply(bitmapTransform(new SepiaFilterTransformation()))//bitmapTransform(new SepiaFilterTransformation(1.0))
//                //对比度滤波器
//                .apply(bitmapTransform(new ContrastFilterTransformation(3f)))//bitmapTransform(new ContrastFilterTransformation(3f))
//                //反转滤波器
//                .apply(bitmapTransform(new InvertFilterTransformation()))
//                //像素化滤波器(马赛克)
//                .apply(bitmapTransform(new PixelationFilterTransformation()))
//                //素描滤波器
//                .apply(bitmapTransform(new SketchFilterTransformation()))
//                //旋转滤波器
//                .apply(bitmapTransform(new SwirlFilterTransformation()))
//                //亮度滤波器
//                .apply(bitmapTransform(new BrightnessFilterTransformation()))
//                //Kuwahara滤波器
//                .apply(bitmapTransform(new KuwaharaFilterTransformation()))
//                //装饰图滤波器
//                .apply(bitmapTransform(new VignetteFilterTransformation()))
//
//        val multi = MultiTransformation(
//            BlurTransformation(25),
//            RoundedCornersTransformation(128, 0, RoundedCornersTransformation.CornerType.BOTTOM)
//        )
//        .apply(bitmapTransform(multi)
//            .fitCenter())


}