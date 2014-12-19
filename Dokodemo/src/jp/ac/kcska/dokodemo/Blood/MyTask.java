package jp.ac.kcska.dokodemo.Blood;

import java.io.ByteArrayOutputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.content.DialogInterface.OnCancelListener;

public class MyTask extends AsyncTask<String, Integer, String> implements OnCancelListener{
 
    private TextView textView;
    private ProgressDialog dialog;
    private Context context;

    /**
     * �R���X�g���N�^
     */
    public MyTask(TextView textView, Context context) {
        super();
        this.textView   = textView;
        this.context    = context;
    }
    
    @Override
    protected void onPreExecute() {
      dialog = new ProgressDialog(context);
      dialog.setTitle("Please wait");
      dialog.setMessage("Loading data...");
      dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
      dialog.setCancelable(true);
      dialog.setOnCancelListener(this);
      dialog.setMax(100);
      dialog.setProgress(0);
      dialog.show();
    }
 
    /**
     * �o�b�N�O�����h�ōs������
     */
    @Override
    protected String doInBackground(String... value) {
    	String data="";
    	String datatatata="";
            try {
                for(int i=0; i<10; i++){
                  if(isCancelled()){
                    break;
                  }
                  Thread.sleep(1);
                  publishProgress((i+1) * 10);
                }
                
                HttpClient httpClient = new DefaultHttpClient();
                StringBuilder uri = new StringBuilder(value[0]);
                HttpGet request = new HttpGet(uri.toString());
                HttpResponse httpResponse = null;
                httpResponse = httpClient.execute(request);
                
                int status = httpResponse.getStatusLine().getStatusCode();
                
                if (HttpStatus.SC_OK == status) {
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        httpResponse.getEntity().writeTo(outputStream);
                        data = outputStream.toString(); // JSON�f�[�^
                } else {
                    Log.d("JSONSampleActivity", "Status" + status);
                    return "99999999";
                }
                
                JSONObject rootObject = new JSONObject(data);
				JSONArray eventArray = rootObject.getJSONArray("events");
				JSONObject jsonObject = eventArray.getJSONObject(1);
				JSONObject json = jsonObject.getJSONObject("event");
				datatatata = json.getString("title");
				
              } catch (InterruptedException e) {
            	  	e.printStackTrace();
              } catch (JSONException e) {
    				e.printStackTrace();
              } catch (Exception e) {
                Log.d("JSONSampleActivity", "Error Execute");
                return "9999";
              }
				/*
				for (int i = 0; i < eventArray.length(); i++) {
				    JSONObject jsonObject = eventArray.getJSONObject(i);
				    JSONObject json = jsonObject.getJSONObject("event");
				    Log.d("JSONSampleActivity", json.getString("title"));
				}
				*/
        return datatatata;
    }
 
    /**
     * �o�b�N�O�����h�������������AUI�X���b�h�ɔ��f����
     */
    @Override
    protected void onPostExecute(String result) {
        textView.setText(String.valueOf(result));
        dialog.dismiss();
    }
    
    @Override
    protected void onProgressUpdate(Integer... values) {
      dialog.setProgress(values[0]);
    }
    
    @Override
    protected void onCancelled() {
      dialog.dismiss();
    }
    
    @Override
    public void onCancel(DialogInterface dialog) {
      this.cancel(true);
    }
}