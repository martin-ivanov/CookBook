package com.cookbook.jms;

import java.io.IOException;
import java.util.List;

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
import org.springframework.transaction.annotation.Transactional;

import com.cookbook.persistence.dao.RecipeDAO;
import com.cookbook.persistence.entity.RecipeEntity;
import com.cookbook.rest.response.wrapper.RecipeWrapper;

@Component
public class RetrieveRecipeListener implements
		SessionAwareMessageListener<Message> {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	RecipeDAO recipeDao;

	/**
	 * Implementation of <code>MessageListener</code>.
	 */

	@Override
	@Transactional
	public void onMessage(Message message, Session session) throws JMSException {
		System.out.println("onMessage recipe");
		try {

			if (message instanceof TextMessage) {
				TextMessage tm = (TextMessage) message;
				String msg = tm.getText();

				TextMessage response = session.createTextMessage();
				response.setJMSCorrelationID(message.getJMSCorrelationID());
				RecipeEntity responseEntity = null;

				if (msg.equalsIgnoreCase("getAll")) {
					List<RecipeEntity> responseEntitiesList = recipeDao
							.getAllRecipes();
					RecipeWrapper responseEntities = new RecipeWrapper();
					responseEntities.setList(responseEntitiesList);

					response.setText(objectMapper
							.writeValueAsString(responseEntities));
				} else {
					RecipeEntity recipe = objectMapper.readValue(msg,
							RecipeEntity.class);
					if (recipe.getId() != null) {

						responseEntity = recipeDao
								.getRecipeById(recipe.getId());
						response.setText(objectMapper
								.writeValueAsString(responseEntity));
					} else {
						if (recipe.getName()!=null || !("".equals(recipe.getName()))){
							List<RecipeEntity> responseEntities = recipeDao
									.searchRecipesByName(recipe.getName());
							RecipeWrapper rw = new RecipeWrapper();
							rw.setList(responseEntities);
							response.setText(objectMapper
									.writeValueAsString(rw));
						}
					}
				}

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