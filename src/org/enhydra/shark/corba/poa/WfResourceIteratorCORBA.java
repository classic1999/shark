package org.enhydra.shark.corba.poa;

import org.enhydra.shark.corba.WorkflowService.WfResourceIteratorPOA;
import org.enhydra.shark.corba.poa.Collective;
import org.enhydra.shark.corba.poa.SharkCORBAUtilities;
import org.enhydra.shark.corba.poa.WfResourceCORBA;
import org.enhydra.shark.api.client.wfbase.*;
import org.omg.WfBase.*;
import org.omg.WfBase.BaseException;
import org.omg.WfBase.GrammarNotSupported;
import org.omg.WfBase.InvalidQuery;
import org.omg.WfBase.NameMismatch;
import org.omg.WorkflowModel.WfResource;
import org.omg.WorkflowModel.WfCreateProcessEventAuditHelper;
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import org.omg.PortableServer.POAPackage.ServantNotActive;

/**
 * Iterator for engine resources.
 *
 * @author David Forslund
 * @version 1.0
 */
public class WfResourceIteratorCORBA extends WfResourceIteratorPOA {

   org.enhydra.shark.api.client.wfservice.WfResourceIterator myIterator;

   private Collective __collective;
    private ORB orb;

   protected WfResourceIteratorCORBA(ORB orb, Collective toJoin,
                                     org.enhydra.shark.api.client.wfservice.WfResourceIterator iter) throws BaseException {
       this.orb = orb;

      __collective = toJoin;
   //   toJoin.__recruit(this);
      this.myIterator = iter;
   }

   public String query_expression() throws BaseException {
      try {
         return myIterator.query_expression();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   /*
    * The following names may be used: username
    */
   public void set_query_expression(String query) throws BaseException,
                                                 InvalidQuery {
      try {
         myIterator.set_query_expression(query);
      } catch (org.enhydra.shark.api.client.wfbase.InvalidQuery iq) {
         throw new InvalidQuery();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public NameValue[] names_in_expression() throws BaseException {
      try {
         return SharkCORBAUtilities.makeCORBANameValueArray(orb,
                                                            myIterator.names_in_expression());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void set_names_in_expression(NameValue[] query) throws BaseException,
                                                         NameMismatch {
      try {
         myIterator.set_names_in_expression(SharkCORBAUtilities.makeMap(query));
      } catch (org.enhydra.shark.api.client.wfbase.NameMismatch nm) {
         throw new NameMismatch();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public String query_grammar() throws BaseException {
      try {
         return myIterator.query_grammar();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void set_query_grammar(String query_grammmar) throws BaseException,
                                                       GrammarNotSupported {
      try {
         myIterator.set_query_grammar(query_grammmar);
      } catch (org.enhydra.shark.api.client.wfbase.GrammarNotSupported gns) {
         throw new GrammarNotSupported();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public int how_many() throws BaseException {
      try {
         return myIterator.how_many();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void goto_start() throws BaseException {
      try {
         myIterator.goto_start();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public void goto_end() throws BaseException {
      try {
         myIterator.goto_end();
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public WfResource get_next_object() throws BaseException {


       try {
           WfResource wfRes = SharkCORBAUtilities.makeWfResource( new WfResourceCORBA(orb, __collective,
                                      myIterator.get_next_object()));
           __collective.__recruit(wfRes);
           return wfRes;

       } catch (org.enhydra.shark.api.client.wfbase.BaseException e) {
           e.printStackTrace();
       }
       return null;

   }

   public WfResource get_previous_object() throws BaseException {


       try {
           WfResourceCORBA impl = new WfResourceCORBA(orb, __collective,
                                     myIterator.get_previous_object());
           WfResource wfRes =  SharkCORBAUtilities.makeWfResource( impl);
           __collective.__recruit(wfRes);
           return wfRes;
       } catch (org.enhydra.shark.api.client.wfbase.BaseException e) {
           e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
       }
       return null;


   }

   public WfResource[] get_next_n_sequence(int max_number) throws BaseException {
      try {
         return SharkCORBAUtilities.makeCORBAResources(__collective,
                                                       myIterator.get_next_n_sequence(max_number));
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public WfResource[] get_previous_n_sequence(int max_number) throws BaseException {
      try {
         return SharkCORBAUtilities.makeCORBAResources(__collective,
                                                       myIterator.get_previous_n_sequence(max_number));
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

}