package com.yangyang.rkq.activity;

import androidx.annotation.IdRes;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yangyang.rkq.Frament.Fragment_circle;
import com.yangyang.rkq.Frament.Fragment_home;
import com.yangyang.rkq.Frament.Fragment_message;
import com.yangyang.rkq.Frament.Fragment_user;
import com.yangyang.rkq.R;

public class MainActivity extends FragmentActivity {
    private RadioGroup radioGroup;
    private RadioButton rb_main_home;
    private RadioButton rb_main_circle;
    private RadioButton rb_main_message;
    private RadioButton rb_main_user;
    private Fragment_home frg_home;
    private Fragment_circle frg_circle;
    private Fragment_message frg_message;
    private Fragment_user frg_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rb_main_home = findViewById(R.id.rb_main_home);
        rb_main_circle = findViewById(R.id.rb_main_circle);
        rb_main_message = findViewById(R.id.rb_main_message);
        rb_main_user = findViewById(R.id.rb_main_user);
        Drawable drawable_home = getResources().getDrawable(R.drawable.main_rb_home);
        drawable_home.setBounds(0, 0, 50, 50);
        rb_main_home.setCompoundDrawables(null, drawable_home, null, null);
        Drawable drawable_friend = getResources().getDrawable(R.drawable.main_radiobutton_circle);
        drawable_friend.setBounds(0, 0, 50, 50);
        rb_main_circle.setCompoundDrawables(null, drawable_friend, null, null);
        Drawable drawable_message = getResources().getDrawable(R.drawable.main_rb_shopping);
        drawable_message.setBounds(0, 0, 50, 50);
        rb_main_message.setCompoundDrawables(null, drawable_message, null, null);
        Drawable drawable_user = getResources().getDrawable(R.drawable.main_rb_user);
        drawable_user.setBounds(0, 0, 50, 50);
        rb_main_user.setCompoundDrawables(null, drawable_user, null, null);
        setTabSelection(0);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int id) {
                switch (id) {
                    case R.id.rb_main_home:
                        setTabSelection(0);
                        break;
                    case R.id.rb_main_circle:
                        setTabSelection(1);
                        break;
                    case R.id.rb_main_message:
                        setTabSelection(2);
                        break;
                    case R.id.rb_main_user:
                        setTabSelection(3);
                        break;
                    default:
                        break;
                }
            }
        });

        getApplicationContext();
        getBaseContext();

    }

    public void setTabSelection(int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragment(transaction);
        switch (index) {
            case 0:
                if (frg_home == null) {
                    frg_home = new Fragment_home();
                    transaction.add(R.id.fragment, frg_home);
                } else {
                    // 如果frg_home不为空，则直接将它显示出来
                    transaction.show(frg_home);
                }
                break;
            // 点击圈子tab
            case 1:
                if (frg_circle == null) {
                    // 如果frg_message为空，则创建一个并添加到界面上
                    frg_circle = new Fragment_circle();
                    transaction.add(R.id.fragment, frg_circle);
                } else {
                    // 如果frg_message不为空，则直接将它显示出来
                    transaction.show(frg_circle);
                }
                break;
            case 2://点击消息tab
                if (frg_message == null) {
                    // 如果frg_message为空，则创建一个并添加到界面上
                    frg_message = new Fragment_message();
                    transaction.add(R.id.fragment, frg_message);
                } else {
                    // 如果frg_message不为空，则直接将它显示出来
                    transaction.show(frg_message);
                }
                break;
            // 点击我的tab
            case 3:
                if (frg_user == null) {
                    // 如果frg_user为空，则创建一个并添加到界面上
                    frg_user = new Fragment_user();
                    transaction.add(R.id.fragment, frg_user);
                } else {
                    // 如果frg_user不为空，则直接将它显示出来
                    transaction.show(frg_user);
                }
                break;

        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (frg_home != null) {
            transaction.hide(frg_home);
        }
        if (frg_circle != null) {
            transaction.hide(frg_circle);
        }
        if (frg_message != null) {
            transaction.hide(frg_message);
        }
        if (frg_user != null) {
            transaction.hide(frg_user);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}