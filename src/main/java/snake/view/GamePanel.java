package snake.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.JPanel;
import javax.swing.Timer;
import snake.difficulty.Difficulty;
import snake.model.Cell;
import snake.model.Direction;
import snake.model.SnakeGame;
import snake.model.State;

public class GamePanel extends JPanel {

    public static final int SCREEN_WIDTH = 420;
    private static final int SCREEN_HEIGHT = 420;
    public static final int UNIT_SIZE = 15;
    private Timer timer;
    private StatusBar statusBar;
    private SnakeGame game;

    public void start() {
        game = new SnakeGame(SCREEN_WIDTH / UNIT_SIZE, SCREEN_HEIGHT / UNIT_SIZE);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        timer = new Timer(game.getDiff().getDelay(), ae -> repaint());
    }

    public void startGame() {
        timer.start();
        game.startGame();
    }

    public void setDifficulty(Difficulty d) {
        game.setDiff(d);
        timer.setDelay(game.getDiff().getDelay());
    }

    public void setStatusBar(StatusBar s) {
        this.statusBar = s;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.updateBoard();
        State state = game.getState();
        if (state == State.GAME) {
            timer.setDelay(game.getDiff().getDelay());
            Cell food = game.getFruit();
            // FOOD
            g.setColor(Color.MAGENTA);
            g.fillOval(food.getY() * UNIT_SIZE, food.getX() * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);

            LinkedList<Cell> snake = game.getSnake();
            // SNAKE
            for (int i = 0, SIZE = snake.size(); i < SIZE; i++) {
                int x = snake.get(i).getX() * UNIT_SIZE, y = snake.get(i).getY() * UNIT_SIZE;
                if (i == SIZE - 1) {
                    // TONGUE
                    Direction direction = game.getDirection();
                    g.setColor(Color.RED);
                    if (direction == Direction.LEFT || direction == Direction.RIGHT) {
                        g.fillRect(y, x + UNIT_SIZE / 2, UNIT_SIZE, UNIT_SIZE / 7);
                    } else {
                        g.fillRect(y + UNIT_SIZE / 2, x, UNIT_SIZE / 7, UNIT_SIZE);
                    }
                } else if (i == SIZE - 2) {
                    // HEAD
                    g.setColor(Color.YELLOW);
                    g.fillRect(y, x, UNIT_SIZE, UNIT_SIZE);
                } else {
                    // BODY
                    g.setColor(Color.GREEN);
                    g.fillRect(y, x, UNIT_SIZE, UNIT_SIZE);
                }
            }
        } else if (state == State.END) {
            timer.stop();
            // GAME OVER
            g.setColor(Color.RED);
            g.setFont(new Font("MV Boli", Font.PLAIN, 60));
            FontMetrics metrics2 = getFontMetrics(g.getFont());
            String gameOver = "GAME OVER!";
            g.drawString(gameOver, (SCREEN_WIDTH - metrics2.stringWidth(gameOver)) / 2, SCREEN_HEIGHT / 2);
        }
        // SCORE
        String s = game.getDiff().getClass().toString();
        int i = s.lastIndexOf(".");
        statusBar.setMessage(game.getFruitConsumed(), s.substring(i + 1), timer.getDelay());
    }

    private class MyKeyAdapter extends KeyAdapter {

        Map<Integer, Direction> map = Stream.of(new Object[][]{
            {KeyEvent.VK_LEFT, Direction.LEFT},
            {KeyEvent.VK_RIGHT, Direction.RIGHT},
            {KeyEvent.VK_UP, Direction.UP},
            {KeyEvent.VK_DOWN, Direction.DOWN}
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> (Direction) data[1]));

        @Override
        public void keyPressed(KeyEvent e) {
            game.setDirection(map.get(e.getKeyCode()));
        }
    }
}
