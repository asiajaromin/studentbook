package pl.jcommerce.joannajaromin.studentbook.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "grades")
public @Data class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Column(name = "grade")
    private Integer grade;
}
