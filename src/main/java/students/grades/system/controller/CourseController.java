package students.grades.system.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import students.grades.system.dto.CreateCourseDto;
import students.grades.system.dto.UpdateCourseDto;
import students.grades.system.model.Course;
import students.grades.system.responses.ApiResponse;
import students.grades.system.responses.GetCourseResponseDto;
import students.grades.system.responses.PaginatedCourseResponseDto;
import students.grades.system.responses.PaginatedResponse;
import students.grades.system.services.CourseService;

import java.util.List;

@RestController
@Validated
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<ApiResponse<Course>> createCourse(@RequestBody @Valid CreateCourseDto dto) {
        try {
            Course course = courseService.createCourse(dto);
            return ResponseEntity.status(201).body(
                    new ApiResponse<>(true, "Course added successfully", course)
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new ApiResponse<>(false, "Unable to add course: "+e.getMessage(), null)
            );
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllCourses() {
        List<GetCourseResponseDto> courses = courseService.getAllCourses();
        if (courses.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(courses); // 200 OK with data
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
        try {
            GetCourseResponseDto course = courseService.getCourseById(id);
            return ResponseEntity.ok(course);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(
                    new ApiResponse<>(false, "Course not found", null)
            );
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Course>> updateCourse(@PathVariable Long id, @RequestBody UpdateCourseDto dto) {
        try {
            Course course = courseService.updateCourse(id, dto);
            return ResponseEntity.ok(new ApiResponse<>(true, "Course updated", course));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(
                    new ApiResponse<>(false, "Unable to update course: " + e.getMessage(), null)
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCourse(@PathVariable Long id) {
        try {
            courseService.deleteCourse(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Course deleted", null));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(
                    new ApiResponse<>(false, "Unable to delete course: " + e.getMessage(), null)
            );
        }
    }

    @GetMapping("/paginated")
    public ResponseEntity<PaginatedResponse<PaginatedCourseResponseDto>> getPaginatedCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size) {
        Page<PaginatedCourseResponseDto> coursePage = courseService.getPaginatedCourses(PageRequest.of(page, size));
        return ResponseEntity.ok(new PaginatedResponse<>(coursePage));
    }

}
