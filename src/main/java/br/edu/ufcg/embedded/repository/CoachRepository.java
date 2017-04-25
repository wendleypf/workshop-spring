package br.edu.ufcg.embedded.repository;

import br.edu.ufcg.embedded.model.Coach;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CoachRepository extends CrudRepository<Coach, Long> {
    List<Coach> findAll();
    Coach getByEmail(String email);
}
