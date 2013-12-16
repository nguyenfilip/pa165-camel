package cz.pa165;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.springframework.stereotype.Component;

@Component
public class WebAppAditionalRoutes extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        JaxbDataFormat jaxb = new JaxbDataFormat(OrderRequest.class.getPackage().getName());
        from("spring-ws:rootqname:{http://cz.fi.muni.order}orderRequest?endpointMapping=#endpointMapping")
        	.unmarshal(jaxb)
            .process(new ConvertorToCommonFormat())
            .to("direct:commonPipeline")
            .unmarshal(jaxb)
            .process(new ExtractCreated())
            .marshal(jaxb);
    }
    
    private static final class ExtractCreated implements Processor {
        public void process(Exchange exchange) throws Exception {
        	OrderBean obean = exchange.getIn().getBody(OrderBean.class);
            OrderResponse response = new OrderResponse();
            response.setCreated(obean.getCreated());
            exchange.getOut().setBody(response);
        }
    }
    

	public static class ConvertorToCommonFormat implements Processor {

		public void process(Exchange arg0) throws Exception {
			OrderRequest request = arg0.getIn().getBody(OrderRequest.class);

			StringBuilder order = new StringBuilder();

			order.append("<order><items>");
			for (String item : request.getItem()) {
				order.append("<item>");
				order.append(item);
				order.append("</item>");
			}
			order.append("</items></order>");

			arg0.getOut().setBody(order);
		}

}

   
}