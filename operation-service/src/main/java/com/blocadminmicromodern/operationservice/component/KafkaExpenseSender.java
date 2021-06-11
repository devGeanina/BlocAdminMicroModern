package com.blocadminmicromodern.operationservice.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.blocadminmicromodern.operationservice.dto.ExpenseCheckDTO;
import com.blocadminmicromodern.operationservice.utils.Constants;

@Component
public class KafkaExpenseSender {

	private final static Logger LOG = LoggerFactory.getLogger(KafkaExpenseSender.class);

	private KafkaTemplate<String, ExpenseCheckDTO> kafkaTemplate;

	@Autowired
	public KafkaExpenseSender(KafkaTemplate<String, ExpenseCheckDTO> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendMessage(ExpenseCheckDTO expenseCheckDTO) {
		
		LOG.info("Sending ExpenseCheckDTO Json Serializer : {}", expenseCheckDTO);
		Message<ExpenseCheckDTO> message = MessageBuilder.withPayload(expenseCheckDTO)
				.setHeader(KafkaHeaders.TOPIC, Constants.EXPENSE_TOPIC).build();
		kafkaTemplate.send(message);

		// Use below example to listen to any incoming futures or to get back a result;
		// not needed for the purpose of this example
		/*
		 * ListenableFuture<SendResult<String, ExpenseCheckDTO>> future =
		 * kafkaTemplate.send(message);
		 * 
		 * future.addCallback(new ListenableFutureCallback<SendResult<String,
		 * ExpenseCheckDTO>>() {
		 * 
		 * @Override public void onSuccess(SendResult<String, ExpenseCheckDTO> result) {
		 * LOG.
		 * info("Success Callback message delivery for ExpenseCheckDTO: [{}] with offset -{}"
		 * , expenseCheckDTO, result.getRecordMetadata().offset()); }
		 * 
		 * @Override public void onFailure(Throwable ex) { LOG.
		 * error("Failure Callback: Unable to deliver message for ExpenseCheckDTO [{}]. {}"
		 * , expenseCheckDTO, ex.getMessage()); } });
		 */
	}
}
