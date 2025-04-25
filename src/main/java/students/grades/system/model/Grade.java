package students.grades.system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Course course;

    @NotNull(message = "Grade value is required")
    private Double gradeValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public @NotNull(message = "Grade value is required") Double getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(@NotNull(message = "Grade value is required") Double gradeValue) {
        this.gradeValue = gradeValue;
    }
}

