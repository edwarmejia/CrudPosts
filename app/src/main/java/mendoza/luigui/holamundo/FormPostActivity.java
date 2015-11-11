package mendoza.luigui.holamundo;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import database.DatabaseSqlLiteHelper;

public class FormPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_post);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Button btn = (Button) findViewById(R.id.btnguardad);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StoreInApi();


            }
        });
    }

    private void StoreInApi() {

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://api.lumenagile.com/posts");
        httpPost.setHeader("content-type", "application/json");
        JSONObject dato = new JSONObject();

        try {

            dato.put("user", ((EditText)findViewById(R.id.txtUsuario)).getText().toString());
            dato.put("name", ((EditText)findViewById(R.id.txtTitle)).getText().toString());
            dato.put("content", ((EditText)findViewById(R.id.txtContent)).getText().toString());

            StringEntity entity = new StringEntity(dato.toString());
            httpPost.setEntity(entity);
            httpClient.execute(httpPost);

            Toast.makeText(getApplicationContext(), "Se guardo correctamente", Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void StoreInDatabase() {
        // guardar en base de datos
        DatabaseSqlLiteHelper helper = new DatabaseSqlLiteHelper(getApplicationContext(), "android", null, 1);


        SQLiteDatabase database = helper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("user", ((EditText)findViewById(R.id.txtUsuario)).getText().toString());
        values.put("title", ((EditText)findViewById(R.id.txtTitle)).getText().toString());
        values.put("content", ((EditText) findViewById(R.id.txtContent)).getText().toString());

        database.insert("posts", null, values);

        Toast.makeText(getApplicationContext(), "Se guardo correctamente", Toast.LENGTH_SHORT).show();

        database.close();
    }

}
