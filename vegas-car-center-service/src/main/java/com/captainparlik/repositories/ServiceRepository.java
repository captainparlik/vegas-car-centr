package com.captainparlik.repositories;

import java.util.List;

import com.captainparlik.model.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service,Long> {

    List<Service> findAllByActive(boolean isActive);

}
