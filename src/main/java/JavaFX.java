import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import weather.Period;
import weather.WeatherAPI;


import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class JavaFX extends Application {
	// Home Page
	VBox dateTimeBox;
	HBox iconArea, appArea;
	Scene homeScreen;
	Button homeButton;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Weather App");
		ArrayList<Period> forecast = WeatherAPI.getForecast("LOT",77,70);
		if (forecast == null){
			throw new RuntimeException("Forecast did not load");
		}

		FileInputStream wallpaperFileName = new FileInputStream("Background/Wallpaper.png");

		Image wallpaper = new Image(wallpaperFileName);
		ImageView wallpaperView = new ImageView(wallpaper);

		wallpaperView.setX(0);
		wallpaperView.setY(0);

		wallpaperView.setFitWidth(374);
		wallpaperView.setFitHeight(810);

		SimpleDateFormat currDate = new SimpleDateFormat("E MM/dd");
		SimpleDateFormat currTime = new SimpleDateFormat("K:mm a");
		Date now = new Date();

		TextField date = new TextField(currDate.format(now));
		date.setPrefSize(120, 30);
		date.setMinSize(120, 30);
		date.setMaxSize(120, 30);
		date.setEditable(false);
		date.setAlignment(Pos.CENTER);
		date.setStyle("-fx-font-size: 18; -fx-text-alignment: center; -fx-background-color: transparent; -fx-control-inner-background: transparent; -fx-border-color: transparent; -fx-text-fill: black;");

		TextField time = new TextField(currTime.format(now));
		time.setPrefSize(120, 30);
		time.setMinSize(120, 30);
		time.setMaxSize(120, 30);
		time.setEditable(false);
		time.setAlignment(Pos.CENTER);
		time.setStyle("-fx-font-size: 18; -fx-text-alignment: center; -fx-background-color: transparent; -fx-control-inner-background: transparent; -fx-border-color: transparent; -fx-text-fill: black;");

		ImageView junimo1 = new ImageView("https://i.redd.it/a02oq30jwi161.gif");
		junimo1.setFitHeight(25);
		junimo1.setFitWidth(25);

		ImageView junimo2 = new ImageView("https://i.redd.it/9f9qan0yg3y51.gif");
		junimo2.setFitHeight(30);
		junimo2.setFitWidth(30);

		FileInputStream app1FileName = new FileInputStream("Background/weather_app_icon.png");
		Image  app1Icon= new Image(app1FileName);
		ImageView app1Viewer = new ImageView(app1Icon);
		app1Viewer.setFitHeight(54);
		app1Viewer.setFitWidth(54);

		FileInputStream app2FileName = new FileInputStream("Background/settings.png");
		Image  app2Icon= new Image(app2FileName);
		ImageView app2Viewer = new ImageView(app2Icon);
		app2Viewer.setFitHeight(54);
		app2Viewer.setFitWidth(54);

		ImageView app3 = new ImageView("https://media.tenor.com/1_u274nYwSUAAAAe/stardew-valley-stardew-valley-pufferfish.png");
		app3.setFitHeight(54);
		app3.setFitWidth(54);

		iconArea = new HBox(junimo1, junimo2);
		iconArea.setAlignment(Pos.CENTER);
		iconArea.setSpacing(50);

		dateTimeBox = new VBox(date, iconArea, time);
		dateTimeBox.setSpacing(7);
		dateTimeBox.setPrefSize(124, 120);
		dateTimeBox.setMaxSize(124, 120);
		dateTimeBox.setMinSize(124, 120);
		dateTimeBox.setLayoutX(374 - 124 - 10);
		dateTimeBox.setLayoutY(0);

		appArea = new HBox(app1Viewer, app2Viewer, app3);
		appArea.setAlignment(Pos.CENTER);
		BorderPane.setMargin(appArea, new Insets(-180, 0, 0, 0)); // Moves up by 20 pixels
		appArea.setSpacing(60);
		appArea.setPrefSize(375, 100);
		appArea.setMaxSize(375, 100);
		appArea.setMinSize(375, 100);

		BorderPane borderPane = new BorderPane();

		borderPane.setCenter(appArea);
		borderPane.setTop(dateTimeBox);

		// Wrap dateTimeBox inside an HBox to align it to the right
		HBox topContainer = new HBox(dateTimeBox);
		topContainer.setAlignment(Pos.TOP_RIGHT);

		// Add margin (10 pixels top & right)
		BorderPane.setMargin(topContainer, new Insets(10, 10, 0, 0));

		// Set it as the top region
		borderPane.setTop(topContainer);

		StackPane fullscreen = new StackPane();
		fullscreen.getChildren().addAll(wallpaperView, borderPane);

		Scene homeScene = new Scene(fullscreen, 374, 810);
		primaryStage.setScene(homeScene);
		primaryStage.show();
	}
}
