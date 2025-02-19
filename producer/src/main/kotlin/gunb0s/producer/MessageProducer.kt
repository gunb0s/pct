package gunb0s.producer

import gunb0s.common.message.PctJob
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import kotlin.math.absoluteValue
import kotlin.random.Random

@Component
class MessageProducer(
    private val template: KafkaTemplate<String, PctJob>
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun produce(topic: String, key: String, message: PctJob) {
        val result = template.send(topic, key, message)
        val jobId = Random.nextLong().absoluteValue.toString()

        logger.info("Sending message to topic: $topic jobId=$jobId")
        result.whenComplete { result, ex ->
            if (ex == null) {
                val recordMetadata =
                    result?.recordMetadata ?: throw RuntimeException("Failed to send message for $message")
                logger.info(
                    "Message sent successfully to topic: ${recordMetadata.topic()}, " +
                            "partition: ${recordMetadata.partition()}, " +
                            "offset: ${recordMetadata.offset()} jobId=$jobId"
                )
            } else {
                logger.error("Failed to send message due to : ${ex.message} jobId=$jobId")
            }
        }.join()

        logger.info("Message sent successfully to topic: $topic jobId=$jobId")
    }
}
