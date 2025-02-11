package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SelectionFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selection, container, false);
        Button singlePlayerButton = view.findViewById(R.id.buttonSinglePlayer);
        Button multiPlayerButton = view.findViewById(R.id.buttonMultiPlayer);

        singlePlayerButton.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_selectionFragment_to_singlePlayerFragment));

        multiPlayerButton.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_selectionFragment_to_multiPlayerFragment));

        return view;
    }
}