package com.sda.f1.model;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import main.java.com.sda.f1.Main;

import java.util.Random;

public class Bolid {
    private String name;
    private Double fuel = 100.0;
    private Double tiresFatigue = 0.0;
    private RaceState raceState = RaceState.NONE;
    private Integer lap;
    private Boolean serviceNeeded;
    private Boolean serviceInProgress;
    private Integer position;
    private Double lapFuelConsumptionRatio = 1.0;
    private Double lapTiresConsumptionRatio = 1.0;
    public Label lapsCount;
    public ImageView bolidImage;
    public ProgressBar fuelProgress;
    public ProgressBar tiresProgress;
    public ImageView goToPitstopIcon;
    public Pitstop pitstop;

    public Bolid(ImageView track, String name, Label laps, ImageView bolid, ProgressBar fuel, ProgressBar tires, ImageView pitstopIcon, Pitstop pitstop) {
        Random generator = new Random();
        this.name = name;
        //this.fuel = 10.0;
        this.fuel = 100.0;
        this.tiresFatigue = 0.0;
        this.setRaceState(RaceState.RACE);
        this.lap = 0;
        this.serviceNeeded = false;
        this.serviceInProgress = false;
        //this.position = 80;
        this.position = Main.trackPoints.size() - 1 - (3 * (Main.bolids.size()));
        this.lapFuelConsumptionRatio = 0.2 + (generator.nextInt(3)/10.0);
        this.lapTiresConsumptionRatio = 0.2 + (generator.nextInt(3)/10.0);
        this.lapsCount = laps;
        this.lapsCount.setVisible(false);
        this.bolidImage = bolid;
        this.fuelProgress = fuel;
        this.tiresProgress = tires;
        this.goToPitstopIcon = pitstopIcon;
        this.pitstop = pitstop;
        this.bolidImage.setLayoutX(track.getLayoutX()-5 + Main.trackPoints.get(position).getX());
        this.bolidImage.setLayoutY(track.getLayoutY()-20 + Main.trackPoints.get(position).getY());
        this.bolidImage.setRotate(270-Main.trackPoints.get(position).getAngle());
        this.bolidImage.setVisible(true);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getFuel() {
        return fuel;
    }

    public void setFuel(Double fuel) {
        this.fuel = fuel;
        if (this.getFuel() < Main.refuelLevel) this.fuelProgress.setStyle("-fx-accent: red;");
        else this.fuelProgress.setStyle("-fx-accent: #008dbc;");
        this.fuelProgress.setProgress(this.getFuel()/100.0);
    }

    public Double getTiresFatigue() {
        return tiresFatigue;
    }

    public void setTiresFatigue(Double tiresFatigue) {
        this.tiresFatigue = tiresFatigue;
        if (this.getTiresFatigue() > Main.replaceTiresLevel) this.tiresProgress.setStyle("-fx-accent: red;");
        else this.tiresProgress.setStyle("-fx-accent: #008dbc;");
        this.tiresProgress.setProgress((this.getTiresFatigue())/100.0);
    }

    public Integer getLap() {
        return lap;
    }

    public void setLap(Integer lap) {
        this.lap = lap;
    }

    public Boolean getServiceNeeded() {
        return serviceNeeded;
    }

    public void setServiceNeeded(Boolean serviceNeeded) {
        this.serviceNeeded = serviceNeeded;
        this.goToPitstopIcon.setVisible(this.serviceNeeded);
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Double getLapFuelConsumptionRatio() {
        return lapFuelConsumptionRatio;
    }

    public void setLapFuelConsumptionRatio(Double lapFuelConsumptionRatio) {
        this.lapFuelConsumptionRatio = lapFuelConsumptionRatio;
    }

    public Double getLapTiresConsumptionRatio() {
        return lapTiresConsumptionRatio;
    }

    public void setLapTiresConsumptionRatio(Double lapTiresConsumptionRatio) {
        this.lapTiresConsumptionRatio = lapTiresConsumptionRatio;
    }

    public RaceState getRaceState() {
        return raceState;
    }

    public void setRaceState(RaceState raceState) {
        this.raceState = raceState;
    }

    public Boolean getServiceInProgress() {
        return serviceInProgress;
    }

    public void setServiceInProgress(Boolean serviceInProgress) {
        this.serviceInProgress = serviceInProgress;
    }
}
