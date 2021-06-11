package com.blocadminmicromodern.budgetservice.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.blocadminmicromodern.budgetservice.dto.ExpenseCheckDTO;
import com.blocadminmicromodern.budgetservice.service.BudgetService;
import com.blocadminmicromodern.budgetservice.utils.Constants;

@Component
public class KafkaExpenseRecorder {
	
	public static final Logger log = LoggerFactory.getLogger(KafkaExpenseRecorder.class);
	
	private BudgetService budgetService;

	@Autowired
	public KafkaExpenseRecorder(BudgetService budgetService) {
		this.budgetService = budgetService;
	}

	@KafkaListener(id = "1", topics = Constants.EXPENSE_TOPIC, groupId = Constants.KAFKA_GROUP, containerFactory = "dtoKafkaListenerContainerFactory")
	void listenerWithMessageConverter(@Payload ExpenseCheckDTO expenseCheckDTO, @Headers MessageHeaders headers) {
		log.info("Received expense. Updating [{}]", expenseCheckDTO);
		budgetService.updateBudget(expenseCheckDTO.getExpenseType(), expenseCheckDTO.getTotalSum());
		log.info("${} for {}",
				expenseCheckDTO.getTotalSum(), expenseCheckDTO.getExpenseType());
	}
}
