package bibimbap.openstack.imageboard.domain.post;

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

    @Column(columnDefinition = "INTEGER")
    private Long user_id;

    @Column(columnDefinition = "VARCHAR(255)")
    private String title;

    @Column(columnDefinition = "VARCHAR(255)")
    private String content;

    @Column(columnDefinition = "INTEGER")
    private Long img_id;
}