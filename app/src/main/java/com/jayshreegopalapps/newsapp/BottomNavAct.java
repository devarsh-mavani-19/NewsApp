package com.jayshreegopalapps.newsapp;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BottomNavAct extends AppCompatActivity {
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        initDatabase();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        getSupportActionBar().hide();

    }

    private void initDatabase() {
        database = openOrCreateDatabase("UsageDB", MODE_PRIVATE, null);
        String query = "create table if not exists CategoryTable" +
                "(" +
                "category_name varchar(20)," +
                "value Integer" +
                ");";
        database.execSQL(query);

        query = "create table if not exists UsageStats " +
                "(" +
                "u_day text," +
                "usage Integer" +
                ")";

        database.execSQL(query);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
        long timestamp = System.currentTimeMillis();

        String time = sdf.format(new Date(timestamp));

        String select=  "select * from UsageStats where u_day = '"+time+"'";
        Cursor cursor = database.rawQuery(select, null);
        if(!cursor.moveToNext()) {
            String insert = "insert into UsageStats values ('"+time+"', 0)";
            database.execSQL(insert);
        }
        cursor.close();

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean b = preferences.getBoolean("first", true);

        if(b) {
            preferences.edit().putBoolean("first", false).commit();
            String insert = "insert into CategoryTable values('business', 0);";
            database.execSQL(insert);
            insert = "insert into CategoryTable values('sports', 0);";
            database.execSQL(insert);
            insert = "insert into CategoryTable values('health', 0);";
            database.execSQL(insert);
            insert = "insert into CategoryTable values('science', 0);";
            database.execSQL(insert);
            insert = "insert into CategoryTable values('technology', 0);";
            database.execSQL(insert);
            insert = "insert into CategoryTable values('entertainment', 0);";
            database.execSQL(insert);
        }
    }

}
