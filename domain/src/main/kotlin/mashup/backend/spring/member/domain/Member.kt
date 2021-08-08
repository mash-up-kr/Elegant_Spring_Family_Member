package mashup.backend.spring.member.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var memberId: Long? = null

    var name: String? = null
    var nickname: String? = null
    var email: String? = null

    @CreatedDate
    lateinit var createdAt: LocalDateTime

    @LastModifiedDate
    lateinit var updatedAt: LocalDateTime

    @Enumerated(EnumType.STRING)
    var status: MemberStatus = MemberStatus.ACTIVE


    @Column(columnDefinition = "boolean default false")
    var deleted: Boolean = false
}