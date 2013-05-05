package org.enhydra.shark.utilities.dods;

import java.util.*;

import com.lutris.appserver.server.sql.CoreDO;
import com.lutris.appserver.server.sql.DBTransaction;
import com.lutris.dods.builder.generator.dataobject.GenericDO;
import org.enhydra.shark.api.TransactionException;
import java.sql.SQLException;
import com.lutris.appserver.server.sql.DatabaseManagerException;
import com.lutris.dods.builder.generator.query.RefAssertionException;
import com.lutris.dods.builder.generator.query.DataObjectException;
import com.lutris.dods.builder.generator.query.QueryException;

public class Buffer {
   private static final List order = Arrays.asList(new String[]{
            //working objects
            "class org.enhydra.shark.instancepersistence.data.ActivityStateDO",
               "class org.enhydra.shark.instancepersistence.data.ProcessStateDO",
               "class org.enhydra.shark.instancepersistence.data.ResourceDO",
               "class org.enhydra.shark.instancepersistence.data.ProcessDefinitionDO",
               "class org.enhydra.shark.instancepersistence.data.ProcessDO",
               "class org.enhydra.shark.instancepersistence.data.ProcessDataDO",
               "class org.enhydra.shark.instancepersistence.data.ProcessDataWOBDO",
               "class org.enhydra.shark.instancepersistence.data.ProcessDataBLOBDO",
               "class org.enhydra.shark.instancepersistence.data.ActivityDO",
               "class org.enhydra.shark.instancepersistence.data.ActivityDataDO",
               "class org.enhydra.shark.instancepersistence.data.ActivityDataWOBDO",
               "class org.enhydra.shark.instancepersistence.data.ActivityDataBLOBDO",
               //"class org.enhydra.shark.instancepersistence.data.AndJoinEntryDO",
               //"class org.enhydra.shark.instancepersistence.data.DeadlineDO",
               //"class org.enhydra.shark.instancepersistence.data.AssignmentDO",
               "class org.enhydra.shark.instancepersistence.data.ProcessRequesterDO",
               // event audits
               "class org.enhydra.shark.eventaudit.data.EventTypeDO",
               "class org.enhydra.shark.eventaudit.data.ProcessStateEventAuditDO",
               "class org.enhydra.shark.eventaudit.data.ActivityStateEventAuditDO",
               "class org.enhydra.shark.eventaudit.data.StateEventAuditDO",
               "class org.enhydra.shark.eventaudit.data.CreateProcessEventAuditDO",
               "class org.enhydra.shark.eventaudit.data.AssignmentEventAuditDO",
               "class org.enhydra.shark.eventaudit.data.DataEventAuditDO",
               "class org.enhydra.shark.eventaudit.data.OldEventAuditDataDO",
               "class org.enhydra.shark.eventaudit.data.NewEventAuditDataDO",
               "class org.enhydra.shark.eventaudit.data.OldEventAuditDataWOBDO",
               "class org.enhydra.shark.eventaudit.data.OldEventAuditDataBLOBDO",
               "class org.enhydra.shark.eventaudit.data.NewEventAuditDataWOBDO",
               "class org.enhydra.shark.eventaudit.data.NewEventAuditDataBLOBDO"
         });

   private Map storage;
   private Map erasage;
   private Map readage;
   protected DBTransaction transaction;

   public Buffer(DBTransaction dbt) throws NullPointerException {
      transaction = dbt;
      storage = new HashMap();
      erasage = new HashMap();
      readage = new HashMap();
   }

   public void clear() {
      storage.clear();
      erasage.clear();
      readage.clear();
   }

   public void clear(String type) {
      storage.remove(type);
      erasage.remove(type);
      readage.remove(type);
   }

   public void store(CoreDO aDO) {
      if (null != aDO)
         store(aDO, aDO.getClass().toString());
   }

   public void erase(CoreDO aDO) {
      if (null != aDO)
         erase(aDO, aDO.getClass().toString());
   }

   public void _read(CoreDO aDO) {
      if (null != aDO)
         _read(aDO, aDO.getClass().toString());
   }

   public void store(CoreDO aDO, String type) {
      //System.err.println("__store__ "+ type +" "+aDO.toString());
      List l = (List)storage.get(type);
      if (null == l) {
         l = new ArrayList();
         storage.put(type, l);
      }
      if (!l.contains(aDO))
         l.add(aDO);
      l = (List)readage.get(type);
      if (null != l && l.contains(aDO))
         l.remove(aDO);
   }

   public void erase(CoreDO aDO, String type) {
      //System.err.println("__erase__ "+ type +" "+aDO.get_OId());
      List l = (List)readage.get(type);
      if (null != l && l.contains(aDO))
         l.remove(aDO);
      l = (List)storage.get(type);
      if (null != l && l.contains(aDO))
         l.remove(aDO);

      if (aDO.isPersistent()) {
         l = (List)erasage.get(type);
         if (null == l) {
            l = new ArrayList();
            erasage.put(type, l);
         }
         if (!l.contains(aDO))
            l.add(aDO);
      }
   }

