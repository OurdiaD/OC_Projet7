package com.openclassrooms.go4lunch.ui.main.list;

import static com.openclassrooms.go4lunch.services.PlaceUtils.getResultWithUser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;
import com.openclassrooms.go4lunch.R;
import com.openclassrooms.go4lunch.databinding.FragmentListBinding;
import com.openclassrooms.go4lunch.models.maps.Result;

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
            NavHostFragment.findNavController(this).navigate(R.id.navigation_map);
        } else {
            getList();
        }
        return root;
    }

    void getList(){
        RecyclerView recyclerView = binding.listPlaces;
        ListPlaceAdapter listPlaceAdapter = new ListPlaceAdapter();
        MutableLiveData<List<Result>> list = listViewModel.getListOfPlace();
        Task<QuerySnapshot> listUser = listViewModel.getUserCollection();
        recyclerView.setAdapter(listPlaceAdapter);
        list.observe(getViewLifecycleOwner(), results -> {
            for (Result result : results){
                listUser.addOnCompleteListener(taskUser -> {
                    getResultWithUser(taskUser, result);
                    listPlaceAdapter.notifyDataSetChanged();
                });

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