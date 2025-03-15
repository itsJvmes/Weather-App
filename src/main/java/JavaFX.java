import javafx.application.Application;

import javafx.scene.Scene;
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


import java.util.ArrayList;

public class JavaFX extends Application {
	// Home Page
	TextField location;
	TextArea temperature,description, clock, detailOnDay, tempPerHour, wind, UV, aqiIndex, prediction;
	HBox hHome1,hHome2,hHome3;
	VBox vHome1,vHome2, vHomeFinal;
	Scene Home;
	// Weather in next 4 days page
	Button homeButton;
	TextArea weather1, weather2, weather3, weather4;
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
		location = new TextField("Location");
		location.setPrefSize(160,40);
		location.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");


		temperature= new TextArea("Temperature");
		temperature.setPrefSize(160,120);
		temperature.setEditable(false);
		//temperature.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		temperature.setStyle("-fx-background-color: transparent; -fx-control-inner-background: transparent; -fx-border-color: transparent; -fx-text-fill: black;");
		temperature.setText("Today's weather is: "+String.valueOf(forecast.get(0).temperature) + forecast.get(0).temperatureUnit );



		description = new TextArea("Description");
		description.setPrefSize(763,160);
		description.setEditable(false);
		description.setStyle("-fx-background-color: transparent; -fx-control-inner-background: transparent; -fx-border-color: transparent; -fx-text-fill: black;");
		description.setWrapText(true);
		description.setText(forecast.get(0).detailedForecast);


		clock = new TextArea("Time Clock");
		clock.setPrefSize(160,160);
		clock.setEditable(false);
		clock.setStyle("-fx-background-color: transparent; -fx-control-inner-background: transparent; -fx-border-color: transparent; -fx-text-fill: black;");
		clock.setWrapText(true);
		clock.setText("Today Wind " +forecast.get(0).shortForecast);

		detailOnDay = new TextArea("More detail about weather during day");
		detailOnDay.setPrefSize(764,35);
		detailOnDay.setEditable(false);
		detailOnDay.setStyle("-fx-background-color: transparent; -fx-control-inner-background: transparent; -fx-border-color: transparent; -fx-text-fill: black;");

		tempPerHour =new TextArea("Display the temperature per hour");
		tempPerHour.setPrefSize(763,117);
		tempPerHour.setEditable(false);
		tempPerHour.setStyle("-fx-background-color: transparent; -fx-control-inner-background: transparent; -fx-border-color: transparent; -fx-text-fill: black;");

		wind = new TextArea("Wind\nHi");
		wind.setPrefSize(160,160);
		wind.setEditable(false);
		wind.setStyle("-fx-background-color: transparent; -fx-control-inner-background: transparent; -fx-border-color: transparent; -fx-text-fill: black;");

		UV =new TextArea("UV");
		UV.setPrefSize(160,160);
		UV.setEditable(false);
		UV.setStyle("-fx-background-color: transparent; -fx-control-inner-background: transparent; -fx-border-color: transparent; -fx-text-fill: black;");

		aqiIndex =new TextArea("AQI_index");
		aqiIndex.setPrefSize(160,160);
		aqiIndex.setEditable(false);
		aqiIndex.setStyle("-fx-background-color: transparent; -fx-control-inner-background: transparent; -fx-border-color: transparent; -fx-text-fill: black;");

		prediction =new TextArea("Predict weather of next 4 days");
		prediction.setPrefSize(160,160);
		prediction.setEditable(false);
		prediction.setStyle("-fx-background-color: transparent; -fx-control-inner-background: transparent; -fx-border-color: transparent; -fx-text-fill: black;");


		vHome1 = new VBox(2, location,temperature);
		hHome1 =new HBox(20, vHome1,description);
		vHome2 = new VBox(3, detailOnDay,tempPerHour);
		hHome2 = new HBox(20, clock,vHome2);
		hHome3 = new HBox(100, wind,UV, aqiIndex, prediction);
		vHomeFinal = new VBox(3,hHome1,hHome2,hHome3);
		//Home =new Scene(vHomeFinal,960,540);
		BorderPane p=new BorderPane();
		p.setCenter(vHomeFinal);
		p.setStyle("-fx-background-color: gray;");
		Home  = new Scene(p,960,540);

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
		homeButton.setOnAction(e -> {
			primaryStage.setScene(Home);
		});
		prediction.setOnMouseClicked(e->{primaryStage.setScene(weather4DPage);});
		hWeather = new HBox(2,homeButton, weather1, weather2, weather3, weather4);
		BorderPane p1=new BorderPane();
		p1.setCenter(hWeather);
		p1.setStyle("-fx-background-color: gray;");
		weather4DPage =new Scene(p1,960,540);
		primaryStage.setScene(Home);
		primaryStage.show();
	}

}
