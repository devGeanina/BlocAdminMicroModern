package com.blocadminmicromodern.budgetservice.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.blocadminmicromodern.budgetservice.dto.ExpenseCheckDTO;
import com.blocadminmicromodern.budgetservice.utils.Constants;

@Configuration
public class KafkaConsumerConfiguration {

	public static final Logger log = LoggerFactory.getLogger(KafkaConsumerConfiguration.class);

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	public ConsumerFactory<String, ExpenseCheckDTO> dtoConsumerFactory() {
		JsonDeserializer<ExpenseCheckDTO> deserializer = new JsonDeserializer<>(ExpenseCheckDTO.class);
		deserializer.addTrustedPackages("*");
		deserializer.setUseTypeMapperForKey(true);
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, Constants.KAFKA_GROUP);
		return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, ExpenseCheckDTO> dtoKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, ExpenseCheckDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(dtoConsumerFactory());
		factory.setErrorHandler(((exception, data) -> {
			log.error("The received message can't be processed");
		}));
		return factory;
	}
}
