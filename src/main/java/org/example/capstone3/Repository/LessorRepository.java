package org.example.capstone3.Repository;

import org.example.capstone3.Model.Billboard;
import org.example.capstone3.Model.Lessor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessorRepository extends JpaRepository<Lessor, Integer> {
    Lessor findLessorById(Integer id);

}
