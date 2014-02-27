package com.seuic.adapter;

import android.graphics.ColorMatrixColorFilter;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class ViewSelected {

	
	
	 /**   
     * 按下这个按钮进行的颜色过滤   
     */    
  public final static float[] SELECTED=new float[] {      
        2, 0, 0, 0, 2,      
        0, 2, 0, 0, 2,      
        0, 0, 2, 0, 2,      
        0, 0, 0, 1, 0 };     
         
    /**   
   11.   * 按钮恢复原状的颜色过滤   
   */    
     public final static float[] NOT_SELECTED=new float[] {      
        1, 0, 0, 0, 0,      
        0, 1, 0, 0, 0,      
        0, 0, 1, 0, 0,      
        0, 0, 0, 1, 0 };     
     
     /**   
     11.   * 学习键按下时按钮颜色过滤   
     */    
       public final static float[] BTN_SELECTED=new float[] {      
    	  0.2f, 0, 0, 0, 50.8f,      
           0, 0.2f, 0, 0, 50.8f,      
           0, 0, 0.2f, 0, 50.8f,      
           0, 0, 0, 1, 0 };     
	 /**   
      * 学习这个按钮进行的颜色过滤   
      */    
   public final static float[] LEARNED=new float[] {      
         0.6f, 0, 0, 0, 0,      
         0, 0.6f, 0, 0, 0,      
         0, 0, 0.6f, 0, 0,      
         0, 0, 0, 1, 0 };   
   
    /**   
   20.   * 按钮焦点改变   
   21.   */    

    
    /**   
   39.   * 按钮触碰按下效果   
   40.   */    
   public final static OnTouchListener imageViewOnTouchListener=new OnTouchListener() {     
    @Override    
    public boolean onTouch(View v, MotionEvent event) {   

      if(event.getAction() == MotionEvent.ACTION_DOWN){     
    	  	((ImageView) v).setColorFilter(new ColorMatrixColorFilter(SELECTED)); 

      }     
     else if(event.getAction() == MotionEvent.ACTION_UP){   
    	  ((ImageView) v).setColorFilter(new ColorMatrixColorFilter(NOT_SELECTED)); 
     }     
    return false;     
    }     
   };     
  
    
    /**   
   39.   * 按钮触碰按下效果   
   40.   */    
   public final static OnTouchListener buttonOnTouchListener=new OnTouchListener() {     
    @Override    
    public boolean onTouch(View v, MotionEvent event) {     
      if(event.getAction() == MotionEvent.ACTION_DOWN){     
    	  v.getBackground().setColorFilter(new ColorMatrixColorFilter(SELECTED)); 
    	  v.setBackgroundDrawable(v.getBackground());   
    	  Log.e("ViewSelected", " SELECTED");
      }     
     else if(event.getAction() == MotionEvent.ACTION_UP){     
    	 v.getBackground().setColorFilter(new ColorMatrixColorFilter(NOT_SELECTED));
	     v.setBackgroundDrawable(v.getBackground());  
	     Log.e("ViewSelected", "NOT_SELECTED");
         
     }     
    return false;     
    }     
   };       
  /**   
  * 设置图片按钮获取焦点改变状态   
  * @param inImageButton   
  */    
   public final  void setImageViewClickChanged(View inView)     
   {     
    inView.setOnTouchListener(imageViewOnTouchListener);  
  }    
   public final void setButtonClickChanged(View inView)     
   {     
    inView.setOnTouchListener(buttonOnTouchListener);  
   }  
   
   public final void buttonClickGreyChanged(View inView)
   {

	   inView.getBackground().setColorFilter(new ColorMatrixColorFilter(LEARNED)); 
	 
	   inView.setBackgroundDrawable(inView.getBackground());
	   inView.setEnabled(false);
   }
   
   public final void imageviewClickGreyChanged(View inView)
   {

	   ((ImageView) inView).setColorFilter(new ColorMatrixColorFilter(LEARNED)); 
	 
	   inView.setEnabled(false);
   }
   
   public final void buttonClickLearn(View inView)
   {

	  inView.getBackground().setColorFilter(new ColorMatrixColorFilter(LEARNED)); 
	  inView.setBackgroundDrawable(inView.getBackground());
	  inView.setEnabled(true);	  
   }
   
   public final void imageviewClickLearn(View inView)
   {

	  ((ImageView) inView).setColorFilter(new ColorMatrixColorFilter(LEARNED)); 
	  inView.setEnabled(true);	  
   }
   
   
   public final void buttonClickRecover(View inView)
   {

	   inView.getBackground().setColorFilter(new ColorMatrixColorFilter(NOT_SELECTED)); 
	   inView.setBackgroundDrawable(inView.getBackground());
	   inView.setEnabled(true);
   }
   
   public final void imageviewClickRecover(View inView)
   {

	   ((ImageView) inView).setColorFilter(new ColorMatrixColorFilter(NOT_SELECTED)); 	  
	   inView.setEnabled(true);
   }
   
   
   public final void buttonClickLearnDefault(View inView)
   {

	   inView.getBackground().setColorFilter(new ColorMatrixColorFilter(BTN_SELECTED)); 
	   inView.setBackgroundDrawable(inView.getBackground());
	   inView.setEnabled(true);
   }
   
   public final void imageviewClickLearnDefault(View inView)
   {

	   ((ImageView) inView).setColorFilter(new ColorMatrixColorFilter(BTN_SELECTED)); 	  
	   inView.setEnabled(true);
   }

}




