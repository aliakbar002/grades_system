package students.grades.system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import students.grades.system.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByName(String name);

    boolean existsByEmail(String email);

    Page<Student> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Student> findByCourses_Id(Long courseId, Pageable pageable);

    Page<Student> findByNameContainingIgnoreCaseAndCourses_Id(String name, Long courseId, Pageable pageable);

}
