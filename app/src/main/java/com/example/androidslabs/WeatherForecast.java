package com.example.androidslabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {
    ImageView weatherImg;
    TextView curTemp;
    TextView minTemp;
    TextView maxTemp;
    TextView uvRatingTv;
    ProgressBar pb;
    ForecastQuery fq;
    public static final String URL_OTTAWA_WEATHER
            = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric";
    public static final String URL_UV_RATING
            = "http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389";
    public static final String URL_DOWNLOAD_IMG =
            "http://openweathermap.org/img/w/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        fq = new ForecastQuery();
        fq.execute();

        weatherImg = findViewById(R.id.weatherImg);
        curTemp = findViewById(R.id.curTemp);
        minTemp = findViewById(R.id.minTemp);
        maxTemp = findViewById(R.id.maxTemp);
        uvRatingTv = findViewById(R.id.uvRating);
        pb = findViewById(R.id.weatherPb);

        pb.setVisibility(ProgressBar.VISIBLE);
    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        String min ="";
        String max ="";
        String current ="";
        String iconName ="";
        String uvRating ="";
        Bitmap image = null;

        @Override
        protected String doInBackground(String... strings) {

            try {
                URL url = new URL(URL_OTTAWA_WEATHER);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream response = urlConnection.getInputStream();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(response, "UTF-8");

                String parameter = null;

                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {

                    if (eventType == XmlPullParser.START_TAG) {

                        if (xpp.getName().equalsIgnoreCase("temperature")) {
                            min = xpp.getAttributeValue(null, "min");
                            publishProgress(25);
                            max = xpp.getAttributeValue(null, "max");
                            publishProgress(50);
                            current = xpp.getAttributeValue(null, "value");
                            publishProgress(75);
                        }

                        if (xpp.getName().equalsIgnoreCase("weather")) {
                            iconName = xpp.getAttributeValue(null, "icon");
                        }

                    }
                    eventType = xpp.next();
                }

                URL url2 = new URL(URL_UV_RATING);
                HttpURLConnection urlConnection2 = (HttpURLConnection) url2.openConnection();
                InputStream response2 = urlConnection2.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(response2));
                String line="";
                StringBuilder stringBuilder = new StringBuilder();
                while((line = reader.readLine())!=null){
                    stringBuilder.append(line);
                }
                String result = stringBuilder.toString();
                JSONObject jObject = new JSONObject(result);
                double uv = jObject.getDouble("value");
                uvRating = String.valueOf(uv);

                if(iconName!=null && !iconName.equalsIgnoreCase("")){
                    String imagefile = iconName+".png";

                    if(fileExistance(imagefile)){
                        FileInputStream fis = null;
                        try {
                            fis = openFileInput(imagefile);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        image = BitmapFactory.decodeStream(fis);
                    }else{
                        String urlString = URL_DOWNLOAD_IMG+iconName+".png";
                        URL imgurl = new URL(urlString);
                        HttpURLConnection connection3 = (HttpURLConnection) imgurl.openConnection();
                        connection3.connect();
                        int responseCode = connection3.getResponseCode();
                        if (responseCode == 200) {
                            image = BitmapFactory.decodeStream(connection3.getInputStream());
                        }

                        FileOutputStream outputStream = openFileOutput( iconName + ".png", Context.MODE_PRIVATE);
                        image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                        outputStream.flush();
                        outputStream.close();
                        publishProgress(100);
                    }
                }


            } catch (Exception e) {

            }

            return "Done";
        }

        @Override
        protected void onPostExecute(String s) {
            curTemp.setText("Current temperature : "+current);
            minTemp.setText("Min Temperature : "+min);
            maxTemp.setText("Max Temperature : "+max);
            uvRatingTv.setText("UV Rating : "+uvRating);
            weatherImg.setImageBitmap(image);

            pb.setVisibility(ProgressBar.INVISIBLE);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            pb.setProgress(values[0]);
        }
    }

    public boolean fileExistance(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }

}
