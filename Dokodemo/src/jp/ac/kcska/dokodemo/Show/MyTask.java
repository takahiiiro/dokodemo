package jp.ac.kcska.dokodemo.Show;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

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

public class MyTask extends AsyncTask<String, Integer, ArrayList<String>>{
 
    private int i;
    private ArrayList<String> list;
    private AsyncCallback _asyncCallback = null;
 
    /*
     * コンストラクタ
     */
    public MyTask(AsyncCallback asyncCallback) {
        this._asyncCallback = asyncCallback;
    }
    
    protected void onPreExecute() {
        super.onPreExecute();
        this._asyncCallback.onPreExecute();
    }
 
    /**
     * バックグランドで行う処理
     */
    @Override
    protected ArrayList<String> doInBackground(String... value) {
    	String data="";
    	String datatatata="";
            try {
                for(int i=0; i<10; i++){
                  if(isCancelled()){
                    break;
                  }
                  Thread.sleep(1);
                  publishProgress((i+1) * 50);
                }
                
                HttpClient httpClient = new DefaultHttpClient();
                StringBuilder uri = new StringBuilder(value[0]);
                HttpGet request = new HttpGet(uri.toString());
                HttpResponse httpResponse = null;
                httpResponse = httpClient.execute(request);
                Log.d("ウルトラソウル", httpResponse.getEntity().toString());
                
                int status = httpResponse.getStatusLine().getStatusCode();
                
                if (HttpStatus.SC_OK == status) {
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        httpResponse.getEntity().writeTo(outputStream);
                        data = outputStream.toString(); // JSONデータ
                } else {
                    Log.d("JSONSampleActivity", "Status" + status);
                    return list;
                }
                
                list=new ArrayList<String>();
            	JSONArray rootArray = new JSONArray(data);
            	for(int i = 0; i < rootArray.length();i++){
            		JSONObject jsonObject = rootArray.getJSONObject(i);
            		list.add(jsonObject.getString("formura_delivery_date"));
            	}
				
              } catch (InterruptedException e) {
            	  	e.printStackTrace();
              } catch (JSONException e) {
    				e.printStackTrace();
              } catch (Exception e) {
                Log.d("JSONSampleActivity", "Error Execute");
                return list;
              }
				/*
				for (int i = 0; i < eventArray.length(); i++) {
				    JSONObject jsonObject = eventArray.getJSONObject(i);
				    JSONObject json = jsonObject.getJSONObject("event");
				    Log.d("JSONSampleActivity", json.getString("title"));
				}
				*/
        return list;
    }
 
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        this._asyncCallback.onProgressUpdate(values[0]);
    }

    protected void onPostExecute(ArrayList<String> result) {
        super.onPostExecute(result);
        this._asyncCallback.onPostExecute(result);
    }
    
    protected void onCancelled() {
        super.onCancelled();
        this._asyncCallback.onCancelled();
    }
}