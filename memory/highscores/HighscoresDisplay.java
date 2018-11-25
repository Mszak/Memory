package memory.highscores;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

/**
 *
 * @author Maniek
 */
public class HighscoresDisplay {
    private JFrame frame;
    private JTextArea textArea;
    private Highscores h;
    private static final int WIDTH = 250;
    private static final int HEIGHT = 250;
    
    public HighscoresDisplay(Highscores h){
        this.frame = new JFrame("Highscores");
        this.textArea = new JTextArea();
        this.h = h;
    }
    public void display(){
        this.frame = new JFrame("Highscores");
        this.frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.textArea.setText(this.h.highscoresToString());
        this.frame.add(this.textArea);    
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
