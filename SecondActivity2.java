package com.example.smarthomesolutions;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class SecondActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    public String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second2);
        Intent intent=getIntent();
        email=intent.getStringExtra(MainActivity.Extra_Text);
        //getSupportActionBar().hide();
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer= findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState==null){
            GardenMaintainerFragment gd=new GardenMaintainerFragment();
            Bundle bundle = new Bundle();
            bundle.putString("my_key",email);
            gd.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,gd).commit();
        navigationView.setCheckedItem(R.id.plant);
    }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch(menuItem.getItemId()){
            case R.id.plant:
                GardenMaintainerFragment gd=new GardenMaintainerFragment();
                Bundle bundle = new Bundle();
                bundle.putString("my_key",email);
                gd.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,gd).commit();
                break;
            case R.id.light:
                SmartLightFragment sl=new SmartLightFragment();
                Bundle bundlesl = new Bundle();
                bundlesl.putString("my_key",email);
                sl.setArguments(bundlesl);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,sl).commit();
                break;
             case R.id.nav_message:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ContactFragment()).commit();
                break;
            case R.id.about:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AboutFragment()).commit();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    //    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//        switch (item.getItemId()){
//            case R.id.nav_message:
////                startActivity(new Intent(this,GardenMaintainerFragment.class));
//                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MessageFragment()).commit();
//                break;
//
//        }
//        return true;
//    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}
