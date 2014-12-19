package jp.ac.kcska.dokodemo.Vial;

import jp.ac.kcska.dokodemo.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;
public class VitalMainActivity extends Activity implements NumberPicker.OnValueChangeListener,OnClickListener {
	
	private TextView temperature;
	private TextView weight;
	private TextView highblood;
	private TextView lowblood;
	private Switch sw;
	private Button btn;
	private TextView tv13;
	private MyTask task;
	static Dialog dialog;
	
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
		btn = (Button) findViewById(R.id.button1); 
		tv13 = (TextView)findViewById(R.id.textView13);
		
		temperature.setOnClickListener(this);
		weight.setOnClickListener(this);
		highblood.setOnClickListener(this);
		lowblood.setOnClickListener(this);
		
		//Task
		task = new MyTask(tv13,this);
		task.execute("http://api.atnd.org/events/?keyword=android&format=json");
		//task.execute("http://dokodemo.jp:3000/vitals.json");
		
		/*
		//-----[POST���M����f�[�^���i�[]
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
        nameValuePair.add(new BasicNameValuePair("patieant_id", "SATOU"));
        nameValuePair.add(new BasicNameValuePair("", "hello!"));
		*/
		
        //���v����
        final Calendar calender = Calendar.getInstance();
		int hour = calender.get(Calendar.HOUR_OF_DAY); //���Ԃ̎擾(24���ԒP��)
		int min = calender.get(Calendar.MINUTE); //���̎擾
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
		
		//Switch�i���E��j����
		sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked == true) {
		            highblood.setText("��  ---mmHg");
		            lowblood.setText("��  ---mmHg");
		            Toast.makeText(VitalMainActivity.this, "��ɂ��܂����B", Toast.LENGTH_SHORT).show();   
		        } else {
		            highblood.setText("��  ---mmHg");
		            lowblood.setText("��  ---mmHg");
		            Toast.makeText(VitalMainActivity.this, "���ɂ��܂����B", Toast.LENGTH_SHORT).show();
		        }
			}
		});
		
		
		//�m�F�_�C�A���O����
		btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v){
				
				//�_�C�A���O
        		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(VitalMainActivity.this);
				alertDialogBuilder.setTitle("���̓��e�œo�^���܂����H");
				
				alertDialogBuilder.setMessage(
						"�����̑̉�:\b"+ temperature.getText() +"\n"
						+"�����̑̏d:\b" + weight.getText() + "\n"
						+"�ō�����:\b" + highblood.getText() + "\n"
						+"�Œጌ��:\b" + lowblood.getText() + "\n"
						);
				
				//�m�F��ʏ���
				alertDialogBuilder.setPositiveButton("OK", 
						new DialogInterface.OnClickListener() {
					
							@Override
							public void onClick(DialogInterface dialog, int which) {
	
								Toast.makeText(VitalMainActivity.this, "�o�^���܂���", Toast.LENGTH_LONG).show(); //�g�[�X�g�\��
							}
						});
				alertDialogBuilder.setNegativeButton("�L�����Z��", 
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
	
	//�̉�
	private void showTemperature() {
		
		final Dialog dialog = new Dialog(VitalMainActivity.this);
		
		dialog.setTitle("�̉��𑪒肵�Ă�������");
		dialog.setContentView(R.layout.dialog_temperature);
		
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
		//OK�������ꂽ�Ƃ��̏���	
			@Override
			public void onClick(View v) {
				
				String num1;
				String num2;
				
				num1 = String.valueOf(numPicker1.getValue());
				num2 = String.valueOf(numPicker2.getValue());
				
				temperature.setText(num1 + "." + num2 +" ��");
				
				dialog.dismiss();
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			//�L�����Z���������ꂽ�Ƃ��̏���
			@Override
			public void onClick(View v) {
				
				dialog.dismiss();
			}
		});
		clear.setOnClickListener(new OnClickListener() {
			//clear�������ꂽ�Ƃ��̏���
			@Override
			public void onClick(View v) {
				temperature.setText("");
				dialog.dismiss();
			}
		});
   dialog.show();
	}
	
	//�̏d
	private void showWeight(){
		final Dialog dialog = new Dialog(VitalMainActivity.this);
		
		dialog.setTitle("�̏d�𑪒肵�Ă�������");
		dialog.setContentView(R.layout.dialog_weight);
		
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
		//OK�������ꂽ�Ƃ��̏���	
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
			//�L�����Z���������ꂽ�Ƃ��̏���
			@Override
			public void onClick(View v) {
				dialog.dismiss();				
			}
		});
		clear.setOnClickListener(new OnClickListener() {
			//clear�������ꂽ�Ƃ��̏���
			@Override
			public void onClick(View v) {
				weight.setText("");
				dialog.dismiss();
				
			}
		});
   
		dialog.show();
	}
	
	//�ō�����
	private void showHighBlood(){
		final Dialog dialog = new Dialog(VitalMainActivity.this);
		
		dialog.setTitle("�����𑪒肵�Ă�������");
		dialog.setContentView(R.layout.activity_vital_main);
		
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
		//OK�������ꂽ�Ƃ��̏���	
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
			//�L�����Z���������ꂽ�Ƃ��̏���
			@Override
			public void onClick(View v) {
				dialog.dismiss();				
			}
		});
		clear.setOnClickListener(new OnClickListener() {
			//clear�������ꂽ�Ƃ��̏���
			@Override
			public void onClick(View v) {
				highblood.setText("");
				dialog.dismiss();
			}
		});
		
		dialog.show();
	}
	
	//�Œጌ��
	private void showLowBlood(){
		final Dialog dialog = new Dialog(VitalMainActivity.this);
		
		dialog.setTitle("�����𑪒肵�Ă�������");
		dialog.setContentView(R.layout.dialog_blood);
		
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
		//OK�������ꂽ�Ƃ��̏���	
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
			//�L�����Z���������ꂽ�Ƃ��̏���
			@Override
			public void onClick(View v) {
				dialog.dismiss();				
			}
		});
		clear.setOnClickListener(new OnClickListener() {
			//clear�������ꂽ�Ƃ��̏���
			@Override
			public void onClick(View v) {
				lowblood.setText("");
				dialog.dismiss();
			}
		});
		
		dialog.show();
	}
}
