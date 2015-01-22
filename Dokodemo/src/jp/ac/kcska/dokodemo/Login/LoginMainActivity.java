package jp.ac.kcska.dokodemo.Login;

import jp.ac.kcska.dokodemo.R;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginMainActivity extends Activity {
	
	String id = "Nakazono";
	String pass = "pass";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_login_main);
		
		Button bt = (Button)findViewById(R.id.button1);
		final EditText ed1 = (EditText)findViewById(R.id.editText1);
		final EditText ed2 = (EditText)findViewById(R.id.editText2);
		
		bt.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO �����������ꂽ���\�b�h�E�X�^�u
				String inputId = ed1.getText().toString();
				if(inputId.equals("")){
					Toast.makeText(LoginMainActivity.this, "ユーザIDを入力してください", Toast.LENGTH_SHORT).show();
				}else{
					if(id.equals(inputId)){
						String inputPass = ed2.getText().toString();
						if(pass.equals(inputPass)){
							Intent intent = new Intent(LoginMainActivity.this, 
								OneTimeActivity.class);
							startActivity(intent);
						}else{
							Toast.makeText(LoginMainActivity.this, "パスワードが違います", Toast.LENGTH_SHORT).show();
						}
					}else{
						Toast.makeText(LoginMainActivity.this, "ユーザＩＤが違います", Toast.LENGTH_SHORT).show();
					}
				}
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login_main, menu);
		return true;
	}
/*
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.pass) {
			dialog_pass_change();
		}else if(id == R.id.info){
			info_change();
		}
		return super.onOptionsItemSelected(item);
	}
*/
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_login_main, container,
					false);
			return rootView;
		}
	}
	
	public void dialog_pass_change() {
		// TODO 自動生成されたメソッド・スタブ
		LayoutInflater factory = LayoutInflater.from(LoginMainActivity.this);
		View inputView = factory.inflate(R.layout.dialog, null);
		final EditText ed1 =(EditText)inputView.findViewById(R.id.editText1);
		final EditText ed2 =(EditText)inputView.findViewById(R.id.editText2);
		final EditText ed3 =(EditText)inputView.findViewById(R.id.editText3);
		new AlertDialog.Builder(LoginMainActivity.this)
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
						Toast.makeText(LoginMainActivity.this, pass, Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(LoginMainActivity.this, "確認用のパスワードが違います", Toast.LENGTH_SHORT).show();
					}
					//Toast.makeText(LoginMainActivity.this, "パスワードを変更しました", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(LoginMainActivity.this, "パスワードが違います", Toast.LENGTH_SHORT).show();
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
		LayoutInflater factory = LayoutInflater.from(LoginMainActivity.this);
		View inputView = factory.inflate(R.layout.information, null);
		new AlertDialog.Builder(LoginMainActivity.this)
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
}
