package jp.ac.kcska.dokodemo.Food;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;

import jp.ac.kcska.dokodemo.R;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class FoodSign_upActivity extends Activity {
	private TimePickerDialog.OnTimeSetListener varTimeSetListener;
	Button bt3;
	ArrayAdapter<String> adapter;
	ArrayAdapter<String> adapter1;
	private AlertDialog m_Dlg = null; 
	int position;
	ListView lisview;
	String del;
	String getUnit;
	double glam = 60;
	int cal = 80;
	double answer;
	double incert;
	double unit_Sum;
	ArrayList<String> list;
	ArrayAdapter<String> adapter3;
	
	TextView tv1;
	ArrayList<Double> all_unit;
	String change = "false";
	Spinner spinner;
	String clockTime;
	Button dateText;
	CustomAdapter customAdapter;
	ArrayList<String> lis;
	
	final Calendar calendar = Calendar.getInstance();
	int time_hour = calendar.get(Calendar.HOUR_OF_DAY);
	int time_minute = calendar.get(Calendar.MINUTE);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_food_sign_up);
		lis = new ArrayList<String>();
		
		all_unit = new ArrayList<Double>();
		dateText = (Button)findViewById(R.id.button1);
		clockTime = String.format("%02d", time_hour) + ":" + String.format("%02d", time_minute);
		dateText.setText(clockTime);
		
		varTimeSetListener = new OnTimeSetListener() {		
			@Override
			public void onTimeSet(TimePicker view, int hour, int min) {
				time_hour = hour;
				time_minute = min;
				setSpinner();
				dateText.setText(hour + ":" + String.format("%02d", min));
				
			}
		};
		
		final TimePickerDialog timeDialog = new TimePickerDialog(
				FoodSign_upActivity.this,
				varTimeSetListener,
				time_hour,
				time_minute,
				true);
		timeDialog.setCancelable(false);
		dateText.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				timeDialog.show();
				
			}
		});
		
		//dateText.setText(String.format("%02d", hour) + ":" + String.format("%02d", min));
		
		//ComboBoxの処理
		list = new ArrayList<String>();
		list.add("朝食");
        list.add("昼食");
        list.add("夕食");
        list.add("間食");
        
        setSpinner();
		
		adapter = new ArrayAdapter<String>(FoodSign_upActivity.this, android.R.layout.simple_list_item_1);
		adapter1 = new ArrayAdapter<String>(FoodSign_upActivity.this, android.R.layout.simple_list_item_1);
		lisview = (ListView)findViewById(R.id.listView1);
		Button bt2 = (Button)findViewById(R.id.button2);
		
		bt2.setOnClickListener(new OnClickListener() {
		

			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				sql_category();
				
			}
		});
		lisview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO 自動生成されたメソッド・スタブ
				ListView list = (ListView)parent;
				del = (String)list.getItemAtPosition(position);
				FoodSign_upActivity.this.position = position;
				Log.d("del", String.valueOf(list.getItemIdAtPosition(position)));
				return false;
			}
		});
		
		
		
		bt3 = (Button)findViewById(R.id.button3);
		bt3.setEnabled(false);
		if(adapter.isEmpty() == false){
			bt3.setEnabled(true);
		}
		bt3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				signUpDialog();
			}
		});
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.food_sign_up, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_food_sign_up, container,
					false);
			return rootView;
		}
	}
	
	public void sql_category(){
		SqlOpenHelper sOpen = new SqlOpenHelper(this);
		SQLiteDatabase db = sOpen.getWritableDatabase();
		
		
		final String str = "category";
		//Cursor c = db.rawQuery("SELECT * FROM " + str,null);
		Cursor c = db.rawQuery(
				String.format(
						"SELECT * FROM %s",
						str),null);
		boolean isEof = c.moveToFirst();
		ListView lisv = new ListView(this);
		final ArrayList<String> rows = new ArrayList<String>();
		final ArrayList<String> c_No = new ArrayList<String>();
		while (isEof) {
			rows.add(c.getString(1));
			c_No.add(c.getString(0));
			isEof = c.moveToNext();
			//layout.addView(lisv);
			
			
		}
		 CustomAdapter mAdapter = new CustomAdapter(this, 0, rows);
		 lisv.setAdapter(mAdapter);
		 lisv.setDivider(null);
		//lisv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, rows));
		lisv.setScrollingCacheEnabled(false);
		lisv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> items, View view, int position,
					long ids) {
				// TODO 自動生成されたメソッド・スタブ
				m_Dlg.dismiss();
				sql_cSyokuhin(rows.get(position).toString(), str, c_No.get(position).toString());
				//TextView text = (TextView)findViewById(R.id.textView1);
				//text.setText(rows.get(position).toString());
				//Toast.makeText(MainActivity.this, c_No.get(position).toString(), Toast.LENGTH_SHORT).show();
				
				
			}
		});
		m_Dlg = new AlertDialog.Builder(this)
		.setTitle("カテゴリ")
		.setPositiveButton("キャンセル", null)
		.setView(lisv)
		.create();
		m_Dlg.setCancelable(false);
		m_Dlg.show();
		c.close();
		db.close();

		
	}
	public void sql_cSyokuhin(String title,String tbl, String cId){
		String table = tbl;
		SqlOpenHelper sOpen = new SqlOpenHelper(this);
		SQLiteDatabase db = sOpen.getWritableDatabase();
		
		
		final String str = "category_syokuhin";
		//Cursor c = db.rawQuery("SELECT * FROM " + str,null);
		Cursor c = db.rawQuery(String.format(
				"SELECT * FROM %s INNER JOIN %s "+ 
				"ON %s.c_Id = %s._id WHERE %s.c_Id = '%s'",
				str,table,str,table,str,cId),null);
		boolean isEof = c.moveToFirst();
		ListView lisv = new ListView(this);
		final ArrayList<String> rows = new ArrayList<String>();
		final ArrayList<String> cs_Id = new ArrayList<String>();
		while (isEof) {
			rows.add(c.getString(2));
			cs_Id.add(c.getString(0));
			isEof = c.moveToNext();
			//layout.addView(lisv);
			
			
		}
		CustomAdapter mAdapter = new CustomAdapter(this, 0, rows);
		lisv.setAdapter(mAdapter);
		lisv.setDivider(null);
		//lisv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, rows));
		lisv.setScrollingCacheEnabled(false);
		lisv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> items, View view, int position,
					long ids) {
				// TODO 自動生成されたメソッド・スタブ
				m_Dlg.dismiss();
				//FoodSign_upActivity.this.position = position;
				sql_Syokuhin(rows.get(position).toString(), str, cs_Id.get(position).toString());
			}
		});
		m_Dlg = new AlertDialog.Builder(this)
		.setTitle(title)
		.setPositiveButton("キャンセル", null)
		.setView(lisv)
		.create();
		m_Dlg.setCancelable(false);
		m_Dlg.show();
		c.close();
		db.close();

		
	}
	
	public void sql_Syokuhin(String title,String tbl, String cs_Id){
		String table = tbl;
		SqlOpenHelper sOpen = new SqlOpenHelper(this);
		SQLiteDatabase db = sOpen.getWritableDatabase();
		
		
		String str = "syokuhin";
		Cursor c = db.rawQuery(String.format(
				"SELECT * FROM %s INNER JOIN %s "+ 
				"ON %s.cs_Id = %s._id WHERE %s.cs_Id = '%s'",
				str,table,str,table,str,cs_Id),null);
		boolean isEof = c.moveToFirst();
		ListView lisv = new ListView(this);
		final ArrayList<String> rows = new ArrayList<String>();
		final ArrayList<String> unit = new ArrayList<String>();
		while (isEof) {
			rows.add(c.getString(2));
			unit.add(c.getString(3));
			isEof = c.moveToNext();	
		}
		CustomAdapter mAdapter = new CustomAdapter(this, 0, rows);
		lisv.setAdapter(mAdapter);
		lisv.setDivider(null);
		//lisv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, rows));
		lisv.setScrollingCacheEnabled(false);
		lisv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> items, View view, int position,
					long ids) {
				// TODO 自動生成されたメソッド・スタブ
				m_Dlg.dismiss();
				//FoodSign_upActivity.this.position = position;
				//sql_category();
				//TextView text = (TextView)findViewById(R.id.textView1);
				//text.setText(rows.get(position).toString()+
						//"\n１単位：" + unit.get(position).toString() + "g");
				getUnit = unit.get(position).toString();
				String str = "食品名：" + rows.get(position).toString()
				+ "		１単位：" + getUnit + "g";
				
				
				registerForContextMenu(lisview);
				kcalCalculate(rows.get(position).toString(), Integer.parseInt(getUnit), str);
				//Toast.makeText(MainActivity.this, id.get(position).toString(), Toast.LENGTH_SHORT).show();
				
				
			}
		});
		m_Dlg = new AlertDialog.Builder(this)
		.setTitle(title)
		.setPositiveButton("キャンセル", null)
		.setView(lisv)
		.create();
		m_Dlg.setCancelable(false);
		m_Dlg.show();
		c.close();
		db.close();

		
	}
	
	@Override
	  public void onCreateContextMenu(ContextMenu menu, View v,
	    ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);

	    switch(v.getId()){
	    case R.id.listView1:

	      
	      menu.setHeaderTitle(del);

	      getMenuInflater().inflate(
	        R.menu.listview1_context, menu
	      );

	      break;
	    }
	  }
	
	@Override
	  public boolean onContextItemSelected(MenuItem item) {

	    switch(item.getItemId()){
	    case R.id.listview1_delete:

			// 項目を削除
			//
	    	
	    	//sql_category();
	    	//adapter.remove(del);
	    	customAdapter.remove(del);
	    	all_unit.remove(position);
	    	customAdapter.notifyDataSetChanged();
	    	//customAdapter = new CustomAdapter(FoodSign_upActivity.this, 0, lis);
	    	change = "true";
	    	Log.d("all_unit_true", String.valueOf(all_unit));
	    	calculate();
			//adapter.notifyDataSetChanged();
//			if(adapter.isEmpty()){
//				bt3.setEnabled(false);
//			}
	    	if(customAdapter.isEmpty()){
				bt3.setEnabled(false);
				tv1.setText("");
			}
			//adapter.remove(del);
			
			return true;
	    case R.id.listview1_edit:
	    	
	    	//adapter.remove(del);
	    	change = "true";
	    	sql_category();
	    	
	        return true;

	    
	    }
	    return false;
	  }
	
	public void kcalCalculate(final String title, int unit, final String Text){
		 final Toast toast = Toast.makeText(FoodSign_upActivity.this, "摂取量を入力してください",
   			  Toast.LENGTH_SHORT);
		glam = (double)unit;
		tv1 = (TextView)findViewById(R.id.textView1);
		
	    
	    
	    final Handler handler = new Handler(){
	        @Override
	        public void handleMessage(Message msg){
	           String inputText = msg.obj.toString();
	           removeMessages(0);
	           // varidation(String) → private メソッドで入力情況をチェック
	          if (varidation(inputText)){
	              // 正常入力された → inputText文字列を処理
	             // 入力ダイアログ表示しない。
	        	  String lisText = Text;
	        	  incert =  new BigDecimal(inputText)
					.setScale(1,BigDecimal.ROUND_DOWN).doubleValue();
					answer = incert / glam; //計算式
					double temp = answer * 100;
					//temp = Math.round(temp);
					//temp = new BigDecimal(String.valueOf(temp))
					//.setScale(1,BigDecimal.ROUND_DOWN).doubleValue();
					answer = temp/100;
					answer = new BigDecimal(String.valueOf(answer))
					.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
					
					lisText = lisText + "		" + answer + "単位";
					
					if(change.equals("true")){
						all_unit.set(position, answer);
						calculate();
						Log.d("all_unit_true", String.valueOf(all_unit));
						listvChange(lisText);
					}else{
						lis.add(lisText);
						all_unit.add(answer);
						
						Log.d("all_unit", String.valueOf(all_unit));
						calculate();
						//adapter.add(lisText);
						//lisview.setAdapter(adapter);
						 customAdapter = new CustomAdapter(FoodSign_upActivity.this, 0, lis);
						 lisview.setAdapter(customAdapter);
						 lisview.setDivider(null);
						 lisview.setSelection((lisview.getCount()-1));
					}
	          }else{
	              // 入力ダイアログ表示する。
	        	  LayoutInflater infla = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	      		final View layout = infla.inflate(R.layout.dialog_kcal, (ViewGroup)findViewById(R.id.layout_root));
	      		final EditText ed1 = (EditText)layout.findViewById(R.id.edit_Text1);
	      		final AlertDialog.Builder buil = new AlertDialog.Builder(FoodSign_upActivity.this).setCancelable(false);
	      		buil.setTitle(title);
	      		buil.setView(layout);
	      		buil.setPositiveButton("ＯＫ",new DialogInterface.OnClickListener(){
	                @Override
	                public void onClick(DialogInterface dialog,int w){
	                	toast.cancel();
	                   Message message = Message.obtain();
	                   message.obj = ed1.getText().toString();
	                   sendMessage(message);
	                   
	                }
	             });
	            buil.setNegativeButton("キャンセル",new DialogInterface.OnClickListener(){
	                 @Override
	                 public void onClick(DialogInterface dialog,int w){
	                	 toast.cancel();
	                 }
	              });
	           buil.create().show();
	           }
	        }
	        
	        Pattern pattern = Pattern.compile(
	        		"^[1-9]+\\d*$"+ 
	        		"|^[0]{1}+[.]+[1-9]{1}+$"+ 
	        		"|^[1-9]+\\d*[.]+\\d{1}+$"
	        		, Pattern.CASE_INSENSITIVE );
	        
	        private boolean varidation(String text){
	           if (text.length()==0){
	        	   toast.show();
	        	   return false;
	           }else if(pattern.matcher(text).matches()== false){
	        	   toast.show();
	        	   return false;
	           }
	           return true;
	        }
	    };
		if(glam != 0){
			Message message = Message.obtain();
		    message.obj = "";
		    handler.sendMessage(message);
		}else{
			String lisText = Text;
			lisText = lisText + "		" + "0" + "単位";
			lis.add(lisText);
			if(change.equals("true")){
				all_unit.set(position, (double) 0);
				calculate();
				Log.d("all_unit_true", String.valueOf(all_unit));
				listvChange(lisText);
			}else{
				all_unit.add((double) 0);
				calculate();
				customAdapter = new CustomAdapter(FoodSign_upActivity.this, 0, lis);
				
				lisview.setAdapter(customAdapter);
				lisview.setDivider(null);
				lisview.setSelection((lisview.getCount()-1));
				
			}
			
			
		}
		
	}
	
	
	public void setSpinner(){
		
		adapter3 = new ArrayAdapter<String>(this,
                R.layout.spinner_item, list);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner) findViewById(R.id.spinner1);
        if( time_hour >= 5 && time_hour <=9){ 
             spinner.setAdapter(adapter3);
        }else if( time_hour >= 11 && time_hour <= 14){
        	spinner.setAdapter(adapter3);
        	spinner.setSelection(1);
		}else if(time_hour >= 17 && time_hour <= 21){
			spinner.setAdapter(adapter3);
        	spinner.setSelection(2);
		}else{
			spinner.setAdapter(adapter3);
        	spinner.setSelection(3);
		}
		
	}
	
	public void listvChange(String text){
//		adapter.remove(del);
//		adapter.insert(text, position);
//		adapter.notifyDataSetChanged();
		customAdapter.remove(del);
		customAdapter.insert(text, position);
		customAdapter.notifyDataSetChanged();
		lisview.setSelection(position);
		
	}

	public void calculate(){
		if(change.equals("true")){
			change = "false";
			unit_Sum = 0;
			for(int i = 0 ; i < all_unit.size() ; i++){
				unit_Sum += all_unit.get(i);
			}
		}else{
			int i = all_unit.size();
			unit_Sum += all_unit.get(i-1);
		}
		unit_Sum = new BigDecimal(String.valueOf(unit_Sum))
		.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
		tv1.setText(String.valueOf(unit_Sum) + "単位");
		bt3.setEnabled(true);
	}
	
	public void signUpDialog(){
		
		LayoutInflater infla = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View layout = infla.inflate(R.layout.dialog_signup, (ViewGroup)findViewById(R.id.sign_layout_root));
		ListView lisv = (ListView)layout.findViewById(R.id.listView1);
		//lisv.setAdapter(adapter);
		lisv.setAdapter(customAdapter);
		lisv.setDivider(null);
		TextView time = (TextView)layout.findViewById(R.id.time);
		TextView timeZone = (TextView)layout.findViewById(R.id.timezone);
		TextView unit_Sum = (TextView)layout.findViewById(R.id.textView4);
		time.setText(dateText.getText());
		timeZone.setText(spinner.getSelectedItem().toString());
		unit_Sum.setText(String.valueOf(this.unit_Sum)+ "単位");
		AlertDialog.Builder buil = new AlertDialog.Builder(this).setCancelable(false);
		buil.setTitle("確認");
		buil.setView(layout);
		buil.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO 自動生成されたメソッド・スタブ
				finish();
				Toast.makeText(FoodSign_upActivity.this, "登録しました", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getApplication(), FoodManageSelectActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		buil.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO 自動生成されたメソッド・スタブ
				
			}
		});
		buil.create();
		buil.show();
	}
	
	

	
}
