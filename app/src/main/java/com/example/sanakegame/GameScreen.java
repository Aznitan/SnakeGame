//package com.example.sanakegame;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class GameScreen extends AppCompatActivity {
//
//    private GameSurface gameSurface;
//    private Button btnUp, btnDown, btnLeft, btnRight;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.gamescreen);
//
//        // Initialize the GameSurface
//        gameSurface = findViewById(R.id.gameSurfaceView);
//
//        // Initialize buttons
//        btnUp = findViewById(R.id.btnUp);
//        btnDown = findViewById(R.id.btnDown);
//        btnLeft = findViewById(R.id.btnLeft);
//        btnRight = findViewById(R.id.btnRight);
//
//        // Set up button listeners for snake movement
//        btnUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                gameSurface.setDirection("UP");
//            }
//        });
//
//        btnDown.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                gameSurface.setDirection("DOWN");
//            }
//        });
//
//        btnLeft.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                gameSurface.setDirection("LEFT");
//            }
//        });
//
//        btnRight.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                gameSurface.setDirection("RIGHT");
//            }
//        });
//    }
//}
