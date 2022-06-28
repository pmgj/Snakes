package snake.difficulty;

public class Advanced extends Difficulty {
    public Advanced() {
        this.delay = 100;
    }

    @Override
    public void changeDelay(int foodConsumed) {
        if (delay > 30 && foodConsumed % 4 == 0) {
            delay -= 20;
        }
    }
}
