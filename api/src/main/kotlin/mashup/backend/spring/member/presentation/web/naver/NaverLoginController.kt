package mashup.backend.spring.member.presentation.web.naver

import mashup.backend.spring.member.application.LoginService
import mashup.backend.spring.member.presentation.api.member.MemberResponse
import mashup.backend.spring.member.presentation.web.oauth.OAuthController
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.util.UriComponentsBuilder
import java.util.concurrent.ThreadLocalRandom
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

@Controller
@RequestMapping("login/naver")
class NaverLoginController(
        @Value("\${oauth.naver.client.id}")
        private val clientId: String,
        @Value("\${oauth.naver.url.callback}")
        private val callbackUrl: String,
        private val loginService: LoginService
) : OAuthController() {
    override fun createOAuthUrl(state: String): String {
        return UriComponentsBuilder.fromHttpUrl("https://nid.naver.com/oauth2.0/authorize")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", callbackUrl)
                .queryParam("state", state)
                .queryParam("response_type", "code")
                .build()
                .toUriString()
    }

    override fun loginCallbackWithOAuth(code: String, state: String): MemberResponse {
        return loginService.loginWithNaver(code, state)
    }
}