package gunb0s.producer

import gunb0s.common.message.ExecutionContext
import gunb0s.common.message.PctJob
import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.NewPartitions
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ISO_DATE_TIME
import kotlin.random.Random

@RestController()
class ProducerController(
    private val messageProducer: MessageProducer,
    private val adminClient: AdminClient,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    data class ProduceDto(
        val partitionName: String,
    )

    @PostMapping
    fun produce(@RequestBody body: ProduceDto) {
        messageProducer.produce(
            body.partitionName,
            LocalDateTime.now().toString(), PctJob(
                Random.nextLong(),
                Random.nextLong(),
                ExecutionContext(
                    0,
                    LocalDateTime.now().format(ISO_DATE_TIME),
                )
            )
        )
    }

    data class AlterPartitionDto(
        val partitionName: String,
        val count: Int
    )

    @PostMapping("alter-partition-count")
    fun alterPartitionCount(@RequestBody body: AlterPartitionDto) {
        try {
            val newPartitions = NewPartitions.increaseTo(body.count)
            val result = adminClient.createPartitions(
                mapOf(
                    body.partitionName to newPartitions
                )
            )

            result.all().get()
        } catch (e: Exception) {
            logger.error("Failed to alter partition count", e)
            throw e
        }
    }
}
