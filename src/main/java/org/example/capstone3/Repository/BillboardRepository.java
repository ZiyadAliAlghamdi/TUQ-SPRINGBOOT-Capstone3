package org.example.capstone3.Repository;

import org.example.capstone3.Model.Billboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillboardRepository extends JpaRepository<Billboard, Integer> {
    Billboard findBillboardById(Integer id);
}
