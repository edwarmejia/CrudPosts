package mendoza.luigui.holamundo;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ModificarPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_post);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Button edt = (Button) findViewById(R.id.modificapost);
        edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Modificarpost();
            }
        });

        Button delete = (Button) findViewById(R.id.eliminapost);
        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DeletePosts();
            }
        });

        String user = getIntent().getExtras().getString("CurrentPostUser");
        String title = getIntent().getExtras().getString("CurrentPostTitle");
        String content = getIntent().getExtras().getString("CurrentPostContent");

        ((TextView)findViewById(R.id.txtUsuario)).setText(user);
        ((TextView)findViewById(R.id.txtTitle)).setText(title);
        ((TextView)findViewById(R.id.txtContent)).setText(content);
    }

    private void Modificarpost() {
        String userId = getIntent().getExtras().getString("CurrentPostUserId");
        HttpClient httpClient = new DefaultHttpClient();
        HttpPut httpPut = new HttpPut("http://api.lumenagile.com/posts/" + userId);
        httpPut.setHeader("content-type", "application/json");
        JSONObject dato = new JSONObject();
        try {
            dato.put("user", ((EditText)findViewById(R.id.txtUsuario)).getText().toString());
            dato.put("name", ((EditText)findViewById(R.id.txtTitle)).getText().toString());
            dato.put("content", ((EditText)findViewById(R.id.txtContent)).getText().toString());
            StringEntity entity = new StringEntity(dato.toString());
            httpPut.setEntity(entity);
            httpClient.execute(httpPut);
            Toast.makeText(getApplicationContext(), "Se actualizo correctamente", Toast.LENGTH_SHORT).show();

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

    public void DeletePosts() {
        String userId = getIntent().getExtras().getString("CurrentPostUserId");
        HttpClient httpClient = new DefaultHttpClient();
        HttpDelete httpDelete = new HttpDelete("http://api.lumenagile.com/posts/" + userId);
        httpDelete.setHeader("content-type", "application/json");
        JSONObject dato = new JSONObject();

        try {
            httpClient.execute(httpDelete);
            Toast.makeText(getApplicationContext(), "Se elimino correctamente", Toast.LENGTH_SHORT).show();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}