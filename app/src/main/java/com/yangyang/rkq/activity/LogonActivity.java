package com.yangyang.rkq.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.yangyang.rkq.Body.LogonBody;
import com.yangyang.rkq.R;
import com.yangyang.rkq.View.layout.CountdownView;
import com.yangyang.rkq.base.MyActivity;

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
    private EditText editText_phone;
    private RelativeLayout password, phone;
    private Button button, button_password;
    private CountdownView countdownView;
    private TextView registered;
    private String mr = "18108345337", mryy = "yangyang";
    private String username, userPassword;
    private LogonBody logonBody;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_logon);
        initView();
        initListener();
    }

    private void initView() {
        editText_password = findViewById(R.id.editText_password);
        editText_user = findViewById(R.id.editText_user);
        editText_phone = findViewById(R.id.editText_phone);
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
        button = findViewById(R.id.logon_code);
        countdownView = findViewById(R.id.code);
        registered = findViewById(R.id.registered);


    }

    private void initListener() {
        use_password.setOnClickListener(this);
        use_phone.setOnClickListener(this);
        countdownView.setOnClickListener(this);
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
            case R.id.code:
                if (!TextUtils.isEmpty(editText_user.getText().toString().trim())) {
                    if (editText_user.getText().toString().trim().length() == 11) {
                        countdownView.start();
                        Toast.makeText(LogonActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                        editText_phone.requestFocus();
                    } else {
                        Toast.makeText(LogonActivity.this, "请输入完整电话号码", Toast.LENGTH_LONG).show();
                        phone.requestFocus();
                    }
                } else {
                    Toast.makeText(LogonActivity.this, "请输入您的电话号码", Toast.LENGTH_LONG).show();
                    phone.requestFocus();
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
                break;
            case R.id.logon_code:
                if (!TextUtils.isEmpty(editText_phone.getText().toString().trim())) {
                    if (editText_phone.getText().toString().trim().length() == 4) {
                        logonBody.setPhoneNumber(editText_phone.getText().toString());
                        Intent intent = new Intent(LogonActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LogonActivity.this, "请输入正确的验证码", Toast.LENGTH_LONG).show();
                        editText_phone.requestFocus();
                    }
                } else {
                    Toast.makeText(LogonActivity.this, "请先输入手机号并获取验证码", Toast.LENGTH_LONG).show();
                    editText_phone.requestFocus();
                }
                break;
            case R.id.logon_password:
                if () {
                    Intent intent = new Intent(LogonActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else if (editText_user.getEditableText().length() == 11 && editText_password.getEditableText().length() == 0) {
                    Toast.makeText(LogonActivity.this, "请输入密码", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LogonActivity.this, "账号密码输入错误", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.registered:
                Intent intent_registered=new Intent(LogonActivity.this, RegisteredAty.class);
                startActivity(intent_privacy);
                break;
            default:
                break;
        }
    }
}
