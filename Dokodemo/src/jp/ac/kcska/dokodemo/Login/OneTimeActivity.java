package jp.ac.kcska.dokodemo.Login;

import java.util.Random;

import jp.ac.kcska.dokodemo.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class OneTimeActivity extends ActionBarActivity{
	String pass = "pass";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.one_time);
		
		TextView tView = (TextView) findViewById(R.id.textView2);
		Random random = new Random();
		String[] fortune = {"A1", "A2", "B1", "B2", "B3"};
		int index = random.nextInt(fortune.length);
		tView.setText(fortune[index]);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.pass) {
			dialog_pass_change();
		}else if(id == R.id.info){
			info_change();
		}else if(id == R.id.logout){
			log_out();
			Toast.makeText(OneTimeActivity.this, "ログアウト出来ません！！", Toast.LENGTH_SHORT).show();
		}
		return super.onOptionsItemSelected(item);
	}

	public void dialog_pass_change() {
		// TODO 自動生成されたメソッド・スタブ
		LayoutInflater factory = LayoutInflater.from(OneTimeActivity.this);
		View inputView = factory.inflate(R.layout.dialog, null);
		final EditText ed1 =(EditText)inputView.findViewById(R.id.editText1);
		final EditText ed2 =(EditText)inputView.findViewById(R.id.editText2);
		final EditText ed3 =(EditText)inputView.findViewById(R.id.editText3);
		new AlertDialog.Builder(OneTimeActivity.this)
		.setTitle("パスワード変更")
		.setView(inputView)
		.setCancelable(false)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO 自動生成されたメソッド・スタブ
				
				
				
				String nowPass = ed1.getText().toString();
				String newPass = ed2.getText().toString();
				String newPass2 = ed3.getText().toString();
				if(nowPass.equals(pass)){
					if(newPass.equals(newPass2)){
						pass = newPass;
						Toast.makeText(OneTimeActivity.this, pass, Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(OneTimeActivity.this, "確認用のパスワードが違います", Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(OneTimeActivity.this, "パスワードが違います", Toast.LENGTH_SHORT).show();
					dialog.cancel();
				}
			}
		})
		.setNegativeButton("cancel", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		})
		.show();	
	}
	
	public void info_change(){
		LayoutInflater factory = LayoutInflater.from(OneTimeActivity.this);
		View inputView = factory.inflate(R.layout.information, null);
		new AlertDialog.Builder(OneTimeActivity.this)
		.setTitle("情報利用変更")
		.setView(inputView)
		.setCancelable(false)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		})
		.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO 自動生成されたメソッド・スタブ
				dialog.cancel();
			}
		})
		.show();
	}
	
	public void log_out() {
		// TODO 自動生成されたメソッド・スタブ
		LayoutInflater factory = LayoutInflater.from(OneTimeActivity.this);
		View inputView = factory.inflate(R.layout.log_out, null);
		new AlertDialog.Builder(OneTimeActivity.this)
		.setTitle("ログアウト")
		.setView(inputView)
		.setCancelable(false)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(OneTimeActivity.this, 
						LoginMainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		})
		.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO 自動生成されたメソッド・スタブ
				dialog.cancel();
			}
		})
		.show();
	}
}
