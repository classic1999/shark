package org.enhydra.shark.corba;

import org.omg.WfBase.*;
import org.omg.WorkflowModel.WfEventAudit;
import org.omg.WorkflowModel._WfEventAuditIteratorImplBase;

/**
 * Iterator for event audits of activity or the process.
 * 
 * @author Sasa Bojanic
 * @version 1.0
 */
public class WfEventAuditIteratorCORBA extends _WfEventAuditIteratorImplBase {

   org.enhydra.shark.api.client.wfmodel.WfEventAuditIterator myIterator;
   private Collective __collective;

   protected WfEventAuditIteratorCORBA(Collective toJoin, org.enhydra.shark.api.client.wfmodel.WfEventAuditIterator iter) throws BaseException {
      __collective = toJoin;
      toJoin.__recruit(this);
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
    * The following names may be used: time_stamp, event_type,
    * activity_key, activity_name, process_key, process_name,
    * process_mgr_name, package_id process_mgr_version.
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
         return SharkCORBAUtilities.makeCORBANameValueArray(this._orb(),
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

   public WfEventAudit get_next_object() throws BaseException {
      try {
         return SharkCORBAUtilities.makeCORBAEventAudit(__collective, myIterator.get_next_object());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public WfEventAudit get_previous_object() throws BaseException {
      try {
         return SharkCORBAUtilities.makeCORBAEventAudit(__collective, myIterator.get_previous_object());
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public WfEventAudit[] get_next_n_sequence(int max_number) throws BaseException {
      try {
         return SharkCORBAUtilities.makeCORBAEventAudits(__collective, myIterator.get_next_n_sequence(max_number));
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

   public WfEventAudit[] get_previous_n_sequence(int max_number) throws BaseException {
      try {
         return SharkCORBAUtilities.makeCORBAEventAudits(__collective, myIterator.get_previous_n_sequence(max_number));
      } catch (Exception ex) {
         throw new BaseException();
      }
   }

}