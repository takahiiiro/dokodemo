package jp.ac.kcska.dokodemo.Show;

import java.util.ArrayList;

public interface AsyncCallback {
	
	void onPreExecute();
    void onPostExecute(ArrayList<String> result);
    void onProgressUpdate(int progress);
    void onCancelled();
    
}
