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

    public final static String NUC_SERVER_SUB = "tcp://192.168.8.63:5556";
    public final static String NUC_SERVER_PUB = "tcp://192.168.8.63:5555";


    // 和大脑的Server的地址
    public final static String ECU_SERVER = "tcp://192.168.8.156:6668";


    // 和大脑的Server pub 的地址
    public final static String ECU_SERVER_SUB = "tcp://192.168.8.156:6666";

    public final static String APP_LOG ="APP_LOG";

    public final static List<String> MODL_LIST = Arrays.asList("通勤模式","会议模式","自驾游模式",
            "情侣模式","健康关怀模式","亲子模式","新手司机模式","敬请期待");

    public final static List<String> CHECK_LIST = Arrays.asList("驾驶员检测",
            "视线跟踪","健康监控","乘员检测","IOT互联","唇音融合","敬请期待");

    public final static List<String> MODE_DESC = Arrays.asList("可体验正常下班通勤路上，约会前的冥想模式，" +
            "座椅自动调整到舒适状态，播放舒适音乐，可语音控制播放电影、玩游戏",
            "可体验车上开会，通过语音实现实时会议开启结束，转录会议内容，微信分享会议记录",
            "体验旅行自动规划路线，有美景自动拍照并和家里分享，自动预定景区门票和酒店" ,
            "体验来自桃子的情人节祝福，自动预约浪漫餐厅，享受女王模式，检测开心自动拍照并分享给家人",
            "体验用健康芯片检测，并可预约专属医生，当检测出驾驶员疲劳时，自动安排节目缓解",
            "检测宝宝哭泣，可自动安排讲故事帮助哄睡宝宝，可以让副驾家人移动到后排照顾宝宝，可以自动打开投影和宝宝一起玩游戏",
            "为新手司机提供360以及语音操作指导，体验自动停车与接送，自动除雾，和家里通话以及开启回家模式，自动开起家里电器" ,
            "");
    public final static List<String> CHECK_DESC =Arrays.asList("检测驾驶员的车上行为动作",
            "在特定区域可根据驾驶员视线角度显示建筑物名称",
            "根据健康监测芯片监测成员健康信息",
            "上车后手机、后排家庭成员（孩子、宠物）遗忘在车上后，会通过语音和氛围灯提示",
            "在车上可以通过语音打开关闭家里监控、台灯和风扇",
            "语音免唤醒，可实现直接语音控制空调开关、导航、听音乐相关操作",
            "");



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
