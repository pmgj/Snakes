package snake.difficulty;

public class Intermediate extends Difficulty {
    public Intermediate() {
        this.delay = 150;
    }

    @Override
    public void changeDelay(int foodConsumed) {
        if (delay > 50 && foodConsumed % 5 == 0) {
            delay -= 15;
        }
    }
}
