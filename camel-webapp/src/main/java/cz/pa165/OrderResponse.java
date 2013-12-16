package cz.pa165;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace="http://cz.fi.muni.order")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderResponse {

    @XmlElement(required = true,namespace="http://cz.fi.muni.order")
    private String created;

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

   
}