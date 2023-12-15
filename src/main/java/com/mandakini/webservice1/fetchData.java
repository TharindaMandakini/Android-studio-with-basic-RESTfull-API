package com.mandakini.webservice1;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class fetchData extends AsyncTask {
    public String data = "";
    public String dataParsed = "";
    public String singleParsed = "";

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        MainActivity.data.setText(this.dataParsed);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            URL url = new URL("https://www.jsonkeeper.com/b/I3V2");
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }
            JSONArray JA = new JSONArray(data);
            for (int i = 0; i < JA.length(); i++) {
                JSONObject JO = (JSONObject) JA.get(i);
                singleParsed = "Name:" + JO.get("name") + "\n" +
                        "Reg No:" + JO.get("reg no") + "\n" +
                        "Email:" + JO.get("e-mail") + "\n" +
                        "Country:" + JO.get("country") + "\n";
                dataParsed = dataParsed + singleParsed + "\n";
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
