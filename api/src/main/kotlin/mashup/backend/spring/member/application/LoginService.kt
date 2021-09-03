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
        return loginWithOAuth(code, null, IdProviderType.GITHUB)
    }

    fun loginWithNaver(code: String, state: String): MemberResponse {
        return loginWithOAuth(code, state, IdProviderType.NAVER)
    }

    private fun loginWithOAuth(code: String, state: String?, idProviderType: IdProviderType): MemberResponse {
        val oAuthAccessToken = oAuthAccessTokenServiceFactory.get(idProviderType).getAccessToken(code, state)
        val user = oAuthUserServiceFactory.get(idProviderType).getUser(oAuthAccessToken.accessToken)
        log.info("${idProviderType}_USER : $user")
        val member = memberService.getOrCreateMember(IdProviderInfo(idProviderType, user.id))
        return memberAssembler.toMemberResponse(member)
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)
    }
}

