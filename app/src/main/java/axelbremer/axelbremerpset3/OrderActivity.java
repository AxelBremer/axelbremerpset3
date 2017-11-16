package axelbremer.axelbremerpset3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderActivity extends AppCompatActivity {

    List<String> order = new ArrayList<>();
    ListView orderListView;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        loadFromSharedPrefs();



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

    public void saveToSharedPrefs(){
        SharedPreferences prefs = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();

        String json = gson.toJson(order);

        editor.putString("order", json);
        editor.commit();
    }

    public void loadFromSharedPrefs(){
        SharedPreferences prefs = getSharedPreferences("settings", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = prefs.getString("order", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        ArrayList<String> order = gson.fromJson(json, type);

        if(order == null) {
            Log.d("NULL", "ORDER/loadFromSharedPrefs: null");
            order = new ArrayList<>();
        }
    }
}
