package com.yangyang.rkq.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.mob.MobSDK;
import com.mob.OperationCallback;
import com.yangyang.rkq.Body.LogonBody;
import com.yangyang.rkq.R;
import com.yangyang.rkq.View.layout.CountdownView;
import com.yangyang.rkq.base.MyActivity;
import com.yangyang.rkq.db.SQLiteHelper;

import java.util.List;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static com.mob.tools.utils.ResHelper.getStringRes;

//注册
public class RegisteredAty extends MyActivity implements View.OnClickListener {
    private EditText editText_user;
    private EditText editText_password;
    private EditText editText_password_again;
    private Button btn_registered;
    private ImageView ivBack;
    private CountdownView countdownView;

    private EditText editText_code;
    private LogonBody logonBody = new LogonBody();
    //同意隐私协议
    private boolean agree = true;
    //手机号
    private String iPhone;
    // 是否发验证码
    private boolean flag = true;

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.aty_registered);
        intView();
        intListener();
        intData();
    }

    private void intView() {
        editText_user = findViewById(R.id.editText_user);
        editText_password = findViewById(R.id.editText_password);
        editText_password_again = findViewById(R.id.editText_password_again);
        btn_registered = findViewById(R.id.btn_registered);
        editText_code = findViewById(R.id.editText_code);
        ivBack = findViewById(R.id.iv_back);
        countdownView=findViewById(R.id.cv_code);
    }

    private void intListener() {

        btn_registered.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        countdownView.setOnClickListener(this);


    }

    private void intData() {
        MobSDK.submitPolicyGrantResult(agree, new OperationCallback<Void>() {
            @Override
            public void onComplete(Void aVoid) {
                // 协议回调
                Log.d("EasyAndroid", "submitPolicyGrantResult-----onComplete--->" + Void.TYPE);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.d("EasyAndroid", "submitPolicyGrantResult-----onComplete--->" + throwable.toString());
            }
        });
        EventHandler eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                // TODO 此处不可直接处理UI线程，处理后续操作需传到主线程中操作
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);

            }
        };

//注册一个事件回调监听，用于处理SMSSDK接口请求的结果
        SMSSDK.registerEventHandler(eh);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                if (editText_password.getText().toString().equals(editText_password_again.getText().toString())) {
                    logonBody.setPhoneNumber(editText_user.getText().toString());
                    logonBody.setPassword(editText_password_again.getText().toString());
                    List<LogonBody> logonBodyList = SQLiteHelper.with(getContext()).query(LogonBody.class);
                    boolean queryGet = false;

                    for (LogonBody body : logonBodyList) {
                        if (editText_user.getText().toString().equals(body.getPhoneNumber())) {
                            queryGet = true;
                            break;
                        }
                    }
                    if (!queryGet) {
                        long insert = SQLiteHelper.with(getContext()).insert(logonBody);
                    }
                    Log.d(TAG, "---------------数据库---------" + logonBodyList);
                    Toast.makeText(RegisteredAty.this, "注册成功", Toast.LENGTH_SHORT).show();
                }
                finish();
                break;
            case R.id.cv_code:
                if (!TextUtils.isEmpty(editText_user.getText().toString().trim())) {
                    if (editText_user.getText().toString().trim().length() == 11) {
                        iPhone = editText_user.getText().toString().trim();
                        SMSSDK.getVerificationCode("86", iPhone);
                        editText_code.requestFocus();
                    } else {
                        Toast.makeText(RegisteredAty.this, "请输入完整电话号码", Toast.LENGTH_LONG).show();
                        editText_user.requestFocus();
                    }
                } else {
                    Toast.makeText(RegisteredAty.this, "请输入您的电话号码", Toast.LENGTH_LONG).show();
                    editText_user.requestFocus();
                }
                break;
            case R.id.btn_registered:
                // 校验
                if (TextUtils.isEmpty(editText_user.getText().toString().trim())) {
                    Toast.makeText(RegisteredAty.this, "请先输入手机号并获取验证码", Toast.LENGTH_LONG).show();
                    editText_user.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(editText_password.getText())
                        || TextUtils.isEmpty(editText_password_again.getText())) {
                    // Toast
                    Toast.makeText(RegisteredAty.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (!editText_password.getText().toString().equals(editText_password_again.getText().toString())) {
                    // Toast
                    Toast.makeText(RegisteredAty.this, "请确认两次输入的密码是否一致", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (editText_code.getText().toString().trim().length() != 4) {
                    Toast.makeText(RegisteredAty.this, "请输入正确的验证码", Toast.LENGTH_LONG).show();
                    editText_code.requestFocus();
                    return;
                }
                // 提交验证码，其中的code表示验证码，如“1357”
                //验证码
                String iCord = editText_code.getText().toString().trim();
                SMSSDK.submitVerificationCode("86", iPhone, iCord);
                flag = false;
                break;
            default:
                break;
        }
    }

    @SuppressWarnings("all")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            Log.d("EasyAndroid", "event=" + event);
            Log.d("EasyAndroid", "result---->" + result);
            if (result == SMSSDK.RESULT_COMPLETE) {
                //短信注册成功后，返回MainActivity,然后提示新好友
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功,验证通过

                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {//服务器验证码发送成功
                    countdownView.start();
                    Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (flag) {
                    countdownView.setVisibility(View.VISIBLE);
                    Toast.makeText(RegisteredAty.this, "验证码获取失败，请重新获取", Toast.LENGTH_SHORT).show();
                    editText_user.requestFocus();
                } else {
                    ((Throwable) data).printStackTrace();
                    int resId = getStringRes(RegisteredAty.this, "smssdk_network_error");
                    Toast.makeText(RegisteredAty.this, "验证码错误", Toast.LENGTH_SHORT).show();
                    editText_code.selectAll();
                }

            }

        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }
}
