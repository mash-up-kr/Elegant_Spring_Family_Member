package mashup.backend.spring.member.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "members")
@EntityListeners(AuditingEntityListener::class)
class Member(
        /**
         * OAuth 인증 관련 정보
         */
        @Embedded
        val providerInfo: IdProviderInfo
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val memberId: Long = 0

    var name: String? = null
    var nickname: String? = null
    var email: String? = null

    /**
     * 생성 시각
     */
    @CreatedDate
    lateinit var createdAt: LocalDateTime

    /**
     * 수정 시각
     */
    @LastModifiedDate
    lateinit var updatedAt: LocalDateTime

    /**
     * 회원 상태
     */
    @Enumerated(EnumType.STRING)
    var status: MemberStatus = MemberStatus.ACTIVE

    /**
     * 삭제 여부
     */
    @Column(columnDefinition = "boolean default false")
    var deleted: Boolean = false
}