package com.hirain.app.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constants {
    // 与商汤Server的地址
//    public final static String NUC_SERVER_SUB = "tcp://192.168.1.123:5556";
    // sub 需要地址，但是pub 不需要地址
//    public final static String NUC_SERVER_SUB = "tcp://192.168.1.100:5556";

    public final static String NUC_SERVER_SUB = "tcp://192.168.31.63:5556";
    public final static String NUC_SERVER_PUB = "tcp://192.168.31.63:5555";


    // 和大脑的Server的地址
    public final static String ECU_SERVER = "tcp://192.168.31.156:6668";


    // 和大脑的Server pub 的地址
    public final static String ECU_SERVER_SUB = "tcp://192.168.31.156:6666";

    public final static String APP_LOG ="APP_LOG";

    public final static List<String> MODL_LIST = Arrays.asList("通勤模式","会议模式","自驾游模式",
            "情侣模式","健康关怀模式","亲子模式","新手司机模式","敬请期待");

    public final static List<String> CHECK_LIST = Arrays.asList("驾驶员检测",
            "视线跟踪","健康监控","乘员检测","IOT互联","敬请期待");
//    public final static List<Integer> CHECK_LIST_IMAGE= Arrays.asList(R.mipmap.check0,
//            R.mipmap.check1,R.mipmap.check2,R.mipmap.check3,R.mipmap.check0,R.mipmap.check0,R.mipmap.check0)

    public final static String VOICE = "主人，咱们去总统府的旅游该出发了，已为您准备好车辆，请记得随身携带好身份证" +
            "随身物品等。另外，目的地会有小雨，请准备好雨伞。";

    public final static Map<String,String> AGE_INPUTS = new HashMap<>();
    public final static Map<String,String> WEIGHT_INPUTS = new HashMap<>();
    public final static Map<String,String> HEiGHT_INPUTS = new HashMap<>();


    static {
        AGE_INPUTS.put("10-20岁","15");
        AGE_INPUTS.put("20-30岁","25");
        AGE_INPUTS.put("30-40岁","35");
        AGE_INPUTS.put("40-50岁","45");
        AGE_INPUTS.put("50-60岁","55");
        AGE_INPUTS.put("60-70岁","75");



        WEIGHT_INPUTS.put("40-50kg","45");
        WEIGHT_INPUTS.put("50-60kg","55");
        WEIGHT_INPUTS.put("60-70kg","65");
        WEIGHT_INPUTS.put("70-80kg","75");
        WEIGHT_INPUTS.put("80-90kg","85");

        HEiGHT_INPUTS.put("140-150cm","145");
        HEiGHT_INPUTS.put("150-160cm","155");
        HEiGHT_INPUTS.put("160-170cm","165");
        HEiGHT_INPUTS.put("170-180cm","175");
        HEiGHT_INPUTS.put("180-190cm","185");
        HEiGHT_INPUTS.put("190-200cm","195");
    }
    public static int ageIndex(String age){
        Integer integer = Integer.valueOf(age);
        if(integer<20){
            return 0;
        }else if(integer>=20 & integer<30){
            return 1;
        }else if(integer>=30 & integer<40){
            return 2;
        }else if(integer>=40 & integer<50){
            return 3;
        }else if(integer>=50 & integer<60){
            return 4;
        } else {
            return 5;
        }

    }
    public static int heightIndex(String height){
        Integer integer = Integer.valueOf(height);
        if(integer<150){
            return 0;
        }else if(integer>=150 & integer<160){
            return 1;
        }else if(integer>=160 & integer<170){
            return 2;
        }else if(integer>=170 & integer<180){
            return 3;
        }else if(integer>=180 & integer<190){
            return 4;
        } else {
            return 5;
        }

    }
    public static int weightIndex(String weight){
        Integer integer = Integer.valueOf(weight);
        if(integer<50){
            return 0;
        }else if(integer>=50 & integer<60){
            return 1;
        }else if(integer>=60 & integer<70){
            return 2;
        }else if(integer>=70 & integer<80){
            return 3;
        }else {
            return 4;
        }

    }


}
