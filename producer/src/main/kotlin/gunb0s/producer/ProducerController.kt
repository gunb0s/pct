package gunb0s.producer

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController()
class ProducerController(
    private val producerService: ProducerService
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping
    fun helloWorld(): String {
        try {
            logger.info("Hello, World!")

            producerService.execute(
                mapOf(
                    "key" to "value"
                )
            )

            return "Hello, World!"
        } catch (e: Exception) {
            logger.error("Failed to say hello world")
            return "Not Hello world"
        }
    }

    data class ProduceDto(
        val uuid: String,
    )

    @PostMapping
    fun produce(@RequestBody body: ProduceDto) {
        return try {
            producerService.produce(body.uuid)
        } catch (e: Exception) {
            logger.error("Failed to produce message", e)
            throw e
        }
    }

//    data class AlterPartitionDto(
//        val partitionName: String,
//        val count: Int
//    )
//
//    @PostMapping("alter-partition-count")
//    fun alterPartitionCount(@RequestBody body: AlterPartitionDto) {
//        try {
//            val newPartitions = NewPartitions.increaseTo(body.count)
//            val result = adminClient.createPartitions(
//                mapOf(
//                    body.partitionName to newPartitions
//                )
//            )
//
//            result.all().get()
//        } catch (e: Exception) {
//            logger.error("Failed to alter partition count", e)
//            throw e
//        }
//    }
}
