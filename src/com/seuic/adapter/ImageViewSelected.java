package com.seuic.adapter;

import android.graphics.ColorMatrixColorFilter;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class ImageViewSelected {

	
	
	 /**   
     * 按下这个按钮进行的颜色过滤   
     */    
  public final static float[] BT_SELECTED=new float[] {      
        2, 0, 0, 0, 2,      
         0, 2, 0, 0, 2,      
        0, 0, 2, 0, 2,      
      0, 0, 0, 1, 0 };     
         
    /**   
   11.   * 按钮恢复原状的颜色过滤   
   */    
     public final static float[] BT_NOT_SELECTED=new float[] {      
        1, 0, 0, 0, 0,      
      0, 1, 0, 0, 0,      
         0, 0, 1, 0, 0,      
        0, 0, 0, 1, 0 };     
        
    /**   
   20.   * 按钮焦点改变   
   21.   */    
    public final static OnFocusChangeListener buttonOnFocusChangeListener=new OnFocusChangeListener() {     
         
     @Override    
     public void onFocusChange(View v, boolean hasFocus) {     
    if (hasFocus) {     
    	 ((ImageView) v).setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));    
      }     
   else    
      {     
	   ((ImageView) v).setColorFilter(new ColorMatrixColorFilter(BT_NOT_SELECTED));      
    }     
  }     
   };     
    
    /**   
   39.   * 按钮触碰按下效果   
   40.   */    
   public final static OnTouchListener buttonOnTouchListener=new OnTouchListener() {     
    @Override    
    public boolean onTouch(View v, MotionEvent event) {     
      if(event.getAction() == MotionEvent.ACTION_DOWN){     
    	  ((ImageView) v).setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));     
      }     
     else if(event.getAction() == MotionEvent.ACTION_UP){     
         ((ImageView) v).setColorFilter(new ColorMatrixColorFilter(BT_NOT_SELECTED));     
         
     }     
    return false;     
    }     
   };     
         
  /**   
  * 设置图片按钮获取焦点改变状态   
  * @param inImageButton   
  */    
   public final void setButtonFocusChanged(View inView)     
   {     
    inView.setOnTouchListener(buttonOnTouchListener);     
    inView.setOnFocusChangeListener(buttonOnFocusChangeListener);     
  }    
	
}
