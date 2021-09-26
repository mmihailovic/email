package projekat.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import projekat.model.Mail;

public class MessageView extends Stage{
	private Label lblUser = new Label();
	private Label lblSubject = new Label("Subject");
	private Label lblText = new Label("Text");
	private TextField tfUser = new TextField();
	private TextField tfSubject = new TextField();
	private TextArea tfText = new TextArea();
	private GridPane root = new GridPane();
	private Mail m;
	public MessageView(Mail m,String tip) {
		this.lblUser.setText(tip);
		this.m = m;
		init();
		setScene(new Scene(root));
		root.setStyle("-fx-background-color:#7694c4");
	}
	
	private void init() {
		tfUser.setDisable(true);
		tfSubject.setDisable(true);
		tfText.setDisable(true);
		root.addRow(0, lblUser,tfUser);
		root.addRow(1, lblSubject,tfSubject);
		root.addRow(2, lblText,tfText);
		root.setPadding(new Insets(10));
		root.setHgap(10);
		root.setVgap(10);
		
		if(lblUser.getText().equals("FROM:")) {
			// inbox messages
			tfUser.setText(m.getSender().getUsername());
		}
		else {
			tfUser.setText(m.getRecipient().getUsername());
		}
		tfSubject.setText(m.getSubject());
		tfText.setText(m.getText());
	}

	public Label getLblUser() {
		return lblUser;
	}

	public void setLblUser(Label lblUser) {
		this.lblUser = lblUser;
	}
	
}
