package com.hero.pomodoro.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewQuery {
	private static final String TAG = "ViewQuery";
	private View root;
	private Activity act;
	private Context context;
	
	private View view;
	/**
	 * 保存当前已存在的view,避免重复查找
	 */
	private SparseArray<View> views;
	
	public ViewQuery(Activity act){
		this.act = act;
		views = new SparseArray<View>();
	}
	public ViewQuery(View root){
		this.root = root;
		this.view = root;
		views = new SparseArray<View>();
	}
	public ViewQuery(Context ctx){
		this.context = ctx;
		views = new SparseArray<View>();
	}
	
	/**
	 * Return the context of activity or view.
	 * @return Context
	 */
	public Context getContext(){
		if(act != null){
			return act;
		}
		if(root != null){
			return root.getContext();
		}
		return context;
	}
	/**
	 * id
	 */
	public ViewQuery id(int id){
		this.view = findView(id);
		return self();
	}
	
	public ViewQuery id(View view){
		this.view = view;
		return self();
	}
	
	public ViewQuery text(int resid){
		if(view instanceof TextView){			
			TextView tv = (TextView) view;
			tv.setText(resid);
		}
		return self();
	}
	
	public ViewQuery text(CharSequence text){
		if(view instanceof TextView){			
			TextView tv = (TextView) view;
			tv.setText(text);
		}
		return self();
	}
	
	public ViewQuery text(CharSequence text, boolean goneIfEmpty){
		if(goneIfEmpty && (text == null || text.length() == 0)){
			return gone();
		}else{
			return text(text);
		}
	}
	
	/**
	 * Set the text color of a TextView. Note that it's not a color resource id.
	 *
	 * @param color color code in ARGB
	 * @return self
	 */
	public ViewQuery textColor(int color){
		if(view instanceof TextView){			
			TextView tv = (TextView) view;
			tv.setTextColor(color);
		}
		return self();
	}
	
	
	
	/**
     * Set the text color of a TextView from  a color resource id.
     *
     * @param color color resource id
     * @return self
     */
    public ViewQuery textColorId(int id){
        return textColor(getContext().getResources().getColor(id));
    }

	
	
	/**
	 * Set view visibility to View.GONE.
	 *
	 * @return self
	 */
	public ViewQuery gone(){
		return visibility(View.GONE);
	}
	
	/**
	 * Set view visibility to View.INVISIBLE.
	 *
	 * @return self
	 */
	public ViewQuery invisible(){
		return visibility(View.INVISIBLE);
	}
	
	/**
	 * Set view visibility to View.VISIBLE.
	 *
	 * @return self
	 */
	public ViewQuery visible(){
		return visibility(View.VISIBLE);
	}
	
	
	/**
	 * @param visibility View.GONE , View.Visibility or View.inVisibility
	 * @return
	 */
	public ViewQuery visibility(int visibility){
		if(view != null && view.getVisibility() != visibility){
			view.setVisibility(visibility);
		}
		return self();
	}
	
	
	/**
	 * Set view background.
	 *
	 * @param id the id
	 * @return self
	 */
	public ViewQuery background(int id){
		if(view != null){
			if(id != 0){
				view.setBackgroundResource(id);
			}else{
				view.setBackgroundDrawable(null);
			}
		}
		return self();
	}
	
	/**
	 * Set view background color.
	 * @param color color code in ARGB
	 * @return self
	 */
	public ViewQuery backgroundColor(int color){
		if(view != null){		
			view.setBackgroundColor(color);		
		}
		return self();
	}
	
	/**
     * Set view background color.
     *
     * @param color color code in resource id
     * @return self
     */
    public ViewQuery backgroundColorId(int colorId){
        if(view != null){       
            view.setBackgroundColor(getContext().getResources().getColor(colorId));     
        }
        return self();
    }
	
    /**
	 * Set the image of an ImageView.
	 * @param resid the resource id
	 * @return self
	 * @see testImage1
	 */
    public ViewQuery image(int resid){
    	if(view instanceof ImageView){
			ImageView iv = (ImageView) view;
			if(resid == 0){
				iv.setImageBitmap(null);
			}else{				
				iv.setImageResource(resid);
			}
		}
    	return self();
    }

	/**
	 * Set the image of an ImageView.
	 *
	 * @param drawable the drawable
	 * @return self
	 * 
	 * @see testImage2
	 * 
	 */
	public ViewQuery image(Drawable drawable){
		if(view instanceof ImageView){
			ImageView iv = (ImageView) view;
			iv.setImageDrawable(drawable);
		}
		return self();
	}
	
	/**
	 * Set the image of an ImageView.
	 *
	 * @param bm Bitmap
	 * @return self
	 * 
	 * @see testImage3
	 */
	public ViewQuery image(Bitmap bm){
		if(view instanceof ImageView){
			ImageView iv = (ImageView) view;
			iv.setImageBitmap(bm);
		}
		return self();
	}
    
    
    /**
	 * Set the adapter of an AdapterView.
	 *
	 * @param adapter adapter
	 * @return self
	 */
	
	@SuppressWarnings({"unchecked", "rawtypes" })
	public ViewQuery adapter(Adapter adapter){
		if(view instanceof AdapterView){
			AdapterView av = (AdapterView) view;
			av.setAdapter(adapter);
		}
		return self();
	}
	
	/**
	 * Set the adapter of an ExpandableListView.
	 * @param adapter adapter
	 * @return self
	 */
	public ViewQuery adapter(ExpandableListAdapter adapter){
		if(view instanceof ExpandableListView){
			ExpandableListView av = (ExpandableListView) view;
			av.setAdapter(adapter);
		}
		return self();
	}
	
	
	/**
	 * Register a callback method for when the view is clicked. Method must have signature of method(View view).
	 *
	 * @param handler The handler that has the public callback method.
	 * @param method The method name of the callback.
	 * @return self
	 */
	public ViewQuery clicked(Object handler, String method){
		EventListener listener = new EventListener(handler).click(method);
		return clicked(listener);
	}
	
	
	/**
	 * Register a callback method for when the view is clicked. 
	 *
	 * @param listener The callback method.
	 * @return self
	 */
	public ViewQuery clicked(OnClickListener listener){
		if(view != null){						
			view.setOnClickListener(listener);
		}
		return self();
	}
	
	/********************get *********************/
	
	/** auto transform to real class
	 * @return real view
	 */
	public <T extends View> T getView(){
		return (T)view;
	}
	
	/**
	 * Gets the text of a TextView.
	 *
	 * @return the text
	 */
	public CharSequence getText(){
		CharSequence result = null;
		if(view instanceof TextView){
			result = ((TextView) view).getText();
		}
		return result;
	}
	
	/**
	 * Gets the tag of the view.
	 *
	 * @return tag
	 */
	public Object getTag(){
		Object result = null;
		if(view != null){
			result = view.getTag();
		}
		return result;
	}
	
	
	
	@SuppressWarnings("unchecked")
	protected ViewQuery self(){
		return this;
	}
	
	
	
	private View findView(int id){
		View result = views.get(id, null);
		Log.i(TAG,"result::"+result);
		if(result!=null){
			return result;
		}
		if(root != null){
			result = root.findViewById(id);
		}else if(act != null){
			result = act.findViewById(id);
		}
		views.put(id, result);
		return result;
	}
	

	

}
