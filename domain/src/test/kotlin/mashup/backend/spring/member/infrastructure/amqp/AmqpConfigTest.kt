package mashup.backend.spring.member.infrastructure.amqp

import org.junit.jupiter.api.Test
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.TimeUnit

@SpringBootTest
internal class AmqpConfigTest {
    @Autowired
    lateinit var receiver: Receiver

    @Autowired
    lateinit var rabbitTemplate: RabbitTemplate

    @Test
    fun test() {
        println("Sending message...")
        rabbitTemplate.convertAndSend(
            AmqpConfig.topicExchangeName,
            "member.#",
            "Hello from RabbitMQ!"
        )
        receiver.latch.await(10000, TimeUnit.MILLISECONDS)
    }
}