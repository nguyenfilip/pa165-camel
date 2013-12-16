package cz.pa165;


import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="order")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderBean {

	@XmlElement(required = true)
	private String created;
	
	@XmlElementWrapper(name="items")
	@XmlAnyElement
    private ArrayList<Object> items;

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public ArrayList<Object> getItems() {
		return items;
	}

	public void setItems(ArrayList<Object> items) {
		this.items = items;
	}
}