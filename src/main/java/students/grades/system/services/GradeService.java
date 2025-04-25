package students.grades.system.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import students.grades.system.dto.GradeDto;
import students.grades.system.model.Course;
import students.grades.system.model.Grade;
import students.grades.system.model.Student;
import students.grades.system.repository.CourseRepository;
import students.grades.system.repository.GradeRepository;
import students.grades.system.repository.StudentRepository;
import students.grades.system.responses.GradeResponse;

@Service
public class GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public GradeResponse assignGrade(GradeDto dto) {
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        boolean isEnrolled = student.getCourses().stream()
                .anyMatch(c -> c.getId().equals(course.getId()));
        if (!isEnrolled) {
            throw new IllegalStateException("Student is not enrolled in this course");
        }

        if (gradeRepository.findByStudentAndCourse(student, course).isPresent()) {
            throw new IllegalStateException("Grade already assigned for this student in this course");
        }

        Grade grade = new Grade();
        grade.setStudent(student);
        grade.setCourse(course);
        grade.setGradeValue(dto.getGradeValue());

        Grade savedGrade = gradeRepository.save(grade);

        return new GradeResponse(
                savedGrade.getId(),
                student.getId(),
                student.getName(),
                course.getId(),
                course.getName(),
                savedGrade.getGradeValue()
        );
    }

    public GradeResponse getStudentGradesInCourse(Long studentId, Long courseId) {
        Grade grade = gradeRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new EntityNotFoundException("No grade's found for student in this course"));

        return new GradeResponse(
                grade.getId(),
                grade.getStudent().getId(),
                grade.getStudent().getName(),
                grade.getCourse().getId(),
                grade.getCourse().getName(),
                grade.getGradeValue()
        );
    }


}
