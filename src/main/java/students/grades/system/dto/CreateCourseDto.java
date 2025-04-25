package students.grades.system.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCourseDto {
    @NotBlank(message = "Course name is required")
    private String name;

    private String description;

    public @NotBlank(message = "Course name is required") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Course name is required") String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
