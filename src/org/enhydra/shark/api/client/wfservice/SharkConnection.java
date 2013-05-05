package org.enhydra.shark.api.client.wfservice;

import org.enhydra.shark.api.client.wfbase.BaseException;

import org.enhydra.shark.api.client.wfmodel.*;
import org.enhydra.shark.api.SharkTransaction;

/**
 * Interface used to actually connect to shark engine, and to perform some
 * administrative operations or to retrieve a WfResource object related
 * to connected user, so it can perform its tasks.
 * <p> The first method to be called by client application is the first method
 * of this interface - connect(), and only if user authentication is OK,
 * other methods can be used (otherwise, every method throws NotConnected
 * exception).
 *
 * @author Sasa Bojanic
 * @author Vladimir Puskas
 */
public interface SharkConnection {

   /**
    * This is the first method to be called in order to communicate with the
    * engine. If the login parameters are correct, user can use other
    * methods of this interface to communicate with shark engine,
    * and if not, he can't do anything.
    *
    * @param    userId user id String.
    * @param    password user password.
    * @param    engineName engine name.
    * @param    scope scope.
    *
    * @throws   BaseException If something unexpected happens.
    * @throws   ConnectFailed If authentication parameters are not correct.
    *
    */
   void connect (String userId, String password,String engineName,String scope) throws BaseException, ConnectFailed;

   /**
    * This is the first method to be called in order to communicate with
    * engine. If the login parameters are correct, user can use other
    * methods of this interface to communicate with shark engine,
    * and if not, he can't do anything.
    *
    * @param    t SharkTransaction.
    * @param    userId user id String.
    * @param    password user password.
    * @param    engineName engine name.
    * @param    scope scope.
    *
    * @throws   BaseException If something unexpected happens.
    * @throws   ConnectFailed If authentication parameters are not correct.
    *
    */
   void connect (SharkTransaction t,String userId, String password,String engineName,String scope) throws BaseException, ConnectFailed;

   /**
    * Disconnects from shark engine.
    *
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    *
    */
   void disconnect () throws BaseException, NotConnected;

   /**
    * Disconnects from shark engine.
    *
    * @param    t SharkTransaction.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    *
    */
   void disconnect (SharkTransaction t) throws BaseException, NotConnected;

   /**
    * Returns WfResource object belonging to the user that has been
    * connected to shark through this interface. This will be the WfResource
    * object that has the same username attribute as the one used in
    * connect() method. After getting this object, client application can
    * present user a list of its assignments that can be retrieved throug
    * this WfResource object.
    *
    * @return   WfResource object belonging to the user.
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    *
    */
   WfResource getResourceObject () throws BaseException, NotConnected;

   /**
    * Returns WfResource object belonging to the user that has been
    * connected to shark through this interface. This will be the WfResource
    * object that has the same username attribute as the one used in
    * connect() method. After getting this object, client application can
    * present user a list of its assignments that can be retrieved throug
    * this WfResource object.
    *
    * @param    t SharkTransaction.
    * @return   WfResource object belonging to the user.
    *
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    *
    */
   WfResource getResourceObject (SharkTransaction t) throws BaseException, NotConnected, NotEnabled;

   /**
    * Creates a process based on its definition given by parameters.
    *
    * @param    pkgId               the package Id of XPDL process definition.
    * @param    pDefId              the process definition Id of XPDL process definition.
    * @return   created WfProcess.
    *
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    * @exception   NotEnabled When it is currently not allowed to create processes based
    * on this definition.
    *
    */
   WfProcess createProcess (String pkgId,String pDefId) throws BaseException, NotConnected, NotEnabled;

   /**
    * Creates a process based on its definition given by parameters.
    *
    * @param    t SharkTransaction.
    * @param    pkgId               the package Id of XPDL process definition.
    * @param    pDefId              the process definition Id of XPDL process definition.
    * @return   created WfProcess.
    *
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    * @exception   NotEnabled When it is currently not allowed to create processes based
    * on this definition
    *
    */
   WfProcess createProcess (SharkTransaction t,String pkgId,String pDefId) throws BaseException, NotConnected, NotEnabled;

   /**
    * Creates a process based on its definition given by parameters.
    *
    * @param    r                   WfRequester object.
    * @param    pkgId               the package Id of XPDL process definition.
    * @param    pDefId              the process definition Id of XPDL process definition.
    * @return   created WfProcess.
    *
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    * @exception   NotEnabled When it is currently not allowed to create processes based
    * on this definition
    */
   WfProcess createProcess(WfRequester r, String pkgId,String pDefId)
   throws BaseException, NotConnected, NotEnabled, InvalidRequester, RequesterRequired;

   /**
    * Creates a process based on its definition given by parameters.
    *
    * @param    t                   SharkTransaction.
    * @param    r                   WfRequester object.
    * @param    pkgId               the package Id of XPDL process definition.
    * @param    pDefId              the process definition Id of XPDL process definition.
    * @return   created WfProcess.
    *
    * @throws   BaseException If something unexpected happens.
    * @throws   NotConnected If user wasn't connected (the connect()
    * wasn't called or authentication failed).
    * @exception   NotEnabled When it is currently not allowed to create processes based
    * on this definition
    */
   WfProcess createProcess(SharkTransaction t, WfRequester r, String pkgId,String pDefId)
   throws BaseException, NotConnected, NotEnabled, InvalidRequester, RequesterRequired;
}

