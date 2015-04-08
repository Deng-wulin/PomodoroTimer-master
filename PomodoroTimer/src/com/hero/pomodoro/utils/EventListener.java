package com.hero.pomodoro.utils;

import java.lang.reflect.Method;

import android.view.View;
import android.view.View.OnClickListener;

/** 事件处理中介
 * @author XFJ-01
 *
 */
public class EventListener implements OnClickListener{
	//时间执行对象
	private Object handler;
	//点击方法
	private String clickMethod;
	//上一次点击时间
	private static long lastclicktime=0;
	
	public EventListener(Object handler) {
		this.handler = handler;
	}
	
	public EventListener click(String method){
		this.clickMethod = method;
		return this;
	}
	
	/**
	 * 点击事件
	 * @param handler
	 * @param methodName
	 * @param params
	 * @return
	 */
	private static Object invokeClickMethod(Object handler, String methodName,  Object... params){
		if(handler == null) return null;
		Method method = null;
		try{  
			if(System.currentTimeMillis()-lastclicktime>300){
				//method = handler.getClass().getDeclaredMethod(methodName,View.class);
				method = handler.getClass().getMethod(methodName,View.class);
				Object result=method.invoke(handler, params);	
				lastclicktime=System.currentTimeMillis();
				return result;
			}
		}catch(Exception e){
			try {
					//method = handler.getClass().getDeclaredMethod(methodName);
					method = handler.getClass().getMethod(methodName);
					Object result=method.invoke(handler);	
					lastclicktime=System.currentTimeMillis();
					return result;
			} catch (Exception e1) {
				e1.printStackTrace();
				//handlerException(e);
			} 
		}
		
		return null;
		
	}
	
	@Override
	public void onClick(View v) {
		invokeClickMethod(handler, clickMethod, v);
	}

}
