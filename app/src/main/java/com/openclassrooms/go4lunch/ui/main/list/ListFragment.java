package com.openclassrooms.go4lunch.ui.main.list;

import static com.openclassrooms.go4lunch.services.PlaceUtils.getResultWithUser;

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
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.openclassrooms.go4lunch.R;
import com.openclassrooms.go4lunch.databinding.FragmentListBinding;
import com.openclassrooms.go4lunch.models.User;
import com.openclassrooms.go4lunch.models.maps.Result;
import com.openclassrooms.go4lunch.ui.main.map.MapFragment;

import java.util.ArrayList;
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
                List<User> usersList = new ArrayList<>();
                listUser.addOnCompleteListener(taskUser -> {
                    /*for (QueryDocumentSnapshot document : taskUser.getResult()){
                        User user = document.toObject(User.class);
                        if (result.getPlace_id().equals(user.getPlaceId())){
                            usersList.add(user);
                        }
                        result.setListUser(usersList);
                        listPlaceAdapter.notifyDataSetChanged();
                    }*/
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