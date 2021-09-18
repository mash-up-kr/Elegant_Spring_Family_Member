package mashup.backend.spring.member.infrastructure.amqp

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AmqpConfig {
    companion object {
        const val topicExchangeName = "test-exchange"
        const val queueName = "member"
    }

    @Bean
    fun queue(): Queue {
        return Queue(queueName, false)
    }

    @Bean
    fun exchange(): TopicExchange? {
        return TopicExchange(topicExchangeName)
    }

    @Bean
    fun binding(queue: Queue?, exchange: TopicExchange?): Binding {
        return BindingBuilder.bind(queue).to(exchange).with("member.#")
    }

    @Bean
    fun container(
        connectionFactory: ConnectionFactory,
        listenerAdapter: MessageListenerAdapter
    ): SimpleMessageListenerContainer? {
        val container = SimpleMessageListenerContainer()
        container.connectionFactory = connectionFactory
        container.setQueueNames(queueName)
        container.setMessageListener(listenerAdapter)
        return container
    }

    @Bean
    fun listenerAdapter(receiver: Receiver?): MessageListenerAdapter {
        return MessageListenerAdapter(receiver, "receiveMessage")
    }
}