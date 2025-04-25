package students.grades.system.responses;

import lombok.Data;

import java.util.List;

@Data
public class EnrolledStudentResponseDto {
    private Long id;
    private String name;
    private String email;
    private List<SimpleCourseDto> courses;

    public EnrolledStudentResponseDto() {
    }

    public EnrolledStudentResponseDto(Long id, String name, String email, List<SimpleCourseDto> courses) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.courses = courses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<SimpleCourseDto> getCourses() {
        return courses;
    }

    public void setCourses(List<SimpleCourseDto> courses) {
        this.courses = courses;
    }
}
