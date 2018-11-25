package memory.highscores;
import memory.game.Memory;

/**
 *
 * @author Maniek
 */
public class Highscore implements Comparable {
    private Long startTime;
    private Long endTime;
    private int clicks;
    private String name;
    
    public Highscore(Long startTime, long endTime, int clicks, String name){
        this.startTime = startTime;
        this.endTime = endTime;
        this.clicks = clicks;
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public int getClicks(){
        return this.clicks;
    }
    public Long getStartTime(){
        return this.startTime;
    }
    public Long getEndTime(){
        return this.endTime;
    }
    public String getTime(){
        return Memory.gameTime(this.startTime, this.endTime);
    }
    @Override
    public String toString(){
        return this.name + " " + this.clicks + " " + this.getTime();
    }

    @Override
    public int compareTo(Object o) {
        if (this.getClass() != o.getClass()){
            throw new Error("Object is not the same class!");
        }
        Highscore compared = (Highscore) o;
        if ((this.endTime - this.startTime) == (compared.endTime - compared.startTime)){
            if (this.clicks == compared.clicks){
                return 0;
            }
        }
        if ((this.endTime - this.startTime) > (compared.endTime - compared.startTime)){
            return 1;
        }
        else{
            return -1;
        }
    }
}
