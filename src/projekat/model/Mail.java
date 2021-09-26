package projekat.model;

import java.io.Serializable;

public class Mail implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5727770942985864319L;
	private String text;
	private User sender;
	private User recipient;
	private String subject;
	private String shorttext;
	public Mail(String text, User sender, User recipient,String subject) {
		super();
		this.text = text;
		this.sender = sender;
		this.recipient = recipient;
		this.subject = subject;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public User getSender() {
		return sender;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}
	public User getRecipient() {
		return recipient;
	}
	public void setRecipient(User recipient) {
		this.recipient = recipient;
	}
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getShorttext() {
		return this.shortText();
	}
	public void setShorttext(String shorttext) {
		this.shorttext = shorttext;
	}
	@Override
	public String toString() {
		return text;
	}
	public String shortText() {
		if(text.length() > 20)
			return text.substring(0,10) + "...";
		return text;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((recipient == null) ? 0 : recipient.hashCode());
		result = prime * result + ((sender == null) ? 0 : sender.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Mail))
			return false;
		Mail other = (Mail) obj;
		if (recipient == null) {
			if (other.recipient != null)
				return false;
		} else if (!recipient.equals(other.recipient))
			return false;
		if (sender == null) {
			if (other.sender != null)
				return false;
		} else if (!sender.equals(other.sender))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
	
}	
