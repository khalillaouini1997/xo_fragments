package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class SinglePlayerFragment extends Fragment {

    EditText playerName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_player, container, false);
        playerName = view.findViewById(R.id.editTextPlayer1);
        Button startGame = view.findViewById(R.id.buttonStartGame);

        startGame.setOnClickListener(v -> {
            String name = playerName.getText().toString();
            Intent intent = new Intent(getActivity(), GameActivity.class);
            intent.putExtra("PLAYER_1", name);
            intent.putExtra("PLAYER_2", "Robot");
            startActivity(intent);
        });
        return view;
    }
}