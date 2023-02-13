package com.example.demo.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StudentRepositoryTest {

    @Autowired
    StudentRepository studentRepositoryTest;

    @AfterEach
    void tearDown() {
        studentRepositoryTest.deleteAll();
    }

    @Test
    void itShouldCheckIfStudentEmailExists() {

        //Given
        Student student = new Student("abdessalam", "abdessalam3@gmail.com",Gender.MALE);
        studentRepositoryTest.save(student);
        //When
        boolean expected = studentRepositoryTest.selectExistsEmail("abdessalam3@gmail.com");
        //Then
        assertThat(expected).isTrue();
    }

    @Test
    void itShouldCheckIfStudentEmailDoesNotExists() {

        //Given
        String email = "abdessalam@gmail.com";
        //When
        boolean expected = studentRepositoryTest.selectExistsEmail(email);
        //Then
        assertThat(expected).isFalse();
    }
}