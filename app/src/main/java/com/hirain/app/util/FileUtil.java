package com.hirain.app.util;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static com.hirain.app.common.Constants.APP_LOG;

public class FileUtil {

    /**
     * @param name    用户名
     * @param time    名字
     * @param content 内容
     */

    public static void createFile(Context context, String name, String type, String time, String content) {
        File rootPath = rootFolder(name, "healthinfo");
        // root/user/image/
        // root/user/healthinfo/{0/1}
        File typeFile = new File(rootPath + File.separator + type);
        if (!typeFile.exists()) {
            typeFile.mkdir();
        }
        String fileName = time;
        File file = new File(typeFile, fileName + ".txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Path path = Paths.get(file.getAbsolutePath());

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8,
                StandardOpenOption.APPEND)) {
            writer.newLine();
            writer.write(content);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //将文件保存到公共的媒体文件夹
//这里的filepath不是绝对路径，而是某个媒体文件夹下的子路径，和沙盒子文件夹类似
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static void saveImage(Context context, String userName, Bitmap bitmap) {
        String fileName = TimeUtil.getTime();
        try {
            //设置保存参数到ContentValues中
            ContentValues contentValues = new ContentValues();
            //设置文件名
            contentValues.put(MediaStore.Downloads.DISPLAY_NAME, fileName);
            //兼容Android Q和以下版本
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                //android Q中不再使用DATA字段，而用RELATIVE_PATH代替
                //RELATIVE_PATH是相对路径不是绝对路径
                //DCIM是系统文件夹，关于系统文件夹可以到系统自带的文件管理器中查看，不可以写没存在的名字
                contentValues.put(MediaStore.Downloads.RELATIVE_PATH, "Download/" + userName+"/image");
                //contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Music/signImage");
                contentValues.put(MediaStore.Downloads.IS_PENDING, true);

            }
            //设置文件类型
            contentValues.put(MediaStore.Downloads.MIME_TYPE, "image/JPEG");

            Uri uri = context.getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues);
            if (uri != null) {
                //若生成了uri，则表示该文件添加成功
                //使用流将内容写入该uri中即可
                OutputStream outputStream = context.getContentResolver().openOutputStream(uri);
                if (outputStream != null) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                    outputStream.flush();
                    outputStream.close();
                }
            }
        } catch (Exception e) {
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static void saveFile(Context context, String userName, String type, String time, String content) {
        String fileName = time;
        try {
            //设置保存参数到ContentValues中
            ContentValues contentValues = new ContentValues();
            //设置文件名
            contentValues.put(MediaStore.Downloads.DISPLAY_NAME, fileName+".txt");
//            contentValues.put(MediaStore.Downloads.D, fileName);

            contentValues.put(MediaStore.Downloads.RELATIVE_PATH, "Download/"+ userName+"/healthinfo/"+type);
            contentValues.put(MediaStore.Downloads.IS_PENDING, true);

//            contentValues.put(MediaStore.DownloadColumns.CONTENT_TYPE, MediaStore.Downloads.CONTENT_TYPE);
            contentValues.put(MediaStore.Downloads.MIME_TYPE, "application/txt");
            Uri contentUri = MediaStore.Downloads.getContentUri("external");
            Uri uri = context.getContentResolver().insert(contentUri,contentValues);
            if (uri != null) {
                //若生成了uri，则表示该文件添加成功
                //使用流将内容写入该uri中即可
                OutputStream outputStream = context.getContentResolver().openOutputStream(uri);
                outputStream.write(content.getBytes());
                outputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


@Deprecated
    public static void saveImageToStream(Bitmap bitmap, String userName) {
        String fileName = TimeUtil.getTime() + ".png";
        File rootPath = rootFolder(userName, "image");

        File file = new File(rootPath, fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream(file)) {

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bitmap.recycle();
        }
        System.out.println(file.getAbsolutePath());


    }

    @NotNull
    public static File rootFolder(String userName, String sourceName) {


        File rootFile = Environment.getExternalStorageDirectory();
        File externalStoragePublicDirectory1 = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS);

        Log.d(APP_LOG, rootFile.getPath() + "》》》 " + rootFile.getAbsolutePath());
        String rootPath = rootFile.getAbsolutePath() + File.separator + "app_file";
        File myDir = new File(rootPath);
        if (!myDir.exists()) {
            myDir.mkdir();
        }
        File userFile = new File(rootPath + File.separator + userName);
        if (!userFile.exists()) {
            userFile.mkdir();
        }
        File sourceFile = new File(userFile.getAbsolutePath() + File.separator + sourceName);
        if (!sourceFile.exists()) {
            sourceFile.mkdir();
        }
        return sourceFile;
    }
}
