package com.talde3.laudiosarean;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.osmdroid.config.Configuration;

public class MainActivity extends AppCompatActivity {

    GuneakFragment guneakFragment = new GuneakFragment();
    MapaFragment mapaFragment = new MapaFragment();
    ProfilaFragment profilaFragment =new ProfilaFragment();
    RankingFragment rankingFragment = new RankingFragment();

    private static final int GUNEEK_FRAGMENT_ID = R.id.guneakFragment;
    private static final int MAPA_FRAGMENT_ID = R.id.mapaFragment;
    private static final int PROFILA_FRAGMENT_ID = R.id.profilaFragment;
    private static final int ITXI_SAIOA_FRAGMENT_ID = R.id.rankingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        loadFragment(guneakFragment);
        navigation.setOnItemSelectedListener(mOnNavigationItemSelectedListener);

        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
    }

    private final BottomNavigationView.OnItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();

            if (itemId == GUNEEK_FRAGMENT_ID) {
                loadFragment(guneakFragment);
                return true;
            } else if (itemId == MAPA_FRAGMENT_ID) {
                loadFragment(mapaFragment);
                return true;
            } else if (itemId == PROFILA_FRAGMENT_ID) {
                loadFragment(profilaFragment);
                return true;
            } else if (itemId == ITXI_SAIOA_FRAGMENT_ID) {
                loadFragment(rankingFragment);
                return true;
            }
            return false;
        }
    };

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }
}