package com.cookbook.jms;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

import com.cookbook.persistence.dao.SubscriptionDAO;
import com.cookbook.persistence.entity.SubscriptionEntity;

@Component
public class SubscribeListener implements
		SessionAwareMessageListener<Message> {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	SubscriptionDAO subscriptionDao;

	/**
	 * Implementation of <code>MessageListener</code>.
	 */

	@Override
	public void onMessage(Message message, Session session) throws JMSException {
		System.out.println("onMessage user");
		try {

			if (message instanceof TextMessage) {
				TextMessage tm = (TextMessage) message;
				String msg = tm.getText();
				SubscriptionEntity subscription = objectMapper.readValue(msg, SubscriptionEntity.class);
				SubscriptionEntity responseEntity = null;
				responseEntity = subscriptionDao.subcribe(subscription);
				TextMessage response = session.createTextMessage();
				response.setJMSCorrelationID(message.getJMSCorrelationID());
				response.setText(objectMapper
						.writeValueAsString(responseEntity));
				// Send the response to the Destination specified by the
				// JMSReplyTo field of the received message,
				// this is presumably a temporary queue created by the
				// client
				MessageProducer producer = session.createProducer(message
						.getJMSReplyTo());
				producer.send(response);

			}
		} catch (JMSException e) {

		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// TODO Auto-generated method stub

	}

}