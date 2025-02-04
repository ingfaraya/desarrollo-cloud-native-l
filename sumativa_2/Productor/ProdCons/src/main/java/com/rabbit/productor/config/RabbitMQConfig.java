package com.rabbit.productor.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class RabbitMQConfig {
    
    @Bean
    public Queue myQueueReporteria(){
        return new Queue("myQueueReporteria", true);
    }

    // Definir el Exchange
    @Bean
    public DirectExchange myExchange() {
        return new DirectExchange("myExchange", true, false); // Durable, no auto-delete
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Soporte para Java 8+ Date/Time
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Desactiva timestamps
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    // Vincular la cola al Exchange con una routing key
    @Bean
    public Binding binding(Queue myQueueReporteria, DirectExchange myExchange) {
        return BindingBuilder.bind(myQueueReporteria)
                             .to(myExchange)
                             .with("reporteriaKey");
    }

    // Configuración del RabbitTemplate para usar Exchange y Routing Key por defecto
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        rabbitTemplate.setExchange("myExchange");       // Exchange predeterminado
        rabbitTemplate.setRoutingKey("reporteriaKey");  // Routing key predeterminada
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        return factory;
    }

    
}
