package snake.difficulty;

public class Beginner extends Difficulty {
    public Beginner() {
        this.delay = 200;
    }

    @Override
    public void changeDelay(int foodConsumed) {
        if (delay > 70 && foodConsumed % 6 == 0) {
            delay -= 10;
        }
    }
}
