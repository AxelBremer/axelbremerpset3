package axelbremer.axelbremerpset3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    ListView menuListView;
    List list = new ArrayList();
    List<Dish> menu = new ArrayList<>();
    List categoryList = new ArrayList();
    ArrayAdapter adapter;
    String url;
    RequestQueue queue;
    MyGlobals myGlob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        menuListView = findViewById(R.id.menuListView);
        url = "https://resto.mprog.nl/";
        queue = Volley.newRequestQueue(this);

        myGlob = new MyGlobals(getApplicationContext());

        myGlob.getMenu();

        myGlob.loadFromSharedPrefs();

        for(int i = 0; i < menu.size(); i++) {
            Dish temp = menu.get(i);
            categoryList.add(temp.getCategory());
        }

        list = categoryList;

        updateListView();

        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(MenuActivity.this, CategoryActivity.class);
                String category = (String) categoryList.get(position);
                intent.putExtra("Category", category);
                startActivity(intent);
            }
        });
    }

    private void updateListView() {
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);

        menuListView.setAdapter(adapter);
    }

    public void onSeeOrderButtonClick(View view) {
        Intent intent = new Intent(MenuActivity.this, OrderActivity.class);
        startActivity(intent);
        finish();
    }
}