package com.blocadminmicromodern.budgetservice.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories
public class DatabaseConfiguration {

}
