package gunb0s.consumer

import gunb0s.common.message.PctJob
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class MessageConsumer {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @KafkaListener(
        topics = ["pctJob"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    fun consume(message: PctJob) {
        logger.info("Consumed message: $message")
    }
}
