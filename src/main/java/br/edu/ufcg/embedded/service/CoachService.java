package br.edu.ufcg.embedded.service;

import br.edu.ufcg.embedded.model.Coach;
import br.edu.ufcg.embedded.repository.CoachRepository;
import br.edu.ufcg.embedded.util.Crud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoachService implements Crud<Coach> {
    private CoachRepository coachRepository;

    @Autowired
    public CoachService(CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
    }

    @Override
    public Coach create(Coach coach) {
        return coachRepository.save(coach);
    }

    @Override
    public List<Coach> getAll() {
        return coachRepository.findAll();
    }

    @Override
    public Coach getById(Long id) {
        return coachRepository.findOne(id);
    }

    public Coach getByEmail(String email){
        return coachRepository.getByEmail(email);
    }

    @Override
    public Coach update(Coach coach) {
        return coachRepository.exists(coach.getId()) ? coachRepository.save(coach) : null;
    }

    @Override
    public boolean removeById(Long id) {
        if (coachRepository.exists(id)) {
            coachRepository.delete(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll() {
        coachRepository.deleteAll();
        return true;
    }
}
