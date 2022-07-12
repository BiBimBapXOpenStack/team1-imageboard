package bibimbap.openstack.imageboard.domain.post;

import bibimbap.openstack.imageboard.domain.image.Image;
import bibimbap.openstack.imageboard.domain.member.Member;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity @Builder @DynamicInsert @DynamicUpdate
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "userId")
    private Member member;

    @Column(columnDefinition = "VARCHAR(255)")
    private String title;

    @Column(columnDefinition = "VARCHAR(255)")
    private String content;

    @OneToOne
    @JoinColumn(name = "imageId")
    private Image image;
}
