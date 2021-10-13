package se.tedbrink.spring_boot_restapi_intro_lecture.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.tedbrink.spring_boot_restapi_intro_lecture.model.Student;

import java.net.URI;
import java.time.LocalDate;
import java.util.*;

@RestController
public class StudentController {

    private List<Student> students = new ArrayList<>(
            Arrays.asList(
                    new Student("1234", "Kalle", "Anka", LocalDate.parse("1952-01-01"), "1952-01-01-9999", "Ankeborg"),
                    new Student("98456", "Janne", "Långben", LocalDate.parse("1954-01-01"), "1954-01-01-1234", "Ankeborg")
            )
    );

    @GetMapping("/firstresource")
    public ResponseEntity<Student> getResource() {
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(students.get(0));
    }

//    @GetMapping("/api/students") ////// Kan inte ha mer än en metod för findAll, genericFind() har defaultValue= "all"
//    public ResponseEntity<List<Student>> findAll() {
//
//        if (students.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(students);
//    }

    @PostMapping("/api/students")
    public ResponseEntity<Student> create(@RequestBody Student student) {
        students.add(student);
        return  ResponseEntity.created(URI.create("localhost:8080/api/student/" + student.getStudentId())).body(student);
    }

    @GetMapping("api/students/{id}")
    public ResponseEntity<Student> findById(@PathVariable("id") String studentId) {

        Optional<Student> foundStudent = students.stream()
                .filter( student -> student.getStudentId().equals(studentId))
                .findFirst();

        return ResponseEntity.ok(foundStudent.get());
    }

    @GetMapping("/api/students")
    public ResponseEntity<?> genericFind(
        @RequestParam(name = "type", defaultValue = "all") String type,
        @RequestParam(name = "value", defaultValue = "all") String value
    ){
        switch (type.toLowerCase().trim()) {
            case "all":
                return ResponseEntity.ok(students);
            case "firstname":
                return ResponseEntity.ok(students.stream().filter(student -> student.getFirstName().equals(value)));
            case "id":
                return ResponseEntity.ok(students.stream().filter(student -> student.getStudentId().equals(value)).findFirst());
            default:
                return ResponseEntity.badRequest().body("Invalid Type");
        }
    }

    @PutMapping("/api/students/{id}")
    public ResponseEntity<Student> update(@PathVariable("id") String studentId,
                                          @RequestBody Student student) {
        if(studentId.equals(student.getStudentId())) {

            Student original = students.stream().filter(s -> student.getStudentId().equals(studentId)).findFirst().get();
//            students.remove(original);

            original.setFirstName(student.getFirstName());
            original.setLastName(student.getLastName());
            original.setBirthDate(student.getBirthDate());
            original.setPsn(student.getPsn());
            original.setAddress(student.getAddress());

//            students.add(original);

            return ResponseEntity.ok().body(original);
        }else
            return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/api/students/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String studentId) {
        Student original = students.stream().filter(s -> s.getStudentId().equals(studentId)).findFirst().get();
        boolean wasRemoved = students.remove(original);

        if (wasRemoved) {
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.badRequest().build();
        }
    }
}




















