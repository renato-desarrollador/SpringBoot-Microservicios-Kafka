package com.kafka.consumer.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrapServers}") //Con esto accedemso a la IP y al host
    private String bootstrapServers;

    public Map<String, Object> consumerConfig(){
        /*A continuación vamos a configurar
        las propiedades del lector*/
        Map<String, Object> properties = new HashMap<>();

        /*Aquí básicamente nosotros le estamos diciendo cuál
        es el servidor de Kafka en dónde está aquí = bootstrapServers*/
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        /*Vamos a decir cuál va a ser el objeto que se
        va a encargar de serializar el mensaje*/
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);

        /*Enviar mensajes de tipo String*/
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return properties;
    };


    @Bean
    public ConsumerFactory<String, String> consumerFactory(){
        // Crea una instancia de ConsumerFactory utilizando la configuración proporcionada
        // Esta fábrica se utiliza para crear consumidores de Kafka que procesan mensajes con claves y valores de tipo String
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

    //Creamos el objeto que va a escuchar los mensajes
    @Bean //Con esto vamos a leer los mensajes
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> consumer(){
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        return factory;
    }
}