   public void erase(Set s) {
      if (null != s && s.size() > 0) {
         Iterator it=s.iterator();
         while (it.hasNext()) {
            CoreDO cDO=(CoreDO)it.next();
            erase(cDO,cDO.getClass().toString());
         }
      }
   }

   public void _read(CoreDO aDO, String type) {
      //System.err.println("___read__ "+ type +" "+aDO.get_OId());
      List l = (List)readage.get(type);
      if (null == l) {
         l = new ArrayList();
         readage.put(type, l);
      }
      if (!l.contains(aDO))
         l.add(aDO);
   }

   private static ArrayList empty = new ArrayList(1);
   public Iterator iterator4type(String type) {
      List s = (List)storage.get(type);
      //List e = (List)erasage.get(type);
      List r = (List)readage.get(type);
      List l = new ArrayList();
      l = (ArrayList)empty.clone();
      if (null != r) {
         l.addAll(r);
      }
      if (null != s) {
         l.addAll(s);
      }
      return l.iterator();
   }

   public Iterator iterator4typeDeleted(String type) {
      List e = (List)erasage.get(type);
      List l = new ArrayList();
      l = (ArrayList)empty.clone();
      if (null != e) {
         l.addAll(e);
      }
      return l.iterator();
   }

   private void iterateType(String type) throws TransactionException {
      List ret = (List)storage.get(type);
      if (null != ret) {
         for (Iterator it = ret.iterator(); it.hasNext();) {
            CoreDO aDO = (CoreDO)it.next();
            transaction.insert(aDO);
            //System.err.print("*");
         }
      }
   }

   private boolean deleteType(String type) throws TransactionException {
      boolean hasDeletedEntities=false;
      List ret = (List)erasage.get(type);
      if (null != ret) {
         for (Iterator it = ret.iterator(); it.hasNext();) {
            GenericDO aDO = (GenericDO)it.next();
            //transaction.delete(aDO);
            //if (aDO.isPersistent()) {
            try {
               //System.out.println("Deleting obj "+aDO);
               aDO.delete();
               hasDeletedEntities=true;
            } catch (Exception e) {
               throw new TransactionException(e);
            }
            //}
         }
      }
      return hasDeletedEntities;
   }

   public void write() throws TransactionException {
      for (Iterator it = order.iterator(); it.hasNext();) {
         String type = (String)it.next();
         //System.err.print("\n__ "+ type +" ");
         iterateType(type);
         //System.err.println(type +" __");
      }

      // this must be done first because of assignment reevaluation, but it
      boolean write=deleteType("class org.enhydra.shark.instancepersistence.data.AssignmentDO");
      if (write) {
         try {
            transaction.write();
         } catch (SQLException e) {
            throw new TransactionException(e);
         }
      }
      iterateType("class org.enhydra.shark.instancepersistence.data.AssignmentDO");

      iterateType("class org.enhydra.shark.instancepersistence.data.DeadlineDO");
      write=deleteType("class org.enhydra.shark.instancepersistence.data.DeadlineDO");

      iterateType("class org.enhydra.shark.instancepersistence.data.AndJoinEntryDO");
      write=deleteType("class org.enhydra.shark.instancepersistence.data.AndJoinEntryDO") || write;

      write=deleteType("class org.enhydra.shark.instancepersistence.data.ProcessRequesterDO") || write;
      write=deleteType("class org.enhydra.shark.instancepersistence.data.ActivityDataBLOBDO") || write;
      write=deleteType("class org.enhydra.shark.instancepersistence.data.ActivityDataWOBDO") || write;
      write=deleteType("class org.enhydra.shark.instancepersistence.data.ActivityDataDO") || write;

      if (write) {
         try {
            transaction.write();
         } catch (SQLException e) {
            throw new TransactionException(e);
         }
      }
      write=deleteType("class org.enhydra.shark.instancepersistence.data.ActivityDO");
      write=deleteType("class org.enhydra.shark.instancepersistence.data.ProcessDataBLOBDO") || write;
      write=deleteType("class org.enhydra.shark.instancepersistence.data.ProcessDataWOBDO") || write;
      write=deleteType("class org.enhydra.shark.instancepersistence.data.ProcessDataDO") || write;

      //TODO: read instancepersistnce option for deleting???
      if (write) {
         try {
            transaction.write();
         } catch (SQLException e) {
            throw new TransactionException(e);
         }
      }

      write=deleteType("class org.enhydra.shark.instancepersistence.data.ProcessDO");
      if (write) {
         try {
            transaction.write();
         } catch (SQLException e) {
            throw new TransactionException(e);
         }
      }
      deleteType("class org.enhydra.shark.instancepersistence.data.ProcessDefinitionDO");

   }

}


