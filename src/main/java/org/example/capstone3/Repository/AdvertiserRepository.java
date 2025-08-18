package org.example.capstone3.Repository;

import org.example.capstone3.Model.Advertiser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertiserRepository extends JpaRepository<Advertiser, Integer> {
    Advertiser findAdvertiserById(Integer id);
}
