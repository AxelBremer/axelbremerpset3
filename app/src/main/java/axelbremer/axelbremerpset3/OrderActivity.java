package axelbremer.axelbremerpset3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    List<String> order = new ArrayList<>();
    ListView orderListView;
    ArrayAdapter adapter;
    MyGlobals myGlob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        myGlob = new MyGlobals(getApplicationContext());

        order = myGlob.loadFromSharedPrefs();



        for(int i = 0; i < order.size(); i++) {
            Log.d("ORDERACTIVITY", order.get(i));
        }

        orderListView = findViewById(R.id.orderListView);

        updateListView();
    }

    private void updateListView() {
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, order);

        orderListView.setAdapter(adapter);
    }

    public void onOrderButtonClick(View view) {
        myGlob.clearPrefs();

        Intent intent = new Intent(OrderActivity.this, LastActivity.class);
        startActivity(intent);
        finish();
    }
}
