package com.denshiksmile.android.wildsurvival;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.denshiksmile.android.wildsurvival.fragments.AboutFragment;
import com.denshiksmile.android.wildsurvival.fragments.CompassFragment;
import com.denshiksmile.android.wildsurvival.fragments.FireFragment;
import com.denshiksmile.android.wildsurvival.fragments.FlashlightFragment;
import com.denshiksmile.android.wildsurvival.fragments.HealthFragment;
import com.denshiksmile.android.wildsurvival.fragments.MapCustomFragment;
import com.denshiksmile.android.wildsurvival.fragments.MealFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here

        ImageView view = (ImageView) findViewById(R.id.robinzon);
        view.setVisibility(View.INVISIBLE);

        Fragment fragment = null;
        Class fragmentClass;

        int id = item.getItemId();
        switch (id) {
            case R.id.nav_map:
                fragmentClass = MapCustomFragment.class;
                break;
            case R.id.nav_flashlight:
                fragmentClass = FlashlightFragment.class;
                break;
            case R.id.nav_compass:
                fragmentClass = CompassFragment.class;
                break;
            case R.id.nav_fire:
                fragmentClass = FireFragment.class;
                break;
            case R.id.nav_meal:
                fragmentClass = MealFragment.class;
                break;
            case R.id.nav_aid:
                fragmentClass = HealthFragment.class;
                break;
            case R.id.nav_about:
                fragmentClass = AboutFragment.class;
                break;
            default:
                fragmentClass = AboutFragment.class;

        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
            if(fragment.equals(new FlashlightFragment())) {
                if(FlashlightFragment.mTimer != null) {
                    FlashlightFragment.mTimer.cancel();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_content, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;

    }


}
