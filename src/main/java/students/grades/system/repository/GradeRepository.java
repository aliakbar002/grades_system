package students.grades.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import students.grades.system.model.Course;
import students.grades.system.model.Grade;
import students.grades.system.model.Student;

import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    Optional<Grade> findByStudentAndCourse(Student student, Course course);

    Optional<Grade> findByStudentIdAndCourseId(Long studentId, Long courseId);


}
