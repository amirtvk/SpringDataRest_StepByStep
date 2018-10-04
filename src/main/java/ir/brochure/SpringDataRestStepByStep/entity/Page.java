package ir.brochure.SpringDataRestStepByStep.entity;

import lombok.Data;
import org.hibernate.annotations.Nationalized;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Page {

    @Id
    @GeneratedValue(generator = "PageIdSeq")
    private Long id;

    @Nationalized
    private String title;

    @Nationalized
    private String description;

}
