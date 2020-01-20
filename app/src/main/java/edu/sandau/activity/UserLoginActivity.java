package edu.sandau.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;


import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.online_exam_app.R;

import java.util.HashMap;
import java.util.Map;

import edu.sandau.util.Data;
import edu.sandau.util.MyClientFact;
import entity.UserInfo;
import service.UserService;


public class UserLoginActivity extends Activity {

    private EditText account,password;
    private Button loginBtn;
    private TextView registerTxT;
    private Button changeUrlBtn;
    private ProgressBar pBar;
    private final int CIRCLING = 450;
    private final int FINISH = 451;
    private final int NETWORK_ERR = 452;
    private final int PASSWORD_ERR = 453;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Toast toast;
            switch (msg.what){
                case CIRCLING:
                    pBar.setVisibility(View.VISIBLE);
                    break;
                case FINISH:
                    pBar.setVisibility(View.INVISIBLE);
                    toast = Toast.makeText(getApplicationContext(), "登陆成功",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    Intent intent = new Intent();
                    intent.setClass(UserLoginActivity.this, MeanActivity.class);
                    startActivity(intent);
                    UserLoginActivity.this.finish();

                    break;
                case NETWORK_ERR:
                    pBar.setVisibility(View.INVISIBLE);
                    toast = Toast.makeText(getApplicationContext(), "网络连接异常",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    break;
                case PASSWORD_ERR:
                    pBar.setVisibility(View.INVISIBLE);
                    toast = Toast.makeText(getApplicationContext(), "用户名或密码错误",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.user_login);

        account = (EditText)findViewById(R.id.user_login_username);
        password = (EditText)findViewById(R.id.user_login_password);
        loginBtn = (Button)findViewById(R.id.user_login_loginBtn);
        registerTxT = (TextView)findViewById(R.id.user_login_registerTxT);
        pBar = (ProgressBar)findViewById(R.id.user_login_pBar);
        pBar.setVisibility(View.INVISIBLE);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(account.getText().toString().equals("")){
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(), "账号不能为空",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    return;
                }else if(account.getText().toString().length()>=20){
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(), "用户名长度超出限制",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    return;
                }
                if (password.getText().toString().equals("")){
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(), "密码不能为空",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    return;
                }else if(password.getText().toString().length()>=20){
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(), "密码长度超出限制",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = CIRCLING;
                        handler.sendMessage(message);

                        message = new Message();
                        try{
                            UserService us = MyClientFact.getInstance().getUserService();
                            Map<String,String> userInfo = new HashMap<String,String>();
                            userInfo.put("name",account.getText().toString());
                            userInfo.put("password",password.getText().toString());
                            UserInfo user = us.login(userInfo);
                            if(user!=null){
                                Data.user=user;
                                Data.saveUser(user);
                                message.what = FINISH;
                                handler.sendMessage(message);
                            }else{
                                message.what = PASSWORD_ERR;
                                handler.sendMessage(message);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            if(e.getMessage().contains("HTTP Status 400")){
                                message.what = PASSWORD_ERR;
                                handler.sendMessage(message);
                                return;
                            }else {
                                message.what = NETWORK_ERR;
                                handler.sendMessage(message);
                                return;
                            }
                        }
                    }
                }).start();
            }
        });
        registerTxT.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent();
                        intent.setClass(UserLoginActivity.this, RegisterActivity.class);
                        startActivity(intent);
                        UserLoginActivity.this.finish();
                    }
                }
                ).start();
            }
        });
    }
}



