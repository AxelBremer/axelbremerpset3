package axelbremer.axelbremerpset3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last);
    }

    public void newOrderClick(View view) {
        Intent intent = new Intent(LastActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
}
