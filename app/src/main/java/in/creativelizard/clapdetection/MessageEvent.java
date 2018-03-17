package in.creativelizard.clapdetection;

/**
 * Created by siddharthamaji on 17/03/18.
 */

public class MessageEvent {

    private String eventType;
    private double time,salience;


    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getSalience() {
        return salience;
    }

    public void setSalience(double salience) {
        this.salience = salience;
    }
}
