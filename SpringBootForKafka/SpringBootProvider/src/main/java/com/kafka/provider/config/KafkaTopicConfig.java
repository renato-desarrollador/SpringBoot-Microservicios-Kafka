package com.kafka.provider.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic generateTopic(){

        Map<String, String> configurations = new HashMap<>();
        // delete (Borra mensaje), compact (Mantiene el mas actual)
        configurations.put(TopicConfig.CLEANUP_POLICY_CONFIG, TopicConfig.CLEANUP_POLICY_DELETE);

        /*Cuando envían un mensaje voy a retenerlo en el
        servidor de Kafka durante un día después de ese día
        o se va a borrar o se mantiene solamente el más actual.*/ //milisegundos (1 día)
        configurations.put(TopicConfig.RETENTION_MS_CONFIG, "86400000"); // Tiempo de retención de mensajes - por defecto -1 (sifnifica que nunca se va a borrar)

        /*Nosotros le vamos a decir cuál es el tamaño máximo que pueden
        tener los segmentos dentro del topic.
        Recuerda que dentro del topic sse divide en diferentes segmentos*/ // Se debe poner en bytes
        configurations.put(TopicConfig.SEGMENT_BYTES_CONFIG, "1073741824");// Tamaño máximo del segmento - 1GB (por defecto)

        /*Cuál es el peso o el tamaño máximo de cada mensaje
        por defecto viene en un megabyte*/
        configurations.put(TopicConfig.MAX_MESSAGE_BYTES_CONFIG, "1000012"); // Tamaño máximo de cada mensaje - por defecto 1MB

        /*EXISTEN MAS CONFIGURACIONES PARA LSO TOPICS*/

        return TopicBuilder.name("unProgramadorNace-Topic")
                .partitions(2)
                .replicas(2)
                .configs(configurations)
                .build();
    }
}
