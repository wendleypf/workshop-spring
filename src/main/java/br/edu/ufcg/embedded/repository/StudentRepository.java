package br.edu.ufcg.embedded.repository;

import br.edu.ufcg.embedded.model.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudentRepository extends CrudRepository<Student, Long> {
    List<Student> findAll();
    Student getByEmail(String email);
}
