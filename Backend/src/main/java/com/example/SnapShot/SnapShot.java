package com.example.SnapShot;

public class SnapShot {
    private String item, from, to;
    private int duration;
    private long time;

    public SnapShot(String itemName, String from, String to, int duration, long localTime) {
        this.item = itemName;
        this.from = from;
        this.to = to;
        this.duration = duration;
        this.time = localTime;
    }

    public long getTime() {
        return this.time;
    }

    public String getItem() {
        return this.item;
    }

    public String getFrom() {
        return this.from;
    }

    public String getTo() {
        return this.to;
    }

    public int getDuration() {
        return this.duration;
    }

    public void print() {
        System.out.println("Create SnapShot Object\n" +
                " item: " + this.item + " take: " + this.from + " put: " + this.to +
                 " time: " + this.duration +" localTime "+this.time);
    }
}
