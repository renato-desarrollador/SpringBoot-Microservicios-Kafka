package com.kafka.provider.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProviderConfig {
    //Vamos a configurar el cliente
    @Value("${spring.kafka.bootstrapServers}") //Con esto accedemso a la IP y al host
    private String bootstrapServers;

    public Map<String, Object> producerConfig(){
        /*A continuación vamos a configurar
        la serialización y el envío de el mensajeal servidor de Kafka*/
        Map<String, Object> properties = new HashMap<>();

        /*Aquí básicamente nosotros le estamos diciendo cuál
        es el servidor de Kafka en dónde está aquí = bootstrapServers*/
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        /*Vamos a decir cuál va a ser el objeto que se
        va a encargar de serializar el mensaje*/
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        /*Enviar mensajes de tipo String*/
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return properties;
    };

    /*Creamos el cliente*/
    @Bean
    public ProducerFactory<String, String> providerFactory(){
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> producerFactory){
        return new KafkaTemplate<>(producerFactory);
    }
}
