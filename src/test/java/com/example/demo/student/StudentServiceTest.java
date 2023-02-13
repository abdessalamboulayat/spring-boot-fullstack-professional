package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import com.example.demo.student.exception.StudentNotFoundException;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    private AutoCloseable autoCloseable;
    private StudentService underTest;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new StudentService(studentRepository);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void getAllStudents() {
        //When
        underTest.getAllStudents();
        //Then
        verify(studentRepository).findAll();
    }

    @Test
    void canAddStudent() {
        //Given
        Student student = new Student("abdessalam","abdessalam@gmail.com",Gender.MALE);
        //When
        underTest.addStudent(student);
        //Then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());

        Student capturedStudent = studentArgumentCaptor.getValue();
        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void AddStudentThrowExceptionEmailTaken() {
        //Given
        Student student = new Student("abdessalam","abdessalam@gmail.com",Gender.MALE);
        given(studentRepository.selectExistsEmail(student.getEmail())).willReturn(true);
        //Then
        assertThatThrownBy(()->underTest.addStudent(student))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Email " + student.getEmail() + " taken");
    }

    @Test
    void canDeleteStudent() {
        //Given
        Long id = 1L;
        given(studentRepository.existsById(id)).willReturn(true);
        //When
        underTest.deleteStudent(id);
        //Given
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.TYPE);
        verify(studentRepository).deleteById(longArgumentCaptor.capture());

        Long idCaptured = longArgumentCaptor.getValue();
        assertThat(idCaptured).isEqualTo(id);
    }

    @Test
    void canDeleteStudentWillThrowException() {
        //Given
        Long id = 1L;
        given(studentRepository.existsById(id)).willReturn(false);
        //When
        //Given
        assertThatThrownBy(()->underTest.deleteStudent(id))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessage("Student with id " + id + " does not exists");
    }
}