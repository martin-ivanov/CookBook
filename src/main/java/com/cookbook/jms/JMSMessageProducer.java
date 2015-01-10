package com.cookbook.jms;

import java.util.UUID;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.SessionCallback;
import org.springframework.jms.support.JmsUtils;
import org.springframework.jms.support.destination.DestinationResolver;

import com.cookbook.errors.AppException;

public class JMSMessageProducer  {

	private static final class ProducerConsumer implements
			SessionCallback<Message> {

		private static final int TIMEOUT = 20000;

		private final String msg;

		private final DestinationResolver destinationResolver;

		private final String queue;

		private final String action;
		
		private final String optinalProperty;

		public ProducerConsumer(final String msg, String queue,
				final DestinationResolver destinationResolver, String action, String optionalProperty) {
			this.msg = msg;
			this.queue = queue;
			this.destinationResolver = destinationResolver;
			this.action = action;
			this.optinalProperty = optionalProperty;
		}

		public Message doInJms(final Session session) throws JMSException {
			System.out.println("do in jms invoked");
			MessageConsumer consumer = null;
			MessageProducer producer = null;
			try {
				final String correlationId = UUID.randomUUID().toString();
				final Destination requestQueue = destinationResolver
						.resolveDestinationName(session, queue + ".REQUEST",
								false);
				final Destination replyQueue = destinationResolver
						.resolveDestinationName(session, queue + ".RESPONSE",
								false);
				// Create the consumer first!
				consumer = session.createConsumer(replyQueue,
						"JMSCorrelationID = '" + correlationId + "'");
				final TextMessage textMessage = session.createTextMessage(msg);
				textMessage.setStringProperty("action", action);
				textMessage.setStringProperty("optional", optinalProperty);
				textMessage.setJMSCorrelationID(correlationId);
				textMessage.setJMSReplyTo(replyQueue);
				// Send the request second!
				producer = session.createProducer(requestQueue);
				System.out.println("queue:" + requestQueue.toString());
				producer.send(requestQueue, textMessage);
				System.out.println("Message sent");
				// Block on receiving the response with a timeout
				return consumer.receive(TIMEOUT);
			} finally {
				// Don't forget to close your resources
				JmsUtils.closeMessageConsumer(consumer);
				JmsUtils.closeMessageProducer(producer);
			}
		}
	}

	@Autowired
	private JmsTemplate jmsTemplate;


	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
	public String request(final String request, String queue, String action, String optionalProperty) throws AppException{
		System.out.println(jmsTemplate.getDestinationResolver());
		// Must pass true as the second param to start the connection
		ProducerConsumer pc = new ProducerConsumer(request, queue,
				jmsTemplate.getDestinationResolver(), action, optionalProperty);
		
		Message msg = jmsTemplate.execute(pc , true);
		String message = "";
		if (msg instanceof TextMessage) {
			TextMessage tm = (TextMessage) msg;

			try {
				message = tm.getText();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (message==null || message.equals("")){
				throw new AppException(404, "SystemError", "Response from JMS not found");
			}
			System.out.println("response: " + message);
		}
		return message;
	}
}