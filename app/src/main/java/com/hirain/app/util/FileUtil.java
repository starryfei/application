package com.hirain.app.util;

import android.graphics.Bitmap;
import android.os.Environment;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileUtil {

    /**
     *
     * @param name 用户名
     *  @param time 名字
     * @param content   内容
     */

    public static void createFile(String name,String type, String time, String content) {
        File rootPath = rootFolder(name,"healthinfo");
        // root/user/image/
        // root/user/healthinfo/{0/1}
        File typeFile =new File(rootPath+File.separator+type);
        if(!typeFile.exists()) {
            typeFile.mkdir();
        }
        String fileName = time;
        File file = new File(typeFile, fileName+".txt");
        System.out.println(file.getAbsolutePath());
        if(!file.exists()) {
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
    public static void saveImageToStream(Bitmap bitmap, String userName) {
       String fileName = TimeUtil.getTime()+".png";
        File rootPath = rootFolder(userName,"image");

        File file = new File(rootPath, fileName);
        try (FileOutputStream outputStream = new FileOutputStream(file)){

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bitmap.recycle();
        }


    }

    @NotNull
    private static File rootFolder(String userName,String sourceName) {
        File rootFile = Environment.getExternalStorageDirectory();
        String rootPath = rootFile.getAbsolutePath() + File.separator + "app_file";
        File myDir = new File(rootPath);
        if (!myDir.exists()) {
            myDir.mkdir();
        }
        File userFile =new File(rootPath+File.separator+userName);
        if(!userFile.exists()) {
            userFile.mkdir();
        }
        File sourceFile =new File(userFile.getAbsolutePath()+File.separator+sourceName);
        if(!sourceFile.exists()) {
            sourceFile.mkdir();
        }
        return sourceFile;
    }
}
