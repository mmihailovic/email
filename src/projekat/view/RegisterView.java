package projekat.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import projekat.controller.Context;
import projekat.controller.Service;

public class RegisterView extends Stage{
	private Label lblUsername = new Label("Username");
	private Label lblPassword = new Label("Password");
	private TextField tfUsername = new TextField();
	private PasswordField tfPassword = new PasswordField();
	private Button btnRegister = new Button("Register");
	private Button btnCancel = new Button("Cancel");
	private GridPane root = new GridPane();
	private Label lblReg = new Label("Registration");
	
	public RegisterView() {
		root.getStylesheets().add(getClass().getResource("loginstyle.css").toExternalForm());
		init();
		initActions();
		setScene(new Scene(root));
		setTitle("Email - Registration");
	}
	private void init() {
		lblReg.setStyle("-fx-font-family:\"Arial\"; -fx-font-size:20");
		root.setStyle("-fx-background-color:linear-gradient(to bottom, #3c8796, #325359)");
		root.addRow(0, lblReg);
		root.addRow(1, lblUsername, tfUsername);
		root.addRow(2, lblPassword, tfPassword);
		root.addRow(3, btnRegister, btnCancel);
		root.setVgap(10);
		root.setHgap(10);
		root.setPadding(new Insets(10));
		btnRegister.setDefaultButton(true);
		tfUsername.requestFocus();
	}
	private void initActions() {
		btnRegister.setOnAction(e -> {
			String username = tfUsername.getText();
			String password = tfPassword.getText();
			try {
				boolean registration = Service.register(username, password);
				if(registration) {
					Alert alert = new Alert(AlertType.INFORMATION,"Registration successfully!",ButtonType.OK);
					alert.showAndWait();
					Context.context.getActiveStage().close();
					Context.context.setActiveStage(Context.context.getPrimaryStage());
					Context.context.getActiveStage().show();
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		btnCancel.setOnAction(e -> {
			Context.context.getActiveStage().close();
			Context.context.setActiveStage(Context.context.getPrimaryStage());
			Context.context.getActiveStage().show();
		});
	}
}
