package ir.brochure.SpringDataRestStepByStep.entity;

import lombok.Data;
import org.hibernate.annotations.Nationalized;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue(generator = "CommentIdSeq")
    private Long id;

    @Nationalized
    private String text;

    private String submitBy;

    private CommentStatus status;

    @Version
    private Long version;
}
