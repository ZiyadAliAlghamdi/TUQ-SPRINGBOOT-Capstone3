package org.example.capstone3.Repository;

import org.example.capstone3.Model.Billboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillboardRepository extends JpaRepository<Billboard, Integer> {
    Billboard findBillboardById(Integer id);

    @Query("select b from Billboard b where b.lessor.id = ?1")
    List<Billboard> findBillboardByLessor(Integer id);


    @Query("select b from Billboard b where b.district=?1")
    List<Billboard> findBillboardsByDistrict(String district);

    List<Billboard> findBillboardsByHeightAndWidth(Double height, Double width);

    List<Billboard> findAllByOrderByLessor_RatingAvgDesc();

    List<Billboard> findBillboardsByAvailabilityStatus(String availabilityStatus);
}
