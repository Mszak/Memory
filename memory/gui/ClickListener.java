package memory.gui;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;
import memory.Status;
import memory.domain.Field;
import memory.game.Memory;

/**
 *
 * @author Maniek
 */
public class ClickListener implements MouseListener, ActionListener{

    private JButton button;
    private Memory game;
    private JFrame frame;
    private Field field;
    private Map<Field, JButton> fieldButtons;
    private int clickCount;
    
    public ClickListener(JButton button, Memory game, JFrame frame, Field field, Map<Field, JButton> map){
        this.button = button;
        this.game = game;
        this.frame = frame;
        this.field = field;
        this.fieldButtons = map;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        int helpTimer = 0;
        int click = e.getButton();
        if (click == MouseEvent.BUTTON1){
            if ((this.field.getStatus() == Status.NOT_PRESSED) && (!this.field.isClicked())){
                this.button.setIcon(new ImageIcon(this.field.getIcon()));
                this.field.setStatus(Status.PRESSED);
                this.game.clicked();
                Timer t;
                t = new Timer(1000, this);
                t.setRepeats(false);
                if (this.game.twoPressed()){
                    this.game.setActionFlag(false);
                    this.frame.setEnabled(false);
                    t.start();
                }
            }
        }      
    }
          

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.game.match()){
            for (Field field : this.fieldButtons.keySet()){
                if (field.getStatus() == Status.PRESSED){
                    this.fieldButtons.get(field).setIcon(new JButton().getIcon());
                    this.fieldButtons.get(field).setBackground(Color.LIGHT_GRAY);
                    this.fieldButtons.get(field).setEnabled(false);
                    field.setStatus(Status.NOT_PRESSED);
                    field.setClicked(true);
                }
            }
            this.game.resetClicksTowardsPenalty();                        
        }
        else{
            for (Field field : this.fieldButtons.keySet()){
                if (field.getStatus() == Status.PRESSED){
                    this.fieldButtons.get(field).setIcon(new JButton().getIcon());
                    field.setStatus(Status.NOT_PRESSED);
                }
            }
            this.game.increaseClicksTowardsPenalty();
            this.game.increaseClicksTowardsPenalty();
        }               
        this.frame.setEnabled(true);
        this.game.setActionFlag(true);
    }
    
    
}
