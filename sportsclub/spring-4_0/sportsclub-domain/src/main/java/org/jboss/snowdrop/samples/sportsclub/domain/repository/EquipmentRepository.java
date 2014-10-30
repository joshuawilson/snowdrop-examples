package org.jboss.snowdrop.samples.sportsclub.domain.repository;

import java.util.Collection;
import java.util.List;

import org.jboss.snowdrop.samples.sportsclub.domain.entity.Equipment;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.EquipmentType;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.criteria.RangeCriteria;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.criteria.ReservationSearchCriteria;

/**
 *
 */
public interface EquipmentRepository extends Repository<Equipment, Long> {

    /**
     * @return All available {@link EquipmentType}s
     */
    public EquipmentType[] getEquipmentTypes();

    Collection<Equipment> findByCriteria(RangeCriteria criteria);

    List<Equipment> findUnreserved(ReservationSearchCriteria criteria);

    Long countUnreserved(ReservationSearchCriteria criteria);
}
