package mashup.backend.spring.member.presentation.web.oauth

import mashup.backend.spring.member.presentation.api.member.MemberResponse
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.concurrent.ThreadLocalRandom
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

@Controller
abstract class OAuthController{
    /**
     * OAuth 로그인 진입점
     */
    @GetMapping("")
    fun loginWithOAuth(httpSession: HttpSession): String {
        val state = createState()
        val url = createOAuthUrl(state)
        httpSession.setAttribute("state", state)
        return "redirect:$url"
    }

    private fun createState(): String = "mashup${ThreadLocalRandom.current().nextInt(1000)}"

    abstract fun createOAuthUrl(state: String): String

    /**
     * OAuth 인증 후처리
     */
    @GetMapping("/callback")
    fun postProcess(
            @RequestParam code: String,
            @RequestParam state: String,
            httpServiceRequest: HttpServletRequest,
            httpSession: HttpSession
    ): String {
        if (state != httpSession.getAttribute("state")) {
            throw RuntimeException("state 값이 일치하지 않음. queryParam.state: ${state}, session.state: ${httpSession.getAttribute("state")}")
        }
        val memberResponse = loginCallbackWithOAuth(code, state)

        httpSession.setAttribute("nickname", memberResponse.name)
        return "redirect:/"
    }

    abstract fun loginCallbackWithOAuth(code: String, state: String): MemberResponse

}