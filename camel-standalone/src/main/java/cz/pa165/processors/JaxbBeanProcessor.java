package cz.pa165.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import cz.pa165.OrderBean;

public class JaxbBeanProcessor implements Processor {
	public void process(Exchange exchange) throws Exception {
		OrderBean request = exchange.getIn().getBody(OrderBean.class);
		System.out.println("The message was created at:" + request.getCreated());
	}
}