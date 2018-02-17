package com.sda.f1.model;

import java.util.List;

public class Pitstop {
    private List<TrackPoint> points;
    private Integer pitstopPlace;

    public Pitstop() {
    }

    public Pitstop(List<TrackPoint> positions, Integer pitstopPlace) {
        this.points = positions;
        this.pitstopPlace = pitstopPlace;
    }

    public List<TrackPoint> getPoints() {
        return points;
    }

    public void setPoints(List<TrackPoint> positions) {
        this.points = positions;
    }

    public Integer getPitstopPlace() {
        return pitstopPlace;
    }

    public void setPitstopPlace(Integer pitstopPlace) {
        this.pitstopPlace = pitstopPlace;
    }
}
