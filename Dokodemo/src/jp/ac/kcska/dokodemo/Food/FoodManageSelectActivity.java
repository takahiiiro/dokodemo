package jp.ac.kcska.dokodemo.Food;

import java.util.Calendar;

import jp.ac.kcska.dokodemo.R;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

public class FoodManageSelectActivity extends Activity {
	
	final Calendar calendar = Calendar.getInstance();  
	// カレンダーから現在の '年' を取得  
	int mYear = calendar.get(Calendar.YEAR);  
	// カレンダーから現在の '月' を取得  
	int mMonth = calendar.get(Calendar.MONTH);  
	// カレンダーから現在の '日' を取得  
	int mDay = calendar.get(Calendar.DAY_OF_MONTH);  
		  
	DatePickerDialog datePickerDialog;
	TextView text;
	public String date;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_food_manage_select);

		text = (TextView)findViewById(R.id.textView1);
		datePickerDialog = new DatePickerDialog(this, null, mYear, mMonth, mDay);
		datePickerDialog.setCancelable(false);
	    final DatePicker datePicker = datePickerDialog.getDatePicker();
	    CalendarView calendarView = datePicker.getCalendarView();
	    calendarView.setShowWeekNumber(false);
		
	    datePickerDialog.setButton(  
	    		DialogInterface.BUTTON_POSITIVE,  
	    		"OK",   
	    		new DialogInterface.OnClickListener() {  
	    			public void onClick(DialogInterface dialog, int which) {  
	    				// Positive Button がクリックされた時の動作 
	    				mYear = datePicker.getYear();
	    				mMonth = datePicker.getMonth();
	    				mDay = datePicker.getDayOfMonth();
	    				
	    				date = mYear + "年" +
				    			String.format("%1$02d", mMonth+1) + "月" +
				    			String.format("%1$02d", mDay) + "日";
	    				startSubActivity();
	    				}  
	    			}      
	    		);  
	    datePickerDialog.setButton(
	    		DialogInterface.BUTTON_NEGATIVE,   
	    		"キャンセル",   
	    		new DialogInterface.OnClickListener() {  
	    			public void onClick(DialogInterface dialog, int which) {  
	    				}
	    			
	    			}  
	    		);  

	    
		Button bt1 = (Button)findViewById(R.id.button1);
		Button bt2 = (Button)findViewById(R.id.button2);
		
		bt1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				Intent intent=new Intent(FoodManageSelectActivity.this,FoodSign_upActivity.class);
			    startActivityForResult(intent,0);
			}
		});
		
		bt2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				datePickerDialog.show();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.food_manage_select, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_food_manage_select, container, false);
			return rootView;
		}
	}
	
	private void startSubActivity(){
		
	       Intent intent = new Intent(this,FoodChangeSelectActivity.class);
	       intent.putExtra("date", date);
	       startActivityForResult(intent,0);
	     }
	

}
