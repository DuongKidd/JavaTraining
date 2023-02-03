package com.smartosc.training.repositories;

import com.smartosc.training.entity.BankDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Duong NV on 4/20/2020.
 */

@Repository
public interface BankDetailRepository extends JpaRepository<BankDetail, Integer> {
}
