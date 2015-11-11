package tasks;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mendoza.luigui.holamundo.ListPostActivity;
import models.Post;

/**
 * Created by ludk on 07/11/15.
 */
public class PostTask extends AsyncTask<String, Void, String> {

    public static final String HTTP_RESPONSE = "httpResponse";

    private Context context;
    private HttpClient httpClient;


    public PostTask(Context context) {

        this.context = context;
        this.httpClient = new DefaultHttpClient();

    }


    @Override
    protected String doInBackground(String... params) {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet("http://api.lumenagile.com/posts");
            httpget.setHeader("content-type", "application/json");
            HttpResponse response = httpClient.execute(httpget);
            return EntityUtils.toString(response.getEntity());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }


    @Override
    protected void onPostExecute(String response) {
        Intent intent = new Intent(context, ListPostActivity.class);
        intent.putExtra("PostsIndexResponse", response);
        context.startActivity(intent);
    }


}
