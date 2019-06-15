package ir.brochure.SpringDataRestStepByStep.entity;

import lombok.Data;
import org.hibernate.annotations.Nationalized;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue(generator = "CommentIdSeq")
    private Long id;

    @Nationalized
    private String text;

    private String submitBy;

}
