package com.auth.app;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.app.bean.Student;

@RestController
@RequestMapping("api/v1/student")
public class AuthController {

	private static final List<Student> STUDENTS = Arrays.asList(new Student(1, "Shivi"), new Student(2, "Toshi"),
			new Student(3, "Jhimi"));

	@GetMapping("/hello")
	public String sayHello() {
		return "Hello To All...!!";
	}

	@GetMapping("/{studentId}")
	public Student getStudentById(@PathVariable("studentId") Integer  id ) {
		return  STUDENTS.stream()
				.filter(student -> id.equals(student.getStudentId()))
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("Student id ::"+ id + " does not exist..!!"));
	}

}
