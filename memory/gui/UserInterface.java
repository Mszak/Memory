package memory.gui;


import java.awt.Container;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import memory.domain.Field;
import memory.game.Memory;


/**
 *
 * @author Maniek
 */
public class UserInterface implements Runnable {
    private int width;
    private int height;
    private Memory game;
    private JFrame frame;
    private ClickListener clickListener;
    private Map<Field, JButton> fieldButtons;
    public static final int SIDE_LENGTH = 48;
    private JLabel clickCounter;
    private JLabel time;
    
    
    public UserInterface(){
        this.width = 8;
        this.height = 8;
        this.game = new Memory();
        this.fieldButtons = new HashMap<Field, JButton>();
    }
    @Override
    public void run() {
        this.frame = new JFrame("Memory");
        int width = (this.width + 1) * SIDE_LENGTH + 10;
        int height = (this.height + 2) * SIDE_LENGTH + 10;
        this.frame.setPreferredSize(new Dimension(width, height));
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        createComponents(frame.getContentPane());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        this.game.setSwings(this.frame, this.clickCounter, this.time);
        this.game.run();
    }
    
    public void createComponents(Container container){
        this.frame.setLayout(null);
        for (Field field : this.game.getFields()){
            JButton button = new JButton("");
            this.clickListener = new ClickListener(button, this.game, this.frame, field, this.fieldButtons);
            button.setBounds(field.getX() * SIDE_LENGTH, field.getY() * SIDE_LENGTH, SIDE_LENGTH, SIDE_LENGTH);
            button.addMouseListener(this.clickListener);
            container.add(button);
            this.fieldButtons.put(field, button);
        }
        JLabel clicks = new JLabel();
        clicks.setBounds(1 * SIDE_LENGTH, this.height * SIDE_LENGTH, SIDE_LENGTH, SIDE_LENGTH);
        clicks.setText("Clicks: ");
        this.clickCounter = new JLabel();
        this.clickCounter.setBounds(2 * SIDE_LENGTH, this.height * SIDE_LENGTH, SIDE_LENGTH, SIDE_LENGTH);
        this.time = new JLabel();
        this.time.setBounds(3 * SIDE_LENGTH, this.height * SIDE_LENGTH, SIDE_LENGTH, SIDE_LENGTH);
        container.add(clicks);
        container.add(this.clickCounter);
        container.add(this.time);
    }
}
