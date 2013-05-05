package org.enhydra.shark.api.internal.eventaudit;

import java.util.List;
import org.enhydra.shark.api.RootException;
import org.enhydra.shark.api.SharkTransaction;
import org.enhydra.shark.api.internal.working.CallbackUtilities;

/**
 * Interface that has to be implemented for each persistent layer that
 * is supposed to be used.
 *
 * @version 1.0
 *
 */
public interface EventAuditManagerInterface {

   /**
    * Method configure is called at Shark start up, to configure
    * implementation of EventAuditManagerInterface.
    *
    * @param cus an instance of CallbackUtilities used to get
    *            properties for configuring Event Audit Manager in Shark.
    *
    * @exception RootException Thrown if configuring doesn't succeed.
    */
   void configure(CallbackUtilities cus) throws RootException;

   /**
    * Method persist stores the assignment event into repository
    * (usually database) using supplied transaction.
    *
    * @param    assea AssignmentEventAuditPersistenceInterface
    * @param    ti SharkTransaction to use.
    *
    * @exception EventAuditException on error.
    */
   public void persist(AssignmentEventAuditPersistenceInterface assea,
                       SharkTransaction ti)
      throws EventAuditException;

   /**
    * Method persist stores the create process event into repository
    * (usually database) using supplied transaction.
    *
    * @param    cpea CreateProcessEventAuditPersistenceInterface
    * @param    ti SharkTransaction to use.
    *
    * @exception EventAuditException on error.
    */
   public void persist(CreateProcessEventAuditPersistenceInterface cpea,
                       SharkTransaction ti)
      throws EventAuditException;

   /**
    * Method persist stores the data event into repository
    * (usually database) using supplied transaction.
    *
    * @param    dea DataEventAuditPersistenceInterface
    * @param    ti SharkTransaction to use.
    *
    * @exception EventAuditException on error.
    */
   public void persist(DataEventAuditPersistenceInterface dea,
                       SharkTransaction ti)
      throws EventAuditException;

   /**
    * Method persist stores the state event into repository
    * (usually database) using supplied transaction.
    *
    * @param    sea StateEventAuditPersistenceInterface
    * @param    ti SharkTransaction to use.
    *
    * @exception EventAuditException on error.
    */
   public void persist(StateEventAuditPersistenceInterface sea,
                       SharkTransaction ti)
      throws EventAuditException;

   /**
    * Method restore retrieves assignment event from the
    * repository using supplied transaction.
    *
    * @param    assea AssignmentEventAuditPersistenceInterface
    * @param    ti SharkTransaction to use.
    *
    * @return   true on success, false otherwise
    *
    * @exception EventAuditException on error.
    */
   public boolean restore(AssignmentEventAuditPersistenceInterface assea,
                          SharkTransaction ti)
      throws EventAuditException;

   /**
    * Method restore retrieves create process event from the
    * repository using supplied transaction.
    *
    * @param    cpea CreateProcessEventAuditPersistenceInterface
    * @param    ti SharkTransaction to use.
    *
    * @return   true on success, false otherwise
    *
    * @exception EventAuditException on error.
    */
   public boolean restore(CreateProcessEventAuditPersistenceInterface cpea,
                          SharkTransaction ti)
      throws EventAuditException;

   /**
    * Method restore retrieves data event from the
    * repository using supplied transaction.
    *
    * @param    dea DataEventAuditPersistenceInterface
    * @param    ti SharkTransaction to use.
    *
    * @return   true on success, false otherwise
    *
    * @exception EventAuditException on error.
    */
   public boolean restore(DataEventAuditPersistenceInterface dea,
                          SharkTransaction ti)
      throws EventAuditException;

   /**
    * Method restore retrieves state event from the
    * repository using supplied transaction.
    *
    * @param    sea StateEventAuditPersistenceInterface
    * @param    ti SharkTransaction to use.
    *
    * @return   true on success, false otherwise
    *
    * @exception EventAuditException on error.
    */
   public boolean restore(StateEventAuditPersistenceInterface sea,
                          SharkTransaction ti)
      throws EventAuditException;

    /**
    * Method restoreProcessHistory
    *
    * @param    procId              a  String
    * @param    ti SharkTransaction to use.
    *
    * @return   a List
    *
    * @exception EventAuditException on error.
    */
   public List restoreProcessHistory(String procId,SharkTransaction ti)
      throws EventAuditException;

   /**
    * Method restoreActivityHistory
    *
    * @param    procId              a  String
    * @param    actId               a  String
    * @param    ti SharkTransaction to use.
    *
    * @return   a List
    *
    * @exception EventAuditException on error.
    */
   public List restoreActivityHistory(String procId,
                                      String actId,
                                      SharkTransaction ti)
      throws EventAuditException;
   
   /**
    * Method delete
    *
    * @param    assea               an AssignmentEventAuditPersistenceInterface
    * @param    ti SharkTransaction to use.
    *
    * @exception EventAuditException on error.
    */
   public void delete(AssignmentEventAuditPersistenceInterface assea,
                      SharkTransaction ti)
      throws EventAuditException;

   /**
    * Method delete
    *
    * @param    cpea                a  CreateProcessEventAuditPersistenceInterface
    * @param    ti SharkTransaction to use.
    *
    * @exception EventAuditException on error.
    */
   public void delete(CreateProcessEventAuditPersistenceInterface cpea,
                      SharkTransaction ti)
      throws EventAuditException;

   /**
    * Method delete
    *
    * @param    dea                 a  DataEventAuditPersistenceInterface
    * @param    ti SharkTransaction to use.
    *
    * @exception EventAuditException on error.
    */
   public void delete(DataEventAuditPersistenceInterface dea,
                      SharkTransaction ti)
      throws EventAuditException;

   /**
    * Method delete
    *
    * @param    sea                 a  StateEventAuditPersistenceInterface
    * @param    ti SharkTransaction to use.
    *
    * @exception EventAuditException on error.
    */
   public void delete(StateEventAuditPersistenceInterface sea,
                      SharkTransaction ti)
      throws EventAuditException;

   /**
    * Method createAssignmentEventAudit
    *
    * @return   an AssignmentEventAuditPersistenceInterface
    */
   public AssignmentEventAuditPersistenceInterface createAssignmentEventAudit();

   /**
    * Method createCreateProcessEventAudit
    *
    * @return   a CreateProcessEventAuditPersistenceInterface
    */
   public CreateProcessEventAuditPersistenceInterface createCreateProcessEventAudit();

   /**
    * Method createDataEventAudit
    *
    * @return   a DataEventAuditPersistenceInterface
    */
   public DataEventAuditPersistenceInterface createDataEventAudit();

   /**
    * Method createStateEventAudit
    *
    * @return   a StateEventAuditPersistenceInterface
    */
   public StateEventAuditPersistenceInterface createStateEventAudit();

   /**
    * Method getNextId
    *
    * @param    idName              a  String
    *
    * @return   a String
    *
    * @exception EventAuditException on error.
    */
   public String getNextId(String idName) throws EventAuditException;
}
