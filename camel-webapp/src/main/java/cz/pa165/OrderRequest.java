package cz.pa165;


import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace="http://cz.fi.muni.order")

@XmlAccessorType(XmlAccessType.FIELD)
public class OrderRequest {

	
	@XmlElementWrapper(name="items", namespace="http://cz.fi.muni.order")
	@XmlElement(name="item", type=String.class, namespace="http://cz.fi.muni.order")
    private ArrayList<String> item;

	public ArrayList<String> getItem() {
		return item;
	}

	public void setItem(ArrayList<String> item) {
		this.item = item;
	}
}