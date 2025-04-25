package students.grades.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import students.grades.system.model.Course;
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    boolean existsByName(String name);
}
