package mashup.backend.spring.member

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MemberApiApplication

fun main(args: Array<String>) {
	runApplication<MemberApiApplication>(*args)
}
