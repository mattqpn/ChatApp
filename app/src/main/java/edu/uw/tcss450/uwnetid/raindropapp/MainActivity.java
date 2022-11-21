package edu.uw.tcss450.uwnetid.raindropapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.auth0.android.jwt.JWT;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.uw.tcss450.uwnetid.raindropapp.model.UserInfoViewModel;

public class MainActivity extends AppCompatActivity
{
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivityArgs args = MainActivityArgs.fromBundle(getIntent().getExtras());

        //Import com.auth0.android.jwt.JWT
        JWT jwt = new JWT(args.getJwt());

        //Check to see if the web token is still valid or not. To make a JWT expire after a
        //longer or shorter time period, change the expiration time when the JWT is
        //created on the web service
        if (!jwt.isExpired(0))
        {
            new ViewModelProvider(this,
                    new UserInfoViewModel.UserInfoViewModelFactory(jwt))
                    .get(UserInfoViewModel.class);
        }
        else
        {
            //In production code, add in your own error handling/flow for when the JWT is expired
            throw new IllegalStateException("JWT is expired!");
        }

        setContentView(R.layout.activity_main);
        // Make sure the new statements go BELOW setContentView

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_weather, R.id.navigation_contacts,
                R.id.navigation_chat).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            //TODO open a settings fragment
            Log.d("SETTINGS", "Clicked");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}