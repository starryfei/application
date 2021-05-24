package com.hirain.app.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageBase64Utils {


    public static Bitmap decodeStringToBitmap(String base64String){
        //将Base64编码字符串解码成Bitmap
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


//        Matrix matrix = new Matrix();
//        matrix.setScale(0.1f, 0.1f);
//        //Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.test);
//        Bitmap mSrcBitmap = Bitmap.createBitmap(decodedByte, 0, 0, decodedByte.getWidth(), decodedByte.getHeight(), matrix, true);
//        decodedByte = null;

        return matrixBitMap(decodedByte);
    }

    public static Bitmap matrixBitMap(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.setScale(0.2f, 0.2f);
        //Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        Bitmap mSrcBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return mSrcBitmap;
    }

    public static Bitmap stringToBitmap(String base64String){
        //将Base64编码字符串解码成Bitmap
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


//        Matrix matrix = new Matrix();
//        matrix.setScale(0.1f, 0.1f);
//        //Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.test);
//        Bitmap mSrcBitmap = Bitmap.createBitmap(decodedByte, 0, 0, decodedByte.getWidth(), decodedByte.getHeight(), matrix, true);
//        decodedByte = null;

        return decodedByte;
    }


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
