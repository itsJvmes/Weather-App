import javafx.application.Application;

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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import weather.Period;
import weather.WeatherAPI;


import java.io.FileInputStream;
import java.util.ArrayList;

public class JavaFX extends Application {
	// Home Page
	Button predictionButton;
	TextField location, temperature, date, windText,windSpeed,shortDescription;
	TextArea  description;
	HBox hHome1;
	VBox vHome1,vHomeWind, vHomeFinal;
	Scene Home;
	// Weather in next 4 days page
	Button homeButton;
	TextArea weather1, weather2,weather3,weather4;
	HBox hWeather;
	Scene weather4DPage;

	public static void main(String[] args) {
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Weather App");
//		int temp = WeatherAPI.getTodaysTemperature(77,70);
		ArrayList<Period> forecast = WeatherAPI.getForecast("LOT",77,70);
		if (forecast == null){
			throw new RuntimeException("Forecast did not load");
		}

		FileInputStream inputStream = new FileInputStream("Background/Autumn/Home - Autumn day.jpg");

		Image image = new Image(inputStream);

		ImageView imageView = new ImageView(image);

		imageView.setX(0);
		imageView.setY(0);

		imageView.setFitHeight(540);
		imageView.setFitWidth(960);

		ImageView locationIcon = new ImageView("https://64.media.tumblr.com/b94b27ab66ec7ea719061e0a48b16f56/tumblr_ock4mcbTIi1r4s4c0o1_400.gif");
		locationIcon.setFitHeight(40);
		locationIcon.setFitWidth(40);
		location = new TextField("Chicago, IL");
		DropShadow dropShadow = new DropShadow();
		location.setEffect(dropShadow);
		location.setPrefSize(215,58);
		location.setMinSize(215, 58);
		location.setMaxSize(215, 58);
		location.setEditable(false);
		location.setStyle("-fx-alignment: left;-fx-font-size: 30;-fx-background-color: transparent; -fx-border-color: transparent;");
		HBox locationHBox = new HBox(locationIcon,location);
		locationHBox.setAlignment(Pos.CENTER);


		//temperature= new TextArea();
		temperature = new TextField();
		temperature.setText(String.valueOf(forecast.getFirst().temperature) + "°" + forecast.getFirst().temperatureUnit);
		temperature.setPrefSize(200, 78);
		temperature.setMinSize(200, 78);
		temperature.setMaxSize(200, 78);
		//temperature.setWrapText(true);
		temperature.setEditable(false);
		//temperature.setStyle("-fx-font-size: 40; -fx-background-color: transparent; -fx-control-inner-background: transparent; -fx-border-color: transparent; -fx-text-fill: black;");
		temperature.setStyle("-fx-alignment: center;-fx-font-size:35;-fx-background-color: transparent; -fx-border-color: transparent;");



		date = new TextField();
		date.setText(forecast.getFirst().name);
		date.setPrefSize(196,48);
		date.setMaxSize(196,48);
		date.setMinSize(196,48);
		date.setEditable(false);
		date.setStyle("-fx-alignment: center;-fx-font-size: 25; -fx-background-color: transparent; -fx-control-inner-background: transparent; -fx-border-color: transparent; -fx-text-fill: black;");

		shortDescription = new TextField();
		shortDescription.setPrefSize(892,42);
		shortDescription.setMaxSize(892,42);
		shortDescription.setMinSize(892,42);
		shortDescription.setEditable(false);
		shortDescription.setStyle("-fx-alignment: center;-fx-font-size: 25; -fx-background-color: transparent; -fx-control-inner-background: transparent; -fx-border-color: transparent; -fx-text-fill: black;");
		shortDescription.setText(forecast.getFirst().shortForecast);

		description = new TextArea("Description");
		description.setPrefSize(892,42);
		description.setEditable(false);
		description.setStyle("-fx-font-size: 20; -fx-background-color: black; -fx-control-inner-background: transparent; -fx-border-color: transparent; -fx-text-fill: black;");
		description.setWrapText(true);
		description.setText(forecast.getFirst().detailedForecast);

		windText = new TextField();
		windText.setText("Wind Speed");
		windText.setPrefSize(160,40);
		windText.setEditable(false);
		windText.setStyle("-fx-font-size: 20; -fx-background-color: transparent; -fx-control-inner-background: transparent; -fx-border-color: transparent; -fx-text-fill: black;");

		windSpeed = new TextField();
		windSpeed.setText(forecast.getFirst().windSpeed);
		windSpeed.setPrefSize(160,40);
		windSpeed.setEditable(false);
		windSpeed.setStyle("-fx-font-size: 20; -fx-background-color: transparent; -fx-control-inner-background: transparent; -fx-border-color: transparent; -fx-text-fill: black;");

		Image predictionButtonIcon = new Image("https://img.icons8.com/?size=100&id=10053&format=png&color=000000");
		ImageView predictionIcon = new ImageView(predictionButtonIcon);
		predictionIcon.setFitHeight(50);
		predictionIcon.setFitWidth(50);
		predictionButton = new Button();
		predictionButton.setGraphic(predictionIcon);
		predictionButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		predictionButton.setOnAction(e->{primaryStage.setScene(weather4DPage);});





		vHome1 = new VBox(1, locationHBox,temperature,date, shortDescription);
		vHome1.setAlignment(Pos.CENTER);
		vHomeWind = new VBox(1, windText,windSpeed);
		hHome1 = new HBox(300,vHomeWind, predictionButton);
		hHome1.setAlignment(Pos.CENTER);
		vHomeFinal = new VBox(10,vHome1,hHome1);
		vHomeFinal.setAlignment(Pos.CENTER);


		Group root = new Group(imageView,vHomeFinal);


		Home = new Scene(root, 960, 540);


		// Weather in next 4 days page
		weather1 =new TextArea("Weather 1");
		weather1.setEditable(false);
		weather1.setPrefSize(145,400);

		weather2 =new TextArea("Weather 2");
		weather2.setEditable(false);
		weather2.setPrefSize(145,400);

		weather3 =new TextArea("Weather 3");
		weather3.setEditable(false);
		weather3.setPrefSize(145,400);

		weather4 =new TextArea("Weather 4");
		weather4.setEditable(false);
		weather4.setPrefSize(145,400);

		Image homeButtonIcon = new Image("https://img.icons8.com/?size=100&id=86527&format=png&color=000000");
		ImageView homeIconView = new ImageView(homeButtonIcon);
		homeIconView.setFitHeight(48);
		homeIconView.setFitWidth(48);
		homeButton = new Button("");
		homeButton.setGraphic(homeIconView);
		homeButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		homeButton.setOnAction(e -> {
			primaryStage.setScene(Home);
		});

		hWeather = new HBox(2,homeButton, weather1, weather2, weather3, weather4);
		BorderPane p1=new BorderPane();
		p1.setCenter(hWeather);
		p1.setStyle("-fx-background-color: gray;");
		weather4DPage =new Scene(p1,960,540);
		primaryStage.setScene(Home);
		primaryStage.show();
	}

}
