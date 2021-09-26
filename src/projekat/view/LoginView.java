package projekat.view;

import java.util.Optional;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import projekat.controller.Context;
import projekat.controller.Service;
import projekat.model.User;

public class LoginView extends GridPane{
	private Label lblUsername = new Label("Username:");
	private Label lblPassword = new Label("Password:");
	private TextField tfUsername = new TextField();
	private PasswordField tfPassword = new PasswordField();
	private Button btnLogin = new Button("Login");
	private Button btnRegister = new Button("Register");
	private Label lblReg = new Label("Login");
	
	public LoginView() throws Exception {
		this.setStyle("-fx-background-color:linear-gradient(to bottom, #3c8796, #325359)");
		getStylesheets().add(getClass().getResource("loginstyle.css").toExternalForm());
		lblReg.setStyle("-fx-font-family:\"Arial\"; -fx-font-size:20");
		this.tfUsername.requestFocus();
		addRow(0,lblReg);
		addRow(1,lblUsername,tfUsername);
		addRow(2,lblPassword,tfPassword);
		addRow(3,btnLogin,btnRegister);
		setVgap(10);
		setHgap(10);
		setPadding(new Insets(10));
		btnLogin.setDefaultButton(true);
		btnLogin.setOnAction(e -> {
			try {
				Optional<User> logged = Service.checkLogin(tfUsername.getText(), tfPassword.getText());
				if(logged.isPresent()) {
					Context.context.setLoggedUser(logged);
					ClientView cw = new ClientView();
					Context.context.getActiveStage().close();
					Context.context.setActiveStage(cw);
					Context.context.getActiveStage().show();
					cw.setOnCloseRequest(e2 -> {
						Platform.exit();
						System.exit(0);
					});
					tfUsername.clear();
					tfPassword.clear();
					tfUsername.requestFocus();
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		btnRegister.setOnAction(e -> {
			RegisterView view = new RegisterView();
			Context.context.getActiveStage().close();
			Context.context.setActiveStage(view);
			Context.context.getActiveStage().show();
		});
	}
}
