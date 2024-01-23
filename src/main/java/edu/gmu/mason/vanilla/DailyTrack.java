package edu.gmu.mason.vanilla;

import java.util.ArrayList;


public class DailyTrack {
    private int date;
    private ArrayList<Long> dayTrack;

    public DailyTrack(int date, ArrayList<Long> dayTrack) {
        this.date = date;
        this.dayTrack = dayTrack;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public ArrayList<Long> getDayTrack() {
        return dayTrack;
    }

    public void setDayTrack(ArrayList<Long> dayTrack) {
        this.dayTrack = dayTrack;
    }
}
