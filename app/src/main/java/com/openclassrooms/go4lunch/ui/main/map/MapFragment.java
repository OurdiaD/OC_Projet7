package com.openclassrooms.go4lunch.ui.main.map;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.openclassrooms.go4lunch.databinding.FragmentMapBinding;
import com.openclassrooms.go4lunch.datas.repositories.PlaceRepository;
import com.openclassrooms.go4lunch.models.maps.Result;
import com.openclassrooms.go4lunch.ui.main.MainActivity;
import com.openclassrooms.go4lunch.ui.main.MainViewModel;

import java.util.List;


public class MapFragment extends Fragment implements OnMapReadyCallback{

    private MapViewModel mapViewModel;
    private FragmentMapBinding binding;
    private GoogleMap mMap;
    private MainViewModel mainViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mapViewModel = new ViewModelProvider(this).get(MapViewModel.class);

        //mainViewModel = MainViewModel.getInstance();

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
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            PlaceRepository.setCurrentLocation(location);
                            LatLng currentLatLng = mapViewModel.getLatLng();
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
                });




        /*Location myLocation;

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                    PackageManager.PERMISSION_GRANTED);
        } else {
            setmMap(googleMap);
        }*/

       /* FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            LatLng myLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                            //mMap.addMarker(new MarkerOptions().position(myLatLng).title("It's Me!"));
                            CameraPosition myPosition = new CameraPosition.Builder()
                                    .target(myLatLng).zoom(17).bearing(90).tilt(30).build();
                            *//*Log.d("lol location", ""+location);
                            Log.d("lol latlng", ""+myLatLng);*//*
                            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(myPosition));
                            mainViewModel.setLatLng(location);
                            mainViewModel.getReponsePlace();
                        }
                    }
                });*/
    }
}