package mashup.backend.spring.member.presentation.web.naver

import mashup.backend.spring.member.application.LoginService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.util.UriComponentsBuilder
import java.util.concurrent.ThreadLocalRandom
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

@Controller
class NaverLoginController(
        @Value("\${oauth.naver.client.id}")
        private val clientId: String,
        @Value("\${oauth.naver.url.callback}")
        private val callbackUrl: String,
        private val loginService: LoginService
) {
    /**
     * 네이버 로그인 진입점
     */
    @GetMapping("/login/naver")
    fun loginWithGitHub(
            httpSession: HttpSession
    ): String {
        val state = createState()
        val url = createNaverOAuthUrl(state)
        httpSession.setAttribute("state", state)
        return "redirect:$url"
    }

    private fun createState(): String = "mashup${ThreadLocalRandom.current().nextInt(1000)}"

    private fun createNaverOAuthUrl(state: String): String {
        return UriComponentsBuilder.fromHttpUrl("https://nid.naver.com/oauth2.0/authorize")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", callbackUrl)
                .queryParam("state", state)
                .queryParam("response_type", "code")
                .build()
                .toUriString()
    }

    /**
     * 네이버 인증 후처리
     */
    @GetMapping("/login/naver/callback")
    fun postProcess(
            @RequestParam code: String,
            @RequestParam state: String,
            httpServiceRequest: HttpServletRequest,
            httpSession: HttpSession
    ): String {
        if (state != httpSession.getAttribute("state")) {
            throw RuntimeException("state 값이 일치하지 않음. queryParam.state: $state, session.state: $state")
        }
        val memberResponse = loginService.loginWithNaver(code, state)

        httpSession.setAttribute("nickname", memberResponse.name)
        return "redirect:/"
    }
}