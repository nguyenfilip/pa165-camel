package cz.pa165;

import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

import cz.pa165.processors.JaxbBeanProcessor;
import cz.pa165.processors.TimeAddingProcessor;

@Component
public class MainRetailStoreRoutes extends SpringRouteBuilder{

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
}
