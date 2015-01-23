package jp.ac.kcska.dokodemo.Blood;

import jp.ac.kcska.dokodemo.R;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class BloodMainActivity extends ActionBarActivity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blood_main);
		
		Button btn1 = (Button)findViewById(R.id.morning_btn1);
		Button btn2 = (Button)findViewById(R.id.morning_btn2);
		Button btn3 = (Button)findViewById(R.id.lunch_btn1);
		Button btn4 = (Button)findViewById(R.id.lunch_btn2);
		Button btn5 = (Button)findViewById(R.id.dinner_btn1);
		Button btn6 = (Button)findViewById(R.id.dinner_btn2);
		Button btn7 = (Button)findViewById(R.id.night_btn1);
		
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
		btn6.setOnClickListener(this);
		btn7.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
		Intent varIntent;
		Button btn = (Button)findViewById(v.getId());
		
		switch (v.getId()){
		case R.id.morning_btn1:
			varIntent =
			new Intent(BloodMainActivity.this, Blood.class);
			varIntent.putExtra( "btn", btn.getText() );	//★
			startActivity(varIntent);
			break;
			
		case R.id.morning_btn2:
			varIntent =
			new Intent(BloodMainActivity.this, Blood.class);
			varIntent.putExtra( "btn", btn.getText() );	//★
			startActivity(varIntent);
			break;
		
		case R.id.lunch_btn1:
			varIntent =
			new Intent(BloodMainActivity.this, Blood.class);
			varIntent.putExtra( "btn", btn.getText() );	//★
			startActivity(varIntent);
			break;
		
		case R.id.lunch_btn2:
			varIntent =
			new Intent(BloodMainActivity.this, Blood.class);
			varIntent.putExtra( "btn", btn.getText() );	//★
			startActivity(varIntent);
			break;
		
		case R.id.dinner_btn1:
			varIntent =
			new Intent(BloodMainActivity.this, Blood.class);
			varIntent.putExtra( "btn", btn.getText() );	//★
			startActivity(varIntent);
			break;
		
		case R.id.dinner_btn2:
			varIntent =
			new Intent(BloodMainActivity.this, Blood.class);
			varIntent.putExtra( "btn", btn.getText() );	//★
			startActivity(varIntent);
			break;
		
		case R.id.night_btn1:
			varIntent =
			new Intent(BloodMainActivity.this, Blood.class);
			varIntent.putExtra( "btn", btn.getText() );	//★
			startActivity(varIntent);
			break;
		}
	}


}
