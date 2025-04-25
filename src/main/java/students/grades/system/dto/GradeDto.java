package students.grades.system.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GradeDto {
    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Course ID is required")
    private Long courseId;

    @NotNull(message = "Grade value is required")
    private Double gradeValue;

    public @NotNull(message = "Student ID is required") Long getStudentId() {
        return studentId;
    }

    public void setStudentId(@NotNull(message = "Student ID is required") Long studentId) {
        this.studentId = studentId;
    }

    public @NotNull(message = "Course ID is required") Long getCourseId() {
        return courseId;
    }

    public void setCourseId(@NotNull(message = "Course ID is required") Long courseId) {
        this.courseId = courseId;
    }

    public @NotNull(message = "Grade value is required") Double getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(@NotNull(message = "Grade value is required") Double gradeValue) {
        this.gradeValue = gradeValue;
    }
}
