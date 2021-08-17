package mashup.backend.spring.member.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    lateinit var memberRepository: MemberRepository

    @Test
    fun save() {
        // given
        val member = Member(IdProviderInfo(
                idProviderType = IdProviderType.KAKAO,
                idProviderUserId = "kakaoUserId"
        ))
        // when
        memberRepository.save(member)
        // then
        val members = memberRepository.findAll()
        assertThat(members).contains(member)
    }
}