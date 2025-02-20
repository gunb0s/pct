package gunb0s.producer

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ProducerMonitoring

@Aspect
@Component
class ProducerMonitoringAspect {
    @Pointcut("@within(gunb0s.producer.ProducerMonitoring) && execution(* execute(..))")
    fun executionMethod() {

    }

    @Before("executionMethod()")
    fun beforeExecution(joinPoint: JoinPoint) {
        val javaClass = joinPoint.target.javaClass
        val logger = LoggerFactory.getLogger(javaClass)

        val method = joinPoint.signature.name
        val className = joinPoint.target.javaClass.simpleName
        val params = joinPoint.args.joinToString(", ") { it.toString() }

        logger.info("Executing method $method in class $className with params $params")
    }

    @AfterReturning(pointcut = "executionMethod()", returning = "result")
    fun logAfterExecution(joinPoint: JoinPoint, result: Any?) {
        val javaClass = joinPoint.target.javaClass
        val logger = LoggerFactory.getLogger(javaClass)

        val method = joinPoint.signature.name
        val className = joinPoint.target.javaClass.simpleName
        val params = joinPoint.args.joinToString(", ") { it.toString() }

        logger.info("Method $method in class $className executed successfully with params $params")
    }

    @AfterThrowing(pointcut = "executionMethod()", throwing = "exception")
    fun logAfterThrowing(joinPoint: JoinPoint, exception: Throwable) {
        val javaClass = joinPoint.target.javaClass
        val logger = LoggerFactory.getLogger(javaClass)

        val method = joinPoint.signature.name
        val className = joinPoint.target.javaClass.simpleName

        logger.error("Method $method in class $className failed with exception: $exception")
    }
}
