package cz.pa165;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.testng.AbstractCamelTestNGSpringContextTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;


@ContextConfiguration(locations={"classpath:applicationContext-standalone.xml"})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class RetailStoreRoutesTest extends AbstractCamelTestNGSpringContextTests  {
	
	@Autowired
	protected ModelCamelContext retailStoreCamelContext;
	
	
	@EndpointInject(uri="file://new-input-orders")
	protected ProducerTemplate newOrdersTemplate;
	
	@Test
	public void helloTest(){
		Assert.assertNotNull(retailStoreCamelContext);
		Assert.assertNotNull(newOrdersTemplate);
	}
	
	@Test(timeOut=20000)
	public void sendOrder() throws Exception{
		retailStoreCamelContext.getRouteDefinition("commonRoute").adviceWith(retailStoreCamelContext,
				new AdviceWithRouteBuilder() {
					@Override
					public void configure() throws Exception {
						interceptSendToEndpoint("file://outputDir")
								.skipSendToOriginalEndpoint().to(
										"mock:sendOrderTestMock");
					}
				});

		MockEndpoint mock = retailStoreCamelContext.getEndpoint("mock:sendOrderTestMock",
				MockEndpoint.class);

		mock.expectedMinimumMessageCount(1);

		newOrdersTemplate
				.sendBody("<order><items>"
						+ "<item><product>SkisxX</product><quantity>20</quantity></item>"
						+ "<item><product>SkisY</product><quantity>20</quantity></item> "
						+ "</items></order>");

		mock.assertIsSatisfied();
		Assert.assertTrue(mock.getExchanges().get(0).getIn().getBody(String.class).contains("created"));
	}
	
	
}
