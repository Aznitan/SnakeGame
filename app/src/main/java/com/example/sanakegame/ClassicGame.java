package com.example.sanakegame;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ClassicGame extends AppCompatActivity {

    private GameSurface gameSurface;
    private Button btnUp, btnDown, btnLeft, btnRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamescreen);

        gameSurface = findViewById(R.id.gameSurfaceView);
        gameSurface.setGameMode("classic");

        btnUp = findViewById(R.id.btnUp);
        btnDown = findViewById(R.id.btnDown);
        btnLeft = findViewById(R.id.btnLeft);
        btnRight = findViewById(R.id.btnRight);

        btnUp.setOnClickListener(v -> gameSurface.setDirection("UP"));
        btnDown.setOnClickListener(v -> gameSurface.setDirection("DOWN"));
        btnLeft.setOnClickListener(v -> gameSurface.setDirection("LEFT"));
        btnRight.setOnClickListener(v -> gameSurface.setDirection("RIGHT"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameSurface.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameSurface.resume();
    }
}
