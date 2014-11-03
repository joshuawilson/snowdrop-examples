package org.jboss.snowdrop.samples.sportsclub.reservations.webflow.beans;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Reservation;
import org.jboss.snowdrop.samples.sportsclub.service.EquipmentService;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Equipment;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.context.FacesContext;

import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

/**
 * @author <a href="mailto:lvlcek@redhat.com">Lukas Vlcek</a>
 * @author Marius Bogoevici</a>
 */
public class EquipmentFilter extends AbstractExtendedDataModelHelper implements Serializable {

    private static final long serialVersionUID = -2449681423406089713L;

    @Autowired
    private transient EquipmentService equipmentService;

    private Reservation reservation;

    private Map<Long, Equipment> equipmentMap = new HashMap<Long, Equipment>();

    public EquipmentFilter() {
        super();
    }

    public Map<Long, ? extends Object> getDomainObjectMap() {
        return equipmentMap;
    }

    public Long getCurrentRowCount() {
        return equipmentService.countAllEquipments();
    }

    public void walk(FacesContext facesContext, DataVisitor dataVisitor, Range range, Object argument) {
        int firstResult = ((SequenceRange) range).getFirstRow();
        int maxResults = ((SequenceRange) range).getRows();
        List<Equipment> equipments = null;
        equipments = (List<Equipment>) equipmentService.getAllEquipments(firstResult, maxResults);
        for (Equipment e : equipments) {
            Long id = e.getId();
            equipmentMap.put(id, e);
            dataVisitor.process(facesContext, id, argument);
        }
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public EquipmentService getEquipmentService() {
        return equipmentService;
    }

    public void setEquipmentService(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    public void reset() {

    }
}
