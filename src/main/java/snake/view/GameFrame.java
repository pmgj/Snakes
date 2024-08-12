package snake.view;

import java.awt.Dialog.ModalityType;
import java.awt.FlowLayout;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import snake.difficulty.Advanced;
import snake.difficulty.Beginner;
import snake.difficulty.Intermediate;
import snake.model.Direction;

public class GameFrame extends JFrame {
    private GamePanel panel = new GamePanel();

    public void start() {
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
        JMenuItem menuKeys = new JMenuItem("Set Keys");
        menuKeys.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, InputEvent.ALT_DOWN_MASK));
        menuKeys.addActionListener(al -> this.showSetKeys());
        menuOptions.add(menuKeys);
        JMenuItem menuExit = new JMenuItem("Exit");
        menuExit.addActionListener(al -> System.exit(0));
        menuOptions.add(menuExit);

        StatusBar statusBar = new StatusBar();
        getContentPane().add(statusBar, java.awt.BorderLayout.SOUTH);
        panel.setStatusBar(statusBar);

        this.pack();
    }

    private void showSetKeys() {
        var dirs = new Direction[] { Direction.LEFT, Direction.RIGHT, Direction.UP, Direction.DOWN };
        for (var direction : dirs) {
            JDialog d = new JDialog(this, "Set Keys", ModalityType.DOCUMENT_MODAL);
            d.setLocationRelativeTo(this);
            d.setLayout(new FlowLayout());
            d.addKeyListener(new KeyAdapter() {

                @Override
                public void keyPressed(KeyEvent e) {
                    panel.setKey(e.getKeyCode(), direction);
                    d.dispose();
                }
            });
            // create a label
            JLabel l = new JLabel("Inform the " + direction + " key...");
            l.setHorizontalTextPosition(SwingConstants.CENTER);
            l.setVerticalTextPosition(SwingConstants.CENTER);
            d.add(l);
            // setsize of dialog
            d.setSize(200, 100);
            // set visibility of dialog
            d.setVisible(true);
            d.setFocusable(true);
        }
    }

    public static void main(String[] args) {
        GameFrame gf = new GameFrame();
        gf.start();
    }
}
