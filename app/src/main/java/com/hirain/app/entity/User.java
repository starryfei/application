package com.hirain.app.entity;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID=1L;
//    private String userId;
    private String name;
    private String sex;
    private String height;
    private String weight;
    private String age;
    private String image;
    private String time;
    private String command ="register";

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", age='" + age + '\'' +
                ", time='" + time + '\'' +
                ", command='" + command + '\'' +
                '}';
    }

    public User() {
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getWeight() {
        return weight;
    }



    public void setCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public void time(String lastTime) {
        this.time = lastTime;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

//
//    @Override
//    public int compareTo(User o) {
//        int i = getName().compareTo(o.getName());
//        return i;
//    }
}
