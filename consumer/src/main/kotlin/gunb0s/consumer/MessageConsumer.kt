package gunb0s.consumer

import gunb0s.common.message.PctJob
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class MessageConsumer {
    private val logger = LoggerFactory.getLogger(this::class.java)
    private val idioms = listOf(
        "가는 말이 고와야 오는 말이 곱다",
        "호랑이 굴에 가야 호랑이 새끼를 잡는다",
        "백문이 불여일견",
        "티끌 모아 태산"
    )

    @KafkaListener(
        topics = ["pctJob"],
        groupId = "\${spring.kafka.consumer.group-id}",
    )
    fun consume(message: PctJob) {
        val jobId = message.jobId
        logger.info("Message processing... for jobId=$jobId")
        logger.info("Random idiom: ${idioms.random()}")
        logger.info("message processed... for jobId=$jobId")
    }

//    @KafkaParallelListener(
//        topics = ["parallel-pctJob"],
//        groupId = "pct-consumer-v1.0.0",
//        concurrency = 16,
//        ordering = ParallelConsumerOptions.ProcessingOrder.UNORDERED
//    )
//    fun listen(
//        record: ConsumerRecord<String, PctJob>
//    ) {
//        try {
//            val message = record.value()
//            logger.info("[Thread ${Thread.currentThread()}] Partition ${record.partition()} - Message - $message")
//        } catch (e: Exception) {
//            e.printStackTrace()
//            logger.error("Error processing message", e)
//        }
//    }
}
