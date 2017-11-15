package axelbremer.axelbremerpset3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DishActivity extends AppCompatActivity {

    List<Dish> menu = new ArrayList<>();
    Dish currentDish;
    TextView nameView, descView, priceView, catView;
    ImageView dishImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);

        getMenu();

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


//        URL url = null;
//        try {
//            url = new URL(currentDish.getUrl());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        Bitmap bmp = null;
//        try {
//            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        dishImageView = findViewById(R.id.dishImageView);
//        dishImageView.setImageBitmap(bmp);


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

    public void onAddButtonClick(View view) {
    }
}
