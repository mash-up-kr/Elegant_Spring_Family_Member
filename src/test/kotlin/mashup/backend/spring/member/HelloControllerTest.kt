package mashup.backend.spring.member

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension::class)
internal class HelloControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun hello() {
        mockMvc.get("/hello")
                .andExpect { status { isOk() } }
                .andExpect { jsonPath("$") { value("Hello, Member-api") } }
    }
}
