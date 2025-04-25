package students.grades.system.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import students.grades.system.dto.EnrollRequestDto;
import students.grades.system.responses.*;
import students.grades.system.dto.CreateStudentDto;
import students.grades.system.dto.UpdateStudentDto;
import students.grades.system.model.Student;
import students.grades.system.services.StudentService;

import java.util.List;



@RestController
@RequestMapping("/student")
@Validated
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<ApiResponse<Student>> createStudent(@RequestBody @Valid CreateStudentDto dto) {
        try {
            Student student = studentService.createStudent(dto);
            return ResponseEntity.status(201).body(
                    new ApiResponse<>(true, "Student added successfully", student)
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new ApiResponse<>(false, "Unable to add student: " + e.getMessage(), null)
            );
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllStudents() {
        List<GetStudentResponseDto> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.ok(students); // 200 with data only
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        try {
            GetStudentResponseDto student = studentService.getStudentById(id);
            return ResponseEntity.ok(student); // 200 with data only
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(
                    new ApiResponse<>(false, "Student not found", null)
            );
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> updateStudent(@PathVariable Long id, @RequestBody UpdateStudentDto dto) {
        try {
            Student updated = studentService.updateStudent(id, dto);
            return ResponseEntity.ok(new ApiResponse<>(true, "Student record updated", updated));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "Unable to update: " + e.getMessage(), null)
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Student deleted", null));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "Unable to delete: " + e.getMessage(), null));
        }
    }

    @PostMapping("/enrollment")
    public ResponseEntity<ApiResponse<EnrolledStudentResponseDto>> enrollStudent(@RequestBody EnrollRequestDto dto) {
        try {
            EnrolledStudentResponseDto enrolled = studentService.enrollStudentInCourses(dto.getStudentId(), dto.getCourseIds());
            return ResponseEntity.ok(new ApiResponse<>(true, "Student enrolled in courses successfully", enrolled));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, e.getMessage(), null));
        }
        catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(new ApiResponse<>(false, e.getMessage(), null)); // 409 Conflict
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ApiResponse<>(false, "Something went wrong", null));
        }
    }

    @GetMapping("/paginated")
    public ResponseEntity<PaginatedResponse<PaginatedStudentResponseDto>> getPaginatedStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long courseId) {
        Page<PaginatedStudentResponseDto> studentsPage = studentService.getPaginatedStudents(name, courseId, PageRequest.of(page, size));
        return ResponseEntity.ok(new PaginatedResponse<>(studentsPage));
    }


}