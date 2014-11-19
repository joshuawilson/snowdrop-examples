package org.jboss.snowdrop.samples.sportsclub.dao.hibernate;

import static org.hibernate.criterion.Restrictions.and;
import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.ge;
import static org.hibernate.criterion.Restrictions.le;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Equipment;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.EquipmentType;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Reservation;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.EquipmentRepository;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.criteria.RangeCriteria;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.criteria.ReservationSearchCriteria;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class HibernateEquipmentRepository extends HibernateRepository<Equipment, Long> implements EquipmentRepository {

    public HibernateEquipmentRepository() {
        super(Equipment.class);
    }

    public EquipmentType[] getEquipmentTypes() {
        return EquipmentType.values();
    }

    @SuppressWarnings("unchecked")
    public Collection<Equipment> findByCriteria(RangeCriteria rangeCriteria) {
        Criteria criteria = convert(rangeCriteria);
        return criteria.list();
    }

    public List<Equipment> findUnreserved(ReservationSearchCriteria criteria) {
        Criteria equipmentCriteria = getCurrentSession().createCriteria(Equipment.class);

        equipmentCriteria.add(Subqueries.propertyNotIn("id", convert(criteria, "equip")));

        if (criteria.getRange() != null)
            equipmentCriteria = applyRange(equipmentCriteria, criteria.getRange());

        return equipmentCriteria.list();
    }

    public Long countUnreserved(ReservationSearchCriteria criteria) {
        Criteria equipmentCriteria = getCurrentSession().createCriteria(Equipment.class);

        equipmentCriteria.add(Subqueries.propertyNotIn("id", convert(criteria, "equip")));

        equipmentCriteria.setProjection(Projections.count("id"));
        return (Long) equipmentCriteria.uniqueResult();
    }

    private DetachedCriteria convert(ReservationSearchCriteria reservationSearchCriteria, String alias) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Reservation.class, alias);

        Date from = reservationSearchCriteria.getFromDate();
        Date to = reservationSearchCriteria.getToDate();

        if (from != null && to != null)
            criteria.add(and(ge("from", from), le("to", to)));
        else {
            if (from != null)
                criteria.add(ge("from", from));
            if (to != null)
                criteria.add(le("to", to));
        }

        if (reservationSearchCriteria.getEquipmentType() != null) {
            List<EquipmentType> types = reservationSearchCriteria.getEquipmentType();
            if (types.size() > 0) {
                Disjunction dis = Restrictions.disjunction();
                for (EquipmentType type : types) {
                    dis.add(eq("equipmentType", type));
                }
                criteria.createCriteria("equipment").add(dis);
            }

        }
        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.property("equipment.id"));

        criteria.setProjection(projectionList);

        return criteria;
    }

    private Criteria convert(RangeCriteria rangeCriteria) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Equipment.class);
        if (rangeCriteria.getRange() != null)
            criteria = applyRange(criteria, rangeCriteria.getRange());
        return criteria;
    }
}
