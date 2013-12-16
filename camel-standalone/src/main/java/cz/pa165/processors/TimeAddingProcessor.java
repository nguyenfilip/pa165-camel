package cz.pa165.processors;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

	public class TimeAddingProcessor implements Processor {
		public void process(Exchange exchange) throws Exception {
			String request = exchange.getIn().getBody(String.class);
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String timeElement = String.format("<created>%s</created>",
					dateFormat.format(cal.getTime()));
			request = request.replace("<order>", "<order>" + timeElement);
			exchange.getIn().setBody(request);
		}
	}