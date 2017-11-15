package axelbremer.axelbremerpset3;

import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONException;

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
    String jsonResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        menuListView = findViewById(R.id.menuListView);
        url = "https://resto.mprog.nl/";
        queue = Volley.newRequestQueue(this);

        getMenu();

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

    private void getMenu() {
//        String newUrl = url + "menu";
//
//        // Request a string response from the provided URL.
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, newUrl,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        jsonResponse = response;
//                        Log.d("RESPONSE", "onResponse: " + response);
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("SHIT", "onErrorResponse: wrong");
//            }
//        });
//        queue.add(stringRequest);

        Dish spaghetti = new Dish("Spaghetti and Meatballs", "entrees", "Seasoned meatballs on top of freshly-made spaghetti. Served with a robust tomato sauce.", "http://resto.mprog.nl/images/spaghetti.jpg", 1, 9.0);
        Dish soup = new Dish("Chicken Noodle Soup", "appetizers", "Delicious chicken simmered alongside yellow onions, carrots, celery, and bay leaves, chicken stock.", "http://resto.mprog.nl/images/chickensoup.jpg", 5, 3.0);

        menu.add(spaghetti);
        menu.add(soup);
    }
}