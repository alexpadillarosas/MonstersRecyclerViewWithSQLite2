package com.blueradix.android.monstersrecyclerviewwithsqlite;

import android.os.Bundle;

import com.blueradix.android.monstersrecyclerviewwithsqlite.entities.Monster;
import com.blueradix.android.monstersrecyclerviewwithsqlite.database.MonsterDatabaseHelper;
import com.blueradix.android.monstersrecyclerviewwithsqlite.recyclerview.MonsterRecyclerViewAdapter;
import com.blueradix.android.monstersrecyclerviewwithsqlite.service.DataService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

/**
 * First and Foremost, in the assets/monsters folder you will find monster.db and monster.db-journal
 *          files to install them in your device, after it, delete them.
 *
 * Second   modify the MonsterDatabaseHelper to return a monster given it's database id
 * Third    Create An Scrolling Activity call it  AddMonsterScrollingActivity
 *          Edit the content_add_monster_scrolling.xml generated file. find it in the Layout folder
 *          Add a controls to input the monster's name, description, scariness level and a couple
 *          of buttons to cancel or add the monster.
 *          Next, create variables to manipulate them in the AddMonsterScrollingActivity
 * Fourth   Add a Splash Screen to the application.
 * Fifth    Test the internationalization in our application
 */

public class MainActivity extends AppCompatActivity {

    private List<Monster> monsters;
    private MonsterRecyclerViewAdapter adapter;
    private DataService monsterDataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* As the SplashTheme was set as a background image, it will stay as background for all
            our activities, and we don't want that. Set
            TODO:  set back the old Theme:   AppTheme  when the activity gets created
        */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        RecyclerView monstersRecyclerView = findViewById(R.id.monstersRecyclerView);

        //set the layout manager
        //monstersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);

        monstersRecyclerView.setLayoutManager(gridLayoutManager);
        //monstersRecyclerView.setLayoutManager(linearLayoutManager);

        monsterDataService = new DataService();
        monsterDataService.init(this);
        //once your database is created, you can find it using Device File Explorer
        //go to: data/data/app_package_name/databases there you will find your databases

        //Load Data from the database
        monsters = monsterDataService.getMonsters();
        //create adapter passing the data, and the context
        adapter = new MonsterRecyclerViewAdapter(monsters, this);
        //attach the adapter to the Recyclerview
        monstersRecyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
