package org.api.stats;

import org.api.stats.entity.ClassStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatRepository extends JpaRepository <ClassStats, String> {

}
