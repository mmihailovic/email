package projekat;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import projekat.controller.Context;
import projekat.view.LoginView;

public class Main extends Application{
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		LoginView main = new LoginView();
		primaryStage.setScene(new Scene(main));
		Context.context.setActiveStage(primaryStage);
		Context.context.setPrimaryStage(primaryStage);
		primaryStage.setTitle("Email - Login");
		primaryStage.show();
		primaryStage.setOnCloseRequest(e -> {
			Platform.exit();
			System.exit(0);
		});
	}
}
