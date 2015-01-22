package jp.ac.kcska.dokodemo.Blood;

import jp.ac.kcska.dokodemo.R;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class BloodMainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blood_main);
		
		((Button)findViewById(R.id.morning_btn1))
		.setOnClickListener(new View.OnClickListener() {
			String mo1;
			
			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				Intent varIntent =
						new Intent(BloodMainActivity.this, Blood.class);
					
				startActivity(varIntent);
			}
		});
		
		((Button)findViewById(R.id.morning_btn2))
		.setOnClickListener(new View.OnClickListener() {
			String mo2;
			
			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				Intent varIntent =
						new Intent(BloodMainActivity.this, Blood.class);
				
				startActivity(varIntent);
			}
		});
		
		((Button)findViewById(R.id.lunch_btn1))
		.setOnClickListener(new View.OnClickListener() {
			String lu1;
			
			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				Intent varIntent =
						new Intent(BloodMainActivity.this, Blood.class);
				
				startActivity(varIntent);
			}
		});
		
		((Button)findViewById(R.id.lunch_btn2))
		.setOnClickListener(new View.OnClickListener() {
			String lu2;
			
			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				Intent varIntent =
						new Intent(BloodMainActivity.this, Blood.class);
				
				startActivity(varIntent);
			}
		});
		
		((Button)findViewById(R.id.dinner_btn1))
		.setOnClickListener(new View.OnClickListener() {
			String di1;
			
			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				Intent varIntent =
						new Intent(BloodMainActivity.this, Blood.class);
				
				startActivity(varIntent);
			}
		});

		((Button)findViewById(R.id.dinner_btn2))
		.setOnClickListener(new View.OnClickListener() {
			String di2;
			
			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				Intent varIntent =
						new Intent(BloodMainActivity.this, Blood.class);
				
				startActivity(varIntent);
			}
		});

		((Button)findViewById(R.id.night_btn1))
		.setOnClickListener(new View.OnClickListener() {
			String ni1;
			
			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				Intent varIntent =
						new Intent(BloodMainActivity.this, Blood.class);
				
				startActivity(varIntent);
			}
		});



	}


}
