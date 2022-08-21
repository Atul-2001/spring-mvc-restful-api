package com.signature.repository;

import com.signature.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {

  @Modifying(clearAutomatically = true, flushAutomatically = true)
  @Transactional
  @Query(value = "UPDATE Vendor v SET v.name = :#{#vendor.name} WHERE v.id = :#{#vendor.id}")
  Integer update(@Param("vendor") Vendor vendor);
}