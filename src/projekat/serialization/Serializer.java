package projekat.serialization;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Serializer {
	public Serializer() {
		
	}
	public void serializeUsers(File file,Object o) throws Exception {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
		out.writeObject(o);
	}
	public Object deserializeUsers(File file) throws Exception {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
			return ois.readObject();
		} catch (IOException ex) {
			return new ArrayList<>();
		}
	}
	public void serializeMessages(File file,Object o) throws Exception {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
		out.writeObject(o);
	}
	public Object deserializeMessage(File file) throws Exception {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
			return ois.readObject();
		} catch (IOException ex) {
			return new ArrayList<>();
		}
	}
}
