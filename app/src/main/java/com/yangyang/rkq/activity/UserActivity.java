package com.yangyang.rkq.activity;

import android.os.Bundle;

import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yangyang.rkq.R;
import com.yangyang.rkq.base.MyActivity;

/**
 * 用户协议
 */
public class UserActivity extends MyActivity implements View.OnClickListener {
    private ImageButton button_back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_user);
        button_back=(ImageButton)findViewById(R.id.button_back);
        button_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_back:
                finish();
                break;
        }

    }
}
