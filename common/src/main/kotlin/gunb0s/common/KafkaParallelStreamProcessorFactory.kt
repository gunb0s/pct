//package gunb0s.common
//
//import io.confluent.parallelconsumer.ParallelConsumerOptions
//import io.confluent.parallelconsumer.ParallelStreamProcessor
//import org.springframework.kafka.core.ConsumerFactory
//
//class KafkaParallelStreamProcessorFactory<K, V> {
//    fun createParallelStreamProcessor(
//        kafkaConsumerFactory: ConsumerFactory<K, V>, // Kafka Consumer 를 생성하는 팩토리
//        topics: Array<String>,
//        ordering: ParallelConsumerOptions.ProcessingOrder,
//        concurrency: Int,
//        batchSize: Int,
//        groupId: String,
//        clientIdPrefix: String,
//        clientIdSuffix: String,
//    ): ParallelStreamProcessor<K, V> {
//        val options = ParallelConsumerOptions.builder<K, V>()
//            .ordering(ordering)
//            .maxConcurrency(concurrency)
//            .batchSize(batchSize)
//            .consumer(
//                kafkaConsumerFactory.createConsumer(
//                    groupId.takeIf { it.isNotEmpty() },
//                    clientIdPrefix,
//                    clientIdSuffix,
//                )
//            )
//            .build()
//
//        val eosStreamProcessor = ParallelStreamProcessor.createEosStreamProcessor(options)
//        eosStreamProcessor.subscribe(topics.toList())
//        return eosStreamProcessor
//    }
//}
