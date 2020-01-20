package edu.sandau.util;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import entity.UserInfo;

public class Data {
    public static UserInfo user = loadUser();    //登录一次永久保存到手机上
    public static String base_url = loadServiceUrl();
    private static String DEFAULT_URL = "http://192.168.0.102:8888/rest";  //当DEBUG==false时，采用这个地址(ip改为自己服务器的ip)
    public static boolean debug = false;    //点击头像三次改为Debug模式，正式发布时应该会注释掉相关代码
    public static boolean demo = true;

    //清除存储的账号文件
    public static void clearCache(){
        Context context = MyApplication.getContext();
        File file = context.getFileStreamPath("user");
        if(file.exists()&&file.isFile())
            file.delete();
    }

    public static void clearData(){
        Context context = MyApplication.getContext();
        File file = context.getFileStreamPath("user");
        if(file.exists()&&file.isFile())
            file.delete();

        user = null;
    }

    public static void saveUser(UserInfo auser){
        Gson gson= new Gson();
        String json = gson.toJson(auser);
        Context context = MyApplication.getContext();

        FileOutputStream fos = null;
        try{
            fos= context.openFileOutput("user",Context.MODE_PRIVATE);
            fos.write(json.getBytes());
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                fos.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }

    }
    public static UserInfo loadUser(){
        UserInfo auser;
        String json = null;
        Context context = MyApplication.getContext();

        File file = context.getFileStreamPath("user");
        if(!file.exists()) {
            return null;
        }
        FileReader fr = null;
        BufferedReader br = null;
        try{
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            json = br.readLine();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                br.close();
                fr.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }

        Gson gson= new Gson();
        auser = gson.fromJson(json, UserInfo.class);
        return auser;
    }

    public static void saveServiceUrl(String url){

        Context context = MyApplication.getContext();

        FileOutputStream fos = null;
        try{
            fos= context.openFileOutput("service_url",Context.MODE_PRIVATE);
            fos.write(url.getBytes());
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                fos.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public static String loadServiceUrl(){
        //base_url="http://192.168.0.153:8888/demo/rest";
        if(!debug)
            return DEFAULT_URL;
        String url;
        Context context = MyApplication.getContext();

        File file = context.getFileStreamPath("service_url");
        if(!file.exists()) {
            return null;
        }
        FileReader fr = null;
        BufferedReader br = null;
        try{
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            url = br.readLine();
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }finally{
            try{
                br.close();
                fr.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return url;
    }
}
