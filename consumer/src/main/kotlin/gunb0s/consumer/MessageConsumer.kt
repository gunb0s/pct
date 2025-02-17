package gunb0s.consumer

import gunb0s.common.KafkaParallelListener
import gunb0s.common.message.PctJob
import io.confluent.parallelconsumer.ParallelConsumerOptions
import org.apache.kafka.clients.consumer.ConsumerRecord
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

    @KafkaParallelListener(
        topics = ["parallel-pctJob"],
        groupId = "pct-consumer-v1.0.0",
        concurrency = 16,
        ordering = ParallelConsumerOptions.ProcessingOrder.UNORDERED
    )
    fun listen(
        record: ConsumerRecord<String, PctJob>
    ) {
        try {
            val message = record.value()
            logger.info("[Thread ${Thread.currentThread()}] Partition ${record.partition()} - Message - $message")
        } catch (e: Exception) {
            e.printStackTrace()
            logger.error("Error processing message", e)
        }
    }
}
