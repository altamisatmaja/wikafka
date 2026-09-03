package bss.atmaja.dev;

import bss.atmaja.dev.entity.WikafkaData;
import bss.atmaja.dev.repository.WikafkaDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaDatabaseConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaDatabaseConsumer.class);

    private WikafkaDataRepository dataRepository;

    public KafkaDatabaseConsumer(WikafkaDataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @KafkaListener(topics = "wikimedia_recentchange", groupId = "myGroup")
    public void consume(String eventMessage) {
        LOGGER.info(String.format("Event Message received -> %s", eventMessage));

        WikafkaData wikafkaData = new WikafkaData();
        wikafkaData.setWikiEventData(eventMessage);
        dataRepository.save(wikafkaData);
    }
}
