package com.sda.f1.controller;

import com.sda.f1.model.*;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.java.com.sda.f1.Main;

import java.io.IOException;
import java.util.Random;

public class TrackController {
    private int angle = 0;
    private Long nextAnimateTime = 0L;
    public Random generator = new Random();
    Boolean started = false;
    public TrafficLight trafficLight = TrafficLight.NONE;

    @FXML
    private ImageView track;
    @FXML
    private ImageView bolidBlue;
    @FXML
    private ImageView bolidBluesky;
    @FXML
    private ImageView bolidFuchsia;
    @FXML
    private ImageView bolidGray;
    @FXML
    private ImageView bolidGreen;
    @FXML
    private ImageView bolidRed;
    @FXML
    private ImageView bolidYellow;

    @FXML
    private ProgressBar fuelBlue;
    @FXML
    private ProgressBar fuelBluesky;
    @FXML
    private ProgressBar fuelFuchsia;
    @FXML
    private ProgressBar fuelGray;
    @FXML
    private ProgressBar fuelGreen;
    @FXML
    private ProgressBar fuelRed;
    @FXML
    private ProgressBar fuelYellow;

    @FXML
    private ProgressBar tiresBlue;
    @FXML
    private ProgressBar tiresBluesky;
    @FXML
    private ProgressBar tiresFuchsia;
    @FXML
    private ProgressBar tiresGray;
    @FXML
    private ProgressBar tiresGreen;
    @FXML
    private ProgressBar tiresRed;
    @FXML
    private ProgressBar tiresYellow;

    @FXML
    private ImageView pitstopBlue;
    @FXML
    private ImageView pitstopBluesky;
    @FXML
    private ImageView pitstopFuchsia;
    @FXML
    private ImageView pitstopGray;
    @FXML
    private ImageView pitstopGreen;
    @FXML
    private ImageView pitstopRed;
    @FXML
    private ImageView pitstopYellow;

    @FXML
    private Label lapsBlue;
    @FXML
    private Label lapsBluesky;
    @FXML
    private Label lapsFuchsia;
    @FXML
    private Label lapsGray;
    @FXML
    private Label lapsGreen;
    @FXML
    private Label lapsRed;
    @FXML
    private Label lapsYellow;

    @FXML
    private Button buttonStart;
    @FXML
    private ImageView pitstopTrafficLight;
    @FXML
    private Spinner lapsCount;

    Double layoutX = 0.0;
    Double layoutY = 0.0;
    Integer offsetX = 0;
    Integer offsetY = 0;

