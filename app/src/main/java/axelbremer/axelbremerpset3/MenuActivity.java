package axelbremer.axelbremerpset3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    String listName;
    ArrayAdapter adapter;
    String url;
    RequestQueue queue;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        menuListView = findViewById(R.id.menuListView);
        url = "https://resto.mprog.nl/";
        listName = "categories";
        queue = Volley.newRequestQueue(this);

        list = getListFromAPI(listName);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);

        menuListView.setAdapter(adapter);
    }

    private List getListFromAPI(String listName) {
        String newUrl = url + listName;
        List list = new ArrayList();

        Log.d("URL", newUrl);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, newUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            jsonArray = new JSONArray(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("SHIT", "onErrorResponse: wrong");
            }
        });
        queue.add(stringRequest);

        for(int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString());
        }

        return list;
    }
}
