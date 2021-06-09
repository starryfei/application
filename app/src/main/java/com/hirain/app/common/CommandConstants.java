package com.hirain.app.common;

public class CommandConstants {
    public final static String OPEN_DOOR_COMMAND = "{\n" +
            "    \"command\":\"door\","+
            "    \"status\":\"open\""+
            "}" ;

    public final static String CLOSE_DOOR_COMMAND ="{\n" +
            "    \"command\":\"door\","+
            "    \"status\":\"close\""+
            "}";

    public final static String DRIVER_CHECK_COMMAND = "{\n" +
            "    \"command\":\"PWMStart\","+
            "    \"status\":\"start\""+
            "}";

    public final static  String VOICE_COMMAND= "{\n" +
            "    \"command\":\"LipVoice\","+
            "    \"status\":\"start\""+
            "}";

    public final static String TIRED_COMMAND =  "{\n" +
            "    \"command\":\"tired\","+
            "    \"status\":\"start\""+
            "}";

    public final static String ISLAND_FW_COMMAND =  "{\n" +
            "    \"command\": \"island_forward\"\n" +
            "}";

    public final static String ISLAND_AF_COMMAND =  "{\n" +
            "    \"command\": \"island_backward\"\n" +
            "}";

    public final static String ISLAND_STOP_COMMAND =  "{\n" +
            "    \"command\": \"island_stop\"\n" +
            "}";
}
