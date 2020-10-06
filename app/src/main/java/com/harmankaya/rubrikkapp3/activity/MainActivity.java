package com.harmankaya.rubrikkapp3.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;
import com.harmankaya.rubrikkapp3.R;
import com.harmankaya.rubrikkapp3.fragment.ItemsListFragment;
import com.harmankaya.rubrikkapp3.fragment.RegisterFragment;
import com.harmankaya.rubrikkapp3.preference.UsersPrefs;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private Menu navMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        setSupportActionBar(toolbar);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        FragmentManager fm = getSupportFragmentManager();
        ItemsListFragment itemsListFragment = new ItemsListFragment();
        fm.beginTransaction().replace(R.id.fragment_container, itemsListFragment).commit();
        updateData();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.nav_register:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new RegisterFragment()).commit();
                break;
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ItemsListFragment()).commit();
            default:
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed()
    {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    private void initViews()
    {
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        navMenu = navigationView.getMenu();
    }

    /**
     * This checks if the user is logged in or not.
     * If not, some menu items will not show
     */
    public void updateData()
    {
        UsersPrefs usersPrefs = new UsersPrefs(this);

        if (usersPrefs.getToken().equals(""))
        {
            Toast.makeText(this, "You have to login to buy and sell items", Toast.LENGTH_SHORT).show();

            navMenu.findItem(R.id.nav_home).setVisible(true);
            navMenu.findItem(R.id.nav_add_item).setVisible(false);
            navMenu.findItem(R.id.nav_sold_items).setVisible(false);
            navMenu.findItem(R.id.nav_bought_items).setVisible(false);
            navMenu.findItem(R.id.nav_profile).setVisible(false);
            navMenu.findItem(R.id.nav_register).setVisible(true);
            navMenu.findItem(R.id.nav_login).setVisible(true);
            navMenu.findItem(R.id.nav_logout).setVisible(false);
        }
        else
        {
            Toast.makeText(this, "Welcome back", Toast.LENGTH_SHORT).show();

            navMenu.findItem(R.id.nav_home).setVisible(true);
            navMenu.findItem(R.id.nav_add_item).setVisible(true);
            navMenu.findItem(R.id.nav_sold_items).setVisible(true);
            navMenu.findItem(R.id.nav_bought_items).setVisible(true);
            navMenu.findItem(R.id.nav_profile).setVisible(true);
            navMenu.findItem(R.id.nav_register).setVisible(false);
            navMenu.findItem(R.id.nav_login).setVisible(false);
            navMenu.findItem(R.id.nav_logout).setVisible(true);
        }
    }
}