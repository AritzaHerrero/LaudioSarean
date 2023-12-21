package com.talde3.laudiosarean;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.talde3.laudiosarean.Jolasak.Arauak;

import org.osmdroid.config.Configuration;

public class MainActivity extends AppCompatActivity {

    GuneakFragment guneakFragment = new GuneakFragment();
    MapaFragment mapaFragment = new MapaFragment();
    ProfilaFragment profilaFragment = new ProfilaFragment();
    RankingFragment rankingFragment = new RankingFragment();

    private static final int GUNEEK_FRAGMENT_ID = R.id.guneakFragment;
    private static final int MAPA_FRAGMENT_ID = R.id.mapaFragment;
    private static final int PROFILA_FRAGMENT_ID = R.id.profilaFragment;
    private static final int ITXI_SAIOA_FRAGMENT_ID = R.id.rankingFragment;

    private FusedLocationProviderClient fusedLocationClient;
    private static final int PERMISSION_REQUEST_CODE = 1001;

    private LocationInfo carrefourLocation;
    private LocationInfo eroskiLocation;
    private LocationInfo plazaLocation;

    private boolean Carrefour = false;
    private boolean Eroski = false;
    private boolean Plaza = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        loadFragment(guneakFragment);
        navigation.setOnItemSelectedListener(mOnNavigationItemSelectedListener);

        // Mapa kargatzeko
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));

        // Inicializar ubicaciones
        carrefourLocation = new LocationInfo("Carrefour", 43.28329, -2.96332, 150);
        eroskiLocation = new LocationInfo("Eroski", 43.28239, -2.96010, 150);
        plazaLocation = new LocationInfo("Plaza", 43.28176, -2.96285, 50);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQUEST_CODE
            );
        } else {
            startLocationUpdates();
        }
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

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(5000); // Intervalo de actualización en milisegundos
        locationRequest.setFastestInterval(3000); // Intervalo más rápido de actualización en milisegundos
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    updateLocationUI(location);
                }
            }
        };

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    private void updateLocationUI(Location location) {
        // Actualizar los TextViews con la nueva ubicación
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

       /* TextView txtLatitud = findViewById(R.id.textViewLatitud);
        TextView txtLongitud = findViewById(R.id.textViewLongitud);

        txtLatitud.setText("Latitud: " + latitude);
        txtLongitud.setText("Longitud: " + longitude);*/

        checkDistance(carrefourLocation, latitude, longitude);
        checkDistance(eroskiLocation, latitude, longitude);
        checkDistance(plazaLocation, latitude, longitude);
    }

    private void checkDistance(LocationInfo locationInfo, double currentLatitude, double currentLongitude) {
        float distance = calculateDistance(currentLatitude, currentLongitude, locationInfo.getLatitude(), locationInfo.getLongitude());
        if (distance <= locationInfo.getTargetDistance()) {
         //   Toast.makeText(this, locationInfo.getLocationName(), Toast.LENGTH_SHORT).show();


            if ("Carrefour".equals(locationInfo.getLocationName()) && !Carrefour) {
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.kokalekua_dialog, null);
                Button prestBai = view.findViewById(R.id.kokalekuaBai);
                Button prestEz = view.findViewById(R.id.kokalekuaEz);

                Carrefour = true;

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(view);
                final AlertDialog alertDialog = builder.create();


                prestEz.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                prestBai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        Intent YermokoSantutegiaIntent = new Intent(MainActivity.this, GuneInformazioa.class);
                        YermokoSantutegiaIntent.putExtra("aukeratutakoGunea", 1);
                        startActivity(YermokoSantutegiaIntent);
                    }
                });

                if (alertDialog.getWindow() != null) {
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }

                alertDialog.show();
            }

            if ("Eroski".equals(locationInfo.getLocationName()) && !Eroski) {
                Intent BurdinHesiaIntent = new Intent(MainActivity.this, GuneInformazioa.class);
                BurdinHesiaIntent.putExtra("aukeratutakoGunea", 2);
                Eroski = true;
                startActivity(BurdinHesiaIntent);
            }

            if ("Plaza".equals(locationInfo.getLocationName()) && !Plaza) {
                Intent SantaAguedakoErmitaIntent = new Intent(MainActivity.this, GuneInformazioa.class);
                SantaAguedakoErmitaIntent.putExtra("aukeratutakoGunea", 3);
                Plaza = true;
                startActivity(SantaAguedakoErmitaIntent);
            }
        }
    }

    private float calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371e3; // radio de la Tierra en metros
        double phi1 = Math.toRadians(lat1);
        double phi2 = Math.toRadians(lat2);
        double deltaPhi = Math.toRadians(lat2 - lat1);
        double deltaLambda = Math.toRadians(lon2 - lon1);

        double a = Math.sin(deltaPhi / 2) * Math.sin(deltaPhi / 2) +
                Math.cos(phi1) * Math.cos(phi2) *
                        Math.sin(deltaLambda / 2) * Math.sin(deltaLambda / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (float) (R * c);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            } else {
                // Manejar el caso en que el usuario deniega los permisos
                Toast.makeText(this, "Permiso denegado. La aplicación no puede acceder a la ubicación.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static class LocationInfo {
        private final String locationName;
        private final double latitude;
        private final double longitude;
        private final float targetDistanceMeters;

        public LocationInfo(String locationName, double latitude, double longitude, float targetDistanceMeters) {
            this.locationName = locationName;
            this.latitude = latitude;
            this.longitude = longitude;
            this.targetDistanceMeters = targetDistanceMeters;
        }

        public String getLocationName() {
            return locationName;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public float getTargetDistance() {
            return targetDistanceMeters;
        }
    }
}