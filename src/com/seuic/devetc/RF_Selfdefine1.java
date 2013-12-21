package com.seuic.devetc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;

import com.seuic.smartgateway.R;

public class RF_Selfdefine1 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.rf_selfdefine1);
//		SharedPreferences sharedPreferences = getSharedPreferences("itcast", Context.MODE_PRIVATE);
//		Editor editor = sharedPreferences.edit();//获取编辑器
//		editor.putString("name", "传智播客");
//		editor.putInt("age", 4);
//		editor.commit();
//		SharedPreferences sharedPreferences = getSharedPreferences("itcast", Context.MODE_PRIVATE);
//		//getString()第二个参数为缺省值，如果preference中不存在该key，将返回缺省值
//		String name = sharedPreferences.getString("name", "");
//		int age = sharedPreferences.getInt("age", 1);
	}

	
	 protected void dialog() {
		 AlertDialog.Builder builder = new Builder(RF_Selfdefine1.this);
		 builder.setMessage("Please input name");
		 builder.setTitle("Button name");
		 builder.setView(new EditText(this));
		 builder.setPositiveButton("Confirm", new OnClickListener() {
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}
		});
		  builder.setNegativeButton("Cancle", new OnClickListener() {
		@Override
		 public void onClick(DialogInterface dialog, int which) {
		 dialog.dismiss();
		  }
		 });
		 builder.create().show();
	   }
}
