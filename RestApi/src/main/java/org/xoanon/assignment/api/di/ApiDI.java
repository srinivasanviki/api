package org.xoanon.assignment.api.di;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xoanon.assignment.api.controller.UsersController;
import org.xoanon.assignment.api.db.CassandraConnector;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by viki on 10/2/15.
 */
public class ApiDI {
    final private Logger log = LoggerFactory.getLogger(this.getClass());

    @Inject
    org.apache.commons.configuration.Configuration config;

    @Inject
    BeanFactory factory;

    @Bean
    @Singleton
    public UsersController usersController() {
      return new UsersController(factory.getBean("cassandraConnector",CassandraConnector.class));
    }

    @Bean
    @Singleton
    public CassandraConnector cassandraConnector() {
        String cassandra_ip=config.getString("cassandra.ip");
        String keyspace=config.getString("cassandra.keyspace");
        return new CassandraConnector(cassandra_ip,keyspace);
    }
}
