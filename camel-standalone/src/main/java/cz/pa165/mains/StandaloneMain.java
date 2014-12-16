package cz.pa165.mains;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.impl.DefaultCamelContext;

import cz.pa165.OrderBean;
import cz.pa165.processors.JaxbBeanProcessor;
import cz.pa165.processors.TimeAddingProcessor;


public class StandaloneMain {
	public static void main(String[] args) throws Exception {
		CamelContext camelContext = new DefaultCamelContext();

		camelContext.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				JaxbDataFormat jaxb = new JaxbDataFormat(OrderBean.class
						.getPackage().getName());
				
				errorHandler(deadLetterChannel("file://errored"));
				
				from("file://old-input-orders?recursive=true&flatten=true").to(
						"xslt:add-items-element.xslt").to(
						"direct:commonPipeline");
				
				from("direct:commonPipeline").from("file://new-input-orders").id("commonRoute")
						.process(new TimeAddingProcessor())
						.to("validator:orderSchema.xsd")
						.unmarshal(jaxb).process(new JaxbBeanProcessor()).marshal(jaxb)
						.to("file://outputDir");
				
			}
		});

		camelContext.start();
		ProducerTemplate producer = camelContext.createProducerTemplate();
		producer.start();
		
		Endpoint inputDir2 = camelContext
				.getEndpoint("file://old-input-orders");
		Exchange fileExchange = inputDir2.createExchange();
		fileExchange.getIn().setBody("<order><item>xyz</item></order>");
		fileExchange.getIn().setHeader("CamelFileName", "myArtificialOrder.txt");
		producer.send(inputDir2, fileExchange);
		
		Thread.sleep(300000);
	}





}
