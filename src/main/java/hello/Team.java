package hello;

import java.time.Duration;
import java.time.Instant;

/**
* Created by eivwik on 04.04.16.
*/
public class Team implements Comparable {
    public static Instant startTime;

    private String name;
    private boolean hasFinished;
    private String timeSpent;
    private Instant finishingTime;
    private String hostIp;

    public Team(String name) {
        this.name = name;
        hasFinished = false;
        timeSpent = null;
        finishingTime = null;
    }

    public static String getStartTime() {
        return startTime.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHasFinished() {
        return hasFinished;
    }

    public void setHasFinished(boolean hasFinished) {
        if(!this.hasFinished && hasFinished) {
            this.hasFinished = true;
            this.finishingTime = Instant.now(); //System.currentTimeMillis() - startTime;
            timeSpent = Duration.between(startTime, finishingTime).toString();
        }
    }

    public Instant getFinishingTime() { return finishingTime; }

    public String getTotalTimeString() {
        return (finishingTime != null)? finishingTime.toString() : null;
    }

    public String getTimeSpent() { return timeSpent; }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Team && this.name.equals(((Team) o).getName()));
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Team) {
            Team t = (Team) o;
            if(this.finishingTime == null) {
                return (t.getFinishingTime() == null)? 0 : -1;
            } else if(t.getFinishingTime() == null){
                return 1;
            }
            return this.finishingTime.compareTo(t.getFinishingTime());
        }
        return -1;
    }
}
