package snake.view.command;

import snake.model.Direction;
import snake.model.SnakeGame;

public class RightCommand implements Command {
    private SnakeGame game;

    public RightCommand(SnakeGame game) {
        this.game = game;
    }

    @Override
    public void execute() {
        this.game.setDirection(Direction.UP);
    }
}
