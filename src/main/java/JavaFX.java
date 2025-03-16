import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextField;
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
	Scene homeScene, weatherAppScene;
	public static void main(String[] args) { launch(args); }

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Weather App");
		ArrayList<Period> forecast = WeatherAPI.getForecast("LOT",77,70);
		if (forecast == null){
			throw new RuntimeException("Forecast did not load");
		}

		// Load wallpaper image
		FileInputStream wallpaperFileName = new FileInputStream("Background/Wallpaper.png");
		Image wallpaper = new Image(wallpaperFileName);
		ImageView wallpaperView = new ImageView(wallpaper);
		wallpaperView.setX(0);
		wallpaperView.setY(0);
		wallpaperView.setFitWidth(374);
		wallpaperView.setFitHeight(810);

		// Get current date and time
		SimpleDateFormat currDate = new SimpleDateFormat("E MM/dd");
		SimpleDateFormat currTime = new SimpleDateFormat("h:mm a");
		Date now = new Date();

		// Create date display
		TextField date = new TextField(currDate.format(now));
		date.setPrefSize(120, 30);
		date.setMinSize(120, 30);
		date.setMaxSize(120, 30);
		date.setEditable(false);
		date.setAlignment(Pos.CENTER);
		date.setStyle(
				"-fx-font-size: 18; " +
				"-fx-text-alignment: center; " +
				"-fx-background-color: transparent; " +
				"-fx-control-inner-background: transparent; " +
				"-fx-border-color: transparent; " +
				"-fx-text-fill: black;"
		);

		// Create time display
		TextField time = new TextField(currTime.format(now));
		time.setPrefSize(120, 30);
		time.setMinSize(120, 30);
		time.setMaxSize(120, 30);
		time.setEditable(false);
		time.setAlignment(Pos.CENTER);
		time.setStyle(
				"-fx-font-size: 18; " +
				"-fx-text-alignment: center; " +
				"-fx-background-color: transparent; " +
				"-fx-control-inner-background: transparent; " +
				"-fx-border-color: transparent; " +
				"-fx-text-fill: black;"
		);

		// Load Junimo animated icons
		ImageView junimo1 = new ImageView("https://i.redd.it/a02oq30jwi161.gif");
		junimo1.setFitHeight(25);
		junimo1.setFitWidth(25);

		ImageView junimo2 = new ImageView("https://i.redd.it/9f9qan0yg3y51.gif");
		junimo2.setFitHeight(30);
		junimo2.setFitWidth(30);

		// Load app icons
		FileInputStream app1FileName = new FileInputStream("Background/Weather_Report (1).png");
		Image  app1Icon= new Image(app1FileName);
		ImageView app1Viewer = new ImageView(app1Icon);
		app1Viewer.setFitHeight(54);
		app1Viewer.setFitWidth(54);
		Button app1Button = new Button();
		app1Button.setGraphic(app1Viewer);
		app1Button.setStyle("-fx-background-color: transparent;");
		app1Button.setOnAction(event -> {
			primaryStage.setScene(weatherAppScene);
		});

		ImageView app2Viewer = new ImageView("https://stardewvalleywiki.com/mediawiki/images/thumb/c/cd/Advanced_Options_Button.png/36px-Advanced_Options_Button.png");
		app2Viewer.setFitHeight(54);
		app2Viewer.setFitWidth(54);
		Button app2Button = new Button();
		app2Button.setGraphic(app1Viewer);
		app2Button.setStyle("-fx-background-color: transparent;");

		ImageView app3Viewer = new ImageView("https://stardewvalleywiki.com/mediawiki/images/a/a5/Stardrop.png");
		app3Viewer.setFitHeight(54);
		app3Viewer.setFitWidth(54);
		Button app3Button = new Button();
		app3Button.setGraphic(app1Viewer);
		app3Button.setStyle("-fx-background-color: transparent;");

		// Create date, time, and icons layout
		HBox iconArea = new HBox(junimo1, junimo2);
		iconArea.setAlignment(Pos.CENTER);
		iconArea.setSpacing(50);

		VBox dateTimeBox = new VBox(date, iconArea, time);
		dateTimeBox.setSpacing(7);
		dateTimeBox.setPrefSize(124, 120);
		dateTimeBox.setMaxSize(124, 120);
		dateTimeBox.setMinSize(124, 120);
		dateTimeBox.setLayoutX(374 - 124 - 10);
		dateTimeBox.setLayoutY(0);

		// Align date/time display to the right
		HBox topContainer = new HBox(dateTimeBox);
		topContainer.setAlignment(Pos.TOP_RIGHT);
		BorderPane.setMargin(topContainer, new Insets(10, 10, 0, 0));

		// Create app icons layout
		HBox appArea = new HBox(app1Button, app2Viewer, app3Viewer);
		appArea.setAlignment(Pos.CENTER);
		appArea.setSpacing(60);
		appArea.setPrefSize(375, 100);
		appArea.setMaxSize(375, 100);
		appArea.setMinSize(375, 100);
		BorderPane.setMargin(appArea, new Insets(-180, 0, 0, 0));

		// Main layout
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(appArea);
		borderPane.setTop(topContainer);

		// Stack everything together
		StackPane fullscreen = new StackPane();
		fullscreen.getChildren().addAll(wallpaperView, borderPane);

		// WEATHER MAIN SCREEN
		// Load wallpaper image
		FileInputStream weatherFileName = new FileInputStream("Background/weather_app_wallpaper.png");
		Image weatherWallpaper = new Image(weatherFileName);
		ImageView weatherWallpaperViewer = new ImageView(weatherWallpaper);
		wallpaperView.setX(0);
		wallpaperView.setY(0);
		wallpaperView.setFitWidth(374);
		wallpaperView.setFitHeight(810);

		ImageView iconLocation1 = new ImageView(app1Icon);
		iconLocation1.setFitHeight(25);
		iconLocation1.setFitWidth(25);

		ImageView iconLocation2 = new ImageView(app1Icon);
		iconLocation2.setFitHeight(30);
		iconLocation2.setFitWidth(30);

		TextField location = new TextField("Chicago, IL");
		location.setPrefSize(195, 60);
		location.setMinSize(195, 60);
		location.setMaxSize(195, 60);
		location.setEditable(false);
		location.setAlignment(Pos.CENTER);
		DropShadow dropShadow = new DropShadow();
		location.setEffect(dropShadow);
		location.setStyle(
			"-fx-font-size: 24; " +
			"-fx-text-alignment: center; " +
			"-fx-background-color: transparent; " +
			"-fx-control-inner-background: transparent; " +
			"-fx-border-color: transparent; " +
			"-fx-text-fill: black;"
		);

		HBox locationArea = new HBox(iconLocation1, location, iconLocation2);
		locationArea.setAlignment(Pos.CENTER);

		TextField degree = new TextField();
		degree.setText(forecast.getFirst().temperature + "°" + forecast.getFirst().temperatureUnit);
		degree.setEffect(dropShadow);
		degree.setPrefSize(195, 80);
		degree.setMinSize(195, 80);
		degree.setMaxSize(195, 80);
		degree.setEditable(false);
		degree.setAlignment(Pos.CENTER);
		degree.setStyle(
			"-fx-font-size: 48; " +
			"-fx-text-alignment: center; " +
			"-fx-background-color: transparent; " +
			"-fx-control-inner-background: transparent; " +
			"-fx-border-color: transparent; " +
			"-fx-text-fill: black;"
		);

		HBox degreeArea = new HBox(degree);
		degreeArea.setAlignment(Pos.CENTER);

		TextField shortDescription = new TextField(forecast.getFirst().shortForecast);
		shortDescription.setEffect(dropShadow);
		shortDescription.setPrefWidth(60);
		shortDescription.setEditable(false);
		shortDescription.setAlignment(Pos.CENTER);
		shortDescription.setStyle(
			"-fx-font-size: 18; " +
			"-fx-text-alignment: center; " +
			"-fx-background-color: transparent; " +
			"-fx-control-inner-background: transparent; " +
			"-fx-border-color: transparent; " +
			"-fx-text-fill: black;"
		);

		TextField windTitle = new TextField("Wind Speed: ");
		windTitle.setPrefSize(54,54);
		windTitle.setAlignment(Pos.CENTER);
		windTitle.setEditable(false);
		windTitle.setStyle(
			"-fx-font-size: 18; " +
			"-fx-text-alignment: center; " +
			"-fx-background-color: transparent; " +
			"-fx-control-inner-background: transparent; " +
			"-fx-border-color: transparent; " +
			"-fx-text-fill: black;" +
			"-fx-font-weight: bold;"
		);

		TextField windDescription = new TextField(forecast.getFirst().windSpeed);
		windDescription.setEffect(dropShadow);
		windDescription.setPrefSize(54,54);
		windDescription.setAlignment(Pos.CENTER);
		windDescription.setEditable(false);
		windDescription.setStyle(
			"-fx-font-size: 18; " +
			"-fx-text-alignment: center; " +
			"-fx-background-color: transparent; " +
			"-fx-control-inner-background: transparent; " +
			"-fx-border-color: transparent; " +
			"-fx-text-fill: black;"
		);

		VBox windArea = new VBox(windTitle, windDescription);
		windArea.setAlignment(Pos.CENTER);

		ImageView homeIcon = new ImageView("https://img1.picmix.com/output/stamp/normal/0/8/4/1/2231480_8738c.gif");
		homeIcon.setFitHeight(54);
		homeIcon.setFitWidth(54);
		Button homeButton = new Button();
		homeButton.setGraphic(homeIcon);
		homeButton.setStyle("-fx-background-color: transparent;");
		homeButton.setOnAction(event -> {
			primaryStage.setScene(homeScene);
		});

		HBox bottomContainer = new HBox(homeButton);
		bottomContainer.setAlignment(Pos.BOTTOM_RIGHT);
		bottomContainer.setSpacing(20);
		bottomContainer.setPadding(new Insets(10, 10, 10, 10));

		VBox weatherVBox = new VBox(locationArea, degreeArea, shortDescription, windArea);
		weatherVBox.setAlignment(Pos.CENTER);
		weatherVBox.setSpacing(20);

		// Stack everything together
		StackPane weatherApp = new StackPane();
		weatherApp.getChildren().addAll(weatherWallpaperViewer, weatherVBox, bottomContainer);

		// Create and show scene
		weatherAppScene = new Scene(weatherApp, 374, 810);
		// Create and show scene
		homeScene = new Scene(fullscreen, 374, 810);
		primaryStage.setScene(homeScene);
		primaryStage.show();

		Button focusButton = new Button();
		focusButton.setVisible(false);
		fullscreen.getChildren().add(focusButton);
		focusButton.requestFocus();
	}
}
