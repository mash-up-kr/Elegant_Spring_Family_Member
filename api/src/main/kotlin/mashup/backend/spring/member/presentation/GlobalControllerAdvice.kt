package mashup.backend.spring.member.presentation

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ModelAttribute
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class GlobalControllerAdvice {
    @ModelAttribute("memberId")
    fun resolveMemberId(request: HttpServletRequest): Long {
        return request.getAttribute("memberId")?.let { it as Long }
            ?: throw RuntimeException("'memberId' not found")
    }
}