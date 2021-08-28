package mashup.backend.spring.member.presentation.web

import mashup.backend.spring.member.presentation.Authenticated
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import javax.servlet.http.HttpServletRequest

@Controller
class MainController {
    @Authenticated
    @GetMapping("/main")
    fun hello(
        request: HttpServletRequest,
        @ModelAttribute("memberId") memberId: Long
    ): String {
        log.info("memberId from RequestAttribute : ${request.getAttribute("memberId")}")
        log.info("memberId from ModelAttribute   : $memberId")
        return "main"
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)
    }
}