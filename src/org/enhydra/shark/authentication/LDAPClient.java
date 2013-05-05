package org.enhydra.shark.authentication;

import org.enhydra.shark.api.internal.working.CallbackUtilities;

import java.util.*;

import javax.naming.*;
import javax.naming.directory.*;

/**
 * Connects to the LDAP server and gets wanted entries.
 *
 * @author Sasa Bojanic, Tanja Jovanovic
 * @version 1.0
 */
public class LDAPClient {
   private final String boundary=",";

   private LDAPOptions ldapOptions;

   private CallbackUtilities cus;
   
   private java.util.Properties env = new java.util.Properties();
   
   private SearchControls constraints=new SearchControls();

   /**
    * Public constructor (CallbackUtilities).
    */
   public LDAPClient (CallbackUtilities cus) {
      this.cus=cus;
      ldapOptions=new LDAPOptions(cus);
      /* Create an environment for the initial directory context.
       The properties specify the LDAP provider, the LDAP server,
       and if needed username and password, or if not, no security
       (anonymous bind). */
      env = new java.util.Properties();
      env.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY,  "com.sun.jndi.ldap.LdapCtxFactory");
      env.put(javax.naming.Context.REFERRAL, "throw");
      env.put(javax.naming.Context.PROVIDER_URL, "ldap://" + ldapOptions.getHost() + ":" + ldapOptions.getPort());
      env.put(javax.naming.Context.SECURITY_PRINCIPAL, ldapOptions.getUser());
      env.put(javax.naming.Context.SECURITY_CREDENTIALS, ldapOptions.getPassword());

      // Set up and perform the search.
      constraints.setSearchScope(ldapOptions.getSearchScope());
      constraints.setCountLimit(0);
   }

   /**
    * Used to retreive LDAPOptions object, which might be set other
    * then default before calling getEntries.
    *
    * @return LDAPOptions object.
    */
   public LDAPOptions getLDAPOptions () {
      return ldapOptions;
   }

   /**
    * Connects to the LDAP server specified by LDAPOptions object, and
    * checks given password.
    *
    * @param username User distinctive name.
    * @param pwd Password to check for the given user.
    * @return true if password is OK.
    */
   public boolean checkPassword (String username,String pwd) {
//System.err.println("Checking pwd "+pwd+" for user "+username);
      try {
         String base = ldapOptions.getSearchBase();
         String filter="";
         String[] objClasses;
         Iterator results;
         SearchResult sr;
         String dn = null;
         int count = 0;

         if (ldapOptions.getStructureType() == 1) {
            objClasses=tokenize(ldapOptions.getGroupObjectClasses(),boundary);
            if (objClasses!=null && objClasses.length>0) {
               if (objClasses.length>1) {
                  filter+="(|";
               }
               for (int i=0; i<objClasses.length; i++) {
                  filter+="(objectClass="+objClasses[i]+")";
               }
               if (objClasses.length>1) {
                  filter+=")";
               }
            }
            filter="(&("+ldapOptions.getGroupUniqueAttributeName()+"=" + ldapOptions.getGroupUsersName()+ ")"+filter+")";
            results=getEntries(filter).iterator();
            if (results.hasNext()) {
               sr = (SearchResult) results.next();
//            attributes = sr.getAttributes();
               try {
                  dn = sr.getName();
               }
               catch (Exception e){}
               if (!dn.equals("")){
                  if (!base.equals("")){
                     dn = dn + "," + base;
                  }
               }
               else {
                  dn = base;
               }
               ldapOptions.setSearchBase(dn);

               filter="";
               objClasses=tokenize(ldapOptions.getUserObjectClasses(),boundary);
               if (objClasses!=null && objClasses.length>0) {
                  if (objClasses.length>1) {
                     filter+="(|";
                  }
                  for (int i=0; i<objClasses.length; i++) {
                     filter+="(objectClass="+objClasses[i]+")";
                  }
                  if (objClasses.length>1) {
                     filter+=")";
                  }
               }
               filter="(&("+ldapOptions.getUserUniqueAttributeName()+"="+username+")"+
                           "("+ldapOptions.getUserPasswordAttributeName()+"="+pwd+")"+
                           filter+")";
                count = getEntries(filter).size();

               ldapOptions.setSearchBase(base);
             }
         }  // if (ldapOptions.getStructureType() == 1) 
         else { 
            objClasses=tokenize(ldapOptions.getUserObjectClasses(),boundary);
            if (objClasses!=null && objClasses.length>0) {
               if (objClasses.length>1) {
                 filter+="(|";
               }
               for (int i=0; i<objClasses.length; i++) {
                  filter+="(objectClass="+objClasses[i]+")";
               }
               if (objClasses.length>1) {
                  filter+=")";
               }
            }
            filter="(&("+ldapOptions.getUserUniqueAttributeName()+"="+username+")"+
               "("+ldapOptions.getUserPasswordAttributeName()+"="+pwd+")"+
               filter+")";
            count = getEntries(filter).size();
         }

         return count > 0; 

      } catch (NamingException e) {
         /* Handle any name/directory exceptions. */
cus.warn("LDAPClient -> Search failed: "+e.getMessage());
      }
      catch (Exception e) {
         /* Handle any other types of exceptions. */
cus.warn("LDAPClient -> Non-naming error: " + e.getMessage());
      }
      catch (Throwable e) {
         /* Handle any other types of exceptions. */
cus.warn("LDAPClient -> Non-naming error: " + e.getMessage());
      }
      return false;
   }
   
   /**
    * Connects to the LDAP server and retrieves wanted data.
    * All neccessary information to establish connection to the server, and
    * to retrieve data are hold within LDAPOptions.
    */
   private java.util.List getEntries (String filter) throws Throwable {
      java.util.List entries=new java.util.ArrayList();
      DirContext ctx=new InitialDirContext(env);
      try {
//System.out.println("GTQ for "+filter);
         NamingEnumeration results = ctx.search(
            ldapOptions.getSearchBase(),
            filter,
            constraints);
         /* For each entry found. */
//int i=0;
         while (results.hasMore()) {
//System.out.println("FE"+(i++));
            SearchResult sr = (SearchResult) results.next();
            entries.add(sr);
         }
//System.out.println("Returning "+i+" entr");
         return entries;
      }
      catch (Exception e){
        e.printStackTrace();
        }
       finally {
         ctx.close();
      }
      return entries;
   }

   /**
    * Take the given string and chop it up into a series
    * of strings on given boundries.  This is useful
    * for trying to get an array of strings out of the
    * resource file.
    *
    * @param input input string.
    * @param boundary boundary string.
    * @return array of result strings. 
    */
   static String[] tokenize(String input,String boundary) {
      if (input==null) input="";
      Vector v = new Vector();
      StringTokenizer t = new StringTokenizer(input,boundary);
      String cmd[];

      while (t.hasMoreTokens())
         v.addElement(t.nextToken());
      cmd = new String[v.size()];
      for (int i = 0; i < cmd.length; i++)
         cmd[i] = (String)v.elementAt(i);

      return cmd;
   }

}
