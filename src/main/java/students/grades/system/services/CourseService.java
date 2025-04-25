package students.grades.system.services;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import students.grades.system.dto.CreateCourseDto;
import students.grades.system.dto.UpdateCourseDto;
import students.grades.system.model.Course;
import students.grades.system.model.Student;
import students.grades.system.repository.CourseRepository;
import students.grades.system.responses.GetCourseResponseDto;
import students.grades.system.responses.PaginatedCourseResponseDto;
import students.grades.system.responses.SimpleStudentDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course createCourse(CreateCourseDto dto) {
        if (courseRepository.existsByName(dto.getName())) {
            throw new EntityExistsException("A course with the same name already exists.");
        }
        Course course = new Course();
        course.setName(dto.getName());
        course.setDescription(dto.getDescription());
        return courseRepository.save(course);
    }

    public List<GetCourseResponseDto> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private GetCourseResponseDto convertToDTO(Course course) {
        GetCourseResponseDto courseDTO = new GetCourseResponseDto();
        courseDTO.setId(course.getId());
        courseDTO.setName(course.getName());
        courseDTO.setDescription(course.getDescription());

        // Map student names
        List<String> studentNames = course.getStudents().stream()
                .map(Student::getName) // Get the name of each student
                .collect(Collectors.toList());
        courseDTO.setStudentNames(studentNames);

        return courseDTO;
    }

    public GetCourseResponseDto getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));
        return convertToDTO(course);
    }

    public Course updateCourse(Long id, UpdateCourseDto dto) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        if (dto.getName() != null) {
            course.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            course.setDescription(dto.getDescription());
        }

        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new EntityNotFoundException("Course not found");
        }
        courseRepository.deleteById(id);
    }

    public Page<PaginatedCourseResponseDto> getPaginatedCourses(Pageable pageable) {
        Page<Course> coursePage = courseRepository.findAll(pageable);

        return coursePage.map(course -> new PaginatedCourseResponseDto(
                course.getId(),
                course.getName(),
                course.getDescription(),
                course.getStudents().stream()
                        .map(student -> new SimpleStudentDto(student.getId(), student.getName(), student.getEmail()))
                        .toList()
        ));
    }

}
