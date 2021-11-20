package com.openclassrooms.go4lunch.ui.main.list;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.go4lunch.R;
import com.openclassrooms.go4lunch.databinding.FragmentListBinding;
import com.openclassrooms.go4lunch.models.maps.Result;
import com.openclassrooms.go4lunch.ui.main.MainActivity;
import com.openclassrooms.go4lunch.ui.main.map.MapFragment;

import java.util.List;

public class ListFragment extends Fragment {

    private ListViewModel listViewModel;
    private FragmentListBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listViewModel = new ViewModelProvider(requireActivity()).get(ListViewModel.class);

        binding = FragmentListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if(listViewModel.getLocation() == null){
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment_content_main, MapFragment.class, null)
                    .commit();
        } else {
            getList();
        }
        return root;
    }

    void getList(){
        RecyclerView recyclerView = binding.listPlaces;
        ListPlaceAdapter listPlaceAdapter = new ListPlaceAdapter();
        MutableLiveData<List<Result>> list = listViewModel.getListOfPlace();
        recyclerView.setAdapter(listPlaceAdapter);
        list.observe(getViewLifecycleOwner(), results -> {
            for (Result result : results){
                result.setListUser(listViewModel.getUserByPlaceId(result.getPlace_id()));
            }
            listPlaceAdapter.setResults(results);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}