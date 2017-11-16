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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {

    List<String> order = new ArrayList<>();
    List<Dish> menu = new ArrayList<>();
    ListView orderListView;
    ArrayAdapter adapter;
    MyGlobals myGlob;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        myGlob = new MyGlobals(getApplicationContext());


        queue = Volley.newRequestQueue(this);

        String newUrl = "https://resto.mprog.nl/menu";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, newUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        menu = myGlob.createMenu(response);
                        Log.d("RESPONSE", "onResponse: " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("SHIT", "onErrorResponse: wrong");
            }
        });
        queue.add(stringRequest);

        order = myGlob.loadFromSharedPrefs();

        orderListView = findViewById(R.id.orderListView);

        updateListView();

        orderListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {

                order.remove(pos);

                updateListView();

                return true;
            }
        });
    }

    private void updateListView() {
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, order);

        orderListView.setAdapter(adapter);
    }

    public void onOrderButtonClick(View view) {
        String newUrl = "https://resto.mprog.nl/order";

        StringRequest postRequest = new StringRequest(Request.Method.POST, newUrl,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);

                        myGlob.clearPrefs();
                        Integer duration = 0;

                        try {
                            JSONObject obj = new JSONObject(response);
                            duration = obj.getInt("preparation_time");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Intent intent = new Intent(OrderActivity.this, LastActivity.class);
                        intent.putExtra("Duration", duration);
                        startActivity(intent);
                        finish();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", "shit");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap();
                params.put("order", getOrderString());

                return params;
            }
        };
        queue.add(postRequest);
    }

    private String getOrderString() {
        String orderString = "[";

        for(int i = 0; i < order.size(); i++) {
            String name = order.get(i);
            Integer id = getDishByName(name).getId();
            orderString += id.toString() + ",";
        }

        orderString += "]";

        return orderString;
    }

    private Dish getDishByName(String name) {
        Dish dish = new Dish();

        for(int i = 0; i < menu.size(); i++) {
            Dish temp = menu.get(i);
            if(temp.getName().equals(name)) {
                dish = temp;
            }
        }

        return dish;
    }
}
