package mashup.backend.spring.member.presentation.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class MainController {
    @GetMapping("/main")
    fun hello() = "main"
}