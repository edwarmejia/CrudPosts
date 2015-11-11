package mendoza.luigui.holamundo;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import tasks.PostTask;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void openSecondActivity(View view) {
        Intent intent = new Intent(this, FormPostActivity.class);
        startActivity(intent);

    }

    public void openSecondActivityTwo(View view) {
        Intent intent = new Intent(this, ListPostActivity.class);
        startActivity(intent);
    }

    public void sendEmail(View view) {

    }


}
