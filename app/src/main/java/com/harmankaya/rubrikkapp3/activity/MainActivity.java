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
import com.harmankaya.rubrikkapp3.fragment.AddItemFragment;
import com.harmankaya.rubrikkapp3.fragment.ItemsListFragment;
import com.harmankaya.rubrikkapp3.fragment.LoginFragment;
import com.harmankaya.rubrikkapp3.fragment.RegisterFragment;
import com.harmankaya.rubrikkapp3.model.User;
import com.harmankaya.rubrikkapp3.preference.UsersPrefs;
import com.harmankaya.rubrikkapp3.rest.ApiClient;
import com.harmankaya.rubrikkapp3.rest.ApiInterface;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        updateData();


        FragmentManager fm = getSupportFragmentManager();
        ItemsListFragment itemsListFragment = new ItemsListFragment();
        fm.beginTransaction().replace(R.id.fragment_container, itemsListFragment).commit();
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
                break;
            case R.id.nav_logout:
                UsersPrefs usersPrefs = new UsersPrefs(this);
                usersPrefs.setToken("");
                usersPrefs.setUserEmail("");
                usersPrefs.setName("");
                usersPrefs.setUserPassword("");
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                break;
            case R.id.nav_login:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new LoginFragment()).commit();
                break;
            case R.id.nav_add_item:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AddItemFragment()).commit();
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
        final UsersPrefs usersPrefs = new UsersPrefs(getApplicationContext());
        System.out.println(usersPrefs.getToken());
        String token = usersPrefs.getToken();

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);

        Call<User> call = api.getUser(token);

        call.enqueue(new Callback<User>()
        {
            @Override
            public void onResponse(Call<User> call, Response<User> response)
            {
                if (response.body().getId() != null)
                {
                    Toast.makeText(getApplicationContext(), "Welcome back", Toast.LENGTH_SHORT).show();

                    navMenu.findItem(R.id.nav_home).setVisible(true);
                    navMenu.findItem(R.id.nav_add_item).setVisible(true);
                    navMenu.findItem(R.id.nav_sold_items).setVisible(true);
                    navMenu.findItem(R.id.nav_bought_items).setVisible(true);
                    navMenu.findItem(R.id.nav_profile).setVisible(true);
                    navMenu.findItem(R.id.nav_register).setVisible(false);
                    navMenu.findItem(R.id.nav_login).setVisible(false);
                    navMenu.findItem(R.id.nav_logout).setVisible(true);

                    usersPrefs.setId(response.body().getId());
                    usersPrefs.setName(response.body().getEmail());
                    usersPrefs.setUserEmail(response.body().getEmail());
                    usersPrefs.setUserPassword(response.body().getPassword());
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "You have to login to buy and sell items", Toast.LENGTH_SHORT).show();

                    navMenu.findItem(R.id.nav_home).setVisible(true);
                    navMenu.findItem(R.id.nav_add_item).setVisible(false);
                    navMenu.findItem(R.id.nav_sold_items).setVisible(false);
                    navMenu.findItem(R.id.nav_bought_items).setVisible(false);
                    navMenu.findItem(R.id.nav_profile).setVisible(false);
                    navMenu.findItem(R.id.nav_register).setVisible(true);
                    navMenu.findItem(R.id.nav_login).setVisible(true);
                    navMenu.findItem(R.id.nav_logout).setVisible(false);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t)
            {

            }
        });
        call.cancel();
    }
}