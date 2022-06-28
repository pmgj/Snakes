package snake.difficulty;

public abstract class Difficulty {

    protected int delay;
    
    public abstract void changeDelay(int foodConsumed);
    
    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
