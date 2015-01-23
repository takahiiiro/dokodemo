package jp.ac.kcska.dokodemo.Show;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import jp.ac.kcska.dokodemo.Vial.AsyncCallback;

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
 
  
    public MyTask(AsyncCallback asyncCallback) {
        this._asyncCallback = asyncCallback;
    }
    
    protected void onPreExecute() {
        super.onPreExecute();
        this._asyncCallback.onPreExecute();
    }
 
    /**
     * �o�b�N�O�����h�ōs������
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
                Log.d("a", httpResponse.getEntity().toString());
                
                int status = httpResponse.getStatusLine().getStatusCode();
                
                if (HttpStatus.SC_OK == status) {
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        httpResponse.getEntity().writeTo(outputStream);
                        data = outputStream.toString(); // JSON�f�[�^
                } else {
                    Log.d("JSONSampleActivity", "Status" + status);
                    return list;
                }
                
                list=new ArrayList<String>();
                
                if(value[0].equals("https://kcsgogo.herokuapp.com/foods.json")){
                	JSONArray rootArray = new JSONArray(data);
                	for(int i = 0; i < rootArray.length();i++){
                		JSONObject jsonObject = rootArray.getJSONObject(i);
                		list.add(""+jsonObject.getString("id"));
                		list.add(""+jsonObject.getString("meal_id")+"");
                		list.add(""+jsonObject.getString("foods_id")+"");
                		list.add(""+jsonObject.getString("url")+"");
                		list.add("\n");
                	}
                }else if(value[0].equalsIgnoreCase("https://kcsgogo.herokuapp.com/medicines.json")){
                	JSONArray rootArray = new JSONArray(data);
                	for(int i = 0; i < rootArray.length();i++){
                		JSONObject jsonObject = rootArray.getJSONObject(i);
                		list.add(""+jsonObject.getString("medicine_name"));
                		list.add("1日"+jsonObject.getString("take_medicine_count")+"回");
                		list.add(""+jsonObject.getString("take_time")+"");
                		list.add(""+jsonObject.getString("take_number_of_days")+"日分");
                		list.add(""+jsonObject.getString("url")+"");
                		list.add("\n");
                	}
                }else if(value[0].equalsIgnoreCase("https://kcsgogo.herokuapp.com/prescriptions.json")){
                	JSONArray rootArray = new JSONArray(data);
                	for(int i = 0; i < rootArray.length();i++){
                		JSONObject jsonObject = rootArray.getJSONObject(i);
                		list.add("患者ID"+jsonObject.getString("patient_id"));
                		list.add("処方日"+jsonObject.getString("day_of_delivery"));
                		list.add("～"+jsonObject.getString("period")+"");
                		list.add("病院id"+jsonObject.getString("hospital_id")+"");
                		list.add(""+jsonObject.getString("url")+"");
                		list.add("\n");
                	}
                }else if(value[0].equalsIgnoreCase("https://kcsgogo.herokuapp.com/vitals.json")){
                	JSONArray rootArray = new JSONArray(data);
                	for(int i = 0; i < rootArray.length();i++){
                		JSONObject jsonObject = rootArray.getJSONObject(i);
                		list.add("患者ID"+jsonObject.getString("patient_id"));
                		list.add("日付"+jsonObject.getString("vital_date")+"");
                		list.add("体温"+jsonObject.getString("temperature")+"℃");
                		list.add("体重"+jsonObject.getString("weight")+"kg");
                		list.add(""+jsonObject.getString("url")+"");
                		list.add("\n");
                	}
                }else if(value[0].equalsIgnoreCase("https://kcsgogo.herokuapp.com/glucoses.json")){
                	JSONArray rootArray = new JSONArray(data);
                	for(int i = 0; i < rootArray.length();i++){
                		JSONObject jsonObject = rootArray.getJSONObject(i);
                		list.add("患者ID"+jsonObject.getString("patient_id"));
                		list.add("タイムゾーン"+jsonObject.getString("glucose_timezone")+"");
                		list.add("時間"+jsonObject.getString("time")+"");
                		list.add("血糖値"+jsonObject.getString("glucose_value")+"");
                		list.add("インスリン投与量"+jsonObject.getString("insulin_dose")+"ｇ");
                		list.add("インスリンタイプ"+jsonObject.getString("insulin_type_id")+"");
                		list.add(""+jsonObject.getString("url")+"");
                		list.add("\n");
                	}
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