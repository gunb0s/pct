package gunb0s.producer

data class ProduceDto(
    val uuid: String,
)

data class LogQueryDto(
    val query: String,
    val start: Long,
)
