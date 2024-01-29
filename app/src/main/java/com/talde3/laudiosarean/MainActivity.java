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
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.talde3.laudiosarean.Room.Dao.IrakasleaDao;
import com.talde3.laudiosarean.Room.Entities.Gunea;
import com.talde3.laudiosarean.Room.Entities.Irakaslea;

import org.osmdroid.config.Configuration;

import java.util.List;
import java.util.Objects;

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
    private LocationInfo yermo;
    private LocationInfo burdinHesia;
    private LocationInfo santaAguedaErmita;
    private LocationInfo katuxakoJauregia;
    private LocationInfo lamuzaSanPedroJauregia;
    private LocationInfo lamuzaJauregia;
    private LocationInfo lezeagakoSorgina;
    private boolean YermoBool = false;
    private boolean BurdinHesiaBool = false;
    private boolean SantaAguedaErmitaBool = false;
    private boolean KatuxakoJauregiaBool = false;
    private boolean LamuzaSanPedroJauregiaBool = false;
    private boolean LamuzaJauregiaBool = false;
    private boolean LezeagakoSorginaBool = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        IrakasleaDao irakasleaDao = LoginActivity.db.irakasleaDao();
        List<Irakaslea> irakasleak = irakasleaDao.getIrakasleak();
        boolean isIrakaslea = false;

        for(int i = 0; i < irakasleak.size(); i++)
        {
            assert currentUser != null;
            if(Objects.requireNonNull(currentUser.getEmail()).equalsIgnoreCase(irakasleak.get(i).getEmail())){
                isIrakaslea = true;
            }
        }

        if (isIrakaslea) {
            erakutsiIrakasleMenua();
        } else {
            erakutsiIkasleMenua();
        }

    }

    private final BottomNavigationView.OnItemSelectedListener mOnNavigationItemSelectedListener = item -> {
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
            public void onLocationResult(@NonNull LocationResult locationResult) {
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

        checkDistance(yermo, latitude, longitude);
        checkDistance(burdinHesia, latitude, longitude);
        checkDistance(santaAguedaErmita, latitude, longitude);
        checkDistance(katuxakoJauregia, latitude, longitude);
        checkDistance(lamuzaSanPedroJauregia, latitude, longitude);
        checkDistance(lamuzaJauregia, latitude, longitude);
        checkDistance(lezeagakoSorgina, latitude, longitude);
    }

    private void checkDistance(LocationInfo locationInfo, double currentLatitude, double currentLongitude) {
        float distance = calculateDistance(currentLatitude, currentLongitude, locationInfo.getLatitude(), locationInfo.getLongitude());
        if (distance <= locationInfo.getTargetDistance()) {
            if ("Yermo".equals(locationInfo.getLocationName()) && !YermoBool) {
                YermoBool = kokalekuaZabaldu(1);
            }
            if ("BurdinHesia".equals(locationInfo.getLocationName()) && !BurdinHesiaBool) {
                BurdinHesiaBool = kokalekuaZabaldu(2);
            }
            if ("SantaAguedaErmita".equals(locationInfo.getLocationName()) && !SantaAguedaErmitaBool) {
                SantaAguedaErmitaBool = kokalekuaZabaldu(3);
            }
            if ("KatuxakoJauregia".equals(locationInfo.getLocationName()) && !KatuxakoJauregiaBool) {
                KatuxakoJauregiaBool = kokalekuaZabaldu(4);
            }
            if ("LamuzaSanPedroJauregia".equals(locationInfo.getLocationName()) && !LamuzaSanPedroJauregiaBool) {
                LamuzaSanPedroJauregiaBool = kokalekuaZabaldu(5);
            }
            if ("LamuzaJauregia".equals(locationInfo.getLocationName()) && !LamuzaJauregiaBool) {
                LamuzaJauregiaBool = kokalekuaZabaldu(6);
            }
            if ("LezeagakoSorgina".equals(locationInfo.getLocationName()) && !LezeagakoSorginaBool) {
                LezeagakoSorginaBool = kokalekuaZabaldu(7);
            }
        }
    }

    private float calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371e3; // Lurreko errrakioa m-tan
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
                // Erabiltzaileak baimenak ezeztatzen baditu
                Toast.makeText(this, getResources().getString(R.string.ubiError), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean kokalekuaZabaldu(int aukeratutakoGunea) {
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.kokalekua_dialog, null);
        Button prestBai = view.findViewById(R.id.kokalekuaBai);
        Button prestEz = view.findViewById(R.id.kokalekuaEz);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();


        prestEz.setOnClickListener(v -> alertDialog.dismiss());

        prestBai.setOnClickListener(v -> {
            alertDialog.dismiss();
            Intent intent = new Intent(MainActivity.this, GuneInformazioa.class);
            intent.putExtra("aukeratutakoGunea", aukeratutakoGunea);
            startActivity(intent);
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        alertDialog.show();

        return true;
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

    private void erakutsiIrakasleMenua() {
        BottomNavigationView navigationIkasle = findViewById(R.id.bottom_navigation);
        BottomNavigationView navigationIrakasle = findViewById(R.id.bottom_navigation_irakasle);
        navigationIkasle.setVisibility(View.GONE);
        navigationIrakasle.setVisibility(View.VISIBLE);
        loadFragment(rankingFragment); // Irakaslearen fragmenta kargatzen du
        navigationIrakasle.setOnItemSelectedListener(mOnNavigationItemSelectedListener); // Nabegazio menuari logika gehitzen dio
    }

    private void erakutsiIkasleMenua() {
        BottomNavigationView navigationIkasle = findViewById(R.id.bottom_navigation);
        BottomNavigationView navigationIrakasle = findViewById(R.id.bottom_navigation_irakasle);
        navigationIkasle.setVisibility(View.VISIBLE);
        navigationIrakasle.setVisibility(View.GONE);
        loadFragment(guneakFragment); // Ikaslearen fragmenta kargatzen du
        navigationIkasle.setOnItemSelectedListener(mOnNavigationItemSelectedListener); // Nabegazio menuari logika gehitzen dio

        // Mapa kargatzeko
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));

        // Informazioa satu basetik eskuratu
        List<Gunea> guneak = LoginActivity.db.guneaDao().getGuneak();

        // Ubikazioak hasieratu
        //yermo = new LocationInfo("Yermo", 43.17177, -2.97165, 75);
        yermo = new LocationInfo(guneak.get(0).getIzena(), Double.parseDouble(guneak.get(0).getKoordenadak().split(",")[0]), Double.parseDouble(guneak.get(0).getKoordenadak().split(",")[1]), Float.parseFloat(guneak.get(0).getKoordenadak().split(",")[2]));
        //burdinHesia = new LocationInfo("BurdinHesia", 43.1692, -2.9586, 150);
        burdinHesia = new LocationInfo(guneak.get(0).getIzena(), Double.parseDouble(guneak.get(1).getKoordenadak().split(",")[0]), Double.parseDouble(guneak.get(1).getKoordenadak().split(",")[1]), Float.parseFloat(guneak.get(1).getKoordenadak().split(",")[2]));
        //santaAguedaErmita = new LocationInfo("SantaAguedaErmita", 43.14831, -2.98162, 125);
        santaAguedaErmita = new LocationInfo(guneak.get(0).getIzena(), Double.parseDouble(guneak.get(2).getKoordenadak().split(",")[0]), Double.parseDouble(guneak.get(2).getKoordenadak().split(",")[1]), Float.parseFloat(guneak.get(2).getKoordenadak().split(",")[2]));
        //katuxakoJauregia = new LocationInfo("KatuxakoJauregia", 43.13329, -2.97083, 100);
        katuxakoJauregia = new LocationInfo(guneak.get(0).getIzena(), Double.parseDouble(guneak.get(3).getKoordenadak().split(",")[0]), Double.parseDouble(guneak.get(3).getKoordenadak().split(",")[1]), Float.parseFloat(guneak.get(3).getKoordenadak().split(",")[2]));
        //lamuzaSanPedroJauregia = new LocationInfo("LamuzaSanPedroJauregia", 43.14278, -2.96198, 100);
        lamuzaSanPedroJauregia = new LocationInfo(guneak.get(0).getIzena(), Double.parseDouble(guneak.get(4).getKoordenadak().split(",")[0]), Double.parseDouble(guneak.get(4).getKoordenadak().split(",")[1]), Float.parseFloat(guneak.get(4).getKoordenadak().split(",")[2]));
        //lamuzaJauregia = new LocationInfo("LamuzaJauregia", 43.14462, -2.96441, 100);
        lamuzaJauregia = new LocationInfo(guneak.get(0).getIzena(), Double.parseDouble(guneak.get(5).getKoordenadak().split(",")[0]), Double.parseDouble(guneak.get(5).getKoordenadak().split(",")[1]), Float.parseFloat(guneak.get(5).getKoordenadak().split(",")[2]));
        //lezeagakoSorgina = new LocationInfo("LezeagakoSorgina", 43.14162, -2.96202, 75);
        lezeagakoSorgina = new LocationInfo(guneak.get(0).getIzena(), Double.parseDouble(guneak.get(6).getKoordenadak().split(",")[0]), Double.parseDouble(guneak.get(6).getKoordenadak().split(",")[1]), Float.parseFloat(guneak.get(6).getKoordenadak().split(",")[2]));

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
}