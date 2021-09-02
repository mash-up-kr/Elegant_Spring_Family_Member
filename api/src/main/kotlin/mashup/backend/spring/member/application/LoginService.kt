package mashup.backend.spring.member.application

import mashup.backend.spring.member.application.member.MemberAssembler
import mashup.backend.spring.member.domain.IdProviderInfo
import mashup.backend.spring.member.domain.IdProviderType
import mashup.backend.spring.member.domain.MemberService
import mashup.backend.spring.member.domain.oauth.OAuthAccessTokenServiceFactory
import mashup.backend.spring.member.domain.oauth.OAuthUserServiceFactory
import mashup.backend.spring.member.presentation.api.member.MemberResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class LoginService(
        private val oAuthAccessTokenServiceFactory: OAuthAccessTokenServiceFactory,
        private val oAuthUserServiceFactory: OAuthUserServiceFactory,
        private val memberService: MemberService,
        private val memberAssembler: MemberAssembler
) {
    fun loginWithGitHub(code: String): MemberResponse {
        val github = IdProviderType.GITHUB
        val oAuthAccessToken = oAuthAccessTokenServiceFactory.get(github).getAccessToken(code)
        val githubUser = oAuthUserServiceFactory.get(github).getUser(oAuthAccessToken.accessToken)
        log.info("githubUser: $githubUser")
        val member = memberService.getOrCreateMember(IdProviderInfo.github(githubUser.id))
        return memberAssembler.toMemberResponse(member)
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)
    }
}