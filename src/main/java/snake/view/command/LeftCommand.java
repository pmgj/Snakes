package snake.view.command;

import snake.model.Direction;
import snake.model.SnakeGame;

public class LeftCommand implements Command {
    private SnakeGame game;

    public LeftCommand(SnakeGame game) {
        this.game = game;
    }

    @Override
    public void execute() {
        this.game.setDirection(Direction.UP);
    }
}
