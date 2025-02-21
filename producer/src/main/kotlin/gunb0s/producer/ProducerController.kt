package gunb0s.producer

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController()
class ProducerController(
    private val producerService: ProducerService
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping
    fun produce(@RequestBody body: ProduceDto) {
        return try {
            producerService.produce(body.uuid)
        } catch (e: Exception) {
            logger.error("Failed to produce message", e)
            throw e
        }
    }

    @GetMapping("/logs")
    fun getLogs(@ModelAttribute queryDto: LogQueryDto) {
        val client = OkHttpClient()
        val url = HttpUrl.Builder()
            .scheme("http")
            .host("loki")
            .port(3100)
            .addPathSegment("loki/api/v1/query_range")
            .addQueryParameter("query", queryDto.query)
            .addQueryParameter("start", queryDto.start.toString())
            .build()
            .toString()
        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val body = response.body?.string()
                logger.info("Logs: $body")
            } else {
                logger.error("Failed to get logs ${response.message}")
            }
        } catch (e: Exception) {
            logger.error("Failed to get logs ${e.message}")
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
