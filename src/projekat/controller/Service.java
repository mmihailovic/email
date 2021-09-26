package projekat.controller;

import java.io.File;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import projekat.model.User;
import projekat.serialization.Serializer;
import projekat.model.Mail;

public class Service {
	public static Optional<User> checkLogin(String username,String password) throws Exception {
		boolean pronadjen = false;
		Serializer serializer = new Serializer();
		List<User>users = (List<User>) serializer.deserializeUsers(new File("users.bin"));
		for(User u:users) {
			if(u.getUsername().equals(username)) {
				if(u.getPassword().equals(password)) {
					pronadjen = true;
					return Optional.of(u);
				}
				else {
					Alert alert = new Alert(AlertType.ERROR,"Wrong password!",ButtonType.OK);
					alert.showAndWait();
					pronadjen = true;
					return Optional.empty();
				}
			}
		}
		if(!pronadjen) {
			Alert alert = new Alert(AlertType.ERROR,"This username is unavailable",ButtonType.OK);
			alert.showAndWait();
			return Optional.empty();
		}
		return Optional.empty();
	}
	public static boolean register(String username,String password) throws Exception {
		Serializer serializer = new Serializer();
		List<User> users = (List<User>) serializer.deserializeUsers(new File("users.bin"));
		for(User u:users) {
			if(u.getUsername().equals(username))
			{
				Alert alert = new Alert(AlertType.ERROR,"Username already existed!",ButtonType.OK);
				alert.showAndWait();
				return false;
			}
		}
		User u = new User(username, password);
		users.add(u);
		serializer.serializeUsers(new File("users.bin"), users);
		return true;
	}
	public static void sendMessage(String text,User sender,String recipient,String subject) {
		Serializer serializer = new Serializer();
		try {
			List<Mail> messages = (List<Mail>) serializer.deserializeMessage(new File("messages.bin"));
			List<User> users = (List<User>) serializer.deserializeUsers(new File("users.bin"));
			User user = null;
			String[] recipients = recipient.split(",");
			for (String s:recipients) {
				for (User u : users) {
					if (u.getUsername().equals(s))
					{
						user = u;
						messages.add(new Mail(text, sender, user,subject));
						break;
					}
				} 
			}
			serializer.serializeMessages(new File("messages.bin"), messages);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}	
