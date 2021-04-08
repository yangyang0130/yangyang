package com.yangyang.rkq.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

/**
 * 登陆
 */
public class LogonActivity extends MyActivity implements View.OnClickListener {
    //输入账号
    private EditText editText_user;
    //输入密码
    private TextView tv_forget_password;
    private EditText editText_password;
    //输入验证码
    private TextView tv_code;
    private TextView now;
    private TextView user, privacy;
    private TextView use_phone;
    private TextView use_password;
    private EditText editText_code;
    private RelativeLayout password, phone;
    private Button button, button_password;
    private CountdownView countdownView;
    private TextView registered;
    private String mr = "18108345337", mryy = "yangyang";
    private String username, userPassword;
    private LogonBody logonBody = new LogonBody();
    //手机号
    private String iPhone;
    // 是否发验证码
    private boolean flag = true;
    //同意隐私协议
    private boolean agree = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_logon);
        initView();
        initListener();
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

    private void initView() {
        editText_password = findViewById(R.id.editText_password);
        editText_user = findViewById(R.id.editText_user);
        editText_code = findViewById(R.id.editText_code);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        tv_code = findViewById(R.id.tv_code);
        tv_forget_password = findViewById(R.id.tv_forget_password);
        use_password = findViewById(R.id.use_password);
        use_phone = findViewById(R.id.use_phone);
        button_password = findViewById(R.id.logon_password);
        user = findViewById(R.id.user);
        privacy = findViewById(R.id.privacy);
        button = findViewById(R.id.btn_logon_code);
        countdownView = findViewById(R.id.cv_code);
        registered = findViewById(R.id.registered);


    }

    private void initListener() {
        use_password.setOnClickListener(this);
        use_phone.setOnClickListener(this);
        button.setOnClickListener(this);
        button_password.setOnClickListener(this);
        user.setOnClickListener(this);
        privacy.setOnClickListener(this);
        registered.setOnClickListener(this);
        countdownView.setOnClickListener(this);
        countdownView.setTotalTime(60);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cv_code:
                if (!TextUtils.isEmpty(editText_user.getText().toString().trim())) {
                    if (editText_user.getText().toString().trim().length() == 11) {
                        iPhone = editText_user.getText().toString().trim();
                        SMSSDK.getVerificationCode("86", iPhone);
                        editText_code.requestFocus();
                    } else {
                        Toast.makeText(LogonActivity.this, "请输入完整电话号码", Toast.LENGTH_LONG).show();
                        editText_user.requestFocus();
                    }
                } else {
                    Toast.makeText(LogonActivity.this, "请输入您的电话号码", Toast.LENGTH_LONG).show();
                    editText_user.requestFocus();
                }
                break;
            case R.id.use_password:
                password.setVisibility(View.VISIBLE);
                use_password.setVisibility(View.GONE);
                phone.setVisibility(View.GONE);
                use_phone.setVisibility(View.VISIBLE);
                tv_forget_password.setVisibility(View.VISIBLE);
                tv_code.setVisibility(View.GONE);
                button.setVisibility(View.GONE);
                button_password.setVisibility(View.VISIBLE);
                registered.setVisibility(View.VISIBLE);
                break;
            case R.id.user:
                Intent intent_user = new Intent(LogonActivity.this, UserActivity.class);
                startActivity(intent_user);
                break;
            case R.id.privacy:
                Intent intent_privacy = new Intent(LogonActivity.this, PrivacyActivity.class);
                startActivity(intent_privacy);
                break;
            case R.id.use_phone:
                password.setVisibility(View.GONE);
                phone.setVisibility(View.VISIBLE);
                use_password.setVisibility(View.VISIBLE);
                use_phone.setVisibility(View.GONE);
                tv_forget_password.setVisibility(View.GONE);
                tv_code.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);
                button_password.setVisibility(View.GONE);
                registered.setVisibility(View.GONE);
                break;
            case R.id.btn_logon_code:
                // 校验
                if (TextUtils.isEmpty(editText_code.getText().toString().trim())) {
                    Toast.makeText(LogonActivity.this, "请先输入手机号并获取验证码", Toast.LENGTH_LONG).show();
                    editText_code.requestFocus();
                    return;
                }
                if (editText_code.getText().toString().trim().length() != 4) {
                    Toast.makeText(LogonActivity.this, "请输入正确的验证码", Toast.LENGTH_LONG).show();
                    editText_code.requestFocus();
                    return;
                }
                // 提交验证码，其中的code表示验证码，如“1357”
                //验证码
                String iCord = editText_code.getText().toString().trim();
                SMSSDK.submitVerificationCode("86", iPhone, iCord);
                flag = false;
                break;
            case R.id.logon_password:
                if (TextUtils.isEmpty(editText_user.getText())){
                    // Toast
                    return;
                }
                if (TextUtils.isEmpty(editText_password.getText())){
                    // Toast
                    return;
                }
                List<LogonBody> logonBodyList = SQLiteHelper.with(this).query(LogonBody.class);
                String username = editText_user.getText().toString();
                String password = editText_password.getText().toString();
                boolean checked = false;
                for (LogonBody body : logonBodyList) {
                    if (username.equals(body.getPhoneNumber()) && password.equals(body.getPassword())){
                        checked = true;
                        break;
                    }
                }
                if (checked){
                    Intent intent = new Intent(LogonActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(LogonActivity.this, "登陆成功", Toast.LENGTH_LONG).show();
                    finish();
                    // 找到了
                }else {
                    Toast.makeText(LogonActivity.this, "当前用户名/密码输入错误", Toast.LENGTH_LONG).show();
                    // 用户名/密码错误
                }
                break;
            case R.id.registered:
                Intent intent_registered = new Intent(LogonActivity.this, RegisteredAty.class);
                startActivity(intent_registered);
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
                    Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
                    logonBody.setPhoneNumber(editText_user.getText().toString());
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
                    Intent intent = new Intent(LogonActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {//服务器验证码发送成功
                    countdownView.start();
                    Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (flag) {
                    countdownView.setVisibility(View.VISIBLE);
                    Toast.makeText(LogonActivity.this, "验证码获取失败，请重新获取", Toast.LENGTH_SHORT).show();
                    editText_user.requestFocus();
                } else {
                    ((Throwable) data).printStackTrace();
                    int resId = getStringRes(LogonActivity.this, "smssdk_network_error");
                    Toast.makeText(LogonActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
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
