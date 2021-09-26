package projekat.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ClientView extends Stage{
	private ToggleButton tgInbox = new ToggleButton("Inbox  ");
	private ToggleButton tgCompose = new ToggleButton("Compose");
	private ToggleButton tgSent = new ToggleButton("Sent   ");
	private ToggleButton tgPassword = new ToggleButton("Change password");
	private ToggleGroup tg = new ToggleGroup();
	private Background defaultBackground = tgInbox.getBackground();
	private ObservableList<String> lista = FXCollections.observableArrayList();
	private BorderPane root = new BorderPane();
	private InboxView inboxView = new InboxView();
	private ComposeView composeView = new ComposeView();
	private SentView sentView = new SentView();
	
	public ClientView() {
		root.setStyle("-fx-background-color:#7694c4");
		init();
		initActions();
		setScene(new Scene(root,850,400));
		tgInbox.fire();
	}
	private void init() {
		//root.getStylesheets().add(getClass().getResource("clientstyle.css").toExternalForm());
		tg.getToggles().addAll(tgInbox,tgCompose,tgSent,tgPassword);
		VBox vbox = new VBox(5);
		vbox.getChildren().addAll(tgInbox,tgCompose,tgSent,tgPassword);
		tgInbox.setBackground(defaultBackground);
		tgSent.setBackground(defaultBackground);
		tgCompose.setBackground(defaultBackground);
		tgPassword.setBackground(defaultBackground);
		root.setPadding(new Insets(10));
		InputStream stream;
		try {
			stream = new FileInputStream("C:\\Users\\marko\\Desktop\\icon.jpg");
			Image image = new Image(stream);
			ImageView view = new ImageView(image);
			VBox vboxleft = new VBox(15);
			vboxleft.getChildren().addAll(view,vbox);
			root.setLeft(vboxleft);
			BorderPane.setMargin(vboxleft, new Insets(0, 30, 0, 0));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void klik(ToggleButton dugme) {
		dugme.hoverProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if(newValue) {
					dugme.setStyle("-fx-background-color:gray");
				}
				if(oldValue) {
					dugme.setStyle("-fx-background-color:none");
				}
			}
			
		});
	}
	private void initActions() {
		lista.addListener(new ListChangeListener<String>() {

			@Override
			public void onChanged(Change<? extends String> c) {
				// TODO Auto-generated method stub
				System.out.println("Detektovana promena na listi!");
			}
			
		});
		klik(tgInbox);
		klik(tgCompose);
		klik(tgSent);
		klik(tgPassword);
		tgInbox.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				root.setCenter(inboxView);
			}
			
		});
		tgCompose.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				try {
					root.setCenter(composeView);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		tgSent.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				root.setCenter(sentView);
			}
			
		});
		tgPassword.setOnAction(e -> {
			ChangePasswordView cpv = new ChangePasswordView();
			cpv.initModality(Modality.APPLICATION_MODAL);
			cpv.show();
		});
	}
	public ToggleButton getTgCompose() {
		return tgCompose;
	}
	public ComposeView getComposeView() {
		return composeView;
	}
	
	
}
