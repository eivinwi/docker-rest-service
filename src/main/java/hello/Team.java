package hello;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
* Created by eivwik on 04.04.16.
*/
public class Team implements Comparable {
    public static Instant startTime;

    private String name;
    private boolean applied;
    private boolean tasks[];
    private String timeSpent;
    private String finishingTime;
    private String hostIp;

    public Team(String name, Integer numTasks) {
        this.name = name;
        timeSpent = null;
        finishingTime = null;
        tasks = new boolean[numTasks];
    }

    public boolean isApplied() {
        return applied;
    }

    public void setApplied() {
        this.applied = true;
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
        for(boolean b : tasks) {
            if(!b) return false;
        }
        setHasFinished();
        return true;
    }

    public void setHasFinished() {
        if(finishingTime == null) {
            this.finishingTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);// Instant.now(); //System.currentTimeMillis() - startTime;
            long seconds = Duration.between(startTime, Instant.now()).getSeconds();
            timeSpent = String.format(
                    "%02d:%02d:%02d",
                    seconds / 3600,
                    (seconds % 3600) / 60,
                    seconds % 60
            );
        }
    }


    public void completeTask(int task) {
        if (task < tasks.length) {
            tasks[task] = true;
        }
    }

    public boolean[] getTasks() { return tasks; }

    public String getFinishingTime() { return finishingTime; }

    public String getTimeSpent() {
        if(!isHasFinished()) {
            long seconds = Duration.between(startTime, Instant.now()).getSeconds();
            timeSpent = String.format(
                "%02d:%02d:%02d",
                seconds / 3600,
                (seconds % 3600) / 60,
                seconds % 60);
        }
        return timeSpent;
    }

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
