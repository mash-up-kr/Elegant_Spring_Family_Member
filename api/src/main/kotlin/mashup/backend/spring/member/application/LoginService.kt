package mashup.backend.spring.member.application

import mashup.backend.spring.member.domain.IdProviderInfo
import mashup.backend.spring.member.domain.IdProviderType
import mashup.backend.spring.member.domain.MemberService
import org.springframework.stereotype.Service

@Service
class LoginService(
        private val memberService: MemberService
) {
    fun loginWithGitHub(githubUserId: String) {
        memberService.getOrCreateMember(IdProviderInfo(IdProviderType.GITHUB, githubUserId))
        // TODO: 신규 회원이면 닉네임 등 업데이트, 회원 정보 dto 리턴
    }
}