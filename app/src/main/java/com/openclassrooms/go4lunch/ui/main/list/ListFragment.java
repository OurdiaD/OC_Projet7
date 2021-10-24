package com.openclassrooms.go4lunch.ui.main.list;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.go4lunch.MainActivity;
import com.openclassrooms.go4lunch.databinding.FragmentListBinding;
import com.openclassrooms.go4lunch.models.maps.Result;
import com.openclassrooms.go4lunch.ui.main.MainViewModel;

import java.util.List;

public class ListFragment extends Fragment {

    private ListViewModel listViewModel;
    private FragmentListBinding binding;
    private MainViewModel mainViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listViewModel =
                new ViewModelProvider(this).get(ListViewModel.class);

        binding = FragmentListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mainViewModel = MainViewModel.getInstance();
        List<Result> list = mainViewModel.getReponsePlace();
        Log.d("lol list fragment", ""+list);

        final TextView textView = binding.textGallery;
        listViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}