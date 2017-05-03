package br.edu.ufcg.embedded.service;

import br.edu.ufcg.embedded.model.Student;
import br.edu.ufcg.embedded.repository.StudentRepository;
import br.edu.ufcg.embedded.util.Crud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService implements Crud<Student>{
    private StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student create(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student getById(Long id) {
        return studentRepository.findOne(id);
    }

    public Student getByEmail(String email) {
        return studentRepository.getByEmail(email);
    }

    @Override
    public Student update(Student student) {
        return studentRepository.exists(student.getId()) ? studentRepository.save(student) : null;
    }

    @Override
    public boolean removeById(Long id) {
        if (studentRepository.exists(id)) {
            studentRepository.delete(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll() {
        studentRepository.deleteAll();
        return true;
    }
}
