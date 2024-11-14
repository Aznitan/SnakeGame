package com.example.sanakegame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private Thread gameThread;
    private boolean running;
    private Paint paint;
    private List<Point> snakeBody;
    private Point food;
    private int snakeSize = 50;
    private String direction = "RIGHT";
    private boolean growing = false;
    private int score = 0;
    private boolean isWrapAround = true;
    private int speed = 100; // Speed in milliseconds

    public GameSurface(Context context) {
        super(context);
        init();
    }

    public GameSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        getHolder().addCallback(this);
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        snakeBody = new ArrayList<>();
        food = new Point();
    }

    public void setGameMode(String mode) {
        if ("classic".equals(mode)) {
            Log.d("GameSurface", "Game mode set to classic");
            this.isWrapAround = true;
            this.speed = 150;
        }
    }

    public void pause() {
        running = false;
        try {
            if (gameThread != null) {
                gameThread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("GameSurface", "Game paused");
    }

    public void resume() {
        if (!running) {
            running = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
        Log.d("GameSurface", "Game resumed");
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // Initialize snake at the center
        snakeBody.clear();
        int startX = getWidth() / 2;
        int startY = getHeight() / 2;
        snakeBody.add(new Point(startX, startY));
        spawnFood();

        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        running = false;
        try {
            if (gameThread != null) {
                gameThread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (running) {
            if (!getHolder().getSurface().isValid()) continue;

            Canvas canvas = getHolder().lockCanvas();
            if (canvas != null) {
                try {
                    update(); // Update game state
                    drawGame(canvas); // Redraw the game
                } finally {
                    getHolder().unlockCanvasAndPost(canvas);
                }
            }

            try {
                Thread.sleep(speed); // Control frame rate
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        // (Logic for updating the snake, food, and collision detection)
    }

    private void spawnFood() {
        Random random = new Random();
        int foodX = random.nextInt(getWidth() / snakeSize) * snakeSize;
        int foodY = random.nextInt(getHeight() / snakeSize) * snakeSize;
        food.set(foodX, foodY);
    }

    private void drawGame(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        paint.setColor(Color.GREEN);
        for (Point segment : snakeBody) {
            canvas.drawRect(segment.x, segment.y, segment.x + snakeSize, segment.y + snakeSize, paint);
        }
        paint.setColor(Color.RED);
        canvas.drawRect(food.x, food.y, food.x + snakeSize, food.y + snakeSize, paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(50);
        canvas.drawText("Score: " + score, 20, 50, paint);
    }

    public void setDirection(String newDirection) {
        // Prevent reversing direction
        if ((direction.equals("UP") && newDirection.equals("DOWN")) ||
                (direction.equals("DOWN") && newDirection.equals("UP")) ||
                (direction.equals("LEFT") && newDirection.equals("RIGHT")) ||
                (direction.equals("RIGHT") && newDirection.equals("LEFT"))) {
            return;
        }
        this.direction = newDirection;
    }

}
