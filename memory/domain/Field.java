package memory.domain;

import memory.Status;

/**
 *
 * @author Maniek
 */
public class Field {
    private int x;
    private int y;
    private boolean clicked;
    private Status status;
    private String icon;
    
    public Field(int x, int y, String icon){
        this.x = x;
        this.y = y;
        this.clicked = false;
        this.status = Status.NOT_PRESSED;
        this.icon = icon;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public boolean isClicked(){
        return this.clicked;
    }
    public Status getStatus(){
        return this.status;
    }
    public void setClicked(boolean b){
        this.clicked = b;
    }
    public void setStatus(Status s){
        this.status = s;
    }
    public String getIcon(){
        return this.icon;
    }
    public void setIcon(String icon){
        this.icon = icon;
    }
    @Override
    public String toString(){
        return "(" + this.x + "," + this.y + "," + this.clicked + "," + this.icon + ")";
    }
}
