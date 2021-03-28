package com.blueradix.android.monstersrecyclerviewwithsqlite;

import android.content.Intent;
import android.os.Bundle;

import com.blueradix.android.monstersrecyclerviewwithsqlite.activities.AddMonsterScrollingActivity;
import com.blueradix.android.monstersrecyclerviewwithsqlite.entities.Monster;
import com.blueradix.android.monstersrecyclerviewwithsqlite.recyclerview.MonsterRecyclerViewAdapter;
import com.blueradix.android.monstersrecyclerviewwithsqlite.service.DataService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import static com.blueradix.android.monstersrecyclerviewwithsqlite.entities.Constants.ADD_MONSTER_ACTIVITY_CODE;

public class MainActivity extends AppCompatActivity {

    private List<Monster> monsters;
    private MonsterRecyclerViewAdapter adapter;
    private DataService monsterDataService;
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //https://developer.android.com/topic/libraries/view-binding?utm_medium=studio-assistant-stable&utm_source=android-studio-3-6
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewMonster();
            }
        });
        rootView = findViewById(android.R.id.content).getRootView();

        RecyclerView monstersRecyclerView = findViewById(R.id.monstersRecyclerView);

        //set the layout manager
        //monstersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);

//        monstersRecyclerView.setLayoutManager(gridLayoutManager);
        monstersRecyclerView.setLayoutManager(linearLayoutManager);

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

        /** TODO 1: implement:
         *      new activity called MonsterDetailScrollingActivity to Rate a monster
         *          add an imageView:
         *              set as id : monsterImageViewDetailActivity
         *              scaleType: fitCenter
         *          add a textView for the Monster Name:
         *              set as id: monsterNameTextViewDetailActivity
         *          add a Raiting Bar:
         *              set as id: monsterRatingBarDetailActivity
         *              style: @style/Widget.AppCompat.RatingBar.Indicator
         *          add a label for the rating bar
         *              set as text : rate this monster
         *       Override the onBackPressed method:
         *          call a method setIntentWWithData, this method will set data we will send back to the MonsterRecyclerViewAdapter
         *       Display the back arrow in the toolbar of the activity. in the onCreate method add this:
         *          getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         *          toolbar.setNavigationOnClickListener(new View.OnClickListener() {
         *             @Override
         *             public void onClick(View v) {
         *                 setIntentWithData();
         *             }
         *          });
         *
         **/

        /** TODO 5: Create A listener Interface call it: OnMonsterListener
         *      place it inside the recyclerview package
         *      add the following method to the interface: void onMonsterClick(Monster monster);
         *      Make the main class implement this interface and create an intent inside of this method to navigate to the MonsterDetailScrollingActivity
         */

        /** TODO 6: Make the recyclerview listen to the user tap / clicks
         *      The recyclerview is the UI component that will know when someone tap(click) a monster so, pass the listener to the MonsterRecyclerViewAdapter constructor
         *      modify the MonsterRecyclerViewAdapter's constructor and the correspondent call
         *      modify the MonsterViewHolder's constructor to receive the listener as it's this class that knows about the Monster's UI components, so it knows when you click it
         *          Do not forget to include the ratingBar (use findViewById)
         *      create a method call bind that will bind each monster with a listener so when the user tap/click on it the listener method onMonsterClick will be called
         */

        /** TODO 7: add a new condition to onActivityResult, to check when we come back to the recyclerview from the MonsterDetailScrollingActivity
         *      call a method modifyMonster where we will update the database with the number of stars the user has given to the monster
         */
    }

    private void addNewMonster() {
        Intent goToAddCreateMonster = new Intent(this, AddMonsterScrollingActivity.class);
        startActivityForResult(goToAddCreateMonster, ADD_MONSTER_ACTIVITY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_MONSTER_ACTIVITY_CODE){
            if(resultCode == RESULT_OK){
                addMonster(data);
            }
        }

    }

    private void addMonster(Intent data) {
        String message;
        Monster monster = (Monster) data.getSerializableExtra(Monster.MONSTER_KEY);
        //insert your monster into the DB
        Long result = monsterDataService.add(monster);
        //result holds the autogenerated id in the table
        if(result > 0){

            message = "Your monster was created with id: "+ result;
        }else{
            message = "We couldn't create your monster, try again";
        }
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show();
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
