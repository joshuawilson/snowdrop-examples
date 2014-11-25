package org.jboss.snowdrop.samples.sportsclub.dao.hibernate;

import org.jboss.snowdrop.samples.sportsclub.domain.repository.criteria.ReservationSearchCriteria;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.Assert;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.EquipmentRepository;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.criteria.RangeCriteria;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.criteria.Range;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.EquipmentType;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Equipment;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

/**
 * @author <a href="mailto:lvlcek@redhat.com">Lukas Vlcek</a>
 */
@ContextConfiguration(locations = {"classpath:test-db-infrastructure.xml",
      "classpath:test-hibernate-infrastructure.xml",
      "classpath:test-dao-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TestHibernateEquipmentRepository
{
   @Autowired
   EquipmentRepository equipmentRepository;

   @Test
   public void testEquipmentTypes()
   {
      EquipmentType[] types = equipmentRepository.getEquipmentTypes();
      Assert.assertEquals(3,types.length);
   }

   @Test
   public void testRangeCriteria()
   {
      RangeCriteria criteria = new RangeCriteria();
      criteria.setRange(new Range(1,3));
      Collection<Equipment> equipments =  equipmentRepository.findByCriteria(criteria);
      Assert.assertEquals(3, equipments.size());
   }


   @Test
   public void testSearchUnreserved()
   {
      Date from = getDate(2009, 1, 1);
      Date to = getDate(2009, 12, 31);

      ReservationSearchCriteria criteria = new ReservationSearchCriteria();
      criteria.setFromDate(from);
      criteria.setToDate(to);

      Collection<Equipment> equipments =  equipmentRepository.findUnreserved(criteria);
      Assert.assertEquals(1, equipments.size());
   }

   @Test
   public void testCountUnreserved()
   {
      Date from = getDate(2009, 1, 1);
      Date to = getDate(2009, 12, 31);

      ReservationSearchCriteria criteria = new ReservationSearchCriteria();
      criteria.setFromDate(from);
      criteria.setToDate(to);

      long count =  equipmentRepository.countUnreserved(criteria);
      Assert.assertEquals(1l, count);
   }

   private Date getDate(int year, int month, int day)
   {
      Calendar cal = Calendar.getInstance(Locale.US);
      cal.clear();
      cal.set(year, month - 1, day);
      return cal.getTime();
   }
}
