package edu.fiuba.view;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Animation {

    private final int startingX;
    private final ArrayList<Integer> frames = new ArrayList<>();
    private final int spriteSize;
    private final boolean onLoop;
    private final int timeBetweenFrames;
    private final int timeBetweenLoops;
    private int currentFrame = 0;

    private Timer timer = new Timer();

    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if (!loopFinished()) {
                next();
            } else if (!onLoop) {
                resetTimer();
            } else {
                loop();
            }
        }
    };



    public Animation(int startingX, int spriteSize, int timeBetweenFrames, int timeBetweenLoops) {
        this.timeBetweenFrames = timeBetweenFrames;
        this.startingX = startingX;
        this.spriteSize = spriteSize;
        this.onLoop = true;
        this.timeBetweenLoops = timeBetweenLoops;

    }

    public Animation(int startingX, int spriteSize, int timeBetweenFrames, int timeBetweenLoops, ArrayList<Integer> frames) {
        this.timeBetweenFrames = timeBetweenFrames;
        this.frames.addAll(frames);
        this.startingX = startingX;
        this.spriteSize = spriteSize;
        this.onLoop = true;
        this.timeBetweenLoops = timeBetweenLoops;

    }

    public void addFrame(int n) {
        frames.add(n);
    }

    private void next() {
        currentFrame++;
    }

    private boolean loopFinished() {
        return currentFrame+1 >= frames.toArray().length;
    }

    public int getCurrentX() {
        return startingX + frames.get(currentFrame) * spriteSize;
    }

    private void loop() {
        currentFrame = 0;
        resetTimer();
        resetTask();
        this.timer.scheduleAtFixedRate(task, timeBetweenLoops + timeBetweenFrames, timeBetweenFrames);
    }

    private void resetTimer() {
        this.timer.cancel();
        this.timer = new Timer();
    }

    public void run() {
        timer.scheduleAtFixedRate(task, timeBetweenFrames, timeBetweenFrames);
    }

    private void resetTask() {
        task = new TimerTask() {
            @Override
            public void run() {
                if (!loopFinished()) {
                    next();
                } else if (!onLoop) {
                    resetTimer();
                } else {
                    loop();
                }
            }
        };
    }
}
