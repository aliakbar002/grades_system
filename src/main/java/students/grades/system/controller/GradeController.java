package students.grades.system.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import students.grades.system.dto.GradeDto;
import students.grades.system.model.Grade;
import students.grades.system.responses.ApiResponse;
import students.grades.system.responses.GradeResponse;
import students.grades.system.services.GradeService;

@RestController
@Validated
@RequestMapping("/grade")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @PostMapping
    public ResponseEntity<ApiResponse<GradeResponse>> assignGrade(@RequestBody @Valid GradeDto dto) {
        try {
            GradeResponse grade = gradeService.assignGrade(dto);
            return ResponseEntity.status(201).body(
                    new ApiResponse<>(true, "Grade recorded successfully", grade)
            );
        } catch (EntityNotFoundException | IllegalStateException e) {
            return ResponseEntity.status(400).body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ApiResponse<>(false, "Something went wrong", null));
        }
    }

    @GetMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<ApiResponse<GradeResponse>> getStudentGrade(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {
        try {
            GradeResponse response = gradeService.getStudentGradesInCourse(studentId, courseId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Grade fetched successfully", response));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ApiResponse<>(false, "Something went wrong", null));
        }
    }


}
