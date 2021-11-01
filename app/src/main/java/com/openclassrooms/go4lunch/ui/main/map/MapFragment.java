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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.openclassrooms.go4lunch.databinding.FragmentMapBinding;
import com.openclassrooms.go4lunch.datas.repositories.PlaceRepository;
import com.openclassrooms.go4lunch.models.User;
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
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        Task<Location> locationResult = fusedLocationClient.getLastLocation();
        locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    Location location = task.getResult();
                    if (location != null) {
                        PlaceRepository.setCurrentLocation(location);
                        LatLng currentLatLng = mapViewModel.getLatLng();

                        CameraPosition myPosition = new CameraPosition.Builder()
                                .target(currentLatLng).zoom(17).bearing(90).tilt(30).build();
                        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(myPosition));

                        MutableLiveData<List<Result>> list = mapViewModel.getListOfPlace();

                        list.observe(getViewLifecycleOwner(), new Observer<List<Result>>() {
                            @Override
                            public void onChanged(List<Result> results) {
                                for (Result result : results) {
                                    result.setListUser(mapViewModel.getUserByPlaceId(result.getPlace_id()));
                                    LatLng position = new LatLng(result.getGeometry().getLocation().getLat(), result.getGeometry().getLocation().getLng());
                                    result.getListUser().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
                                        @Override
                                        public void onChanged(List<User> users) {
                                            if (users == null || users.size() == 0) {
                                                mMap.addMarker(new MarkerOptions().position(position).title(result.getName()));
                                            } else {
                                                mMap.addMarker(new MarkerOptions().position(position).title(result.getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                                            }
                                        }
                                    });

                                }
                            }
                        });
                    }
                }
            }
        });
    }
}