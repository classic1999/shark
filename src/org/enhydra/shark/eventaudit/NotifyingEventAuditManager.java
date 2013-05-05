package org.enhydra.shark.eventaudit;

import java.util.*;

import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.internal.eventaudit.*;
import org.enhydra.shark.api.internal.working.CallbackUtilities;

/**
 * TODO: document
 * 
 * @author <a href="daniel.frey@xmatrix.ch">Daniel Frey </a>
 * @version 0.2
 */
public class NotifyingEventAuditManager implements EventAuditManagerInterface {

   public static final EventType DATA_EVENT_TYPE = new EventType(DataEventAuditPersistenceInterface.class);

   public static final EventType ASSIGNMENT_EVENT_TYPE = new EventType(AssignmentEventAuditPersistenceInterface.class);

   public static final EventType STATE_EVENT_TYPE = new EventType(StateEventAuditPersistenceInterface.class);

   public static final EventType CREATION_EVENT_TYPE = new EventType(CreateProcessEventAuditPersistenceInterface.class);

   //    private static final Logger LOGGER =
   // Logger.getLogger(NotifyingEventAuditManager.class);
   //    private static final boolean DEBUG = LOGGER.isDebugEnabled();

   private static EventAuditManagerInterface delegate;

   private static final Map listeners = new HashMap();

   private static CallbackUtilities cus;

   private static boolean DEBUG;

   public void configure(CallbackUtilities _cus) throws RootException {
      cus = _cus;
      delegate.configure(cus);
      DEBUG = Boolean.valueOf(cus.getProperty("NotifyingEventAuditManager.Debug",
                                              "false"))
         .booleanValue();
      final String clazzName = cus.getProperty("NotifyingEventAuditManager.Delegate",
                                               "org.enhydra.shark.eventaudit.DODSEventAuditManager");
      try {
         final Class clazz = Class.forName(clazzName);
         delegate = (EventAuditManagerInterface) clazz.newInstance();
      } catch (ClassNotFoundException e) {
         cus.error("could not find delegate class", e.getMessage());
      } catch (IllegalAccessException e) {
         cus.error("cannot access delegate class", e.getMessage());
      } catch (InstantiationException e) {
         cus.error("cannot instantiate delegate class", e.getMessage());
      }
   }

   public void persist(AssignmentEventAuditPersistenceInterface aea,
                       SharkTransaction ti) throws EventAuditException {
      delegate.persist(aea, ti);
      fire(aea, ASSIGNMENT_EVENT_TYPE);
   }

   public void persist(DataEventAuditPersistenceInterface dea,
                       SharkTransaction ti) throws EventAuditException {
      delegate.persist(dea, ti);
      fire(dea, DATA_EVENT_TYPE);
   }

   public void persist(StateEventAuditPersistenceInterface sea,
                       SharkTransaction ti) throws EventAuditException {
      delegate.persist(sea, ti);
      fire(sea, STATE_EVENT_TYPE);
   }

   public void persist(CreateProcessEventAuditPersistenceInterface cpea,
                       SharkTransaction ti) throws EventAuditException {
      delegate.persist(cpea, ti);
      fire(cpea, CREATION_EVENT_TYPE);
   }

   public boolean restore(AssignmentEventAuditPersistenceInterface aea,
                          SharkTransaction ti) throws EventAuditException {
      final boolean b = delegate.restore(aea, ti);
      fire(aea, ASSIGNMENT_EVENT_TYPE);
      return b;
   }

   public boolean restore(DataEventAuditPersistenceInterface dea,
                          SharkTransaction ti) throws EventAuditException {
      final boolean b = delegate.restore(dea, ti);
      fire(dea, DATA_EVENT_TYPE);
      return b;
   }

   public boolean restore(StateEventAuditPersistenceInterface sea,
                          SharkTransaction ti) throws EventAuditException {
      final boolean b = delegate.restore(sea, ti);
      fire(sea, STATE_EVENT_TYPE);
      return b;
   }

   public boolean restore(CreateProcessEventAuditPersistenceInterface cpea,
                          SharkTransaction ti) throws EventAuditException {
      final boolean b = delegate.restore(cpea, ti);
      fire(cpea, CREATION_EVENT_TYPE);
      return b;
   }

   public List restoreProcessHistory(String procId, SharkTransaction ti) throws EventAuditException {
      final List list = delegate.restoreProcessHistory(procId, ti);
      return list;
   }

   public List restoreActivityHistory(String procId,
                                      String actId,
                                      SharkTransaction ti) throws EventAuditException {
      final List list = delegate.restoreActivityHistory(procId, actId, ti);
      return list;
   }

   public void delete(AssignmentEventAuditPersistenceInterface aea,
                      SharkTransaction ti) throws EventAuditException {
      delegate.delete(aea, ti);
      fire(aea, ASSIGNMENT_EVENT_TYPE);
   }

   public void delete(DataEventAuditPersistenceInterface dea,
                      SharkTransaction ti) throws EventAuditException {
      delegate.delete(dea, ti);
      fire(dea, DATA_EVENT_TYPE);
   }

