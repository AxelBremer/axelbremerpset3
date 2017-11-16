package axelbremer.axelbremerpset3;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Axel Bremer on 16-11-2017.
 */

public class MyGlobals {
    Context mContext;
    RequestQueue queue;
    List<Dish> menu = new ArrayList<>();
    Boolean wait = true;

    // constructor
    public MyGlobals(Context context){
        this.mContext = context;

        queue = Volley.newRequestQueue(this.mContext);

        String newUrl = "https://resto.mprog.nl/menu";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, newUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        createMenu(response);
                        wait = false;
                        Log.d("RESPONSE", "onResponse: " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("SHIT", "onErrorResponse: wrong");
            }
        });
        queue.add(stringRequest);
    }

    public void saveToSharedPrefs(List<String> order){
        Log.d("saving", "loadFromSharedPrefs: ");
        SharedPreferences prefs = mContext.getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();

        String json = gson.toJson(order);

        editor.putString("order", json);
        editor.commit();
    }

    public List<Dish> createMenu(String response) {
        List<Dish> menu = new ArrayList<>();

        Log.d("menu", "createMenu: ");
        try {
            JSONObject obj = new JSONObject(response);
            JSONArray arr = obj.getJSONArray("items");

            for(int i = 0; i < arr.length(); i++) {
                JSONObject dish = arr.getJSONObject(i);
                String name = dish.getString("name");
                Log.d("DISH", name);
                String cat = dish.getString("category");
                Log.d("DISH", cat);
                String desc = dish.getString("description");
                Log.d("DISH", desc);
                String url = dish.getString("image_url");
                Log.d("DISH", url);
                Double price = dish.getDouble("price");
                Integer id = dish.getInt("id");
                Dish newDish = new Dish(name, cat, desc, url, id, price);
                menu.add(newDish);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return menu;
    }

    public ArrayList<String> loadFromSharedPrefs(){
        Log.d("loading", "loadFromSharedPrefs: ");
        SharedPreferences prefs = mContext.getSharedPreferences("settings", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = prefs.getString("order", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        ArrayList<String> order = gson.fromJson(json, type);

        if(order == null) {
            Log.d("NULL", "ORDER/loadFromSharedPrefs: null");
            order = new ArrayList<>();
        }

        return order;
    }

    public List<Dish> getMenu() {

//        Dish spaghetti = new Dish("Spaghetti and Meatballs", "entrees", "Seasoned meatballs on top of freshly-made spaghetti. Served with a robust tomato sauce.", "http://resto.mprog.nl/images/spaghetti.jpg", 1, 9.0);
//        Dish soup = new Dish("Chicken Noodle Soup", "appetizers", "Delicious chicken simmered alongside yellow onions, carrots, celery, and bay leaves, chicken stock.", "http://resto.mprog.nl/images/chickensoup.jpg", 5, 3.0);
//
//        menu.add(spaghetti);
//        menu.add(soup);

        while(wait) {
            Log.d("WAIT", "getMenu: ");
        }

        return menu;
    }

    public void clearPrefs() {
        SharedPreferences prefs = mContext.getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }
}
