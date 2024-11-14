package com.example.sanakegame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
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


        btnUp = findViewById(R.id.btnUp);
        btnDown = findViewById(R.id.btnDown);
        btnLeft = findViewById(R.id.btnLeft);
        btnRight = findViewById(R.id.btnRight);


        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameSurface.setDirection("UP");
            }
        });

        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameSurface.setDirection("DOWN");
            }
        });

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameSurface.setDirection("LEFT");
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameSurface.setDirection("RIGHT");
            }
        });
    }

    private static class GameSurface extends SurfaceView implements SurfaceHolder.Callback, Runnable {

        private Thread gameThread;
        private boolean running;
        private Paint paint;
        private int snakeX, snakeY;
        private int snakeSize = 50;
        private String direction = "RIGHT";

        public GameSurface(Context context) {
            super(context);
            getHolder().addCallback(this);
            paint = new Paint();
            paint.setColor(Color.GREEN);
            paint.setStyle(Paint.Style.FILL);
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            snakeX = getWidth() / 2;
            snakeY = getHeight() / 2;
            running = true;
            gameThread = new Thread(this);
            gameThread.start();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            running = false;
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while (running) {
                if (!getHolder().getSurface().isValid()) {
                    continue;
                }

                Canvas canvas = getHolder().lockCanvas();
                if (canvas != null) {
                    update();
                    drawGame(canvas);
                    getHolder().unlockCanvasAndPost(canvas);
                }

                try {
                    Thread.sleep(100); // Control game speed
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void update() {
            switch (direction) {
                case "UP":
                    snakeY -= snakeSize;
                    break;
                case "DOWN":
                    snakeY += snakeSize;
                    break;
                case "LEFT":
                    snakeX -= snakeSize;
                    break;
                case "RIGHT":
                    snakeX += snakeSize;
                    break;
            }

            // Boundary check
            if (snakeX < 0) snakeX = getWidth();
            if (snakeX > getWidth()) snakeX = 0;
            if (snakeY < 0) snakeY = getHeight();
            if (snakeY > getHeight()) snakeY = 0;
        }

        private void drawGame(Canvas canvas) {
            canvas.drawColor(Color.BLACK); // Clear the screen
            canvas.drawRect(snakeX, snakeY, snakeX + snakeSize, snakeY + snakeSize, paint); // Draw the snake
        }
    }
}
