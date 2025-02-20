package gunb0s.producer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@EnableAspectJAutoProxy
@SpringBootApplication(scanBasePackages = ["gunb0s"])
class ProducerApplication

fun main(args: Array<String>) {
    runApplication<ProducerApplication>(*args)
}
