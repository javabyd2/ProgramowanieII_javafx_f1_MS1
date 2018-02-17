package com.sda.f1.model;

public class TrackPoint {
    private Integer x = 0;
    private Integer y = 0;
    private Integer angle = 0;

    public TrackPoint() {
    }

    public TrackPoint(Integer x, Integer y, Integer angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getAngle() {
        return angle;
    }

    public void setAngle(Integer angle) {
        this.angle = angle;
    }
}
