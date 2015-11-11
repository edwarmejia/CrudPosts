package mendoza.luigui.holamundo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapters.PostAdapter;
import database.DatabaseSqlLiteHelper;
import models.Post;

public class ListPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_post);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setListContent();

    }

    private void setListContent() {

        PostAdapter adapter = new PostAdapter(this, R.layout.simple_list_item_post, GetPostsFromWebService());
        ListView list = (ListView) findViewById(R.id.listPost);
        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), ModificarPostActivity.class);

                String postId = ((TextView) view.findViewById(R.id.txtUserId)).getText().toString();
                String postUser = ((TextView) view.findViewById(R.id.txtUsuario)).getText().toString();
                String postTitle = ((TextView) view.findViewById(R.id.txtTitle)).getText().toString();
                String postContent = ((TextView) view.findViewById(R.id.txtContent)).getText().toString();

                intent.putExtra("CurrentPostUserId", postId);
                intent.putExtra("CurrentPostUser", postUser);
                intent.putExtra("CurrentPostTitle", postTitle);
                intent.putExtra("CurrentPostContent", postContent);

                startActivity(intent);
            }
        });

    }

    private List<Post> GetPosts() {

        List<Post> posts = new ArrayList<>();

        DatabaseSqlLiteHelper helper = new DatabaseSqlLiteHelper(this, "android", null, 1);

        SQLiteDatabase db = helper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * from posts;", null);

        while (c.moveToNext()) {
            Post post = new Post();
            post.id = c.getString(0);
            post.user = c.getString(1);
            post.title = c.getString(2);

            posts.add(post);
        }

        return posts;

    }

    private List<Post> GetPostsFromWebService()
    {
        List<Post> posts = new ArrayList<>();

        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("http://api.lumenagile.com/posts");

        try{

            HttpResponse httpResponse = httpClient.execute(httpGet);
            String stringResponse = EntityUtils.toString(httpResponse.getEntity());

            JSONArray jsonResponse = new JSONArray(stringResponse);
            for (int i = 0; i < jsonResponse.length(); i++)
            {
                Post post = new Post();

                JSONObject obj = jsonResponse.getJSONObject(i);

                post.id = obj.getString("id");
                post.user = obj.getString("user");
                post.title = obj.getString("name");
                post.content = obj.getString("content");

                System.out.print(post);

                posts.add(post);

            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return posts;
    }



}