    @FXML
    void initialize() throws IOException{
        layoutX = track.getLayoutX();
        layoutY = track.getLayoutY();
        offsetX = layoutX.intValue()-5;
        offsetY = layoutY.intValue()-20;

        Main.bolids.add(new Bolid(track, "Blue", lapsBlue, bolidBlue, fuelBlue, tiresBlue, pitstopBlue, Main.pitstops.get(0)));
        Main.bolids.add(new Bolid(track, "Bluesky", lapsBluesky, bolidBluesky, fuelBluesky, tiresBluesky, pitstopBluesky, Main.pitstops.get(1)));
        Main.bolids.add(new Bolid(track, "Fuchsia", lapsFuchsia, bolidFuchsia, fuelFuchsia, tiresFuchsia, pitstopFuchsia, Main.pitstops.get(2)));
        Main.bolids.add(new Bolid(track, "Gray", lapsGray, bolidGray, fuelGray, tiresGray, pitstopGray, Main.pitstops.get(3)));
        Main.bolids.add(new Bolid(track, "Green", lapsGreen, bolidGreen, fuelGreen, tiresGreen, pitstopGreen, Main.pitstops.get(4)));
        Main.bolids.add(new Bolid(track, "Red", lapsRed, bolidRed, fuelRed, tiresRed, pitstopRed, Main.pitstops.get(5)));
        Main.bolids.add(new Bolid(track, "Yellow", lapsYellow, bolidYellow, fuelYellow, tiresYellow, pitstopYellow, Main.pitstops.get(6)));

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 30);
        lapsCount.setValueFactory(valueFactory);
        checkPitstopTrafficLight();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (nextAnimateTime.equals(0L) || now >= nextAnimateTime) {
                    nextAnimateTime = now + 100000000L;
                    if (started) {
                        Main.bolids.forEach(car -> animate(car));
                        checkPitstopTrafficLight();
                    }
                }
            }
        }.start();
    }

    private void checkPitstopTrafficLight() {
        boolean pitstopTrafficLightGreen = true;
        for (int i = 0; i < Main.bolids.size(); i++) {
            if (Main.bolids.get(i).getRaceState() == RaceState.RACE) {
                if (Main.bolids.get(i).getPosition() >= 5 && Main.bolids.get(i).getPosition() <= 14) {
                    pitstopTrafficLightGreen = false;
                    break;
                }
            }
        }
        if (pitstopTrafficLightGreen) {
            trafficLight = TrafficLight.GREEN;
            pitstopTrafficLight.setImage(new Image(getClass().getClassLoader().getResourceAsStream("trafficsignal_green.gif")));
        } else {
            trafficLight = TrafficLight.RED;
            pitstopTrafficLight.setImage(new Image(getClass().getClassLoader().getResourceAsStream("trafficsignal_red.gif")));
        }
    }

    public void animate(Bolid bolid) {
        Integer position = bolid.getPosition();
        if (!bolid.getServiceInProgress()) {
            position++;
            if (position > Main.trackPoints.size() - 1) {
                position = 0;
                bolid.setLap(bolid.getLap() + 1);
            }
            bolid.setPosition(position);
            bolid.setFuel(bolid.getFuel() - bolid.getLapFuelConsumptionRatio());
            bolid.setTiresFatigue(bolid.getTiresFatigue() + bolid.getLapTiresConsumptionRatio());
            bolid.setServiceNeeded(bolid.getFuel() < Main.refuelLevel || bolid.getTiresFatigue() > Main.replaceTiresLevel);
            if (bolid.getServiceNeeded() && bolid.getPosition() == 95) {
                bolid.setRaceState(RaceState.PITSTOP);
                position = 0;
                bolid.setPosition(position);
                bolid.setServiceNeeded(false);
            }
            if (bolid.getRaceState() == RaceState.STOP) {
                bolid.setRaceState(RaceState.PITSTOP);
            }
            if (bolid.getRaceState() == RaceState.PITSTOP && bolid.getPosition() == bolid.pitstop.getPoints().size() - 3 && trafficLight == TrafficLight.RED) {
                bolid.setPosition(--position);
                bolid.setRaceState(RaceState.STOP);
            }
            if (bolid.getRaceState() == RaceState.PITSTOP && bolid.getPosition() == bolid.pitstop.getPoints().size() - 1) {
                position = 14;
                bolid.setRaceState(RaceState.RACE);
                bolid.setPosition(position);
            }
        }
        TrackPoint trackPoint;
        switch (bolid.getRaceState()) {
            case RACE:
                trackPoint = new TrackPoint(
                        offsetX + Main.trackPoints.get(position).getX(),
                        offsetY + Main.trackPoints.get(position).getY(),
                        270-Main.trackPoints.get(position).getAngle());
                break;
            case PITSTOP:
                trackPoint = bolid.pitstop.getPoints().get(bolid.getPosition());
                if (!bolid.getServiceInProgress()) {
                    if (bolid.getPosition() == bolid.pitstop.getPitstopPlace()) {
                        bolid.setServiceInProgress(true);
                    } else {
                        trackPoint = new TrackPoint(
                                offsetX + bolid.pitstop.getPoints().get(position).getX(),
                                offsetY + bolid.pitstop.getPoints().get(position).getY(),
                                270 - bolid.pitstop.getPoints().get(position).getAngle());
                    }
                } else {
                    position = bolid.getPosition();
                    if (bolid.getFuel() < 100) {
                        bolid.setFuel(Math.min(100, bolid.getFuel()+3));
                    }
                    if (bolid.getTiresFatigue() > 0) {
                        bolid.setTiresFatigue(Math.max(0, bolid.getTiresFatigue()-5));
                    }
                    boolean carReadyToGo = bolid.getFuel() >= 100 && bolid.getTiresFatigue() <= 0;
                    if (carReadyToGo && noOtherCarPitstopMovingToCollision(bolid)) {
                        bolid.setServiceInProgress(false);
                    }
                }
                break;
            case STOP:
                trackPoint = new TrackPoint(
                        offsetX + bolid.pitstop.getPoints().get(position).getX(),
                        offsetY + bolid.pitstop.getPoints().get(position).getY(),
                        270-bolid.pitstop.getPoints().get(position).getAngle());
                break;
            default:
                trackPoint = new TrackPoint(0, 0, 0);
                break;
        }

        if (!bolid.getServiceInProgress()) {
            bolid.bolidImage.setLayoutX(trackPoint.getX());
            bolid.bolidImage.setLayoutY(trackPoint.getY());
            bolid.bolidImage.setRotate(trackPoint.getAngle());

            bolid.lapsCount.setText(bolid.getLap().toString());
            bolid.lapsCount.setVisible(!bolid.lapsCount.getText().equals("0"));
        }
    }

    private boolean noOtherCarPitstopMovingToCollision(Bolid bolid) {
        boolean noRisk = true;
        for (int i = 0; i < Main.bolids.size(); i++) {
            if (!Main.bolids.get(i).equals(bolid)) {
                Bolid otherBolid = Main.bolids.get(i);
                if ((otherBolid.getRaceState() == RaceState.PITSTOP)
                        && ((otherBolid.getPosition() + 5) > bolid.getPosition())
                        && ((otherBolid.getPosition() - 5) < bolid.getPosition())) {
                    noRisk = false;
                    break;
                }
            }
        }
        return noRisk;
    }

    @FXML
    public void onActionButtonExit(ActionEvent actionEvent) {
        Platform.exit();
    }

    @FXML
    public void onActionButtonStart(ActionEvent actionEvent) {
        started = true;
        buttonStart.setDisable(true);
        lapsCount.setDisable(true);
    }
}
