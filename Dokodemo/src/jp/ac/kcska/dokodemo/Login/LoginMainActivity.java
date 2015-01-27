package jp.ac.kcska.dokodemo.Login;

import java.util.ArrayList;

import jp.ac.kcska.dokodemo.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginMainActivity extends Activity {
	private ProgressDialog dialog;
	
	Button bt;
	EditText ed1;
	EditText ed2;
	
	String id = "";
	String pass = "";
	String url = "https://kcsgogo.herokuapp.com/logins.json";
	String count = "0";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_login_main);
		dialog = new ProgressDialog(LoginMainActivity.this);
        dialog.setTitle("Please wait");
        dialog.setMessage("Loading data...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(true);
        dialog.setMax(100);
        dialog.setProgress(0);
        
		bt = (Button)findViewById(R.id.button1);
		ed1 = (EditText)findViewById(R.id.editText1);
		ed2 = (EditText)findViewById(R.id.editText2);
		
		bt.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				id = ed1.getText().toString();
				pass = ed2.getText().toString();
				if(id.equals("")){
					Toast.makeText(LoginMainActivity.this, 
							"ユーザIDを入力してください", Toast.LENGTH_SHORT).show();
				}else if(pass.equals("")){
					Toast.makeText(LoginMainActivity.this, 
							"パスワードを入力してください", Toast.LENGTH_SHORT).show();
				}else{
					MyTask asyncGet = new MyTask(new AsyncCallback() {
			            public void onPreExecute() {dialog.show();}
			            public void onProgressUpdate(int progress) {dialog.setProgress(progress);}
			            public void onPostExecute(ArrayList<String> result) {
			            	dialog.dismiss();
			            	Log.d("結果", String.valueOf(result));
			            	checkUser(result);
			            	
			            }
			            public void onCancelled() {dialog.dismiss();}
			        });
					asyncGet.execute(url, id, pass, count);
				}
			}
		});
	}
	
	public void checkUser(ArrayList<String> result){
		if(result.isEmpty()){
			Toast.makeText(LoginMainActivity.this,
					"もう一度入力してください", Toast.LENGTH_SHORT).show();
		}else{
			String _id = result.get(0).toString();
			String _pass= result.get(1).toString();
			if(id.equals(_id) && pass.equals(_pass)){
				Intent intent = new Intent(LoginMainActivity.this, OneTimeActivity.class);
				intent.putExtra("patient_id", _id);
					startActivity(intent);
			}else if(id.equals(_id)){
				Toast.makeText(LoginMainActivity.this,
						"パスワードが違います", Toast.LENGTH_SHORT).show();
			}else if(pass.equals(_pass)){
				Toast.makeText(LoginMainActivity.this,
						"ユーザIDが違います", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(LoginMainActivity.this,
						"もう一度入力してください", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
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
	/*
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
	}*/
	
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
