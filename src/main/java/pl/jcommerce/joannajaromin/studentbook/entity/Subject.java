package pl.jcommerce.joannajaromin.studentbook.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="subjects")
@Data
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Integer id;

    @Column(name = "subject_name")
    private String name;

    @OneToMany(mappedBy = "grades")
    private List<Grade> grades;

}
