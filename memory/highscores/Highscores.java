package memory.highscores;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maniek
 */
public class Highscores{
    private List<Highscore> entries;
    private PrintWriter writer;
    private Scanner reader;
    private static final String FILE_PATH = "src/memory/highscores/Highscores.txt";
    
    public Highscores(){
        this.entries = new ArrayList<Highscore>();
        this.readFile();
    }
    public void addHighscore(Highscore h){
        if (this.entries.size() < 5){
            this.entries.add(h);
        }
        else{
            if (this.entries.get(4).getEndTime() - this.entries.get(4).getStartTime() > (h.getEndTime() - h.getStartTime())){
                this.entries.remove(4);
                this.entries.add(h);
            }
        }
        Collections.sort(this.entries);
        try {
            this.writer = new PrintWriter(FILE_PATH, "UTF-8");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Highscores.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Highscores.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Highscore highscore : this.entries){
            this.writer.println(highscore.getStartTime() + ";" + highscore.getEndTime() + ";" + highscore.getClicks() + ";" + highscore.getName());
        }
        this.writer.close();
    }
    public void printHighscores(){
        for (Highscore h : this.entries){
            System.out.println(h);
        }
    }
    public String highscoresToString(){
        String help = "";
        for (int i = 0; i < this.entries.size(); i++){
            help += Integer.toString((i+1));
            help += ".  ";
            help += this.entries.get(i);
            help += "\n";
        }
        return help;
    }
    public void readFile(){
        try {
            this.reader = new Scanner(new File(FILE_PATH));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Highscores.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (this.reader.hasNextLine()){
            String [] readLine = this.reader.nextLine().split(";");
            Long startTime = Long.parseLong(readLine[0]);
            Long endTime = Long.parseLong(readLine[1]);
            int clicks = Integer.parseInt(readLine[2]);
            String name = readLine[3];
            Highscore h = new Highscore(startTime, endTime, clicks, name);
            this.entries.add(h);        
        }
        if(!this.entries.isEmpty()){
            Collections.sort(this.entries);
        }
        this.reader.close();
    }
}