   public void delete(StateEventAuditPersistenceInterface sea,
                      SharkTransaction ti) throws EventAuditException {
      delegate.delete(sea, ti);
      fire(sea, STATE_EVENT_TYPE);
   }

   public void delete(CreateProcessEventAuditPersistenceInterface cpea,
                      SharkTransaction ti) throws EventAuditException {
      delegate.delete(cpea, ti);
      fire(cpea, CREATION_EVENT_TYPE);
   }

   public AssignmentEventAuditPersistenceInterface createAssignmentEventAudit() {
      final AssignmentEventAuditPersistenceInterface audit = delegate.createAssignmentEventAudit();
      return audit;
   }

   public CreateProcessEventAuditPersistenceInterface createCreateProcessEventAudit() {
      final CreateProcessEventAuditPersistenceInterface audit = delegate.createCreateProcessEventAudit();
      return audit;
   }

   public DataEventAuditPersistenceInterface createDataEventAudit() {
      final DataEventAuditPersistenceInterface audit = delegate.createDataEventAudit();
      return audit;
   }

   public StateEventAuditPersistenceInterface createStateEventAudit() {
      final StateEventAuditPersistenceInterface audit = delegate.createStateEventAudit();
      return audit;
   }

   public String getNextId(String idName) throws EventAuditException {
      final String id = delegate.getNextId(idName);
      return id;
   }

   private static void fire(EventAuditPersistenceInterface persister,
                            EventType type) {

      synchronized (listeners) {
         if (DEBUG) {
            cus.debug("firing event for "
                      + (type == null ? "all types " : "type \""
                                                       + type + "\"")
                      + (persister.getActivityId() == null ? ""
                                                          : ", activity \""
                                                            + persister.getActivityId()
                                                            + "\"")
                      + (persister.getProcessId() == null ? ""
                                                         : ", instance \""
                                                           + persister.getProcessId()
                                                           + "\"")
                      + (persister.getProcessDefinitionId() == null ? ""
                                                                   : ", process \""
                                                                     + persister.getProcessDefinitionId()
                                                                     + "\"")
                      + (persister.getPackageId() == null ? ""
                                                         : ", package \""
                                                           + persister.getPackageId()
                                                           + "\""));
         }

         final Set listenerset = new HashSet();
         listenerset.addAll(collectListeners((Map) listeners.get(null),
                                             persister));
         listenerset.addAll(collectListeners((Map) listeners.get(type),
                                             persister));
         listenerset.remove(null);

         for (final Iterator iterator = listenerset.iterator(); iterator.hasNext();) {
            final EventAuditListener listener = (EventAuditListener) iterator.next();
            final EventAuditEvent event = new EventAuditEvent(NotifyingEventAuditManager.class);
            event.setPersister(persister);
            listener.eventAuditChanged(event);
         }
      }
   }

   private static Set collectListeners(final Map allTypes,
                                       EventAuditPersistenceInterface persister) {

      List list;
      final Set listenerset = new HashSet();
      if (allTypes != null) {
         list = (List) allTypes.get(null);
         if (list != null) {
            listenerset.addAll(list);
         }
         list = (List) allTypes.get(persister.getPackageId());
         if (list != null) {
            listenerset.addAll(list);
         }
         list = (List) allTypes.get(persister.getActivityId());
         if (list != null) {
            listenerset.addAll(list);
         }
         list = (List) allTypes.get(persister.getProcessId());
         if (list != null) {
            listenerset.addAll(list);
         }
         list = (List) allTypes.get(persister.getProcessDefinitionId());
         if (list != null) {
            listenerset.addAll(list);
         }
      }

      return listenerset;
   }

   public static void addEventAuditListener(final EventAuditListener listener) {
      addEventAuditListener(listener, null);
   }

   public static void addEventAuditListener(final EventAuditListener listener,
                                            final EventType type) {
      addEventAuditListener(listener, type, null);
   }

   public static void addEventAuditListener(final EventAuditListener listener,
                                            final EventType type,
                                            final String id) {
      Map hash = (Map) listeners.get(type);
      if (hash == null) {
         hash = new HashMap();
         listeners.put(type, hash);
      }
      List list = (List) hash.get(id);
      if (list == null) {
         list = new ArrayList();
         hash.put(id, list);
      }
      list.add(listener);
   }

   public static void removeEventAuditListener(final EventAuditListener listener) {
      removeEventAuditListener(listener, null, null);
   }

   public static void removeEventAuditListener(final EventAuditListener listener,
                                               final EventType type) {
      removeEventAuditListener(listener, type, null);
   }

   public static void removeEventAuditListener(final EventAuditListener listener,
                                               final EventType type,
                                               final String id) {
      final Map hash = (Map) listeners.get(type);
      if (hash == null) { return; }
      final List list = (List) hash.get(id);
      if (list == null) { return; }
      list.remove(listener);
   }

   private static class EventType {

      private String name;

      private EventType(Class type) {
         final String full = type.getName();
         name = full.substring(full.lastIndexOf('.') + 1);
      }

      public String toString() {
         return name;
      }
   }
}