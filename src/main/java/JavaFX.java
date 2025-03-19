import javafx.application.Application;

import java.text.DecimalFormat;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import weather.Period;
import weather.WeatherAPI;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class JavaFX extends Application {
	GridInfo gridInfo;
	ArrayList<Period> forecast;

	// Home page and main weather page
	TextField degreeField, lonOption, latOption, locationField, currLocation,mainWeatherLocation, shortDescription,windTitle,windDescription;
	Boolean unitToC = true;
	Button settingButton, searchButton, unitField;
	SimpleDateFormat currDate, currTime;
	Date now;
	TextField date,time, mainWeatherDegree;
	DropShadow dropShadow = new DropShadow();
	Scene homeScene, settingScene, weatherAppScene;

	// Weather in next 4 days page
	int swapBGcount = 0;
	String changeBGIcon = "https://img.icons8.com/?size=100&id=102352&format=png&color=000000";
	String home3DIcon = "https://img.icons8.com/?size=100&id=xHeTZeKGjXUD&format=png&color=000000";
	String tempIcon = "https://img.icons8.com/?size=100&id=7q580FH1Tah7&format=png&color=000000";
	String umbrellaIcon = "https://img.icons8.com/?size=100&id=IgkEaRo6KwmP&format=png&color=000000";
	String windIcon = "https://img.icons8.com/?size=100&id=KXHiLGrszdFI&format=png&color=000000";
	String weatherIcon  = "https://img.icons8.com/?size=100&id=67538&format=png&color=000000";
	String directionIcon = "https://img.icons8.com/?size=100&id=67355&format=png&color=000000";
	Button home3DButton, changeBG, windSwapButton;
	private boolean isWindScene = false;
	TextField tempDay1,probDay1,tempDay1Night,probDay1Night, tempDay2, probDay2,tempDay2Night,probDay2Night, tempDay3, probDay3,tempDay3Night, probDay3Night;
	HBox hDay1, hDay1Night, hDay2, hDay2Night, hDay3, hDay3Night, hInteraction;
	VBox vDay1, vDay1Night, vDay2, vDay2Night, vDay3, vDay3Night, vPredict;
	Scene weather3DPage;
	public static void main(String[] args) { launch(args); }

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Weather App");
		gridInfo = MyWeatherAPI.convertLatLonToGrid("41.8832","-87.6324");
		//forecast = WeatherAPI.getForecast("LOT",77,70);
		forecast = WeatherAPI.getForecast(gridInfo.region,gridInfo.gridX, gridInfo.gridY);
		//System.out.println(gridInfo.city+", "+gridInfo.state+ " at "+gridInfo.gridX+" "+gridInfo.gridY);
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
		currDate = new SimpleDateFormat("E MM/dd");
		currTime = new SimpleDateFormat("h:mm a");
		now = new Date();

		// Create date display
		date = new TextField(currDate.format(now));
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
		time = new TextField(currTime.format(now));
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
		FileInputStream app1FileName = new FileInputStream("Background/icons8-weather-100.png");
		Image  app1Icon= new Image(app1FileName);
		ImageView app1Viewer = new ImageView(app1Icon);
		app1Viewer.setFitHeight(81);
		app1Viewer.setFitWidth(81);
		Button app1Button = new Button();
		app1Button.setGraphic(app1Viewer);
		app1Button.setStyle("-fx-background-color: transparent;");
		app1Button.setOnAction(event -> {
			primaryStage.setScene(weatherAppScene);
		});

		FileInputStream app2FileName = new FileInputStream("Background/icons8-setting-100.png");
		Image app2Icon = new Image(app2FileName);
		ImageView app2Viewer = new ImageView(app2Icon);
		app2Viewer.setFitHeight(81);
		app2Viewer.setFitWidth(81);
		settingButton = new Button();
		settingButton.setGraphic(app2Viewer);
		settingButton.setStyle("-fx-background-color: transparent;");
		settingButton.setOnAction(event -> {
			primaryStage.setScene(settingScene); // FIX
		});


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
		HBox appArea = new HBox(app1Button, settingButton);
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

		// Setting Screen

		unitField = new Button("Change Unit ");
		//unitField.setStyle("-fx-background-color: transparent;");
		unitField.setOnAction(event -> {
			if (degreeField.getText().equals("Fahrenheit")) {
				degreeField.setText("Celsius");
				changeUnit();
			} else {
				degreeField.setText("Fahrenheit");
				changeUnit();
			}
		});

		degreeField = new TextField();
		degreeField.setText("Fahrenheit");
		degreeField.setAlignment(Pos.CENTER);
		degreeField.setEditable(false);

		HBox unitArea = new HBox(30, unitField, degreeField);
		unitArea.setAlignment(Pos.CENTER);

		lonOption = new TextField();
		lonOption.setPromptText("Enter Longitude");
		lonOption.setAlignment(Pos.CENTER);
		lonOption.setPrefSize(120, 120);
		lonOption.setMaxSize(120, 120);
		lonOption.setMinSize(120, 120);

		latOption = new TextField();
		latOption.setPromptText("Enter Latitude");
		latOption.setAlignment(Pos.CENTER);
		latOption.setPrefSize(120, 120);
		latOption.setMaxSize(120, 120);
		latOption.setMinSize(120, 120);

		HBox CoordArea = new HBox(30, latOption, lonOption);
		CoordArea.setAlignment(Pos.CENTER);

		searchButton= new Button("Search");
		searchButton.setAlignment(Pos.CENTER);
		searchButton.setOnAction(event -> {
//			double lat = Double.parseDouble(latOption.getText());
//			double lon = Math.abs(Double.parseDouble(lonOption.getText()))*-1;
//			latOption.setText(String.format("%.4f", lat));
//			lonOption.setText(String.format("%.4f", lon));
			double lat = Double.parseDouble(latOption.getText());
			double lon = Math.abs(Double.parseDouble(lonOption.getText())) * -1;

			latOption.setText(formatDecimal(lat));
			lonOption.setText(formatDecimal(lon));

			gridInfo = MyWeatherAPI.convertLatLonToGrid(latOption.getText(), lonOption.getText());
			if (gridInfo == null) {
				locationField.clear();
				locationField.setText("Location not found");
			}
			else{
				forecast = WeatherAPI.getForecast(gridInfo.region, gridInfo.gridX, gridInfo.gridY);
				locationField.setText(gridInfo.city + ", " + gridInfo.state);
				updateWeatherOnLocation();
			}
		});

		currLocation = new TextField();
		currLocation.setEditable(false);
		currLocation.setText("Current Location: ");
		currLocation.setAlignment(Pos.CENTER);
		currLocation.setStyle("-fx-background-color: transparent;");

		locationField = new TextField();
		locationField.setEditable(false);
		locationField.setText("Chicago, IL");
		locationField.setAlignment(Pos.CENTER);
		locationField.setStyle("-fx-background-color: transparent;");

		Button homeSettingButton = new Button("Home");
		homeSettingButton.setStyle("-fx-background-color: transparent;");
		homeSettingButton.setOnAction(event -> {
			primaryStage.setScene(homeScene);
		});

		VBox settingBox = new VBox(30, unitArea, CoordArea, searchButton, currLocation ,locationField, homeSettingButton);
		settingBox.setAlignment(Pos.CENTER);

		settingScene = new Scene(settingBox, 374, 810);


		// WEATHER MAIN SCREEN
		// Load wallpaper image
		FileInputStream weatherFileName = new FileInputStream("Background/weather_app_wallpaper.png");
		Image weatherWallpaper = new Image(weatherFileName);
		ImageView weatherWallpaperViewer = new ImageView(weatherWallpaper);
		wallpaperView.setX(0);
		wallpaperView.setY(0);
		wallpaperView.setFitWidth(374);
		wallpaperView.setFitHeight(810);

		ImageView iconLocation1 = new ImageView("https://img.icons8.com/?size=100&id=124191&format=png&color=000000");
		iconLocation1.setFitHeight(25);
		iconLocation1.setFitWidth(25);

		ImageView iconLocation2 = new ImageView("https://img.icons8.com/?size=100&id=124191&format=png&color=000000");
		iconLocation2.setFitHeight(30);
		iconLocation2.setFitWidth(30);

		mainWeatherLocation = new TextField("Chicago, IL");
		mainWeatherLocation.setPrefSize(195, 60);
		mainWeatherLocation.setMinSize(195, 60);
		mainWeatherLocation.setMaxSize(195, 60);
		mainWeatherLocation.setEditable(false);
		mainWeatherLocation.setAlignment(Pos.CENTER);

		mainWeatherLocation.setEffect(dropShadow);
		mainWeatherLocation.setStyle(
				"-fx-font-size: 24; " +
						"-fx-text-alignment: center; " +
						"-fx-background-color: transparent; " +
						"-fx-control-inner-background: transparent; " +
						"-fx-border-color: transparent; " +
						"-fx-text-fill: black;"
		);

		HBox locationArea = new HBox(iconLocation1, mainWeatherLocation, iconLocation2);
		locationArea.setAlignment(Pos.CENTER);

		mainWeatherDegree = new TextField();
		mainWeatherDegree.setText(forecast.getFirst().temperature + "°" + forecast.getFirst().temperatureUnit);
		mainWeatherDegree.setEffect(dropShadow);
		mainWeatherDegree.setPrefSize(195, 80);
		mainWeatherDegree.setMinSize(195, 80);
		mainWeatherDegree.setMaxSize(195, 80);
		mainWeatherDegree.setEditable(false);
		mainWeatherDegree.setAlignment(Pos.CENTER);
		mainWeatherDegree.setStyle(
				"-fx-font-size: 48; " +
						"-fx-text-alignment: center; " +
						"-fx-background-color: transparent; " +
						"-fx-control-inner-background: transparent; " +
						"-fx-border-color: transparent; " +
						"-fx-text-fill: black;"
		);

		HBox degreeArea = new HBox(mainWeatherDegree);
		degreeArea.setAlignment(Pos.CENTER);

		shortDescription = new TextField(forecast.getFirst().shortForecast);
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

		windTitle = new TextField("Wind Speed: ");
		windTitle.setEffect(dropShadow);
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

		windDescription = new TextField(forecast.getFirst().windSpeed);
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

		ImageView predictIcon = new ImageView("https://img.icons8.com/?size=100&id=67571&format=png&color=000000");
		predictIcon.setFitHeight(54);
		predictIcon.setFitWidth(54);

		Button predictSceneButton = new Button();
		predictSceneButton.setGraphic(predictIcon);
		predictSceneButton.setStyle("-fx-background-color: transparent;");
		predictSceneButton.setOnAction(event -> {
			primaryStage.setScene(weather3DPage);
		});

		ImageView homeIcon = new ImageView("https://img.icons8.com/?size=100&id=xHeTZeKGjXUD&format=png&color=000000");
		homeIcon.setFitHeight(54);
		homeIcon.setFitWidth(54);
		Button homeButton = new Button();
		homeButton.setGraphic(homeIcon);
		homeButton.setStyle("-fx-background-color: transparent;");
		homeButton.setOnAction(event -> {
			updateDateTime();
			primaryStage.setScene(homeScene);
		});

		homeButton.toFront();
		// buttons in main weather page
		HBox bottomContainer = new HBox(homeButton, predictSceneButton);
		bottomContainer.setAlignment(Pos.BOTTOM_RIGHT);
		bottomContainer.setSpacing(70);
		bottomContainer.setPadding(new Insets(10, 10, 10, 10));

		VBox weatherVBox = new VBox(locationArea, degreeArea, shortDescription, windArea);
		weatherVBox.setAlignment(Pos.CENTER);
		weatherVBox.setSpacing(20);

		// Stack everything together
		StackPane weatherApp = new StackPane();
		weatherApp.getChildren().addAll(weatherWallpaperViewer, weatherVBox, bottomContainer);

		// Create and show scene
		weatherAppScene = new Scene(weatherApp, 374, 810);


// PREDICT WEATHER IN NEXT 3 DAYS PAGE

		FileInputStream wallpaperPredictionPage = new FileInputStream("Background/Summer/Home - Summer dawn.jpg");
		Image wallpaperPrediction = new Image(wallpaperPredictionPage);
		ImageView predictionPageView = new ImageView(wallpaperPrediction);
		predictionPageView.setFitWidth(374);
		predictionPageView.setFitHeight(810);

		// Text Fields
		StackPane day1 = createBlurredTextField(forecast.get(1).name, "left",23, 210, 72);
		tempDay1 = createTextField(forecast.get(1).temperature + "°" + forecast.get(1).temperatureUnit, "left",18, 130, 25);
		probDay1 = createTextField(String.valueOf(forecast.get(1).probabilityOfPrecipitation.value)+"%", "left",18, 130, 25);

		StackPane day1Night = createBlurredTextField(forecast.get(2).name, "left",23, 210, 72);
		tempDay1Night = createTextField(forecast.get(2).temperature + "°" + forecast.get(2).temperatureUnit, "left",18, 130, 25);
		probDay1Night = createTextField(String.valueOf(forecast.get(2).probabilityOfPrecipitation.value)+"%","left", 18, 130, 25);

		StackPane day2 = createBlurredTextField(forecast.get(3).name, "left",23, 210, 72);
		tempDay2 = createTextField(forecast.get(3).temperature + "°" + forecast.get(3).temperatureUnit, "left",18, 130, 25);
		probDay2 = createTextField(String.valueOf(forecast.get(3).probabilityOfPrecipitation.value)+"%", "left",18, 130, 25);

		StackPane day2Night = createBlurredTextField(forecast.get(4).name, "left",23, 210, 72);
		tempDay2Night = createTextField(forecast.get(4).temperature + "°" + forecast.get(4).temperatureUnit, "left",18, 130, 25);
		probDay2Night = createTextField(String.valueOf(forecast.get(4).probabilityOfPrecipitation.value)+"%", "left",18, 130, 25);

		StackPane day3 = createBlurredTextField(forecast.get(5).name, "left",23, 210, 72);
		tempDay3 = createTextField(forecast.get(5).temperature + "°" + forecast.get(5).temperatureUnit, "left",18, 130, 25);
		probDay3 = createTextField(String.valueOf(forecast.get(5).probabilityOfPrecipitation.value)+"%", "left",18, 130, 25);

		StackPane day3Night = createBlurredTextField(forecast.get(6).name, "left",23, 210, 72);
		tempDay3Night = createTextField(forecast.get(6).temperature + "°" + forecast.get(6).temperatureUnit, "left",18, 130, 25);
		probDay3Night = createTextField(String.valueOf(forecast.get(6).probabilityOfPrecipitation.value)+"%", "left",18, 130, 25);

		// Buttons
		changeBG = createIconButton(changeBGIcon);
		changeBG.setOnAction(event -> {
			String newBackgroundPath = swapBackGround();
			Image newBackground = null;
			try {
				newBackground = new Image(new FileInputStream(newBackgroundPath));
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
			predictionPageView.setImage(newBackground);
		});
		home3DButton = createIconButton(home3DIcon);
		home3DButton.setOnAction(event -> {
			primaryStage.setScene(weatherAppScene);
		});
		windSwapButton = createIconButton(windIcon);
		windSwapButton.setOnAction(event -> {
			if (!isWindScene) {
				// Switch to wind mode
				tempDay1.setText(forecast.get(1).windSpeed);
				probDay1.setText(forecast.get(1).windDirection);

				tempDay1Night.setText(forecast.get(2).windSpeed);
				probDay1Night.setText(forecast.get(2).windDirection);

				tempDay2.setText(forecast.get(3).windSpeed);
				probDay2.setText(forecast.get(3).windDirection);

				tempDay2Night.setText(forecast.get(4).windSpeed);
				probDay2Night.setText(forecast.get(4).windDirection);

				tempDay3.setText(forecast.get(5).windSpeed);
				probDay3.setText(forecast.get(5).windDirection);

				tempDay3Night.setText(forecast.get(6).windSpeed);
				probDay3Night.setText(forecast.get(6).windDirection);

				// Update icons for tempDay1 and probDay1
				vDay1.getChildren().setAll(createTextFieldWithIcon(tempDay1, windIcon),createTextFieldWithIcon(probDay1, directionIcon));
				vDay1Night.getChildren().setAll(createTextFieldWithIcon(tempDay1Night, windIcon),createTextFieldWithIcon(probDay1Night, directionIcon));
				vDay2.getChildren().setAll(createTextFieldWithIcon(tempDay2, windIcon),createTextFieldWithIcon(probDay2, directionIcon));
				vDay2Night.getChildren().setAll(createTextFieldWithIcon(tempDay2Night, windIcon),createTextFieldWithIcon(probDay2Night, directionIcon));
				vDay3.getChildren().setAll(createTextFieldWithIcon(tempDay3, windIcon),createTextFieldWithIcon(probDay3, directionIcon));
				vDay3Night.getChildren().setAll(createTextFieldWithIcon(tempDay3Night, windIcon),createTextFieldWithIcon(probDay3Night, directionIcon));

				// Update the button icon
				Image icon = new Image(weatherIcon);
				ImageView iconView = new ImageView(icon);
				iconView.setFitHeight(48);
				iconView.setFitWidth(48);
				windSwapButton.setGraphic(iconView);
			} else {
				// Switch back to normal mode
				unitToC=!unitToC;
				changeUnit();
				probDay1.setText(forecast.get(1).probabilityOfPrecipitation.value + "%");
				probDay1Night.setText(forecast.get(2).probabilityOfPrecipitation.value + "%");
				probDay2.setText(forecast.get(3).probabilityOfPrecipitation.value + "%");
				probDay2Night.setText(forecast.get(4).probabilityOfPrecipitation.value + "%");
				probDay3.setText(forecast.get(5).probabilityOfPrecipitation.value + "%");
				probDay3Night.setText(forecast.get(6).probabilityOfPrecipitation.value + "%");

				// Update icons for all fields
				tempIcon = iconBasedOnShortDesc(forecast.get(1).shortForecast);
				vDay1.getChildren().setAll(createTextFieldWithIcon(tempDay1, tempIcon), createTextFieldWithIcon(probDay1, umbrellaIcon));
				tempIcon = iconBasedOnShortDesc(forecast.get(2).shortForecast);
				vDay1Night.getChildren().setAll(createTextFieldWithIcon(tempDay1Night, tempIcon), createTextFieldWithIcon(probDay1Night, umbrellaIcon));
				tempIcon = iconBasedOnShortDesc(forecast.get(3).shortForecast);
				vDay2.getChildren().setAll(createTextFieldWithIcon(tempDay2, tempIcon), createTextFieldWithIcon(probDay2, umbrellaIcon));
				tempIcon = iconBasedOnShortDesc(forecast.get(4).shortForecast);
				vDay2Night.getChildren().setAll(createTextFieldWithIcon(tempDay2Night, tempIcon), createTextFieldWithIcon(probDay2Night, umbrellaIcon));
				tempIcon = iconBasedOnShortDesc(forecast.get(5).shortForecast);
				vDay3.getChildren().setAll(createTextFieldWithIcon(tempDay3, tempIcon), createTextFieldWithIcon(probDay3, umbrellaIcon));
				tempIcon = iconBasedOnShortDesc(forecast.get(6).shortForecast);
				vDay3Night.getChildren().setAll(createTextFieldWithIcon(tempDay3Night, tempIcon), createTextFieldWithIcon(probDay3Night, umbrellaIcon));

				// Update the button icon
				Image icon = new Image(windIcon);
				ImageView iconView = new ImageView(icon);
				iconView.setFitHeight(48);
				iconView.setFitWidth(48);
				windSwapButton.setGraphic(iconView);
			}
			isWindScene = !isWindScene;
		});


		// Layout
		tempIcon = iconBasedOnShortDesc(forecast.get(1).shortForecast);
		vDay1 = new VBox(createTextFieldWithIcon(tempDay1,tempIcon), createTextFieldWithIcon(probDay1, umbrellaIcon));
		hDay1 = new HBox(10,day1, vDay1);

		tempIcon = iconBasedOnShortDesc(forecast.get(2).shortForecast);
		vDay1Night = new VBox(createTextFieldWithIcon(tempDay1Night,tempIcon), createTextFieldWithIcon(probDay1Night, umbrellaIcon));
		hDay1Night = new HBox(10,day1Night, vDay1Night);

		tempIcon = iconBasedOnShortDesc(forecast.get(3).shortForecast);
		vDay2 = new VBox(createTextFieldWithIcon(tempDay2,tempIcon), createTextFieldWithIcon(probDay2, umbrellaIcon));
		hDay2 = new HBox(10,day2, vDay2);

		tempIcon = iconBasedOnShortDesc(forecast.get(4).shortForecast);
		vDay2Night = new VBox(createTextFieldWithIcon(tempDay2Night,tempIcon), createTextFieldWithIcon(probDay2Night, umbrellaIcon));
		hDay2Night = new HBox(10,day2Night, vDay2Night);

		tempIcon = iconBasedOnShortDesc(forecast.get(5).shortForecast);
		vDay3 = new VBox(createTextFieldWithIcon(tempDay3,tempIcon), createTextFieldWithIcon(probDay3, umbrellaIcon));
		hDay3 = new HBox(10,day3, vDay3);

		tempIcon = iconBasedOnShortDesc(forecast.get(6).shortForecast);
		vDay3Night = new VBox(createTextFieldWithIcon(tempDay3Night,tempIcon), createTextFieldWithIcon(probDay3Night, umbrellaIcon));
		hDay3Night = new HBox(10,day3Night, vDay3Night);

		vPredict = new VBox(10, hDay1, hDay1Night, hDay2, hDay2Night, hDay3, hDay3Night);
		vPredict.setStyle("-fx-alignment: center;");
		hInteraction = new HBox(90, changeBG, home3DButton, windSwapButton);


		VBox layoutPrediction = new VBox(210, vPredict, hInteraction);
		Group rootPredict = new Group(predictionPageView, layoutPrediction);

		weather3DPage = new Scene(rootPredict, 374, 810);

// END PREDICT WEATHER IN NEXT 3 DAYS PAGE

		// Create and show scene
		homeScene = new Scene(fullscreen, 374, 810);
		primaryStage.setScene(homeScene);
		primaryStage.show();

		Button focusButton = new Button();
		focusButton.setVisible(false);
		fullscreen.getChildren().add(focusButton);
		focusButton.requestFocus();
	}
	private void updateDateTime(){
		now = new Date();
		date.setText(currDate.format(now));
		time.setText(currTime.format(now));
	}
	private void updateWeatherOnLocation(){
		unitToC=!unitToC;
		changeUnit();
		probDay1.setText(String.valueOf(forecast.get(1).probabilityOfPrecipitation.value)+"%");
		probDay1Night.setText(String.valueOf(forecast.get(2).probabilityOfPrecipitation.value)+"%");
		probDay2.setText(String.valueOf(forecast.get(3).probabilityOfPrecipitation.value)+"%");
		probDay2Night.setText(String.valueOf(forecast.get(4).probabilityOfPrecipitation.value)+"%");
		probDay3.setText(String.valueOf(forecast.get(5).probabilityOfPrecipitation.value)+"%");
		probDay3Night.setText(String.valueOf(forecast.get(6).probabilityOfPrecipitation.value)+"%");
		mainWeatherLocation.setText(gridInfo.city+", "+ gridInfo.state);
		shortDescription.setText(forecast.get(0).shortForecast);
		windDescription.setText(forecast.get(0).windSpeed);

	}

	private String swapBackGround(){
		String[] links={
				"Background/Autumn/Home - Autumn dawn.jpg",
				"Background/Autumn/Home - Autumn day.jpg",
				"Background/Autumn/Home - Autumn rain.jpg",
				"Background/Autumn/Home - Autumn sunset.jpg",

				"Background/Spring/Home - Spring dawn.jpg",
				"Background/Spring/Home - Spring day.jpg",
				"Background/Spring/Home - Spring rain.jpg",
				"Background/Spring/Home - Spring sunset.jpg",

				"Background/Summer/Home - Summer dawn.jpg",
				"Background/Summer/Home - Summer day.jpg",
				"Background/Summer/Home - Summer rain.jpg",
				"Background/Summer/Home - Summer sunset.jpg",

				"Background/Winter/Home - Winter dawn.jpg",
				"Background/Winter/Home - Winter day.jpg",
				"Background/Winter/Home - Winter rain.jpg",
				"Background/Winter/Home - Winter sunset.jpg",
		};
		if (swapBGcount == links.length){
			swapBGcount = 0;
		}
		else{
			swapBGcount+=1;
		}
		return links[swapBGcount];
	}

	private HBox createTextFieldWithIcon(TextField probDay, String link) {
		// Load the icon
		ImageView Icon = new ImageView(new Image(link));
		Icon.setFitHeight(25); // Adjust size as needed
		Icon.setFitWidth(25);
		// Create an HBox to contain the icon and text field
		HBox hBox = new HBox(Icon, probDay); // 5px spacing between icon and text field
		hBox.setAlignment(Pos.CENTER_LEFT); // Align properly

		return hBox;
	}
	private TextField createTextField(String text, String alignment, int fontSize, double width, double height) {
		TextField textField = new TextField(text);
		textField.setEditable(false);
		textField.setPrefSize(width, height);

		// Set the base styles
		textField.setStyle("-fx-font-size: " + fontSize + "px; " +
				"-fx-background-color: transparent; " +
				"-fx-control-inner-background: transparent; " +
				"-fx-border-color: transparent; " +
				"-fx-text-fill: black;");

		// Apply alignment based on parameter
		switch (alignment.toLowerCase()) {
			case "center":
				textField.setStyle(textField.getStyle() + "-fx-alignment: center;");
				break;
			case "right":
				textField.setStyle(textField.getStyle() + "-fx-alignment: center-right;");
				break;
			default: // Default to left
				textField.setStyle(textField.getStyle() + "-fx-alignment: center-left;");
				break;
		}
		textField.setEffect(dropShadow);
		return textField;
	}
	private StackPane createBlurredTextField(String text,String alignment, int fontSize, double width, double height) {
		Region blurredBackground = new Region();
		blurredBackground.setPrefSize(width, height);
		//blurredBackground.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5);"); // Semi-transparent white
		blurredBackground.setStyle("-fx-background-color: transparent;");
		blurredBackground.setEffect(new GaussianBlur(80)); // Apply blur effect

		// TextField with NO transparency (let the background handle blur)
		TextField textField = new TextField(text);
		textField.setEditable(false);
		textField.setPrefSize(width, height);
		textField.setStyle("-fx-font-size: " + fontSize + "px; " +
				"-fx-background-color: transparent; " + // Background is handled by blur layer
				"-fx-border-color: transparent; " +
				"-fx-text-fill: black;");

		// Apply text alignment dynamically
		switch (alignment.toLowerCase()) {
			case "center":
				textField.setStyle(textField.getStyle() + "-fx-alignment: center;");
				break;
			case "right":
				textField.setStyle(textField.getStyle() + "-fx-alignment: center-right;");
				break;
			default: // Default to left
				textField.setStyle(textField.getStyle() + "-fx-alignment: center-left;");
				break;
		}

		// StackPane to overlay the blurred background and TextField
		StackPane stack = new StackPane(blurredBackground, textField);
		stack.setPrefSize(width, height);
		stack.setEffect(dropShadow);
		return stack;
	}
	private Button createIconButton(String iconUrl) {
		Button button = new Button();
		Image icon = new Image(iconUrl);
		ImageView iconView = new ImageView(icon);
		iconView.setFitHeight(48);
		iconView.setFitWidth(48);
		button.setGraphic(iconView);
		button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		return button;
	}
	private double formularToC(int temperatureF){
		return ((temperatureF - 32) * (5.0 / 9.0));
	}
	private void changeUnit() {
		if (unitToC) {
			mainWeatherDegree.setText(String.valueOf(Math.round(formularToC(forecast.get(0).temperature)))+"°C");
			tempDay1.setText(String.valueOf(Math.round(formularToC(forecast.get(1).temperature)))+"°C");
			tempDay1Night.setText(String.valueOf(Math.round(formularToC(forecast.get(2).temperature)))+"°C");
			tempDay2.setText(String.valueOf(Math.round(formularToC(forecast.get(3).temperature)))+"°C");
			tempDay2Night.setText(String.valueOf(Math.round(formularToC(forecast.get(4).temperature)))+"°C");
			tempDay3.setText(String.valueOf(Math.round(formularToC(forecast.get(5).temperature)))+"°C");
			tempDay3Night.setText(String.valueOf(Math.round(formularToC(forecast.get(6).temperature)))+"°C");
		} else {
			mainWeatherDegree.setText(forecast.getFirst().temperature + "°F");
			tempDay1.setText(forecast.get(1).temperature + "°F");
			tempDay1Night.setText(forecast.get(2).temperature + "°F");
			tempDay2.setText(forecast.get(3).temperature + "°F");
			tempDay2Night.setText(forecast.get(4).temperature + "°F");
			tempDay3.setText(forecast.get(5).temperature + "°F");
			tempDay3Night.setText(forecast.get(6).temperature + "°F");

		}
		unitToC =!unitToC;
	}

	private String iconBasedOnShortDesc(String shortDesc) {
		if (shortDesc.contains("Sunny")) {
			return "https://img.icons8.com/?size=100&id=67607&format=png&color=000000";
		} else if (shortDesc.contains("Rainy")) {
			return "https://img.icons8.com/?size=100&id=67594&format=png&color=000000";
		} else if (shortDesc.contains("Cloudy")) {
			return "https://img.icons8.com/?size=100&id=67538&format=png&color=000000";
		} else if (shortDesc.contains("Clear")) {
			return "https://img.icons8.com/?size=100&id=67614&format=png&color=000000";
		} else if (shortDesc.contains("Shower")) {
			return "https://img.icons8.com/?size=100&id=51451&format=png&color=000000";
		} else if (shortDesc.contains("Thunderstorm")) {
			return "https://img.icons8.com/?size=100&id=67614&format=png&color=000000";
		} else if (shortDesc.contains("Snow")) {
			return "https://img.icons8.com/?size=100&id=qp5AWjqoNOSD&format=png&color=000000";
		} else {
			return "https://img.icons8.com/?size=100&id=67538&format=png&color=000000";
		}
	}
	private String formatDecimal(double value) {
		DecimalFormat df = new DecimalFormat("#.####");
		return df.format(value);
	}

}