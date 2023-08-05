package snake.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import snake.difficulty.Beginner;
import snake.difficulty.Difficulty;

public class SnakeGame {

    private final CellState[][] board;
    private Direction direction;
    private final int width;
    private final int height;
    private LinkedList<Cell> snake;
    private Cell fruit;
    private State state;
    private int fruitConsumed;
    private Difficulty diff;

    public SnakeGame(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new CellState[this.width][this.height];
        this.state = State.START;
        this.diff = new Beginner();
    }

    public Cell getFruit() {
        return fruit;
    }

    public LinkedList<Cell> getSnake() {
        return snake;
    }

    public Direction getDirection() {
        return direction;
    }

    public void startGame() {
        this.createSnake();
        this.createFruit();
        this.state = State.GAME;
        this.direction = Direction.RIGHT;
        this.fruitConsumed = 0;
        this.showBoard();
    }

    public void setDirection(Direction d) {
        if ((d == Direction.LEFT && direction != Direction.RIGHT)
                || (d == Direction.RIGHT && direction != Direction.LEFT)
                || (d == Direction.UP && direction != Direction.DOWN)
                || (d == Direction.DOWN && direction != Direction.UP)) {
            direction = d;
        }
    }

    public void updateBoard() {
        if (this.state == State.GAME) {
            this.move();
            this.checkFood();
            if (checkCollision()) {
                this.state = State.END;
            }
            this.showBoard();
        }
    }

    public int getFruitConsumed() {
        return fruitConsumed;
    }

    public State getState() {
        return state;
    }

    public Difficulty getDiff() {
        return diff;
    }

    public void setDiff(Difficulty diff) {
        this.diff = diff;
    }

    private void createSnake() {
        snake = new LinkedList<>();
        snake.add(new Cell(0, 0));
        snake.add(new Cell(0, 1));
        snake.add(new Cell(0, 2));
        snake.add(new Cell(0, 3));
        snake.add(new Cell(0, 4));
    }

    private void createFruit() {
        Random r = new Random();
        fruit = new Cell(r.nextInt(this.width), r.nextInt(this.height));
        if (this.snake.contains(fruit)) {
            this.createFruit();
        }
    }

    private void showBoard() {
        // Board
        for (int i = 0; i < this.width; i++) {
            Arrays.fill(this.board[i], CellState.EMPTY);
        }
        // Snake
        snake.forEach(c -> {
            if (onBoard(c)) {
                this.board[c.x()][c.y()] = CellState.SNAKE;
            }
        });
        // Fruit
        this.board[fruit.x()][fruit.y()] = CellState.FRUIT;
    }

    private boolean onBoard(Cell cell) {
        BiFunction<Integer, Integer, Boolean> inLimit = (value, limit) -> value >= 0 && value < limit;
        return (inLimit.apply(cell.x(), this.width) && inLimit.apply(cell.y(), this.height));
    }

    private boolean checkCollision() {
        Cell head = this.snake.getLast();
        List<Cell> body = this.snake.subList(0, this.snake.size() - 1);
        return body.contains(head) || !onBoard(head);
    }

    private void checkFood() {
        Cell head = this.snake.getLast();
        if (head.equals(fruit)) {
            this.fruitConsumed++;
            this.createFruit();
            this.diff.changeDelay(this.fruitConsumed);
        } else {
            this.snake.removeFirst();
        }
    }

    private void move() {
        Cell head = this.snake.getLast();
        int row = head.x(), col = head.y();
        switch (this.direction) {
            case DOWN -> {
                row++;
            }
            case UP -> {
                row--;
            }
            case LEFT -> {
                col--;
            }
            case RIGHT -> {
                col++;
            }
        }
        head = new Cell(row, col);
        this.snake.add(head);
    }

    public static void main(String[] args) {
        SnakeGame s = new SnakeGame(10, 10);
        s.startGame();
        for (int i = 0; i < 8; i++) {
            s.updateBoard();
            System.out.println("##########");
        }
    }
}
