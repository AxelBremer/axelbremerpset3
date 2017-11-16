package axelbremer.axelbremerpset3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DishActivity extends AppCompatActivity {

    List<Dish> menu = new ArrayList<>();
    List<String> order = new ArrayList<>();
    Dish currentDish;
    TextView nameView, descView, priceView, catView;
    ImageView dishImageView;
    MyGlobals myGlob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);

        myGlob = new MyGlobals(getApplicationContext());

        order = myGlob.loadFromSharedPrefs();

        menu = myGlob.getMenu();

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
