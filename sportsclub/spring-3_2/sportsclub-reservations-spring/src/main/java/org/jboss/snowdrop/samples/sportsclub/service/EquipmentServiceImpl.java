package org.jboss.snowdrop.samples.sportsclub.service;

import static org.jboss.snowdrop.samples.sportsclub.service.CriteriaUtils.createReservationSearchCriteria;

import org.jboss.snowdrop.samples.sportsclub.domain.entity.EquipmentType;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Equipment;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.EquipmentRepository;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.criteria.RangeCriteria;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.criteria.Range;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.criteria.ReservationSearchCriteria;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:lvlcek@redhat.com">Lukas Vlcek</a>
 */
@Transactional(readOnly = true)
public class EquipmentServiceImpl implements EquipmentService {

    private EquipmentRepository equipmentRepository;

    public EquipmentType[] getEquipmentTypes() {
        return equipmentRepository.getEquipmentTypes();
    }

    public Equipment findEquipmentById(long id) {
        return equipmentRepository.findById(id);
    }

    public Collection<Equipment> getAllEquipments() {
        return equipmentRepository.findAll();
    }

    public Long countAllEquipments() {
        return equipmentRepository.countAll();
    }

    public Collection<Equipment> getAllEquipments(int firstResult, int maxResults) {
        RangeCriteria criteria = new RangeCriteria();
        criteria.setRange(new Range(firstResult, maxResults));
        return equipmentRepository.findByCriteria(criteria);
    }

    public List<Equipment> getUnreservedEquipments(Date fromDate, Date toDate, Integer nim, Integer max,
            List<EquipmentType> types) {
        ReservationSearchCriteria criteria = createReservationSearchCriteria(fromDate, toDate, nim, max, types);
        return equipmentRepository.findUnreserved(criteria);
    }

    public Long countUnreservedEquipmentsForRange(Date fromDate, Date toDate, List<EquipmentType> types) {
        ReservationSearchCriteria criteria = createReservationSearchCriteria(fromDate, toDate, 0, 0, types);
        return equipmentRepository.countUnreserved(criteria);
    }

    public EquipmentRepository getEquipmentRepository() {
        return equipmentRepository;
    }

    public void setEquipmentRepository(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }
}
