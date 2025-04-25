package students.grades.system.responses;

import lombok.Data;

import java.util.List;
@Data
public class PaginatedCourseResponseDto {
    private Long id;
    private String name;
    private String description;
    private List<SimpleStudentDto> students;

    public PaginatedCourseResponseDto() {
    }

    public PaginatedCourseResponseDto(Long id, String name, String description, List<SimpleStudentDto> students) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.students = students;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SimpleStudentDto> getStudents() {
        return students;
    }

    public void setStudents(List<SimpleStudentDto> students) {
        this.students = students;
    }
}