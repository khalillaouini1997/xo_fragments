package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class MultiPlayerFragment extends Fragment {

    EditText player1Name, player2Name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_multi_player, container, false);
        player1Name = view.findViewById(R.id.editTextPlayer1);
        player2Name = view.findViewById(R.id.editTextPlayer2);
        Button startGame = view.findViewById(R.id.buttonStartGame);

        startGame.setOnClickListener(v -> {
            String name1 = player1Name.getText().toString();
            String name2 = player2Name.getText().toString();
            Intent intent = new Intent(getActivity(), GameActivity.class);
            intent.putExtra("PLAYER_1", name1);
            intent.putExtra("PLAYER_2", name2);
            startActivity(intent);
        });
        return view;
    }
}