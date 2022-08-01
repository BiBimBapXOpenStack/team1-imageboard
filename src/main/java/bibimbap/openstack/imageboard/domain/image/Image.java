package bibimbap.openstack.imageboard.domain.image;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity @Builder @DynamicInsert @DynamicUpdate
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(255)")
    private String imageName;

    @Column(columnDefinition = "VARCHAR(30000)")
    private String imageURL;
}
