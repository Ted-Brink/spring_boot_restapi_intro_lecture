package se.tedbrink.spring_boot_restapi_intro_lecture.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.tedbrink.spring_boot_restapi_intro_lecture.model.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class StudentController {

    private List<Student> students = new ArrayList<>(
            Arrays.asList(
                    new Student("1234", "kalle", "Anka", LocalDate.parse("1952-01-01"), "1952-01-01-9999", "Ankeborg"),
                    new Student("98456", "janne", "Långben", LocalDate.parse("1954-01-01"), "1954-01-01-1234", "Ankeborg")
            )
    );

    @GetMapping("/firstresource")
    public ResponseEntity<Student> getResource() {
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(students.get(0));
    }

    @GetMapping("/api/students")
    public ResponseEntity<List<Student>> findAll() {

        if (students.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(students);
    }
}