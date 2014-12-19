package jp.ac.kcska.dokodemo.Blood;


import java.util.Calendar;

import jp.ac.kcska.dokodemo.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.app.Dialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;

public class Blood extends Activity implements
		NumberPicker.OnValueChangeListener {
	private TextView text1;
	private TextView text2;
	private Button button;
	private Spinner spin;
	private TextView text3;
	private MyTask task;

	private TimePickerDialog.OnTimeSetListener varTimeSetListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blood_blood);
		
		text3 = (TextView)findViewById(R.id.textView1);
		task = new MyTask(text3, this);
		//task.execute("http://dokodemo.jp:3000/bloodsugars.json");
		task.execute("http://api.atnd.org/events/?keyword=android&format=json");

		final Calendar calender = Calendar.getInstance();

		// 時間の取得(２４時間単位)

		int hour = calender.get(Calendar.HOUR_OF_DAY);

		// 分の取得

		int min = calender.get(Calendar.MINUTE);

		final TextView text = (TextView) findViewById(R.id.time);
		text.setText(hour + ":" + String.format("%02d", min));

		varTimeSetListener = new OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker arg0, int hourOfDay, int minute) {

				text.setText(hourOfDay + ":" + String.format("%02d", minute));

			}
		};
		
		

		// 時間設定
		((TextView) findViewById(R.id.time))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {

						TimePickerDialog timeDialog = new TimePickerDialog(
								Blood.this, varTimeSetListener, calender
										.get(Calendar.HOUR_OF_DAY),
								calender.get(Calendar.MINUTE), true);

						timeDialog.show();

					}
				});
		
		

		// 血糖血入力
		text1 = (TextView) findViewById(R.id.bloodselect);
		text1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				show_blood();
			}
		});

		// インスリン入力
		text2 = (TextView) findViewById(R.id.ins);
		text2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				show_ins();
			}
		});

		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				spin = (Spinner) findViewById(R.id.spinner1);

				
				
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Blood.this);
				alertDialogBuilder.setTitle("この内容で登録しますか？");
				
				//チェックした内容の表示
				alertDialogBuilder.setMessage
				("\n" + "血糖値:    " + text1.getText() + "  mg/dl" +
				  "\n\n" + "インスリン投与量:   " + text2.getText() + "  単位" +
				  "\n\n" + "インスリンの種類:   " + spin.getSelectedItem() + "\n");
			
				
				
				//確認画面処理
				//ＯＫが押されたとき
				alertDialogBuilder.setPositiveButton("OK", 
						new DialogInterface.OnClickListener() {
					
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO 自動生成されたメソッド・スタブ
								 //input();

								//トースト表示
								Toast.makeText(Blood.this, "登録しました", Toast.LENGTH_LONG).show();
								//トースト終了
							}
						});
				
				//キャンセルが押されたとき
				alertDialogBuilder.setNegativeButton("キャンセル", 
						new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO 自動生成されたメソッド・スタブ
								
							}
						});
				alertDialogBuilder.setCancelable(true);
				AlertDialog alertDialog = alertDialogBuilder.create();
				
				alertDialog.show();

			}
		});
	}
	
	

	// 血糖血入力が押されたときの処理
	private void show_blood() {

		final Dialog dialog = new Dialog(Blood.this);

		dialog.setTitle("入力してください");
		dialog.setContentView(R.layout.activity_blood_showdialog);

		Button ok = (Button) dialog.findViewById(R.id.ok);
		Button cancel = (Button) dialog.findViewById(R.id.cancel);
		Button clear = (Button) dialog.findViewById(R.id.clear);

		final NumberPicker numPicker1 = (NumberPicker) dialog
				.findViewById(R.id.numberPicker1);
		final NumberPicker numPicker2 = (NumberPicker) dialog
				.findViewById(R.id.numberPicker2);

		numPicker1.setMaxValue(100);
		numPicker1.setMinValue(1);
		numPicker1.setWrapSelectorWheel(true);
		numPicker1.setOnValueChangedListener(this);

		numPicker2.setMaxValue(9);
		numPicker2.setMinValue(0);
		numPicker2.setWrapSelectorWheel(true);
		numPicker2.setOnValueChangedListener(this);

		ok.setOnClickListener(new OnClickListener() {
			
			// OKが押されたときの処理
			@Override
			public void onClick(View v) {

				String nu1;
				String nu2;

				nu1 = String.valueOf(numPicker1.getValue());
				nu2 = String.valueOf(numPicker2.getValue());

				text1.setText(nu1 + nu2);

				dialog.dismiss();

			}
		});

		cancel.setOnClickListener(new OnClickListener() {
			// キャンセルが押されたときの処理
			@Override
			public void onClick(View v) {
				dialog.dismiss();

			}
		});
		
		clear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自動生成されたメソッド・スタブ
				dialog.dismiss();
				text1.setText("");
			}
		});

		dialog.show();
	}

	
	
	//インスリン入力が押されたときの処理
	private void show_ins() {

		final Dialog dialog = new Dialog(Blood.this);

		dialog.setTitle("入力してください");
		dialog.setContentView(R.layout.activity_blood_showdialog);

		Button ok = (Button) dialog.findViewById(R.id.ok);
		Button cancel = (Button) dialog.findViewById(R.id.cancel);
		Button clear = (Button) dialog.findViewById(R.id.clear);

		final NumberPicker numPicker1 = (NumberPicker) dialog
				.findViewById(R.id.numberPicker1);
		final NumberPicker numPicker2 = (NumberPicker) dialog
				.findViewById(R.id.numberPicker2);

		numPicker1.setMaxValue(10);
		numPicker1.setMinValue(1);
		numPicker1.setWrapSelectorWheel(true);
		numPicker1.setOnValueChangedListener(this);

		numPicker2.setMaxValue(9);
		numPicker2.setMinValue(0);
		numPicker2.setWrapSelectorWheel(true);
		numPicker2.setOnValueChangedListener(this);

		ok.setOnClickListener(new OnClickListener() {
			// OKが押されたときの処理
			@Override
			public void onClick(View v) {

				String nu1;
				String nu2;

				nu1 = String.valueOf(numPicker1.getValue());
				nu2 = String.valueOf(numPicker2.getValue());

				text2.setText(nu1 + nu2);

				dialog.dismiss();

			}
		});

		cancel.setOnClickListener(new OnClickListener() {
			// キャンセルが押されたときの処理
			@Override
			public void onClick(View v) {
				dialog.dismiss();

			}
		});
		
		clear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 自動生成されたメソッド・スタブ
				dialog.dismiss();
				text2.setText("");
			}
		});


		dialog.show();
	}

	@Override
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
		// TODO 自動生成されたメソッド・スタブ

		Log.i("value is", "" + newVal);
	}

	
/*	  private void input(){
	  
	  //パラメータの生成 
		  DefaultHttpClient client = new DefaultHttpClient(); HttpPost httpPost = new HttpPost("");
	  
	  ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
	 params.add(new BasicNameValuePair("", null)); params.add(new BasicNameValuePair("", null));
	  
	  
	  //パラメータの設定
	 try { 
		 httpPost.setEntity(new UrlEncodedFormEntity(params ,"UTF-8"));
		 HttpResponse response = client.execute(httpPost); int status = response.getStatusLine().getStatusCode();
	  
	  
	  } catch (IOException e) { e.printStackTrace(); }
	  
	  
	  
	  }
	  */
}
