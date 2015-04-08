package com.hero.pomodoro;

import cn.bmob.v3.Bmob;
import android.app.Application;

public class MyApplication extends Application{
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
        // 使用时请将第二个参数Application ID替换成你在Bmob服务器端创建的Application ID
        Bmob.initialize(this, "36270e6bf9ee05662b586a4f2e340746");
	}

}
