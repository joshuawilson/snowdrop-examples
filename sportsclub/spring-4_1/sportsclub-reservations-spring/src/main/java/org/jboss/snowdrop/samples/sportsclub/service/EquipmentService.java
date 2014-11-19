package org.jboss.snowdrop.samples.sportsclub.service;

import org.jboss.snowdrop.samples.sportsclub.domain.entity.EquipmentType;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Equipment;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:lvlcek@redhat.com">Lukas Vlcek</a>
 */
public interface EquipmentService {

    public EquipmentType[] getEquipmentTypes();

    public Equipment findEquipmentById(long id);

    public Collection<Equipment> getAllEquipments();

    public Long countAllEquipments();

    public Collection<Equipment> getAllEquipments(int firstResult, int maxResults);

    List<Equipment> getUnreservedEquipments(Date fromDate, Date toDate, Integer nim, Integer max,
            List<EquipmentType> types);

    Long countUnreservedEquipmentsForRange(Date fromDate, Date toDate, List<EquipmentType> types);

}
