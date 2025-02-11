package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private int[] gameState = {0, 0, 0, 0, 0, 0, 0, 0, 0}; // 0: empty, 1: X, 2: O
    private int[][] winPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // lignes
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // colonnes
            {0, 4, 8}, {2, 4, 6}             // diagonales
    };
    private boolean isPlayerOneTurn = true;
    private boolean gameActive = true;
    private String player1 = "Joueur X";
    private String player2;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        player2 = intent.getStringExtra("PLAYER_2");
        if (player2 == null || player2.isEmpty()) {
            player2 = "Joueur O";
        }

        TextView playerNames = findViewById(R.id.playerNames);
        playerNames.setText(player1 + " vs " + player2);

        GridLayout grid = findViewById(R.id.gameGrid);
        for (int i = 0; i < grid.getChildCount(); i++) {
            final int index = i;
            grid.getChildAt(i).setOnClickListener(v -> makeMove((ImageView) v, index));
        }

        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(this::resetGame);
    }

    private void makeMove(ImageView imageView, int index) {
        if (!gameActive || gameState[index] != 0) return;

        gameState[index] = isPlayerOneTurn ? 1 : 2;
        imageView.setImageResource(isPlayerOneTurn ? R.drawable.x : R.drawable.o);

        if (checkWinner()) {
            gameActive = false;
            showWinner(isPlayerOneTurn ? player1 + " gagne !" : player2 + " gagne !");
            return;
        }

        if (isDraw()) {
            gameActive = false;
            showWinner("Match nul !");
            return;
        }

        isPlayerOneTurn = !isPlayerOneTurn;

        if (!isPlayerOneTurn && "Robot".equals(player2)) {
            handler.postDelayed(this::robotMove, 500);
        }
    }

    private void robotMove() {
        if (!gameActive) return;

        int bestMove = getBestMove();
        if (bestMove != -1) {
            GridLayout grid = findViewById(R.id.gameGrid);
            ImageView imageView = (ImageView) grid.getChildAt(bestMove);
            makeMove(imageView, bestMove);
        }
    }

    private boolean checkWinner() {
        for (int[] winPosition : winPositions) {
            if (gameState[winPosition[0]] != 0 &&
                    gameState[winPosition[0]] == gameState[winPosition[1]] &&
                    gameState[winPosition[1]] == gameState[winPosition[2]]) {
                return true;
            }
        }
        return false;
    }

    private boolean isDraw() {
        for (int state : gameState) {
            if (state == 0) return false;
        }
        return true;
    }

    private int getBestMove() {
        Random random = new Random();
        int move;

        // Vérifier si le robot peut gagner en un coup
        for (int[] winPos : winPositions) {
            int countO = 0;
            int emptyIndex = -1;
            for (int pos : winPos) {
                if (gameState[pos] == 2) countO++;
                else if (gameState[pos] == 0) emptyIndex = pos;
            }
            if (countO == 2 && emptyIndex != -1) return emptyIndex; // Jouer pour gagner
        }

        // Bloquer l'adversaire s'il peut gagner en un coup
        for (int[] winPos : winPositions) {
            int countX = 0;
            int emptyIndex = -1;
            for (int pos : winPos) {
                if (gameState[pos] == 1) countX++;
                else if (gameState[pos] == 0) emptyIndex = pos;
            }
            if (countX == 2 && emptyIndex != -1) return emptyIndex; // Bloquer l'adversaire
        }

        // Jouer au centre si possible
        if (gameState[4] == 0) return 4;

        // Jouer dans un coin si possible
        int[] corners = {0, 2, 6, 8};
        for (int corner : corners) {
            if (gameState[corner] == 0) return corner;
        }

        // Jouer aléatoirement
        do {
            move = random.nextInt(9);
        } while (gameState[move] != 0);

        return move;
    }


    private void showWinner(String message) {
        TextView winnerText = findViewById(R.id.winnerText);
        winnerText.setText(message);
    }

    public void resetGame(View view) {
        gameActive = true;
        isPlayerOneTurn = true;

        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 0;
        }

        GridLayout grid = findViewById(R.id.gameGrid);
        for (int i = 0; i < grid.getChildCount(); i++) {
            ((ImageView) grid.getChildAt(i)).setImageResource(R.drawable.ic_launcher_background);
        }

        TextView winnerText = findViewById(R.id.winnerText);
        winnerText.setText("");
    }
}
