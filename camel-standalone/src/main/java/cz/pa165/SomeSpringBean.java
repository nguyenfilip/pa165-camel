package cz.pa165;

import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;


public class SomeSpringBean {

	@EndpointInject(uri="file://new-input-orders")
	private ProducerTemplate newFileEndpoint;
	
	
	public void sendSomethingThere(){
		System.out.println("Some spring bean sending something to a route!! :-)");
		
		newFileEndpoint.sendBody("<order><items>"
		  +"<item><product>Bean sent ArtificialProduct1</product><quantity>20</quantity></item>"
		  +"<item><product>Bean sent ArtificialProduct2</product><quantity>20</quantity></item> "
		  +"</items></order>");
		
	}
}
