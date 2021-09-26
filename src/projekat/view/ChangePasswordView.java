package projekat.view;

import java.io.File;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import projekat.controller.Context;
import projekat.model.User;
import projekat.serialization.Serializer;

public class ChangePasswordView extends Stage{
	private Label lblTop = new Label("Type new password:");
	private Label lblBot = new Label("Repeat password:");
	private PasswordField pfTop = new PasswordField();
	private PasswordField pfBot = new PasswordField();
	private Button btnConfirm = new Button("Confirm");
	private Button btnCancel = new Button("Cancel");
	private GridPane root = new GridPane();
	
	public ChangePasswordView() {
		root.setStyle("-fx-background-color:linear-gradient(to bottom, #3c8796, #325359)");
		init();
		initActions();
		setScene(new Scene(root));
	}
	private void init() {
		root.getStylesheets().add(getClass().getResource("loginstyle.css").toExternalForm());
		root.addRow(1,lblTop,pfTop);
		root.addRow(2,lblBot,pfBot);
		root.addRow(3,btnConfirm,btnCancel);
		root.setHgap(10);
		root.setVgap(10);
		root.setPadding(new Insets(10));
		btnConfirm.setDefaultButton(true);
	}
	private void initActions() {
		btnCancel.setOnAction(e -> {
			close();
		});
		btnConfirm.setOnAction(e -> {
			if(!pfTop.getText().equals(pfBot.getText()))
			{
				Alert alert = new Alert(AlertType.ERROR,"The passwords don't match",ButtonType.OK);
				alert.showAndWait();
			}
			else if(pfTop.getText().equals(Context.context.getLoggedUser().get().getPassword())) {
				Alert alert = new Alert(AlertType.ERROR,"The new password is the same as currently password",ButtonType.OK);
				alert.showAndWait();
			}
			else {
				Context.context.getLoggedUser().get().setPassword(pfTop.getText());
				Serializer serializer = new Serializer();
				try {
					List<User> users = (List<User>) serializer.deserializeUsers(new File("users.bin"));
					for(User u:users) {
						if(u.getUsername().equals(Context.context.getLoggedUser().get().getUsername())) {
							u.setPassword(pfTop.getText());
							serializer.serializeUsers(new File("users.bin"), users);
							System.out.println("Promenjena sifra");
							break;
						}
					}
					Alert alert = new Alert(AlertType.INFORMATION,"The password is changed successfully!");
					alert.showAndWait();
					close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
}
