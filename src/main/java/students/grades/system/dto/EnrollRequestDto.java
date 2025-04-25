package students.grades.system.dto;

import lombok.Data;

import java.util.List;

@Data
public class EnrollRequestDto {
    private Long studentId;
    private List<Long> courseIds;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public List<Long> getCourseIds() {
        return courseIds;
    }

    public void setCourseIds(List<Long> courseIds) {
        this.courseIds = courseIds;
    }
}
