package memory;

/**
 *
 * @author Maniek
 */
public enum Status {
    NOT_PRESSED, PRESSED;
    
    private Status next;
    static {
        NOT_PRESSED.next = PRESSED;
        PRESSED.next = NOT_PRESSED;
    }
    public Status getNext(){
        return next;
    }
}

