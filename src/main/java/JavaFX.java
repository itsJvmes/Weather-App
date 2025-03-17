import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import weather.Period;
import weather.WeatherAPI;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class JavaFX extends Application {

	// Weather in next 4 days page
	int swapBGcount = 0;
	String changeBGIcon = "https://img.icons8.com/?size=100&id=89962&format=png&color=000000";
	String homeIcon = "https://img.icons8.com/?size=100&id=86527&format=png&color=000000";
	String tempIcon = "https://img.icons8.com/?size=100&id=648&format=png&color=000000";
	String rainIcon = "https://img.icons8.com/?size=100&id=656&format=png&color=000000";
	String windIcon = "https://img.icons8.com/?size=100&id=74197&format=png&color=000000";
	String weatherIcon  = "https://img.icons8.com/?size=100&id=Zb9EIGS9RBLh&format=png&color=000000";
	String directionIcon = "https://img.icons8.com/?size=100&id=9672&format=png&color=000000";
	Button homeButton, changeBG, windSwapButton;
	private boolean isWindScene = false;
	TextField tempDay1,probDay1,tempDay1Night,probDay1Night, tempDay2, probDay2,tempDay2Night,probDay2Night, tempDay3, probDay3,tempDay3Night, probDay3Night;
	HBox hDay1, hDay1Night, hDay2, hDay2Night, hDay3, hDay3Night, hInteraction;
	VBox vDay1, vDay1Night, vDay2, vDay2Night, vDay3, vDay3Night, vPredict;
	Scene weather3DPage;
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Weather App");
		ArrayList<Period> forecast = WeatherAPI.getForecast("LOT", 77, 70);
		if (forecast == null) {
			throw new RuntimeException("Forecast did not load");
		}

		FileInputStream wallpaperPredictionPage = new FileInputStream("Background/Summer/Home - Summer dawn.jpg");
		Image wallpaper = new Image(wallpaperPredictionPage);
		ImageView predictionPageView = new ImageView(wallpaper);
		predictionPageView.setFitWidth(374);
		predictionPageView.setFitHeight(810);


		// Text Fields
		StackPane day1 = createBlurredTextField(forecast.get(1).name, "center",23, 210, 72);
		tempDay1 = createTextField(forecast.get(1).temperature + "°" + forecast.get(1).temperatureUnit, "left",18, 130, 25);
		probDay1 = createTextField(String.valueOf(forecast.get(1).probabilityOfPrecipitation.value)+"%", "left",18, 130, 25);

		StackPane day1Night = createBlurredTextField(forecast.get(2).name, "center",23, 210, 72);
		tempDay1Night = createTextField(forecast.get(2).temperature + "°" + forecast.get(2).temperatureUnit, "left",18, 130, 25);
		probDay1Night = createTextField(String.valueOf(forecast.get(2).probabilityOfPrecipitation.value)+"%","left", 18, 130, 25);

		StackPane day2 = createBlurredTextField(forecast.get(3).name, "center",23, 210, 72);
		tempDay2 = createTextField(forecast.get(3).temperature + "°" + forecast.get(3).temperatureUnit, "left",18, 130, 25);
		probDay2 = createTextField(String.valueOf(forecast.get(3).probabilityOfPrecipitation.value)+"%", "left",18, 130, 25);

		StackPane day2Night = createBlurredTextField(forecast.get(4).name, "center",23, 210, 72);
		tempDay2Night = createTextField(forecast.get(4).temperature + "°" + forecast.get(4).temperatureUnit, "left",18, 130, 25);
		probDay2Night = createTextField(String.valueOf(forecast.get(4).probabilityOfPrecipitation.value)+"%", "left",18, 130, 25);

		StackPane day3 = createBlurredTextField(forecast.get(5).name, "center",23, 210, 72);
		tempDay3 = createTextField(forecast.get(5).temperature + "°" + forecast.get(5).temperatureUnit, "left",18, 130, 25);
		probDay3 = createTextField(String.valueOf(forecast.get(5).probabilityOfPrecipitation.value)+"%", "left",18, 130, 25);

		StackPane day3Night = createBlurredTextField(forecast.get(6).name, "center",23, 210, 72);
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
		homeButton = createIconButton(homeIcon);
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
				tempDay1.setText(forecast.get(1).temperature + "°" + forecast.get(2).temperatureUnit);
				probDay1.setText(forecast.get(1).probabilityOfPrecipitation.value + "%");

				tempDay1Night.setText(forecast.get(2).temperature + "°" + forecast.get(3).temperatureUnit);
				probDay1Night.setText(forecast.get(2).probabilityOfPrecipitation.value + "%");

				tempDay2.setText(forecast.get(3).temperature + "°" + forecast.get(4).temperatureUnit);
				probDay2.setText(forecast.get(3).probabilityOfPrecipitation.value + "%");

				tempDay2Night.setText(forecast.get(4).temperature + "°" + forecast.get(5).temperatureUnit);
				probDay2Night.setText(forecast.get(4).probabilityOfPrecipitation.value + "%");

				tempDay3.setText(forecast.get(5).temperature + "°" + forecast.get(6).temperatureUnit);
				probDay3.setText(forecast.get(5).probabilityOfPrecipitation.value + "%");

				tempDay3Night.setText(forecast.get(6).temperature + "°" + forecast.get(7).temperatureUnit);
				probDay3Night.setText(forecast.get(6).probabilityOfPrecipitation.value + "%");

				// Update icons for all fields
				vDay1.getChildren().setAll(createTextFieldWithIcon(tempDay1, tempIcon), createTextFieldWithIcon(probDay1, rainIcon));
				vDay1Night.getChildren().setAll(createTextFieldWithIcon(tempDay1Night, tempIcon), createTextFieldWithIcon(probDay1Night, rainIcon));
				vDay2.getChildren().setAll(createTextFieldWithIcon(tempDay2, tempIcon), createTextFieldWithIcon(probDay2, rainIcon));
				vDay2Night.getChildren().setAll(createTextFieldWithIcon(tempDay2Night, tempIcon), createTextFieldWithIcon(probDay2Night, rainIcon));
				vDay3.getChildren().setAll(createTextFieldWithIcon(tempDay3, tempIcon), createTextFieldWithIcon(probDay3, rainIcon));
				vDay3Night.getChildren().setAll(createTextFieldWithIcon(tempDay3Night, tempIcon), createTextFieldWithIcon(probDay3Night, rainIcon));

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
		vDay1 = new VBox(createTextFieldWithIcon(tempDay1,tempIcon), createTextFieldWithIcon(probDay1,rainIcon));
		hDay1 = new HBox(10,day1, vDay1);

		vDay1Night = new VBox(createTextFieldWithIcon(tempDay1Night,tempIcon), createTextFieldWithIcon(probDay1Night,rainIcon));
		hDay1Night = new HBox(10,day1Night, vDay1Night);

		vDay2 = new VBox(createTextFieldWithIcon(tempDay2,tempIcon), createTextFieldWithIcon(probDay2,rainIcon));
		hDay2 = new HBox(10,day2, vDay2);

		vDay2Night = new VBox(createTextFieldWithIcon(tempDay2Night,tempIcon), createTextFieldWithIcon(probDay2Night,rainIcon));
		hDay2Night = new HBox(10,day2Night, vDay2Night);

		vDay3 = new VBox(createTextFieldWithIcon(tempDay3,tempIcon), createTextFieldWithIcon(probDay3,rainIcon));
		hDay3 = new HBox(10,day3, vDay3);

		vDay3Night = new VBox(createTextFieldWithIcon(tempDay3Night,tempIcon), createTextFieldWithIcon(probDay3Night,rainIcon));
		hDay3Night = new HBox(10,day3Night, vDay3Night);

		vPredict = new VBox(10, hDay1, hDay1Night, hDay2, hDay2Night, hDay3, hDay3Night);
		vPredict.setStyle("-fx-alignment: center;");
		hInteraction = new HBox(90, changeBG, homeButton, windSwapButton);


		VBox layoutPrediction = new VBox(210, vPredict, hInteraction);
		Group rootPredict = new Group(predictionPageView, layoutPrediction);

		weather3DPage = new Scene(rootPredict, 374, 810);
		primaryStage.setScene(weather3DPage);
		primaryStage.show();
	}


	private String swapBackGround(){
		String[] links={
				"Background/Autumn/Home - Autumn dawn.jpg",
				"Background/Autumn/Home - Autumn day.jpg",
				"Background/Autumn/Home - Autumn rain.jpg",
				"Background/Autumn/Home - Autumn sunset.jpg",
				"Background/Autumn/Home - Autumn midnight.jpg",

				"Background/Spring/Home - Spring dawn.jpg",
				"Background/Spring/Home - Spring day.jpg",
				"Background/Spring/Home - Spring rain.jpg",
				"Background/Spring/Home - Spring sunset.jpg",
				"Background/Spring/Home - Spring midnight.jpg",

				"Background/Summer/Home - Summer dawn.jpg",
				"Background/Summer/Home - Summer day.jpg",
				"Background/Summer/Home - Summer rain.jpg",
				"Background/Summer/Home - Summer sunset.jpg",
				"Background/Summer/Home - Summer midnight.jpg",

				"Background/Winter/Home - Winter dawn.jpg",
				"Background/Winter/Home - Winter day.jpg",
				"Background/Winter/Home - Winter rain.jpg",
				"Background/Winter/Home - Winter sunset.jpg",
				"Background/Winter/Home - Winter midnight.jpg",
		};
		if (swapBGcount == links.length){
			swapBGcount = 0;
		}
		else{
			swapBGcount+=1;
		}
		int temp = swapBGcount;
		if ((temp+1)%5 ==0 ){
			tempDay1.setStyle("-fx-font-size: " + 18 + "px; " + "-fx-background-color: transparent; " + "-fx-control-inner-background: transparent; " + "-fx-border-color: transparent; " +	"-fx-text-fill: white;");
			probDay1.setStyle("-fx-font-size: " + 18 + "px; " + "-fx-background-color: transparent; " + "-fx-control-inner-background: transparent; " + "-fx-border-color: transparent; " +	"-fx-text-fill: white;");
			tempDay1Night.setStyle("-fx-font-size: " + 18 + "px; " + "-fx-background-color: transparent; " + "-fx-control-inner-background: transparent; " + "-fx-border-color: transparent; " +	"-fx-text-fill: white;");
			probDay1Night.setStyle("-fx-font-size: " + 18 + "px; " + "-fx-background-color: transparent; " + "-fx-control-inner-background: transparent; " + "-fx-border-color: transparent; " +	"-fx-text-fill: white;");
			tempDay2.setStyle("-fx-font-size: " + 18 + "px; " + "-fx-background-color: transparent; " + "-fx-control-inner-background: transparent; " + "-fx-border-color: transparent; " +	"-fx-text-fill: white;");
			probDay2.setStyle("-fx-font-size: " + 18 + "px; " + "-fx-background-color: transparent; " + "-fx-control-inner-background: transparent; " + "-fx-border-color: transparent; " +	"-fx-text-fill: white;");
			tempDay2Night.setStyle("-fx-font-size: " + 18 + "px; " + "-fx-background-color: transparent; " + "-fx-control-inner-background: transparent; " + "-fx-border-color: transparent; " +	"-fx-text-fill: white;");
			probDay2Night.setStyle("-fx-font-size: " + 18 + "px; " + "-fx-background-color: transparent; " + "-fx-control-inner-background: transparent; " + "-fx-border-color: transparent; " +	"-fx-text-fill: white;");
			tempDay3.setStyle("-fx-font-size: " + 18 + "px; " + "-fx-background-color: transparent; " + "-fx-control-inner-background: transparent; " + "-fx-border-color: transparent; " +	"-fx-text-fill: white;");
			probDay3.setStyle("-fx-font-size: " + 18 + "px; " + "-fx-background-color: transparent; " + "-fx-control-inner-background: transparent; " + "-fx-border-color: transparent; " +	"-fx-text-fill: white;");
			tempDay3Night.setStyle("-fx-font-size: " + 18 + "px; " + "-fx-background-color: transparent; " + "-fx-control-inner-background: transparent; " + "-fx-border-color: transparent; " +	"-fx-text-fill: white;");
			probDay3Night.setStyle("-fx-font-size: " + 18 + "px; " + "-fx-background-color: transparent; " + "-fx-control-inner-background: transparent; " + "-fx-border-color: transparent; " +	"-fx-text-fill: white;");

		}
		else{
			tempDay1.setStyle("-fx-font-size: " + 18 + "px; " + "-fx-background-color: transparent; " + "-fx-control-inner-background: transparent; " + "-fx-border-color: transparent; " +	"-fx-text-fill: black;");
			probDay1.setStyle("-fx-font-size: " + 18 + "px; " + "-fx-background-color: transparent; " + "-fx-control-inner-background: transparent; " + "-fx-border-color: transparent; " +	"-fx-text-fill: black;");
			tempDay1Night.setStyle("-fx-font-size: " + 18 + "px; " + "-fx-background-color: transparent; " + "-fx-control-inner-background: transparent; " + "-fx-border-color: transparent; " +	"-fx-text-fill: black;");
			probDay1Night.setStyle("-fx-font-size: " + 18 + "px; " + "-fx-background-color: transparent; " + "-fx-control-inner-background: transparent; " + "-fx-border-color: transparent; " +	"-fx-text-fill: black;");
			tempDay2.setStyle("-fx-font-size: " + 18 + "px; " + "-fx-background-color: transparent; " + "-fx-control-inner-background: transparent; " + "-fx-border-color: transparent; " +	"-fx-text-fill: black;");
			probDay2.setStyle("-fx-font-size: " + 18 + "px; " + "-fx-background-color: transparent; " + "-fx-control-inner-background: transparent; " + "-fx-border-color: transparent; " +	"-fx-text-fill: black;");
			tempDay2Night.setStyle("-fx-font-size: " + 18 + "px; " + "-fx-background-color: transparent; " + "-fx-control-inner-background: transparent; " + "-fx-border-color: transparent; " +	"-fx-text-fill: black;");
			probDay2Night.setStyle("-fx-font-size: " + 18 + "px; " + "-fx-background-color: transparent; " + "-fx-control-inner-background: transparent; " + "-fx-border-color: transparent; " +	"-fx-text-fill: black;");
			tempDay3.setStyle("-fx-font-size: " + 18 + "px; " + "-fx-background-color: transparent; " + "-fx-control-inner-background: transparent; " + "-fx-border-color: transparent; " +	"-fx-text-fill: black;");
			probDay3.setStyle("-fx-font-size: " + 18 + "px; " + "-fx-background-color: transparent; " + "-fx-control-inner-background: transparent; " + "-fx-border-color: transparent; " +	"-fx-text-fill: black;");
			tempDay3Night.setStyle("-fx-font-size: " + 18 + "px; " + "-fx-background-color: transparent; " + "-fx-control-inner-background: transparent; " + "-fx-border-color: transparent; " +	"-fx-text-fill: black;");
			probDay3Night.setStyle("-fx-font-size: " + 18 + "px; " + "-fx-background-color: transparent; " + "-fx-control-inner-background: transparent; " + "-fx-border-color: transparent; " +	"-fx-text-fill: black;");
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

		return textField;
	}

	private StackPane createBlurredTextField(String text,String alignment, int fontSize, double width, double height) {
		Region blurredBackground = new Region();
		blurredBackground.setPrefSize(width, height);
		blurredBackground.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5);"); // Semi-transparent white
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
}