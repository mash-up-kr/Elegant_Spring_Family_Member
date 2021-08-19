package mashup.backend.spring.member.domain

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberServiceImpl(
        private val memberRepository: MemberRepository
) : MemberService {
    @Transactional
    override fun getOrCreateMember(idProviderInfo: IdProviderInfo): Member {
        return memberRepository.findByProviderInfo(idProviderInfo)
                ?: memberRepository.save(Member(idProviderInfo))
    }
}