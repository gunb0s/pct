package gunb0s.producer

import gunb0s.common.message.ExecutionContext
import gunb0s.common.message.PctJob
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ISO_DATE_TIME
import kotlin.random.Random

@Service
class ProducerService(
    private val messageProducer: MessageProducer,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)
    private var int = 0

    @Scheduled(cron = "*/10 * * * * *")
    fun scheduledTask() {
        val jobId = Random.nextLong()
        logger.info("Scheduled task for jobId=$jobId")
        produce(jobId.toString())
    }

    fun execute(data: Any) {
        int++
        if (int % 2 == 0) {
            logger.info("Executing ProducerService")
        } else {
            throw IllegalStateException("ProducerService failed")
        }
    }

    fun produce(uuid: String) {
        messageProducer.produce(
            "pctJob",
            LocalDateTime.now().toString(), PctJob(
                Random.nextLong(),
                uuid.toLong(),
                ExecutionContext(
                    0,
                    LocalDateTime.now().format(ISO_DATE_TIME),
                )
            )
        )
    }
}
