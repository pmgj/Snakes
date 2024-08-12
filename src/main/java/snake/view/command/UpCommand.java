package snake.view.command;

import snake.model.Direction;
import snake.model.SnakeGame;

public class UpCommand implements Command {
    private SnakeGame game;

    public UpCommand(SnakeGame game) {
        this.game = game;
    }

    @Override
    public void execute() {
        this.game.setDirection(Direction.UP);
    }
}
