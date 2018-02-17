package main.java.com.sda.f1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda.f1.model.Bolid;
import com.sda.f1.model.Pitstop;
import com.sda.f1.model.TrackPoint;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends Application {

    public static List<TrackPoint> trackPoints = new ArrayList<>();
    public static Integer refuelLevel = 40;
    public static Integer replaceTiresLevel = 65;
    public static List<Bolid> bolids = new ArrayList<Bolid>();
    public static List<Pitstop> pitstops = new ArrayList<>();

    @Override
    public void start(final Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("track.fxml"));
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root, 1200, 800);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException{
        loadTrackPoints();
        loadPitstopPoints();
        launch(args);
    }

    private static void loadTrackPoints() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        TrackPoint[] pointsArray = objectMapper.readValue(new File("track_points.json"), TrackPoint[].class);
        trackPoints.clear();
        trackPoints.addAll(Arrays.asList(pointsArray));
    }

    private static void loadPitstopPoints() throws IOException{
        addPitstopPoints("pitstop_points_1.json", 4);
        addPitstopPoints("pitstop_points_2.json", 10);
        addPitstopPoints("pitstop_points_3.json", 16);
        addPitstopPoints("pitstop_points_4.json", 22);
        addPitstopPoints("pitstop_points_5.json", 28);
        addPitstopPoints("pitstop_points_6.json", 34);
        addPitstopPoints("pitstop_points_7.json", 40);
    }

    private static void addPitstopPoints(String fileName, Integer pitstopPlace) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        TrackPoint[] pointsArray = objectMapper.readValue(new File(fileName), TrackPoint[].class);
        List<TrackPoint> pitstopPoints = new ArrayList<>();
        pitstopPoints.clear();
        pitstopPoints.addAll(Arrays.asList(pointsArray));
        pitstops.add(new Pitstop(pitstopPoints, pitstopPlace));
    };
}
