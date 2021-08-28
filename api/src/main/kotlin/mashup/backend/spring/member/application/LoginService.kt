package mashup.backend.spring.member.application

import mashup.backend.spring.member.application.member.MemberAssembler
import mashup.backend.spring.member.domain.IdProviderInfo
import mashup.backend.spring.member.domain.MemberService
import mashup.backend.spring.member.domain.oauth.OAuthAccessTokenService
import mashup.backend.spring.member.domain.oauth.OAuthUserService
import mashup.backend.spring.member.presentation.api.member.MemberResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class LoginService(
        @Qualifier("gitHubAccessTokenService")
        private val gitHubAccessTokenService: OAuthAccessTokenService,
        @Qualifier("gitHubUserService")
        private val gitHubUserService: OAuthUserService,
        private val memberService: MemberService,
        private val memberAssembler: MemberAssembler
) {
    fun loginWithGitHub(code: String): MemberResponse {
        val oAuthAccessToken = gitHubAccessTokenService.getAccessToken(code)
        val githubUser = gitHubUserService.getUser(oAuthAccessToken.accessToken)
        LoggerFactory.getLogger(this::class.java).info("githubUser: $githubUser")
        val member = memberService.getOrCreateMember(IdProviderInfo.github(githubUser.id))
        return memberAssembler.toMemberResponse(member)
    }
}