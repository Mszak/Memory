package memory.game;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import memory.Status;
import memory.domain.Field;
import memory.highscores.Highscore;
import memory.highscores.Highscores;
import memory.highscores.HighscoresDisplay;

/**
 *
 * @author Maniek
 */
public class Memory implements Runnable{
    private List<Field> fields;
    private List<String> icons;
    private Random random;
    private JFrame frame;
    private JLabel clickCounter;
    private JLabel time;
    private int clicks;
    private int clicksTowardsPenalty;
    private boolean actionFlag;
    private static final String ICON_PATH = "src/memory/icons/icon"; //icons for game have to be named icon1,icon2,etc...
    
    public Memory(){
        this.fields = new ArrayList<Field>();
        this.icons = new ArrayList<String>();
        this.random = new Random();
        this.initialize();
        this.clicks = 0;
        this.clicksTowardsPenalty = 0;
        this.actionFlag = false;
    }
    public void initializeIcons(){
        for (int i = 1; i < 33 ; i++){
            String string = ICON_PATH;
            string += Integer.toString(i);
            string += ".png";
            this.icons.add(string);
        }
    }
    public void initialize(){
        this.initializeIcons();
        List<String> help = new ArrayList<String>();
        int bound = 63;
        int number = 0;
        for (String string : this.icons){
            help.add(string);
        }
        for (String string : this.icons){
            help.add(string);
        }
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (bound == 0){
                    number = 0;
                }
                else{
                    number = this.random.nextInt(bound); 
                }
                Field field = new Field(i, j, help.get(number));
                this.fields.add(field);
                help.remove(number);
                bound--;
            }
        }
    }
    public void Showfields(){
        System.out.println(this.fields);
    }
    public List<Field> getFields(){
        return this.fields;
    }
    public List<String> getIcons(){
        return this.icons;
    }
    public void setActionFlag(boolean b){
        this.actionFlag = b;
    }
    
    public boolean twoPressed(){
        int help = 0;
        for (Field field : this.fields){
            if (field.getStatus() == Status.PRESSED){
                help++;
            }
        }
        if (help == 2){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean match(){
        List<Field> help = new ArrayList<Field>();
        for (Field field : this.fields){
            if (field.getStatus() == Status.PRESSED){
                help.add(field);
            }
        }
        if (help.get(0).getIcon().equals(help.get(1).getIcon())){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isStillPlaying(){
        int help = 0;
        for (Field field : this.fields){
            if (field.isClicked()){
                help++;
            }
        }
        if (help == 64){
            return false;
        }
        else{
            return true;
        }
    }
    public void setSwings(JFrame frame, JLabel counter, JLabel time){
        this.frame = frame;
        this.clickCounter = counter;
        this.time = time;
    }
    public void clicked(){
        this.clicks++;
    }

    @Override
    public void run() {
        long startTime = System.nanoTime();
        long elapsedTime = 0;
        long endTime = 0;
        while (this.isStillPlaying()){
            elapsedTime = System.nanoTime();
            this.time.setText(gameTime(startTime, elapsedTime));
            this.clickCounter.setText(Integer.toString(this.clicks));
            if ((this.clicksTowardsPenalty != 0) && (this.actionFlag)){
                if (this.PenaltyCheck()){
                    this.penalty();
                }
            }           
        }
        endTime = System.nanoTime();
        this.frame.setEnabled(false);
        String nameOfPlayer = JOptionPane.showInputDialog("Type your name, winner!");
        Highscores h = new Highscores();
        h.addHighscore(new Highscore(startTime, endTime,this.clicks, nameOfPlayer));
        HighscoresDisplay hd = new HighscoresDisplay(h);
        hd.display();
    }
    public static String gameTime(long start, long elapsed){
        long time = (elapsed - start)/1000000000;
        int hours = 0;
        int minutes = 0;
        int seconds = 0;
        boolean n = true;
        while(n){
            while (time > 3599 ){
                hours++;
                time -= 3600;
            }
            while (time > 59){
                minutes++;
                time -= 60;
            }
            seconds = (int)time;
            n = false;
        }
        String game = "";
        if (hours >= 10){
            game += Integer.toString(hours);
        }
        else{
            game += "0";
            game += Integer.toString(hours);
        }
        game += ":";
        if (minutes >= 10){
            game += Integer.toString(minutes);
        }
        else{
            game += "0";
            game += Integer.toString(minutes);
        }
        game += ":";
        if (seconds >= 10){
            game += Integer.toString(seconds);
        }
        else{
            game += "0";
            game += Integer.toString(seconds);
        }
        return game;
    }
    public void penalty(){
        List<String> help = new ArrayList<String>();
        int i = 0;
        for (Field field : this.fields){
            if (!field.isClicked()){
                help.add(field.getIcon());
            }
        }
        for (Field field : this.fields){
            if (!field.isClicked()){
                if (help.size() == 1){
                    field.setIcon(help.get(0));
                }
                else{
                    i = this.random.nextInt(help.size()-1);
                    field.setIcon(help.get(i));
                    help.remove(i); 
                }
            }
        }
        JOptionPane.showMessageDialog(this.frame, "Penalty!");
    }
    public int howManyClicked(){
        int help = 0;
        for (Field field : this.fields){
            if (field.isClicked()){
                help++;
            }
        }
        return help;
    }
    public boolean PenaltyCheck(){
        int remaining = 64 - this.howManyClicked();
        remaining = remaining/2;
        if ((remaining % 2) != 0){
            remaining++;
        }
        if ((this.clicksTowardsPenalty >= remaining)){
            this.clicksTowardsPenalty = 0;
            return true;
        }
        else{
            return false;
        }
    }
    public void increaseClicksTowardsPenalty(){
        this.clicksTowardsPenalty++;
    }
    public void resetClicksTowardsPenalty(){
        this.clicksTowardsPenalty = 0;
    }   
}
