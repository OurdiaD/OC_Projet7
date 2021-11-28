package com.openclassrooms.go4lunch.ui.main.map;

import static com.openclassrooms.go4lunch.services.PlaceUtils.getResultWithUser;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.openclassrooms.go4lunch.databinding.FragmentMapBinding;
import com.openclassrooms.go4lunch.datas.repositories.PlaceRepository;
import com.openclassrooms.go4lunch.models.User;
import com.openclassrooms.go4lunch.models.maps.Result;
import com.openclassrooms.go4lunch.ui.details.DetailsActivity;

import java.util.ArrayList;
import java.util.List;


public class MapFragment extends Fragment implements OnMapReadyCallback{

    private MapViewModel mapViewModel;
    private FragmentMapBinding binding;
    private GoogleMap mMap;
    SupportMapFragment mapFragment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mapViewModel = new ViewModelProvider(requireActivity()).get(MapViewModel.class);

        binding = FragmentMapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentByTag("mapFragment");
        initApiMaps();
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
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        Task<Location> locationResult = fusedLocationClient.getLastLocation();
        locationResult.addOnCompleteListener(requireActivity(), task -> {
            if (task.isSuccessful()) {
                Location location = task.getResult();
                if (location != null) {
                    PlaceRepository.setCurrentLocation(location);
                    LatLng currentLatLng = mapViewModel.getLatLng();

                    CameraPosition myPosition = new CameraPosition.Builder()
                            .target(currentLatLng).zoom(17).bearing(90).tilt(30).build();
                    mMap.moveCamera(CameraUpdateFactory.newCameraPosition(myPosition));

                    addMarkers();
                }
            }
        });
    }

    public void initApiMaps() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                    PackageManager.PERMISSION_GRANTED);
        } else{
            if (mapFragment != null) {
                mapFragment.getMapAsync(this);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapFragment.getMapAsync(this);
    }

    public void addMarkers() {
        MutableLiveData<List<Result>> list = mapViewModel.getListOfPlace();
        Task<QuerySnapshot> listUser = mapViewModel.getUserCollection();

        list.observe(getViewLifecycleOwner(), results -> {
            mMap.clear();
            for (Result result : results) {
                listUser.addOnCompleteListener(taskUser -> {
                    getResultWithUser(taskUser, result);

                    LatLng position = new LatLng(result.getGeometry().getLocation().getLat(), result.getGeometry().getLocation().getLng());
                    if (result.getListUser() == null || result.getListUser().size() == 0) {
                        mMap.addMarker(new MarkerOptions().position(position).title(result.getName()).snippet(result.getPlace_id()));
                    } else {
                        mMap.addMarker(new MarkerOptions().position(position).title(result.getName()).snippet(result.getPlace_id()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    }
                    mMap.setOnMarkerClickListener(markerClickListner());
                });

            }
        });
    }

    public GoogleMap.OnMarkerClickListener markerClickListner(){
        return marker -> {
            Intent intent = new Intent(getContext(), DetailsActivity.class);
            intent.putExtra("place_id", marker.getSnippet());
            ActivityCompat.startActivity(requireContext(), intent, null);
            return true;
        };
    }

}