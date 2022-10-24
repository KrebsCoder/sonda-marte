package com.api.sondamarte.repositories;

import com.api.sondamarte.models.PlanetModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.UUID;

@Repository
public interface PlanetRepository extends JpaRepository<PlanetModel, UUID> {


}
