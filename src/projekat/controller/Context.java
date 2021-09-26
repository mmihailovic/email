package projekat.controller;

import java.util.Optional;

import javafx.stage.Stage;
import projekat.model.User;

public class Context {
	public static final Context context = new Context();
	private Optional<User> loggedUser;
	private Stage activeStage;
	private Stage primaryStage;
	
	private Context() {
		
	}
	public Optional<User> getLoggedUser() {
		return loggedUser;
	}
	public void setLoggedUser(Optional<User> loggedUser) {
		this.loggedUser = loggedUser;
	}
	public Stage getActiveStage() {
		return activeStage;
	}
	public void setActiveStage(Stage activeStage) {
		this.activeStage = activeStage;
	}
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
}
