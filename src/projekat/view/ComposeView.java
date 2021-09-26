package projekat.view;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;

import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import projekat.controller.Context;
import projekat.controller.Service;
import projekat.model.Mail;
import projekat.serialization.Serializer;

public class ComposeView extends GridPane {
	private Label lblTo = new Label("To:");
	private Label lblSubject = new Label("Subject:");
	private TextField tfTo = new TextField();
	private TextField tfSubject = new TextField();
	private TextArea tfText = new TextArea();
	private Button btnSend = new Button("Send");
	private ToggleButton btnLogout = new ToggleButton("Logout");
	
	public ComposeView() {
		this.getStylesheets().add(getClass().getResource("loginstyle.css").toExternalForm());
		HBox hbox = new HBox(193);
		hbox.getChildren().addAll(tfTo,btnLogout);
		addRow(1,lblTo,hbox);
		addRow(2,lblSubject,tfSubject);
		addRow(3,tfText);
		addRow(4,btnSend);
		setHgap(10);
		setVgap(10);
		GridPane.setColumnSpan(tfText, 2);
		tfText.setPromptText("Type text of message");
		tfTo.setPromptText("Split recipients with ,");
		btnSend.setOnAction(e -> {
			Service.sendMessage(tfText.getText(), Context.context.getLoggedUser().get(), tfTo.getText(),tfSubject.getText());
			tfText.clear();
			tfSubject.clear();
			tfTo.clear();
			Alert alert = new Alert(AlertType.INFORMATION,"Uspesno poslato!",ButtonType.OK);
			alert.showAndWait();
		});
		btnSend.setDefaultButton(true);
		btnLogout.setOnAction(e -> {
			Context.context.getActiveStage().close();
			Context.context.setLoggedUser(null);
			Context.context.setActiveStage(Context.context.getPrimaryStage());
			Context.context.getActiveStage().show();
		});
	}
	public void replyToMessage(Mail m) {
		ClientView c = (ClientView) Context.context.getActiveStage();
		c.getTgCompose().fire();
		this.tfTo.setText(m.getSender().getUsername());
		this.tfText.requestFocus();
		this.tfText.setText("");
	}
	public void forward(Mail m) {
		ClientView c = (ClientView) Context.context.getActiveStage();
		c.getTgCompose().fire();
		this.tfText.setText(m.getText());
		this.tfTo.requestFocus();
		this.tfTo.setText("");
	}
}
