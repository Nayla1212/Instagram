package com.example.instagram;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.instagram.fragments.ComposeActivityFragment;
import com.example.instagram.fragments.ProfileActivityFragment;
import com.example.instagram.fragments.TimelineActivityFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

public class ComposeActivity extends AppCompatActivity {

    public static final String TAG = "ComposeActivity";
    private BottomNavigationView bottomNavigationView;
    final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        //Replacing app name with Instagram script
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.instagram_script);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //Handling bottom navigation toolbar
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        //Sets colors for when items are seleced/de-selected
        ColorStateList iconColorStates = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_checked},
                        new int[]{android.R.attr.state_checked}
                },
                new int[]{
                        Color.parseColor("#808080"),
                        Color.parseColor("#FF000000")
                });
        bottomNavigationView.setItemIconTintList(iconColorStates);
        bottomNavigationView.setItemTextColor(iconColorStates);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.btnComposeActivity:
                        Toast.makeText(ComposeActivity.this, "Compose", Toast.LENGTH_SHORT).show();
                        fragment = new ComposeActivityFragment();
                        break;
                    case R.id.btnHome:
                        Toast.makeText(ComposeActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        fragment = new TimelineActivityFragment();
                        break;
                    case R.id.btnProfile:
                    default:
                        Toast.makeText(ComposeActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                        fragment = new ProfileActivityFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.btnHome);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.btnLogOut){
            //TODO: does it know that I'm referring to the current ParseUser? basically does this work?
            ParseUser.logOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
