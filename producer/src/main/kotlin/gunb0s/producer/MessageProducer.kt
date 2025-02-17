package gunb0s.producer

import gunb0s.common.message.PctJob
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class MessageProducer(
    private val template: KafkaTemplate<String, PctJob>
) {
    private val topic = "pctJob"

    fun produce(key: String, message: PctJob) {
        template.send(topic, key, message)
    }
}
