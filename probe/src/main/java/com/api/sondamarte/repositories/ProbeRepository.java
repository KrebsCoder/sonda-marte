package com.api.sondamarte.repositories;

import com.api.sondamarte.models.PlanetModel;
import com.api.sondamarte.models.ProbeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProbeRepository extends JpaRepository<ProbeModel, UUID> {

    Optional<ProbeModel> findByName(String name);
}
