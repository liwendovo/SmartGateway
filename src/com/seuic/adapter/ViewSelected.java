package com.seuic.adapter;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class ViewSelected {

	
	
	 /**   
     * ���������ť���е���ɫ����   
     */    
  public final static float[] SELECTED=new float[] {      
        2, 0, 0, 0, 2,      
        0, 2, 0, 0, 2,      
        0, 0, 2, 0, 2,      
        0, 0, 0, 1, 0 };     
         
    /**   
   11.   * ��ť�ָ�ԭ״����ɫ����   
   */    
     public final static float[] NOT_SELECTED=new float[] {      
        1, 0, 0, 0, 0,      
        0, 1, 0, 0, 0,      
        0, 0, 1, 0, 0,      
        0, 0, 0, 1, 0 };     
	 /**   
      * ѧϰ�����ť���е���ɫ����   
      */    
   public final static float[] LEARNED=new float[] {      
         0.6f, 0, 0, 0, 0,      
         0, 0.6f, 0, 0, 0,      
         0, 0, 0.6f, 0, 0,      
         0, 0, 0, 1, 0 };   
   
    /**   
   20.   * ��ť����ı�   
   21.   */    
    public final static OnFocusChangeListener imageViewOnFocusChangeListener=new OnFocusChangeListener() {     
         
     @Override    
     public void onFocusChange(View v, boolean hasFocus) {  
    	  Log.e("leewoo", "imageViewOnFocusChangeListener ");
	    if (hasFocus) {     
//	    	((ImageView) v).setColorFilter(new ColorMatrixColorFilter(SELECTED));  
	    	((ImageView) v).setColorFilter(new ColorMatrixColorFilter(SELECTED)); 
	    	  ((ImageView) v).setImageDrawable(((ImageView) v).getDrawable()); 
	      }     
	   else    
	      {     
//		    ((ImageView) v).setColorFilter(new ColorMatrixColorFilter(NOT_SELECTED));   
		   ((ImageView) v).setColorFilter(new ColorMatrixColorFilter(NOT_SELECTED)); 
	    	  ((ImageView) v).setImageDrawable(((ImageView) v).getDrawable()); 
	    }     
     }     
   };     
    
    /**   
   39.   * ��ť��������Ч��   
   40.   */    
   public final static OnTouchListener imageViewOnTouchListener=new OnTouchListener() {     
    @Override    
    public boolean onTouch(View v, MotionEvent event) {   
    	  Log.e("leewoo", "imageViewOnTouchListener ");
      if(event.getAction() == MotionEvent.ACTION_DOWN){     
//    	  ((ImageView) v).setColorFilter(new ColorMatrixColorFilter(SELECTED));  
    	
    	  	((ImageView) v).setColorFilter(new ColorMatrixColorFilter(SELECTED)); 
	    	  ((ImageView) v).setImageDrawable(((ImageView) v).getDrawable()); 
      }     
     else if(event.getAction() == MotionEvent.ACTION_UP){     
//         ((ImageView) v).setColorFilter(new ColorMatrixColorFilter(NOT_SELECTED));  
    	  ((ImageView) v).setColorFilter(new ColorMatrixColorFilter(NOT_SELECTED)); 
    	  ((ImageView) v).setImageDrawable(((ImageView) v).getDrawable()); 
     }     
    return false;     
    }     
   };     
   /**   
   20.   * ��ť����ı�   
   21.   */    
    public final static OnFocusChangeListener buttonOnFocusChangeListener=new OnFocusChangeListener() {     
         
     @Override    
     public void onFocusChange(View v, boolean hasFocus) {         
	    if (hasFocus) {     	    	 
	    	 v.getBackground().setColorFilter(new ColorMatrixColorFilter(SELECTED)); 
	    	 v.setBackgroundDrawable(v.getBackground());   
	      }     
	    else    
	      {     
		     v.getBackground().setColorFilter(new ColorMatrixColorFilter(NOT_SELECTED));
		     v.setBackgroundDrawable(v.getBackground());  
	      }     
     }     
   };     
    
    /**   
   39.   * ��ť��������Ч��   
   40.   */    
   public final static OnTouchListener buttonOnTouchListener=new OnTouchListener() {     
    @Override    
    public boolean onTouch(View v, MotionEvent event) {     
      if(event.getAction() == MotionEvent.ACTION_DOWN){     
    	  v.getBackground().setColorFilter(new ColorMatrixColorFilter(SELECTED)); 
    	  v.setBackgroundDrawable(v.getBackground());      
      }     
     else if(event.getAction() == MotionEvent.ACTION_UP){     
    	 v.getBackground().setColorFilter(new ColorMatrixColorFilter(NOT_SELECTED));
	     v.setBackgroundDrawable(v.getBackground());  
         
     }     
    return false;     
    }     
   };       
  /**   
  * ����ͼƬ��ť��ȡ����ı�״̬   
  * @param inImageButton   
  */    
   public final void setImageViewFocusChanged(View inView)     
   {     
    inView.setOnTouchListener(imageViewOnTouchListener);     
    inView.setOnFocusChangeListener(imageViewOnFocusChangeListener);     
  }    
//   public final void setButtonFocusChanged(View inView)     
//   {     
//    inView.setOnTouchListener(buttonOnTouchListener);     
//    inView.setOnFocusChangeListener(buttonOnFocusChangeListener);     
//  }  
   
   public final void buttonclickgreychanged(View inView)
   {

	   inView.getBackground().setColorFilter(new ColorMatrixColorFilter(LEARNED)); 
	 
	   inView.setBackgroundDrawable(inView.getBackground());
	   inView.setEnabled(false);
   }
   
   public final void buttonclicklearn(View inView)
   {

	  inView.getBackground().setColorFilter(new ColorMatrixColorFilter(LEARNED)); 
	  inView.setBackgroundDrawable(inView.getBackground());
	  inView.setEnabled(true);	  
   }
   
   public final void buttonclickrecover(View inView)
   {

	   inView.getBackground().setColorFilter(new ColorMatrixColorFilter(NOT_SELECTED)); 
	   inView.setBackgroundDrawable(inView.getBackground());
	   inView.setEnabled(true);
   }
   
}




