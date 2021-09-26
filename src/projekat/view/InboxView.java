package projekat.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import projekat.controller.Context;
import projekat.model.Mail;
import projekat.model.User;
import projekat.serialization.Serializer;

public class InboxView extends VBox implements Runnable{
	private TextField tf = new TextField();
	private TableView<Mail> tv = new TableView<>();
	private List<Mail> messages = new ArrayList<>();
	private Serializer serializer = new Serializer();
	private ToggleButton btnLogout = new ToggleButton("Logout");
	private ToggleButton btnDelete = new ToggleButton("Delete");
	
	public InboxView() {
		Thread t = new Thread(this);
		t.start();
		getStylesheets().add(getClass().getResource("inboxstyle.css").toExternalForm());
		init();
		initActions();
	}
	private void init() {
		setSpacing(10);
		btnDelete.setDisable(true);
		TableColumn<Mail,User> sender = new TableColumn<>("From:");
		sender.setCellValueFactory(new PropertyValueFactory<>("sender"));
		TableColumn<Mail,String> subject = new TableColumn<>("Subject:");
		subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
		TableColumn<Mail,String> text = new TableColumn<>("Text:");
		text.setCellValueFactory(new PropertyValueFactory<>("shorttext"));
		tv.getColumns().addAll(sender,subject,text);
		tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tf.setPromptText("Find messages");
		
		HBox hbox = new HBox(155);
		HBox hboxbuttons = new HBox(10);
		hboxbuttons.getChildren().addAll(btnDelete,btnLogout);
		tf.setPrefWidth(350);
		hbox.getChildren().addAll(tf,hboxbuttons);
		getChildren().addAll(hbox,tv);
		tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tv.setPlaceholder(new Label("There are no messages"));
		
		ContextMenu contextMenu = new ContextMenu();
		MenuItem menuItemDelete = new MenuItem("Delete");
		MenuItem menuItemReply = new MenuItem("Reply");
		MenuItem menuItemForward = new MenuItem("Forward");
		contextMenu.getItems().addAll(menuItemDelete,menuItemReply,menuItemForward);
		menuItemDelete.setOnAction(e -> {
			Alert alert = new Alert(AlertType.CONFIRMATION,"Are you sure want to delete this message?",ButtonType.YES,ButtonType.NO);
			ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
			if(ButtonType.YES.equals(result)) {
				deleteMessage(tv.getSelectionModel().getSelectedItem());
				Alert alertDelete = new Alert(AlertType.INFORMATION,"Message successfully deleted!",ButtonType.OK);
				alertDelete.showAndWait();
			}
		});
		menuItemReply.setOnAction(e -> {
			ClientView client = (ClientView) Context.context.getActiveStage();
			client.getComposeView().replyToMessage(tv.getSelectionModel().getSelectedItem());
		});
		menuItemForward.setOnAction(e -> {
			ClientView client = (ClientView) Context.context.getActiveStage();
			client.getComposeView().forward(tv.getSelectionModel().getSelectedItem());
		});
		tv.setContextMenu(contextMenu);
	}
	private void initActions() {
		tf.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				try {
					messages = (List<Mail>) serializer.deserializeMessage(new File("messages.bin"));
					tv.getItems().clear();
					for(Mail m:messages) {
						if(m.getRecipient().getUsername().equals(Context.context.getLoggedUser().get().getUsername()))
						if(m.getText().toLowerCase().contains(tf.getText().toLowerCase()))
							tv.getItems().add(m);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		btnLogout.setOnAction(e -> {
			Context.context.getActiveStage().close();
			Context.context.setLoggedUser(null);
			Context.context.setActiveStage(Context.context.getPrimaryStage());
			Context.context.getActiveStage().show();
		});
		tv.setOnMouseClicked(e -> {
			if(e.getClickCount() == 2) {
				Mail m = tv.getSelectionModel().getSelectedItem();
				MessageView view = new MessageView(m,"FROM:");
				view.showAndWait();
			}
		});
		tv.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Mail>() {

			@Override
			public void changed(ObservableValue<? extends Mail> observable, Mail oldValue, Mail newValue) {
				// TODO Auto-generated method stub
				if(newValue == null) {
					btnDelete.setDisable(true);
				}
				else {
					btnDelete.setDisable(false);
				}
			}
		
		});
		btnDelete.setOnAction(e -> {
			deleteMessage(tv.getSelectionModel().getSelectedItem());
		});
	}
	private void deleteMessage(Mail m) {
		try {
			List<Mail> mails = (List<Mail>) serializer.deserializeMessage(new File("messages.bin"));
			mails.remove(m);
			serializer.serializeMessages(new File("messages.bin"), mails);
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			messages.clear();
			List<Mail> allMessages;
			try {
				allMessages = (List<Mail>) serializer.deserializeMessage(new File("messages.bin"));
				for(Mail m:allMessages) {
					if(m.getRecipient().getUsername().equals(Context.context.getLoggedUser().get().getUsername()))
						messages.add(m);
				}
				
				tv.getItems().clear();
				tv.getItems().addAll(messages);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
