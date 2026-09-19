package org.emat.repository;

import java.util.List;
import org.emat.entity.Dia3CInfoSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Dia3CInfoSeriesRepository extends JpaRepository<Dia3CInfoSeries, Long> {

    List<Dia3CInfoSeries> findAllByIsActiveTrue();
}