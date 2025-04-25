package students.grades.system.services;


import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import students.grades.system.responses.EnrolledStudentResponseDto;
import students.grades.system.responses.GetStudentResponseDto;
import students.grades.system.responses.PaginatedStudentResponseDto;
import students.grades.system.responses.SimpleCourseDto;
import students.grades.system.dto.CreateStudentDto;
import students.grades.system.dto.UpdateStudentDto;
import students.grades.system.model.Course;
import students.grades.system.model.Student;
import students.grades.system.repository.CourseRepository;
import students.grades.system.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public Student createStudent(CreateStudentDto dto) {
        if (studentRepository.existsByName(dto.getName())) {
            throw new EntityExistsException("A student with the same name already exists.");
        }
        if (studentRepository.existsByEmail(dto.getEmail())) {
            throw new EntityExistsException("A student with the same email already exists.");
        }
        Student student = new Student();
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        return studentRepository.save(student);
    }

    public List<GetStudentResponseDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private GetStudentResponseDto convertToDTO(Student student) {
        GetStudentResponseDto studentDTO = new GetStudentResponseDto();
        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setCourses(student.getCourses().stream()
                .map(Course::getName) // Extract course name
                .collect(Collectors.toList()));
        return studentDTO;
    }


    public GetStudentResponseDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        return convertToDTO(student);
    }
    public Student updateStudent(Long id, UpdateStudentDto dto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        if (dto.getName() != null) {
            student.setName(dto.getName());
        }
        if (dto.getEmail() != null) {
            student.setEmail(dto.getEmail());
        }

        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new EntityNotFoundException("Student not found");
        }
        studentRepository.deleteById(id);
    }

    public EnrolledStudentResponseDto enrollStudentInCourses(Long studentId, List<Long> courseIds) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        List<Course> existingCourses = student.getCourses();
        List<Course> allRequestedCourses = courseRepository.findAllById(courseIds);

        if (allRequestedCourses.isEmpty()) {
            throw new EntityNotFoundException("No courses found for the given IDs");
        }

        List<String> alreadyAssignedCourseNames = new ArrayList<>();
        List<Course> newlyAssignedCourses = new ArrayList<>();

        for (Course course : allRequestedCourses) {
            boolean alreadyEnrolled = existingCourses.stream()
                    .anyMatch(existing -> existing.getId().equals(course.getId()));

            if (alreadyEnrolled) {
                alreadyAssignedCourseNames.add(course.getName()); // ✅ add course name
            } else {
                newlyAssignedCourses.add(course);
            }
        }

        // Assign only new courses
        student.getCourses().addAll(newlyAssignedCourses);
        studentRepository.save(student);

        // Log or return the duplicate courses
        if (!alreadyAssignedCourseNames.isEmpty()) {
            String duplicates = String.join(", ", alreadyAssignedCourseNames);
            throw new IllegalStateException("Courses are already assigned to the student: " + duplicates);
        }
        // Prepare response DTO
        List<SimpleCourseDto> courseDTOs = student.getCourses().stream()
                .map(c -> new SimpleCourseDto(c.getId(), c.getName(), c.getDescription()))
                .toList();
        return new EnrolledStudentResponseDto(student.getId(), student.getName(), student.getEmail(), courseDTOs);
    }


    public Page<PaginatedStudentResponseDto> getPaginatedStudents(String name, Long courseId,Pageable pageable) {
        Page<Student> page;

        if (name != null && courseId != null) {
            page = studentRepository.findByNameContainingIgnoreCaseAndCourses_Id(name, courseId, pageable);
        } else if (name != null) {
            page = studentRepository.findByNameContainingIgnoreCase(name, pageable);
        } else if (courseId != null) {
            page = studentRepository.findByCourses_Id(courseId, pageable);
        } else {
            page = studentRepository.findAll(pageable);
        }
        return page.map(student -> new PaginatedStudentResponseDto(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getCourses().stream()
                        .map(course -> new SimpleCourseDto(course.getId(), course.getName(), course.getDescription()))
                        .toList()
        ));
    }

}
