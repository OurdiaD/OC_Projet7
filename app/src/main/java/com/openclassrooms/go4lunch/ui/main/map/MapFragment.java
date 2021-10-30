package com.openclassrooms.go4lunch.ui.main.map;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.openclassrooms.go4lunch.databinding.FragmentMapBinding;
import com.openclassrooms.go4lunch.datas.repositories.PlaceRepository;
import com.openclassrooms.go4lunch.models.maps.Result;

import java.util.List;


public class MapFragment extends Fragment implements OnMapReadyCallback{

    private MapViewModel mapViewModel;
    private FragmentMapBinding binding;
    private GoogleMap mMap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mapViewModel = new ViewModelProvider(this).get(MapViewModel.class);

        binding = FragmentMapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentByTag("mapFragment");
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        //PlaceRepository.setCurrentLocation(location);
        Log.d("lol mfrag68", "mapready" );
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        /*fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Log.d("lol mfrag", "loc success " + location);
                        if (location != null) {
                            PlaceRepository.setCurrentLocation(location);
                            LatLng currentLatLng = mapViewModel.getLatLng();

                            Log.d("lol mfrag", "loc success " + currentLatLng);
                            mMap = googleMap;
                            mMap.setMyLocationEnabled(true);
                            CameraPosition myPosition = new CameraPosition.Builder()
                                    .target(currentLatLng).zoom(17).bearing(90).tilt(30).build();
                            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(myPosition));
                            //mainViewModel.setLatLng(location);
                            MutableLiveData<List<Result>> list = mapViewModel.getListOfPlace();
                            Log.d("lol mfrag84", "" + list);
                        }
                    }
                });*/
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        Task<Location> locationResult = fusedLocationClient.getLastLocation();
        locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    // Set the map's camera position to the current location of the device.
                    Location location = task.getResult();
                    if (location != null) {
                        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), DEFAULT_ZOOM));
                        PlaceRepository.setCurrentLocation(location);
                        LatLng currentLatLng = mapViewModel.getLatLng();

                        Log.d("lol mfrag", "loc success " + currentLatLng);

                        CameraPosition myPosition = new CameraPosition.Builder()
                                .target(currentLatLng).zoom(17).bearing(90).tilt(30).build();
                        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(myPosition));
                        //mainViewModel.setLatLng(location);
                        MutableLiveData<List<Result>> list = mapViewModel.getListOfPlace();
                    }
                }
            }
        });
    }
}