package org.example.capstone3.Repository;

import org.example.capstone3.Model.Lessor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessorRepository extends JpaRepository<Lessor, Integer> {
    Lessor findLessorById(Integer id);
}
