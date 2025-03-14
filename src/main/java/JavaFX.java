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


import java.awt.*;
import java.util.ArrayList;

public class JavaFX extends Application {
	// Home Page
	TextField Location;
	TextArea temperature,description, Clock, detailOnDay, tempPerHour, Wind, UV, AQI_index, Prediction;
	HBox hHome1,hHome2,hHome3;
	VBox vHome1,vHome2, vHomeFinal;
	Scene Home;
	// Weather in next 4 days page
	Button homeButton;
	TextArea Weather1, Weather2, Weather3, Weather4;
	HBox hWeather;
	Scene Weather4DPage;

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
		Location = new TextField("Location");
		Location.setPrefSize(160,40);
		Location.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");


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
		description.setText(forecast.get(0).detailedForecast);

		Clock = new TextArea("Time Clock");
		Clock.setPrefSize(160,160);
		Clock.setEditable(false);
		Clock.setStyle("-fx-background-color: transparent; -fx-control-inner-background: transparent; -fx-border-color: transparent; -fx-text-fill: black;");

		detailOnDay = new TextArea("More detail about weather during day");
		detailOnDay.setPrefSize(764,35);
		detailOnDay.setEditable(false);
		detailOnDay.setStyle("-fx-background-color: transparent; -fx-control-inner-background: transparent; -fx-border-color: transparent; -fx-text-fill: black;");

		tempPerHour =new TextArea("Display the temperature per hour");
		tempPerHour.setPrefSize(763,117);
		tempPerHour.setEditable(false);
		tempPerHour.setStyle("-fx-background-color: transparent; -fx-control-inner-background: transparent; -fx-border-color: transparent; -fx-text-fill: black;");

		Wind = new TextArea("Wind\nHi");
		Wind.setPrefSize(160,160);
		Wind.setEditable(false);
		Wind.setStyle("-fx-background-color: transparent; -fx-control-inner-background: transparent; -fx-border-color: transparent; -fx-text-fill: black;");

		UV =new TextArea("UV");
		UV.setPrefSize(160,160);
		UV.setEditable(false);
		UV.setStyle("-fx-background-color: transparent; -fx-control-inner-background: transparent; -fx-border-color: transparent; -fx-text-fill: black;");

		AQI_index=new TextArea("AQI_index");
		AQI_index.setPrefSize(160,160);
		AQI_index.setEditable(false);
		AQI_index.setStyle("-fx-background-color: transparent; -fx-control-inner-background: transparent; -fx-border-color: transparent; -fx-text-fill: black;");

		Prediction=new TextArea("Predict weather of next 4 days");
		Prediction.setPrefSize(160,160);
		Prediction.setEditable(false);
		Prediction.setStyle("-fx-background-color: transparent; -fx-control-inner-background: transparent; -fx-border-color: transparent; -fx-text-fill: black;");


		vHome1 = new VBox(2,Location,temperature);
		hHome1 =new HBox(20, vHome1,description);
		vHome2 = new VBox(3, detailOnDay,tempPerHour);
		hHome2 = new HBox(20,Clock,vHome2);
		hHome3 = new HBox(100,Wind,UV,AQI_index,Prediction);
		vHomeFinal = new VBox(3,hHome1,hHome2,hHome3);
		//Home =new Scene(vHomeFinal,960,540);
		BorderPane p=new BorderPane();
		p.setCenter(vHomeFinal);
		p.setStyle("-fx-background-color: gray;");
		Home  = new Scene(p,960,540);

		// Weather in next 4 days page
		Weather1=new TextArea("Weather 1");
		Weather1.setEditable(false);
		Weather1.setPrefSize(145,400);

		Weather2=new TextArea("Weather 2");
		Weather2.setEditable(false);
		Weather2.setPrefSize(145,400);

		Weather3=new TextArea("Weather 3");
		Weather3.setEditable(false);
		Weather3.setPrefSize(145,400);

		Weather4=new TextArea("Weather 4");
		Weather4.setEditable(false);
		Weather4.setPrefSize(145,400);

		Image homeButtonIcon = new Image("https://www.flaticon.com/free-icon/home-icon-silhouette_69524?term=home&page=1&position=9&origin=tag&related_id=69524");
		ImageView homeIconView = new ImageView(homeButtonIcon);
		homeIconView.setFitHeight(48);
		homeIconView.setFitWidth(48);
		homeButton = new Button("Home");
		homeButton.setGraphic(homeIconView);
		homeButton.setOnAction(e -> {
			primaryStage.setScene(Home);
		});
		Prediction.setOnMouseClicked(e->{primaryStage.setScene(Weather4DPage);});
		hWeather = new HBox(2,homeButton,Weather1,Weather2,Weather3,Weather4);
		BorderPane p1=new BorderPane();
		p1.setCenter(hWeather);
		p1.setStyle("-fx-background-color: gray;");
		Weather4DPage=new Scene(p1,960,540);
		primaryStage.setScene(Home);
		primaryStage.show();
	}

}
