package org.jboss.snowdrop.samples.sportsclub.service;

import java.util.Date;
import java.util.List;

import org.jboss.snowdrop.samples.sportsclub.domain.entity.EquipmentType;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.criteria.Range;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.criteria.ReservationSearchCriteria;

/**
 * @author Marius Bogoevici
 */
public class CriteriaUtils {

    public static ReservationSearchCriteria createReservationSearchCriteria(Date fromDate, Date toDate, Integer min,
            Integer max, List<EquipmentType> types) {
        ReservationSearchCriteria criteria = new ReservationSearchCriteria();
        criteria.setFromDate(fromDate);
        criteria.setToDate(toDate);
        if (min != null && max != null) {
            Range range = new Range(min, max);
            criteria.setRange(range);
        }
        if (types != null && types.size() > 0) {
            criteria.setEquipmentType(types);
        }
        return criteria;
    }
}
