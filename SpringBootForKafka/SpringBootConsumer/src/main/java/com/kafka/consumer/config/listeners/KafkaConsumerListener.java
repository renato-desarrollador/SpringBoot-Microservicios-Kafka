package com.kafka.consumer.config.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;


@Configuration
public class KafkaConsumerListener {

    private Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerListener.class);

    //@KafkaListener es un anotación para poder escuchar los mensajes que se están enviando
    @KafkaListener(topics = {"unProgramadorNace-Topic"}, groupId = "my-groupd-id") //Nos permite formar grupos de consumidores
    private void listener(String message){//no tenemos groupId porque tenemos un solo topic, osea un consumidor solitario
        LOGGER.info("Mensaje Recibido, el mensaje es: " + message);
    }
}
