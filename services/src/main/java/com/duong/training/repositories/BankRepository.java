/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.duong.training.repositories;

import com.duong.training.entity.Bank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<Bank, Integer> {

    Optional<Bank> findByCode(String code);

    Optional<Bank> findByLegalName(String legalName);

    Optional<Bank> findByPrefixCard(String prefixCard);

    @Query("FROM Bank b WHERE b.code LIKE %:searchValue%" +
            " OR b.legalName LIKE %:searchValue%" +
            " OR b.prefixCard LIKE %:searchValue%" +
            " OR b.modifiedBy LIKE %:searchValue%")
    Page<Bank> searchByNameAndDes(@Param("searchValue") String searchValue, Pageable pageable);

    List<Bank> findByCodeOrLegalNameOrPrefixCard(String code, String legalName, String prefixCard);

}
