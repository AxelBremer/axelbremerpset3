package axelbremer.axelbremerpset3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DishActivity extends AppCompatActivity {

    List<Dish> menu = new ArrayList<>();
    List<String> order = new ArrayList<>();
    Dish currentDish;
    TextView nameView, descView, priceView, catView;
    ImageView dishImageView;
    RequestQueue queue;
    MyGlobals myGlob;
    Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);

        myGlob = new MyGlobals(getApplicationContext());

        order = myGlob.loadFromSharedPrefs();

        queue = Volley.newRequestQueue(this);

        String newUrl = "https://resto.mprog.nl/menu";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, newUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        menu = myGlob.createMenu(response);
                        Log.d("RESPONSE", "onResponse: " + response);

                        Intent intent = getIntent();

                        Integer chosenId = intent.getIntExtra("Id",-1);

                        currentDish = getDishById(chosenId);

                        nameView = findViewById(R.id.dishNameTextView);
                        nameView.setText(currentDish.getName());
                        descView = findViewById(R.id.dishDescTextView);
                        descView.setText(currentDish.getDescription());
                        priceView = findViewById(R.id.dishPriceTextView);
                        priceView.setText(Double.toString(currentDish.getPrice()));
                        catView = findViewById(R.id.dishCatTextView);
                        catView.setText(currentDish.getCategory());

                        new Thread() {
                            @Override
                            public void run() {
                                URL url = null;
                                try {
                                    url = new URL(currentDish.getUrl());
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                                Bitmap bmp = null;
                                try {
                                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                    setImage(bmp);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            refreshImage();
                                        }
                                    });
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("SHIT", "onErrorResponse: wrong");
            }
        });
        queue.add(stringRequest);
    }

    private void refreshImage() {
        dishImageView = findViewById(R.id.dishImageView);
        dishImageView.setImageBitmap(bmp);
    }

    private void setImage(Bitmap b) {
        bmp = b;
    }

    private Dish getDishById(Integer chosenId) {
        Dish dish = new Dish();

        for(int i = 0; i < menu.size(); i++) {
            Dish temp = menu.get(i);
            if(temp.getId() == chosenId) {
                dish = temp;
            }
        }

        return dish;
    }

    public void onAddButtonClick(View view) {
        order.add(currentDish.getName());

        for(int i = 0; i < order.size(); i++) {
            Log.d("DISHACTIVITY", order.get(i));
        }

        myGlob.saveToSharedPrefs(order);

        Intent intent = new Intent(DishActivity.this, OrderActivity.class);
        startActivity(intent);
        finish();


    }

    public void onSeeOrderButtonClick(View view) {
        Intent intent = new Intent(DishActivity.this, OrderActivity.class);
        startActivity(intent);
        finish();
    }
}
