package mashup.backend.spring.member.application.member

import mashup.backend.spring.member.domain.Member
import mashup.backend.spring.member.presentation.api.member.MemberResponse
import org.springframework.stereotype.Component

@Component
class MemberAssembler {
    fun toMemberResponse(member: Member) = MemberResponse(
            id = member.memberId,
            name = member.name
    )
}