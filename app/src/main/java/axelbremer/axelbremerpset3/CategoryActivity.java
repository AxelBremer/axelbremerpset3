package axelbremer.axelbremerpset3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    ListView categoryListView;
    List list = new ArrayList();
    List<String> order = new ArrayList<>();
    String category;
    List<Dish> menu = new ArrayList<>();
    List<Dish> catMenu = new ArrayList<>();
    ArrayAdapter adapter;
    MyGlobals myGlob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        categoryListView = findViewById(R.id.categoryListView);

        myGlob = new MyGlobals(getApplicationContext());

        order = myGlob.loadFromSharedPrefs();

        Intent intent = getIntent();
        category = intent.getStringExtra("Category");

        menu = myGlob.getMenu();

        for(int i = 0; i < menu.size(); i++) {
            Dish temp = menu.get(i);
            Log.d("MENU", "dish: " + temp.getName());
            if(temp.getCategory().equals(category)) {
                catMenu.add(temp);
            }
        }

        for(int i = 0; i < catMenu.size(); i++) {
            Dish temp = catMenu.get(i);
            Log.d("CATMENU", "dishInCat: " + temp.getName());
            list.add(temp.getName());
        }

        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(CategoryActivity.this, DishActivity.class);
                Dish chosen = getDishByName((String)list.get(position));
                Integer chosenId = chosen.getId();
                intent.putExtra("Id", chosenId);
                startActivity(intent);
                finish();
            }
        });

        updateListView();

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

    private void updateListView() {
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);

        categoryListView.setAdapter(adapter);
    }

    public void onSeeOrderButtonClick(View view) {
        Intent intent = new Intent(CategoryActivity.this, OrderActivity.class);
        startActivity(intent);
        finish();
    }
}
