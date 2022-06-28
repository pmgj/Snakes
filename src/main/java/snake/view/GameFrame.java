package snake.view;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import snake.difficulty.Advanced;
import snake.difficulty.Beginner;
import snake.difficulty.Intermediate;

public class GameFrame extends JFrame {

    public void start() {
        GamePanel panel = new GamePanel();
        panel.start();
        this.setTitle("Snake");
        this.add(panel);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        JMenuBar menubar = new JMenuBar();
        this.setJMenuBar(menubar);
        JMenu menuOptions = new JMenu("Options");
        menuOptions.setMnemonic(KeyEvent.VK_O);
        menubar.add(menuOptions);
        JMenuItem startItem = new JMenuItem("Start");
        startItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        startItem.addActionListener(al -> panel.startGame());
        menuOptions.add(startItem);
        JMenu menuDiff = new JMenu("Difficulty");
        menuOptions.add(menuDiff);
        JMenuItem beginnerItem = new JMenuItem("Begginner");
        beginnerItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.ALT_DOWN_MASK));
        beginnerItem.addActionListener(al -> panel.setDifficulty(new Beginner()));
        JMenuItem intermedItem = new JMenuItem("Intermediate");
        intermedItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.ALT_DOWN_MASK));
        intermedItem.addActionListener(al -> panel.setDifficulty(new Intermediate()));
        JMenuItem advancedItem = new JMenuItem("Advanced");
        advancedItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.ALT_DOWN_MASK));
        advancedItem.addActionListener(al -> panel.setDifficulty(new Advanced()));
        menuDiff.add(beginnerItem);
        menuDiff.add(intermedItem);
        menuDiff.add(advancedItem);
        JMenuItem menuExit = new JMenuItem("Exit");
        menuExit.addActionListener(al -> System.exit(0));
        menuOptions.add(menuExit);

        StatusBar statusBar = new StatusBar();
        getContentPane().add(statusBar, java.awt.BorderLayout.SOUTH);
        panel.setStatusBar(statusBar);
        
        this.pack();
    }

    public static void main(String[] args) {
        GameFrame gf = new GameFrame();
        gf.start();
    }
}
