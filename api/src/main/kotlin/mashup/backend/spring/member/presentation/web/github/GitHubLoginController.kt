package mashup.backend.spring.member.presentation.web.github

import mashup.backend.spring.member.application.LoginService
import mashup.backend.spring.member.presentation.api.member.MemberResponse
import mashup.backend.spring.member.presentation.web.oauth.OAuthLoginController
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.util.UriComponentsBuilder

@Controller
@RequestMapping("login/github")
class GitHubLoginController(
        @Value("\${oauth.github.client.id}")
        private val clientId: String,
        @Value("\${oauth.github.url.callback}")
        private val callbackUrl: String,
        private val loginService: LoginService
) : OAuthLoginController()  {
    override fun createOAuthUrl(state: String): String {
        return UriComponentsBuilder.fromHttpUrl("https://github.com/login/oauth/authorize")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", callbackUrl)
                .queryParam("state", state)
                .build()
                .toUriString()
    }

    override fun loginCallbackWithOAuth(code: String, state: String): MemberResponse {
        return loginService.loginWithGitHub(code)
    }
}

