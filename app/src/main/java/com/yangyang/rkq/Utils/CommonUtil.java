package com.yangyang.rkq.Utils;

import android.content.Context;

import com.yangyang.rkq.Frament.Fragment_home;


/**
 * Created by yy on 2020/5/27.
 */

public class CommonUtil {
    public static int dp2px(Fragment_home context, float dpValue){
        if (null==context){
            return 0;
        }
        final  float scale=context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public static int getScreenWidth(Fragment_home context){
        if (null==context){
            return 0;
        }
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}
