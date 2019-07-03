package pl.jcommerce.joannajaromin.studentbook.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "grades")
@Data
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id")
    private final Integer id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private final Student student;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private final Subject subject;

    @Column(name = "grade")
    private final Integer grade;
}
