package jp.ac.kcska.dokodemo.Vial;

import android.os.Bundle;
import android.view.View;
import java.util.ArrayList;
import java.util.Calendar;
import jp.ac.kcska.dokodemo.R;
import jp.ac.kcska.dokodemo.Vial.AsyncCallback;
import jp.ac.kcska.dokodemo.Vial.MyTask;
import jp.ac.kcska.dokodemo.Vial.PostMyTask; 
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

public class VitalMainActivity extends Activity implements NumberPicker.OnValueChangeListener,OnClickListener {
	
	private TextView temperature,weight,highblood,lowblood,text1,text2,text3,text4,text5,text6,text7,text9,text10,text11,text12;
	private String TextTime;//★
	final String url = "https://kcsgogo.herokuapp.com/vitals";
	final String url2 = "https://kcsgogo.herokuapp.com/pressures"; 
	private Switch sw;
	private Button btn;
	private TextView tv3;
	private ProgressDialog dialog;
	private ProgressDialog tdialog,wdialog;
	private MyTask asyncGet;
	private PostMyTask asyncPost;
	private PostMyTask asyncPost2;
	private RelativeLayout relative;
	private ImageView img,img2;
	
	private TimePickerDialog.OnTimeSetListener varTimeSetListener;
	AlertDialog.Builder mAlertDlg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vital_main);
		
		temperature = (TextView)findViewById(R.id.textView5);
		weight = (TextView) findViewById(R.id.textView7);
		highblood = (TextView) findViewById(R.id.textView10);
		lowblood = (TextView) findViewById(R.id.textView12);
		sw = (Switch) findViewById(R.id.switch1);
		TextTime = sw.getTextOff().toString();
		highblood.setText("朝  ---mmHg");
        lowblood.setText("朝  ---mmHg");
		btn = (Button) findViewById(R.id.button1); 
		tv3 = (TextView)findViewById(R.id.textView3);
		relative = (RelativeLayout)findViewById(R.id.RelativeLayout1);
		img = (ImageView) findViewById(R.id.imageView1);
		
		
		text1 = (TextView)findViewById(R.id.textView1);
		text2 = (TextView)findViewById(R.id.textView2);
		text3 = (TextView)findViewById(R.id.textView3);
		text4 = (TextView)findViewById(R.id.textView4);
		text5 = (TextView)findViewById(R.id.textView5);
		text6 = (TextView)findViewById(R.id.textView6);
		text7 = (TextView)findViewById(R.id.textView7);
		text9 = (TextView)findViewById(R.id.textView9);
		text10 = (TextView)findViewById(R.id.textView10);
		text11 = (TextView)findViewById(R.id.textView11);
		text12 = (TextView)findViewById(R.id.textView12);
		
		temperature.setOnClickListener(this);
		weight.setOnClickListener(this);
		highblood.setOnClickListener(this);
		lowblood.setOnClickListener(this);
		relative.setOnClickListener(this);
		img.setOnClickListener(this);
		
		
		//Task
		asyncGet = new MyTask(new AsyncCallback() {
            public void onPreExecute() {
                // do something
            	dialog = new ProgressDialog(VitalMainActivity.this);
                dialog.setTitle("Please wait");
                dialog.setMessage("Loading data...");
                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                dialog.setCancelable(true);
                dialog.setMax(100);
                dialog.setProgress(0);
                dialog.show();
            }
            public void onProgressUpdate(int progress) {
            	// do something
            	dialog.setProgress(progress);
            }
            public void onPostExecute(ArrayList<String> result) {
            	// do something
            	tv3.setText("");
            	for(String str : result){
            		String crlf = System.getProperty("line.separator");
            		tv3.append(crlf+str);
            	}
                dialog.dismiss();
            }
            public void onCancelled() {
            	// do something
            	dialog.dismiss();
            }
        });
		asyncGet.execute("https://kcsgogo.herokuapp.com/standard_temperatures.json");
		//task.execute("http://dokodemo.jp:3000/vitals.json");
		
		/*
		//-----[POST送信するデータを格納]
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
        nameValuePair.add(new BasicNameValuePair("patieant_id", "SATOU"));
        nameValuePair.add(new BasicNameValuePair("", "hello!"));
		*/
		
		//時計処理
        final Calendar calender = Calendar.getInstance();
		int hour = calender.get(Calendar.HOUR_OF_DAY); //時間の取得(24時間単位)
		int min = calender.get(Calendar.MINUTE); //分の取得
		final TextView text = (TextView)findViewById(R.id.textView1);
		text.setText(hour + ":" + String.format("%02d", min));
		
		varTimeSetListener = new OnTimeSetListener() {
			
			@Override
			public void onTimeSet(TimePicker view, int hour, int min) {
				
				text.setText(hour + ":" + String.format("%02d", min));	
			}
		};
		((TextView)findViewById(R.id.textView1))
		.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TimePickerDialog timeDialog = new TimePickerDialog(
						VitalMainActivity.this,
						varTimeSetListener,
						calender.get(Calendar.HOUR_OF_DAY),
						calender.get(Calendar.MINUTE),
						true);
				
				timeDialog.show();	
			}
		});
		
		//Switch（朝・夜）処理
		sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				TextTime=sw.getTextOff().toString();
				if (isChecked == true) {
		            highblood.setText("夜  ---mmHg");
		            lowblood.setText("夜  ---mmHg");
		            relative.setBackgroundColor(0xff444444);
		            img.setImageResource(R.drawable.ic_image_brightness_2);
		            btn.setTextColor(Color.WHITE);
		            sw.setTextColor(Color.WHITE);
		            text1.setTextColor(Color.WHITE);
		            text2.setTextColor(Color.WHITE);
		            text3.setTextColor(Color.WHITE);
		            text4.setTextColor(Color.WHITE);
		            text5.setTextColor(Color.WHITE);
		            text6.setTextColor(Color.WHITE);
		            text7.setTextColor(Color.WHITE);
		            text9.setTextColor(Color.WHITE);
		            text10.setTextColor(Color.WHITE);
		            text11.setTextColor(Color.WHITE);
		            text12.setTextColor(Color.WHITE);
		            TextTime=sw.getTextOn().toString();//時間
		            
		            Toast.makeText(VitalMainActivity.this, "夜にしました。", Toast.LENGTH_SHORT).show();
		        } else {
		            highblood.setText("朝  ---mmHg");
		            lowblood.setText("朝  ---mmHg");
		            relative.setBackgroundColor(0xffffff00);
		            img.setImageResource(R.drawable.ic_image_wb_sunny);
		            btn.setTextColor(Color.BLACK);
		            sw.setTextColor(Color.BLACK);
		            text1.setTextColor(Color.BLACK);
		            text2.setTextColor(Color.BLACK);
		            text3.setTextColor(Color.BLACK);
		            text4.setTextColor(Color.BLACK);
		            text5.setTextColor(Color.BLACK);
		            text6.setTextColor(Color.BLACK);
		            text7.setTextColor(Color.BLACK);
		            text9.setTextColor(Color.BLACK);
		            text10.setTextColor(Color.BLACK);
		            text11.setTextColor(Color.BLACK);
		            text12.setTextColor(Color.BLACK);
		            TextTime=sw.getTextOff().toString();//時間
		            Toast.makeText(VitalMainActivity.this, "朝にしました。", Toast.LENGTH_SHORT).show();
		        }
			}
		});
		
		
		
		
		//�m�F�_�C�A���O����
		btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v){
				Log.d("!!!!!!!", TextTime);
				//�_�C�A���O
        		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(VitalMainActivity.this);
				alertDialogBuilder.setTitle("この内容で登録しますか？");
				
				alertDialogBuilder.setMessage(
						"今日の体温:\b"+ temperature.getText() +"\n"
						+"今日の体重:\b" + weight.getText() + "\n"
						+"最高血圧:\b" + highblood.getText() + "\n"
						+"最低血圧:\b" + lowblood.getText() + "\n"
						);
				//確認画面処理
				alertDialogBuilder.setPositiveButton("OK", 
						new DialogInterface.OnClickListener() {
					
							@Override
							public void onClick(DialogInterface dialog, int which) {
								
								TextView text = (TextView)findViewById(R.id.textView1);//時間
								TextView text1 = (TextView)findViewById(R.id.textView5);//体温
								TextView text2 = (TextView)findViewById(R.id.textView7);//体重
								
								
								TextView text3 = (TextView)findViewById(R.id.textView10);//最高血圧
								TextView text4 = (TextView)findViewById(R.id.textView12);//最低血圧
								
								final String time = String.valueOf(text.getText());
								final String txt1 = String.valueOf(text1.getText());
								final String txt2 = String.valueOf(text2.getText());
								
								final String timezone = TextTime;
								
								final String txt3 = String.valueOf(text3.getText());
								final String txt4 = String.valueOf(text4.getText());
								
								PostMyTask asyncPost = new PostMyTask(new AsyncCallback() {
						            public void onPreExecute() {
						                // do something
						            	tdialog = new ProgressDialog(VitalMainActivity.this);
						                tdialog.setTitle("Please wait");
						                tdialog.setMessage("Loading data...");
						                tdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
						                tdialog.setCancelable(true);
						                tdialog.setMax(100);
						                tdialog.setProgress(0);
						                tdialog.show();
						            }
						            public void onProgressUpdate(int progress) {
						            	// do something
						            	tdialog.setProgress(progress);
						            }
						            public void onPostExecute(ArrayList<String> result) {
						            	// do something
						            	Log.d("AAAAAAAAAA", "");
						            	//Toast.makeText(Blood.this, "登録しました", Toast.LENGTH_LONG).show();
										//トースト終了
						            	tdialog.dismiss();
						            	finish();
						            }
						            public void onCancelled() {
						            	tdialog.dismiss();
						            }
						            });
								asyncPost.execute(url,url2,time,txt1,txt2,timezone,txt3,txt4);
								
							}
				});
				
				alertDialogBuilder.setNegativeButton("キャンセル", 
						new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
							
							}
						});
				alertDialogBuilder.setCancelable(true);
				AlertDialog alertDialog = alertDialogBuilder.create();
				
				alertDialog.show();
        	}
        });	
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.textView5:
			showTemperature();
			break;
		case R.id.textView7:
			showWeight();
			break;
		case R.id.textView10:
			showHighBlood();
			break;
		case R.id.textView12:
			showLowBlood();
			break;
		}		
	}

	@Override
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
		
		Log.i("value is",""+newVal);
	}
	
	//体温
	private void showTemperature() {
		
		final Dialog dialog = new Dialog(VitalMainActivity.this);
		
		dialog.setTitle("体温を測定してください");
		dialog.setContentView(R.layout.activity_vital_dialog_temperature);
		
		Button ok = (Button)dialog.findViewById(R.id.ok);
		Button cancel = (Button)dialog.findViewById(R.id.cancel);
		Button clear = (Button)dialog.findViewById( R.id.clear);
		
		final NumberPicker numPicker1 = (NumberPicker) dialog.findViewById(R.id.numberPicker1);
		final NumberPicker numPicker2 = (NumberPicker) dialog.findViewById(R.id.numberPicker2);
		
		numPicker1.setMaxValue(45);
		numPicker1.setMinValue(30);
		numPicker1.setValue(35);
		numPicker1.setWrapSelectorWheel(true);
		numPicker1.setOnValueChangedListener(this);
		
		numPicker2.setMaxValue(9);
		numPicker2.setMinValue(0);
		numPicker2.setValue(0);
		numPicker2.setWrapSelectorWheel(true);
		numPicker2.setOnValueChangedListener(this);
		
		ok.setOnClickListener(new OnClickListener() {
			//OKが押されたときの処理	
			@Override
			public void onClick(View v) {
				
				String num1;
				String num2;
				
				num1 = String.valueOf(numPicker1.getValue());
				num2 = String.valueOf(numPicker2.getValue());
				
				temperature.setText(num1 + "." + num2 +" ℃");
				
				dialog.dismiss();
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			//キャンセルが押されたときの処理
			@Override
			public void onClick(View v) {
				
				dialog.dismiss();
			}
		});
		clear.setOnClickListener(new OnClickListener() {
			//clearが押されたときの処理
			@Override
			public void onClick(View v) {
				temperature.setText("");
				dialog.dismiss();
			}
		});
   dialog.show();
	}
	
	//体重
	private void showWeight(){
		final Dialog dialog = new Dialog(VitalMainActivity.this);
		
		dialog.setTitle("体重を測定してください");
		dialog.setContentView(R.layout.activity_vital_dialog_weight);
		
		Button ok = (Button)dialog.findViewById(R.id.weight_ok);
		Button cancel = (Button)dialog.findViewById(R.id.weight_cancel);
		Button clear = (Button)dialog.findViewById(R.id.weight_clear);
		
		final NumberPicker numPicker2 = (NumberPicker) dialog.findViewById(R.id.numberPicker2);
		final NumberPicker numPicker3 = (NumberPicker) dialog.findViewById(R.id.numberPicker3);
		final NumberPicker numPicker4 = (NumberPicker) dialog.findViewById(R.id.numberPicker4);
		
		numPicker2.setMaxValue(19);
		numPicker2.setMinValue(1);
		numPicker2.setValue(5);
		numPicker2.setWrapSelectorWheel(true);
		numPicker2.setOnValueChangedListener(this);
		
		numPicker3.setMaxValue(9);
		numPicker3.setMinValue(0);
		numPicker3.setValue(5);
		numPicker3.setWrapSelectorWheel(true);
		numPicker3.setOnValueChangedListener(this);
		
		numPicker4.setMaxValue(9);
		numPicker4.setMinValue(0);
		numPicker4.setValue(0);
		numPicker4.setWrapSelectorWheel(true);
		numPicker4.setOnValueChangedListener(this);
		
		ok.setOnClickListener(new OnClickListener() {
			//OKが押されたときの処理	
			@Override
			public void onClick(View v) {			
				String num2;
				String num3;
				String num4;
				
				num2 = String.valueOf(numPicker2.getValue());
				num3 = String.valueOf(numPicker3.getValue());
				num4 = String.valueOf(numPicker4.getValue());
				
				weight.setText(num2 + num3 + "." + num4 +" Kg");
				
				dialog.dismiss();			
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			//キャンセルが押されたときの処理
			@Override
			public void onClick(View v) {
				dialog.dismiss();				
			}
		});
		clear.setOnClickListener(new OnClickListener() {
			//clearが押されたときの処理
			@Override
			public void onClick(View v) {
				weight.setText("");
				dialog.dismiss();
				
			}
		});
   
		dialog.show();
	}
	
	//最高血圧
	private void showHighBlood(){
		final Dialog dialog = new Dialog(VitalMainActivity.this);
		
		dialog.setTitle("血圧を測定してください");
		dialog.setContentView(R.layout.activity_vital_dialog_blood);
		
		Button ok = (Button)dialog.findViewById(R.id.blood_ok);
		Button cancel = (Button)dialog.findViewById(R.id.blood_cancel);
		Button clear = (Button)dialog.findViewById(R.id.blood_clear);
		
		final NumberPicker numPicker1 = (NumberPicker) dialog.findViewById(R.id.numberPicker1);
		final NumberPicker numPicker2 = (NumberPicker) dialog.findViewById(R.id.numberPicker2);
		
		numPicker1.setMaxValue(30);
		numPicker1.setMinValue(1);
		numPicker1.setValue(12);
		numPicker1.setWrapSelectorWheel(true);
		numPicker1.setOnValueChangedListener(this);
		
		numPicker2.setMaxValue(9);
		numPicker2.setMinValue(0);
		numPicker2.setValue(0);
		numPicker2.setWrapSelectorWheel(true);
		numPicker2.setOnValueChangedListener(this);
		
		ok.setOnClickListener(new OnClickListener() {
			//OKが押されたときの処理	
			@Override
			public void onClick(View v) {			
				String num1;
				String num2;
				
				num1 = String.valueOf(numPicker1.getValue());
				num2 = String.valueOf(numPicker2.getValue());
				
				highblood.setText(num1 + num2 +" mmHg");
				dialog.dismiss();			
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			//キャンセルが押されたときの処理
			@Override
			public void onClick(View v) {
				dialog.dismiss();				
			}
		});
		clear.setOnClickListener(new OnClickListener() {
			//clearが押されたときの処理
			@Override
			public void onClick(View v) {
				highblood.setText("");
				dialog.dismiss();
			}
		});
		
		dialog.show();
	}
	
	//最低血圧
	private void showLowBlood(){
		final Dialog dialog = new Dialog(VitalMainActivity.this);
		
		dialog.setTitle("血圧を測定してください");
		dialog.setContentView(R.layout.activity_vital_dialog_blood);
		
		Button ok = (Button)dialog.findViewById(R.id.blood_ok);
		Button cancel = (Button)dialog.findViewById(R.id.blood_cancel);
		Button clear = (Button)dialog.findViewById(R.id.blood_clear);
		
		final NumberPicker numPicker1 = (NumberPicker) dialog.findViewById(R.id.numberPicker1);
		final NumberPicker numPicker2 = (NumberPicker) dialog.findViewById(R.id.numberPicker2);
		
		numPicker1.setMaxValue(20);
		numPicker1.setMinValue(1);
		numPicker1.setValue(5);
		numPicker1.setWrapSelectorWheel(true);
		numPicker1.setOnValueChangedListener(this);
		
		numPicker2.setMaxValue(9);
		numPicker2.setMinValue(0);
		numPicker2.setValue(5);
		numPicker2.setWrapSelectorWheel(true);
		numPicker2.setOnValueChangedListener(this);
		
		ok.setOnClickListener(new OnClickListener() {
			//OKが押されたときの処理	
			@Override
			public void onClick(View v) {			
				String num1;
				String num2;
				
				num1 = String.valueOf(numPicker1.getValue());
				num2 = String.valueOf(numPicker2.getValue());
				
				lowblood.setText(num1 + num2 +" mmHg");
				dialog.dismiss();			
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			//キャンセルが押されたときの処理
			@Override
			public void onClick(View v) {
				dialog.dismiss();				
			}
		});
		clear.setOnClickListener(new OnClickListener() {
			//clearが押されたときの処理
			@Override
			public void onClick(View v) {
				lowblood.setText("");
				dialog.dismiss();
			}
		});
		
		dialog.show();
	}
}
