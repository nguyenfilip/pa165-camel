package cz.pa165;

import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;


public class SomeSpringBean {

	@EndpointInject(uri="file://new-orders-input")
	private ProducerTemplate newFileEndpoint;
	
	
	public void sendSomethingThere(){
		System.out.println("Some spring bean sending something to a route!! :-)");
		
		newFileEndpoint.sendBody("<order><items>"
		  +"<item><product>Skis</product><quantity>20</quantity></item>"
		  +"<item><product>Skis2</product><quantity>20</quantity></item> "
		  +"</items></order>");
	}
}
