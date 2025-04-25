package students.grades.system.responses;

import lombok.Data;

@Data
public class GradeResponse {
    private Long gradeId;
    private Long studentId;
    private String studentName;
    private Long courseId;
    private String courseName;
    private Double gradeValue;

    public GradeResponse() {
    }

    public GradeResponse(Long gradeId, Long studentId, String studentName, Long courseId, String courseName, Double gradeValue) {
        this.gradeId = gradeId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.courseId = courseId;
        this.courseName = courseName;
        this.gradeValue = gradeValue;
    }

    public Long getGradeId() {
        return gradeId;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Double getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(Double gradeValue) {
        this.gradeValue = gradeValue;
    }
}