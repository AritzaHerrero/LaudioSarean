package com.talde3.laudiosarean;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MapView mapa;
    private IMapController mapController;

    public MapaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment mapaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapaFragment newInstance(String param1, String param2) {
        MapaFragment fragment = new MapaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mapa, container, false);

        // Inicializar el mapa
        mapa = view.findViewById(R.id.mapaGPS);
        mapa.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        mapa.setMultiTouchControls(true);
        mapController = mapa.getController();
        mapController.setZoom(15.5);
        GeoPoint geoPoint = new GeoPoint(43.15130, -2.96970);
        mapController.setCenter(geoPoint);

        ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
        items.add(new OverlayItem("Yermoko Andre Mariaren Santutegia", "Info, ",
                new GeoPoint(43.17177, -2.97165))); // Lat/Lon decimal degrees

        items.add(new OverlayItem("Burdin Hesia", "Info, ",
                new GeoPoint(43.1692, -2.9586))); // Lat/Lon decimal degrees

        items.add(new OverlayItem("Santa Aguedako ermita", "Info, ",
                new GeoPoint(43.14831, -2.98162))); // Lat/Lon decimal degrees

        items.add(new OverlayItem("Katuxako jauregia", "Info, ",
                new GeoPoint(43.13329, -2.97083))); // Lat/Lon decimal degrees

        items.add(new OverlayItem("Lamuzako San Pedro eliza", "Info, ",
                new GeoPoint(43.14278, -2.96198))); // Lat/Lon decimal degrees

        items.add(new OverlayItem("Lamuza jauregia", "Info, ",
                new GeoPoint(43.14462, -2.96441))); // Lat/Lon decimal degrees

        items.add(new OverlayItem("Lezeagako sorgina", "Info, ",
                new GeoPoint(43.14303, -2.96248))); // Lat/Lon decimal degrees

        items.add(new OverlayItem("Trivia", "Info, ",
                new GeoPoint(43.14322, -2.96269))); // Lat/Lon decimal degrees

        ItemizedOverlayWithFocus<OverlayItem> overlay = new ItemizedOverlayWithFocus<>(
                getContext(), items, // Contexto y lista de OverlayItem
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        return true;
                    }

                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        return false;
                    }
                });

        Drawable markerDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.church_mapa_24);
        for (OverlayItem item : items) {
            item.setMarker(markerDrawable);
        }

        overlay.setFocusItemsOnTap(true);
        overlay.setMarkerBackgroundColor(getResources().getColor(R.color.green_light2));
        mapa.getOverlays().add(overlay);
        mapa.setUseDataConnection(true);

        return view;
    }



}