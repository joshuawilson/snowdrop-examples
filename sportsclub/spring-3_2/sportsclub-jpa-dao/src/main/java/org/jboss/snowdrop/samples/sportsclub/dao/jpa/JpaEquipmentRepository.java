package org.jboss.snowdrop.samples.sportsclub.dao.jpa;

import org.jboss.snowdrop.samples.sportsclub.domain.entity.Equipment;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.EquipmentType;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Reservation;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.EquipmentRepository;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.criteria.RangeCriteria;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.criteria.ReservationSearchCriteria;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

import java.util.Collection;
import java.util.List;

/**
 * @author Marius Bogoevici
 */
@Repository
public class JpaEquipmentRepository extends JpaRepository<Equipment, Long> implements EquipmentRepository {

    public JpaEquipmentRepository() {
        super(Equipment.class);
    }

    public EquipmentType[] getEquipmentTypes() {
        return EquipmentType.values();
    }

    @SuppressWarnings("unchecked")
    public Collection<Equipment> findByCriteria(RangeCriteria criteria) {
        Query query = entityManager.createQuery("FROM " + Equipment.class.getSimpleName());
        if (criteria.getRange() != null)
            query = applyRange(query, criteria.getRange());
        return query.getResultList();
    }

    public List<Equipment> findUnreserved(ReservationSearchCriteria criteria) {
        String reservationSearch = getQuery(criteria, "SELECT r.equipment ");

        Query query = entityManager.createQuery("FROM Equipment e "
                                              + "WHERE e not in (" + reservationSearch + ")");

        if (criteria.getFromDate() != null) {
            query.setParameter("from", criteria.getFromDate());
        }

        if (criteria.getToDate() != null) {
            query.setParameter("to", criteria.getToDate());
        }

        if (criteria.getEquipmentType() != null && !criteria.getEquipmentType().isEmpty()) {
            query.setParameter("equipmentTypes", criteria.getEquipmentType());
        }

        return (List<Equipment>) query.getResultList();
    }

    public Long countUnreserved(ReservationSearchCriteria criteria) {
        String reservationSearch = getQuery(criteria, "SELECT r.equipment ");

        Query query = entityManager.createQuery("SELECT (count(e.id)) "
                                              + "FROM Equipment e "
                                              + "WHERE e not in (" + reservationSearch + ")");

        if (criteria.getFromDate() != null) {
            query.setParameter("from", criteria.getFromDate());
        }

        if (criteria.getToDate() != null) {
            query.setParameter("to", criteria.getToDate());
        }

        if (criteria.getEquipmentType() != null && !criteria.getEquipmentType().isEmpty()) {
            query.setParameter("equipmentTypes", criteria.getEquipmentType());
        }

        if (criteria.getRange() != null) {
            applyRange(query, criteria.getRange());
        }

        return (Long) query.getSingleResult();
    }

    private String getQuery(ReservationSearchCriteria criteria, String select) {
        String q = (select != null ? select : "");
        String fetch = (select == null ? " left join fetch r.equipment" : "");

        q += "FROM " + Reservation.class.getSimpleName() + " r" + fetch + " WHERE 1 = 1";

        if (criteria.getFromDate() != null) {
            q += " AND r.from >= :from";
        }

        if (criteria.getToDate() != null) {
            q += " AND r.to <= :to";
        }

        if (criteria.getEquipmentType() != null && !criteria.getEquipmentType().isEmpty()) {
            q += " AND r.equipment.equipmentType IN (:equipmentTypes)";
        }

        return q;
    }
}
