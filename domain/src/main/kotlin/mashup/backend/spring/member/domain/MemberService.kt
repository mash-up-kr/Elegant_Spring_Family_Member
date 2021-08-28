package mashup.backend.spring.member.domain

interface MemberService {
    fun getOrCreateMember(idProviderInfo: IdProviderInfo): Member
}