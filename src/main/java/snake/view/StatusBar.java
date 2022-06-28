package snake.view;

import java.awt.Dimension;
import javax.swing.JLabel;

public class StatusBar extends JLabel {

    public StatusBar() {
        super.setPreferredSize(new Dimension(100, 32));
    }

    public void setMessage(int score, String level, int delay) {
        setText(String.format("Score: %d | Level: %s | Delay: %d", score, level, delay));
    }
}
