package com.example.savor;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.savor.remote.authentication.firestore.FireStore;
import com.google.android.material.bottomnavigation.BottomNavigationView;

//userName honysamy@gmail.com
//password Hani@123

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivityLog";
    NavHostFragment navHostFragment;
    BottomNavigationView bottomNavigationView;
    NavController navController;
    SharedPreferences sharedPreferences;
    public static final String USER_NAME = "userName";
    public static final String STORED_DATE = "dateOfTheCurrentDay";
    public static final String PRES_NAME = "PREF";
    public static final String TODAY_MEAL_ID ="mealId";
    public static final String IS_ONLINE = "isOnline";


    public static boolean isSplashed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();
        bottomNavigationView = findViewById(R.id.bottomMenue);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        sharedPreferences=getSharedPreferences(MainActivity.PRES_NAME, Context.MODE_PRIVATE);
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        Network network = connectivityManager.getActiveNetwork(); //on Start
        if(network==null)
        {
            Log.i(TAG, "Network Status: Offline"+isSplashed);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(MainActivity.IS_ONLINE,false);
            editor.apply();

        }
        //live status
        connectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback(){
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                Log.i(TAG, "onAvailable: "+isSplashed);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(MainActivity.IS_ONLINE,true);
                editor.apply();
                Bundle bundle =new Bundle();
                bundle.putBoolean("isOnline",true);
                if(isSplashed ) {

                    runOnUiThread(() -> navController.navigate(R.id.homeFragment, bundle));
               }

            }

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                Log.i(TAG, "onLost: "+isSplashed);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(MainActivity.IS_ONLINE,false);
                editor.apply();
                Bundle bundle =new Bundle();
                bundle.putBoolean("isOnline",false);
                if(isSplashed) {

                      runOnUiThread(()->navController.navigate(R.id.homeFragment,bundle));
                }
            }
        });
String userName =sharedPreferences.getString(MainActivity.USER_NAME,null);
        FireStore fireStore=new FireStore(this);
        //fireStore.UploadData(userName);
        fireStore.getData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}