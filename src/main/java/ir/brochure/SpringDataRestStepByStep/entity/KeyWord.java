package ir.brochure.SpringDataRestStepByStep.entity;

import lombok.Data;
import org.hibernate.annotations.Nationalized;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class KeyWord {

    @Id
    @GeneratedValue(generator = "KeywordIdSeq")
    private Long id;

    @Nationalized
    private String word;

}
