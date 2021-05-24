package com.hirain.app.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageBase64Utils {

    /**
     * 解析并且压缩
     * @param base64String
     * @return
     */
    public static Bitmap decodeStringToBitmap(String base64String){
        //将Base64编码字符串解码成Bitmap
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return matrixBitMap(decodedByte);
    }

    /**
     * 压缩bitmap
     * @param bitmap
     * @return
     */
    public static Bitmap matrixBitMap(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.setScale(0.2f, 0.2f);
        //Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        Bitmap mSrcBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return mSrcBitmap;
    }

    /**
     * 解析图片
     * @param base64String
     * @return
     */
    public static Bitmap stringToBitmap(String base64String){
        //将Base64编码字符串解码成Bitmap
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


        return decodedByte;
    }

    /**
     * 图片转字符串
     * @param bitmap
     * @return
     */
    public static String encodeImageToString(Bitmap bitmap) {
        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            //读取图片到ByteArrayOutputStream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            byte[] bytes = outputStream.toByteArray();
            String strImg = Base64.encodeToString(bytes, Base64.DEFAULT);
            return strImg;
        } catch (IOException e){
            e.printStackTrace();
        }
        return "";


    }

}
