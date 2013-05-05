package org.enhydra.shark.usergroup;
import org.enhydra.shark.api.internal.working.CallbackUtilities;
import org.enhydra.shark.utilities.LRUMap;
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
   
   protected static LRUMap userAttributes=null;
   
   protected static LRUMap groupAttributes=null;

   /**
    * Public constructor (CallbackUtilities).
    */
   public LDAPClient (CallbackUtilities cus) {
// System.out.println("constructor LDAPClient");
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
      if (LDAPClient.userAttributes==null) {
         int uatts=0;
         try {
            uatts=Integer.parseInt(cus.getProperty("LDAPClient.userAttributesCacheSize","0"));
         } catch (Exception ex) {}
//System.out.println("uatts= " + uatts);         
         LDAPClient.userAttributes=new LRUMap(uatts);
      }
      if (LDAPClient.groupAttributes==null) {
         int gatts=0;
         try {
            gatts=Integer.parseInt(cus.getProperty("LDAPClient.groupAttributesCacheSize","0"));
         } catch (Exception ex) {}
//System.out.println("gatts= " + gatts);         
         LDAPClient.groupAttributes=new LRUMap(gatts);
      }
   }

   /**
    * Used to retreive LDAPOptions object, which might be set other
    * then default before calling getEntries.
    *
    * return LDAPOptions object.
    */
   public LDAPOptions getLDAPOptions () {
      return ldapOptions;
   }


   /**
    * Connects to the LDAP server and retrieves wanted data.
    * All neccessary information to establish connection to the server, and
    * to retrieve data are hold within LDAPOptions.
    */
   public java.util.List getAllOrganizationalUnitEntries () throws Throwable {
//System.out.println("method getAllOrganizationalUnitEntries()");
      java.util.List entries=new java.util.ArrayList();
      String base = ldapOptions.getSearchBase();
      String filter="";
      String[] objClasses;
      Iterator results;
      SearchResult sr;
      Attributes attributes;
      String dn = null;

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
         filter="(&("+ldapOptions.getGroupUniqueAttributeName()+"=" + ldapOptions.getGroupGroupsName()+ ")"+filter+")";
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
            filter="(&("+ldapOptions.getGroupUniqueAttributeName()+"=*)"+filter+")";
            results=getEntries(filter).iterator();
            ldapOptions.setSearchBase(base);

            while (results.hasNext()) {
               sr = (SearchResult) results.next();
               attributes = sr.getAttributes();
               try {
                  String id=(String)attributes.get(ldapOptions.getGroupUniqueAttributeName()).get();
                  if (!id.equals(ldapOptions.getGroupGroupsName())) {
                     entries.add(id);
                  }
                  if (LDAPClient.groupAttributes.getMaximumSize()>0) {
                     LDAPClient.groupAttributes.put(id, attributes);
        
                  }  
               }
               catch (Exception e){}
            } // while
         } // if (results.hasNext())
     } // if (ldapOptions.getStructureType() == 1)
     else {
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
        filter="(&("+ldapOptions.getGroupUniqueAttributeName()+"=*)"+filter+")";
        results=getEntries(filter).iterator();
        /* For each entry found. */
        while (results.hasNext()) {
           sr = (SearchResult) results.next();
           attributes = sr.getAttributes();
           try {
              String id=(String)attributes.get(ldapOptions.getGroupUniqueAttributeName()).get();
              entries.add(id);
              if (LDAPClient.groupAttributes.getMaximumSize()>0) {
                 LDAPClient.groupAttributes.put(id, attributes);
              }
           }
           catch (Exception ex) {}
        } // while
      } // else from if (ldapOptions.getStructureType() == 1)
      return entries;
   }

   /**
    * Connects to the LDAP server and retrieves wanted data.
    * All neccessary information to establish connection to the server, and
    * to retrieve data are hold within LDAPOptions.
    */
   public java.util.List getAllUserEntries () throws Throwable {
//System.out.println("method getAllUserEntries()");
      java.util.List entries=new java.util.ArrayList();
      String base = ldapOptions.getSearchBase();
      String filter="";
      String[] objClasses;
      Iterator results;
      SearchResult sr;
      Attributes attributes;
      String dn = null;

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
            filter="(&("+ldapOptions.getUserUniqueAttributeName()+"=*)"+filter+")";
            results=getEntries(filter).iterator();
            ldapOptions.setSearchBase(base);

            while (results.hasNext()) {
               sr = (SearchResult) results.next();
               attributes = sr.getAttributes();
               try {
                  String id=(String)attributes.get(ldapOptions.getUserUniqueAttributeName()).get();
                  entries.add(id);
                  if (LDAPClient.userAttributes.getMaximumSize()>0) {
                     LDAPClient.userAttributes.put(id, attributes);
                  }
               }
               catch (Exception e){}
            } // while
         } // if (results.hasNext())
     } // if (ldapOptions.getStructureType() == 1)
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
        filter="(&("+ldapOptions.getUserUniqueAttributeName()+"=*)"+filter+")";
        results=getEntries(filter).iterator();
        /* For each entry found. */
        while (results.hasNext()) {
           sr = (SearchResult) results.next();
           attributes = sr.getAttributes();
           try {
              String id=(String)attributes.get(ldapOptions.getUserUniqueAttributeName()).get();
              entries.add(id);
              if (LDAPClient.userAttributes.getMaximumSize()>0) {
                 LDAPClient.userAttributes.put(id, attributes);
              }
           } catch (Exception ex) {}
        }
     } // else from if (ldapOptions.getStructureType() == 1)
     return entries;
   }

   /**
    * Connects to the LDAP server and retrieves wanted data.
    * All neccessary information to establish connection to the server, and
    * to retrieve data are hold within LDAPOptions.
    */
   public java.util.List getAllUserEntries (String groupName) throws Throwable {
//System.out.println("method getAllUserEntries(groupName) for " + groupName);
      if (!doesGroupExist(groupName)){
         throw new Exception("Group " + groupName + "does not exist.");  
      }
      java.util.List entries=new java.util.ArrayList();
      String base = ldapOptions.getSearchBase();
      String filter="";
      String[] objClasses;
      Iterator results;
      SearchResult sr;
      Attributes attributes;
      String dn = null;

      if (ldapOptions.getStructureType() == 1){
         entries = getAllImmediateUserEntries(groupName);
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
        filter="(&("+ldapOptions.getGroupUniqueAttributeName()+"=" + ldapOptions.getGroupGroupRelationsName() + ")"+filter+")";
        results=getEntries(filter).iterator();
        if (results.hasNext()){
           sr = (SearchResult) results.next();
//           attributes = sr.getAttributes();
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
           constraints.setSearchScope(1);

           filter="";
           objClasses=tokenize(ldapOptions.getRelationObjectClasses(),boundary);
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
           filter="(&("+ldapOptions.getRelationUniqueAttributeName()+"="+groupName+")"+filter+")";
           results=getEntries(filter).iterator();
           ldapOptions.setSearchBase(dn);  // visak
           constraints.setSearchScope(2);  // visak

           if (results.hasNext()) {
              filter="";
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
               filter="(&("+ldapOptions.getGroupUniqueAttributeName()+"=*)"+filter+")";
               sr = (SearchResult) results.next();
               attributes = sr.getAttributes();
               Attribute attribute = attributes.get("member");

               NamingEnumeration ne = attribute.getAll();
               while (ne.hasMore()){
                  String member_dn = (String) ne.next();
                  ldapOptions.setSearchBase(member_dn);
                  constraints.setSearchScope(0);
                  Iterator elems = getEntries(filter).iterator();
                  SearchResult sres = (SearchResult) elems.next();
                  Attributes attrs = sres.getAttributes();
                  try {
                     String id=(String)attrs.get(ldapOptions.getGroupUniqueAttributeName()).get();
                     if (LDAPClient.userAttributes.getMaximumSize()>0) {
                        LDAPClient.userAttributes.put(id, attrs);
                     }
                     ldapOptions.setSearchBase(base);
                     constraints.setSearchScope(2);
                     List l = getAllUserEntries(id);
                     if (l!=null && !l.isEmpty()){
                        for (int i=0;i<l.size();i++){
                           if (!entries.contains((String)l.get(i))){
                              entries.add((String)l.get(i));
                           }
                        }
                     }
                  }
                  catch (Exception e){}
              } // while
           } //if (results.hasNext())
        } //if (results.hasNext())
      } // if (ldapOptions.getStructureType() == 1)
      else {
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
        filter="(&("+ldapOptions.getGroupUniqueAttributeName()+"="+groupName+")"+filter+")";
        //System.out.println("filter="+filter);
        results=getEntries(filter).iterator();
        if (results.hasNext()){
           sr = (SearchResult) results.next();
//           attributes = sr.getAttributes();
           try {
              dn = sr.getName();
           }
           catch (Exception e){}
           //System.out.println("dn1="+dn);
           if (!dn.equals("")){
              if (!base.equals("")) {
                 dn = dn + "," + base;
              }
           }
           else {
              dn = base;
           }
           //System.out.println("dn2="+dn);
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
           filter="(&("+ldapOptions.getUserUniqueAttributeName()+"="+"*)"+filter+")";
           //System.out.println("filter2="+filter);
           results=getEntries(filter).iterator();
           while (results.hasNext()) {
              sr = (SearchResult) results.next();
              attributes = sr.getAttributes();
              try {
                 String id=(String)attributes.get(ldapOptions.getUserUniqueAttributeName()).get();
                 //System.out.println("adding id "+id);
                 entries.add(id);
                 if (LDAPClient.userAttributes.getMaximumSize()>0) {
                    LDAPClient.userAttributes.put(id, attributes);
                 }
              } catch (Exception ex) {}
           }
        } // if (results.hasNext())
     } // else from if (ldapOptions.getStructureType() == 1)
     ldapOptions.setSearchBase(base);
     constraints.setSearchScope(2);
     return entries;
   }

   /**
    * Connects to the LDAP server and retrieves wanted data.
    * All neccessary information to establish connection to the server, and
    * to retrieve data are hold within LDAPOptions.
    */
   public java.util.List getAllImmediateUserEntries (String groupName) throws Throwable {
//System.out.println("method getAllImmediateUserEntries(groupName) for " + groupName);
      if (!doesGroupExist(groupName)){
         throw new Exception("Group " + groupName + "does not exist.");  
      }
      java.util.List entries=new java.util.ArrayList();
      String base = ldapOptions.getSearchBase();
      String filter="";
      String[] objClasses;
      Iterator results;
      SearchResult sr;
      Attributes attributes;
      String dn = null;

      if (ldapOptions.getStructureType() == 1){
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
         filter="(&("+ldapOptions.getGroupUniqueAttributeName()+"=" + ldapOptions.getGroupUserRelationsName() + ")"+filter+")";
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
            constraints.setSearchScope(1);

            filter="";
            objClasses=tokenize(ldapOptions.getRelationObjectClasses(),boundary);
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
            filter="(&("+ldapOptions.getRelationUniqueAttributeName()+"="+groupName+")"+filter+")";
            results=getEntries(filter).iterator();
            constraints.setSearchScope(2);
            ldapOptions.setSearchBase(base);

            if (results.hasNext()) {
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
               filter="(&("+ldapOptions.getUserUniqueAttributeName()+"=*)"+filter+")";

               sr = (SearchResult) results.next();
               attributes = sr.getAttributes();
               Attribute attribute = attributes.get("member");
               NamingEnumeration na = attribute.getAll();

               while (na.hasMore()){
                  String member_dn = (String) na.next();
                  ldapOptions.setSearchBase(member_dn);
                  constraints.setSearchScope(0);
                  Iterator elems = getEntries(filter).iterator();
                  SearchResult sres = (SearchResult) elems.next();
                  Attributes attrs = sres.getAttributes();
                  try {
                     String id=(String)attrs.get(ldapOptions.getUserUniqueAttributeName()).get();
                     if (!entries.contains(id)){
                        entries.add(id);
                     }
                     if (LDAPClient.userAttributes.getMaximumSize()>0) {
                        LDAPClient.userAttributes.put(id, attrs);
                     }
                  }
                  catch (Exception e){}
              } // while
          } // if (results.hasNext())
       } // if (result.hasNext())
     } // if (ldapOptions.getStructureType() == 1)
     else {
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
        filter = "(&("+ldapOptions.getGroupUniqueAttributeName()+"="+groupName+")"+filter+")";
        results=getEntries(filter).iterator();
        if (results.hasNext()){
           sr = (SearchResult) results.next();
           attributes = sr.getAttributes();
           try {
              dn = sr.getName();
           }
           catch (Exception e){}
           constraints.setSearchScope(1);
           if (!dn.equals("")){
             if (!base.equals("")) {
                 dn = dn + "," + base;
             }
           }
           else {
             dn = base;
           }
           ldapOptions.setSearchBase(dn);

           filter = "";
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
           filter = "(&("+ldapOptions.getUserUniqueAttributeName()+"=*)"+filter+")";

           results=getEntries(filter).iterator();
           while (results.hasNext()) {
              sr = (SearchResult) results.next();
              attributes = sr.getAttributes();
              try {
                 String id=(String)attributes.get(ldapOptions.getUserUniqueAttributeName()).get();
                 entries.add(id);
                 if (LDAPClient.userAttributes.getMaximumSize()>0) {
                    LDAPClient.userAttributes.put(id, attributes);
                 }
              } catch (Exception ex) {}
           }
        } // results.hasNext
     } // else from if (ldapOptions.getStructureType() == 1)
     constraints.setSearchScope(2);
     ldapOptions.setSearchBase(base);
     return entries;
   }

   /**
    * Connects to the LDAP server and retrieves wanted data.
    * All neccessary information to establish connection to the server, and
    * to retrieve data are hold within LDAPOptions.
    */
   public java.util.List getAllSubOrganizationalUnitEntries (String groupName) throws Throwable {
//System.out.println("method getAllSubOrganizationalUnitEntries(groupName) for " + groupName);      
      if (!doesGroupExist(groupName)){
         throw new Exception("Group " + groupName + "does not exist.");  
      }
      java.util.List entries=new java.util.ArrayList();
      String base = ldapOptions.getSearchBase();
      int scope = constraints.getSearchScope();
      String filter="";
      String[] objClasses;
      Iterator results;
      SearchResult sr;
      Attributes attributes;
      String dn = null;

      if (ldapOptions.getStructureType() == 1) {
         List tempEntries = getAllImmediateSubOrganizationalUnitEntries(groupName);
         if (tempEntries!=null && !tempEntries.isEmpty()){
            for (int i=0;i<tempEntries.size();i++){
               if (!entries.contains(tempEntries.get(i))){
                  entries.add(tempEntries.get(i));
                  List subResults = getAllSubOrganizationalUnitEntries((String)tempEntries.get(i));
                  if (subResults!= null && !subResults.isEmpty()){
                     for (int j=0;j<subResults.size();j++){
                        if (!entries.contains((String)subResults.get(i))){
                            entries.add((String)subResults.get(j));
                         }
                     }
                  }
               }
            }
         }
      } // if (ldapOptions.getStructureType() == 1)
      else {
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
         filter="(&("+ldapOptions.getGroupUniqueAttributeName()+"="+groupName+")"+filter+")";
         results=getEntries(filter).iterator();
         if (results.hasNext()){
            sr = (SearchResult) results.next();
//            attributes = sr.getAttributes();
            try {
               dn = sr.getName();
            }
            catch (Exception e){}
            if (!dn.equals("")){
               if (!base.equals("")) {
                  dn = dn + "," + base;
                }
            }
            else {
                  dn = base;
            }
            ldapOptions.setSearchBase(dn);

            filter="";
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
             filter="(&("+ldapOptions.getGroupUniqueAttributeName()+"="+"*)"+filter+")";
             results=getEntries(filter).iterator();
             while (results.hasNext()) {
                sr = (SearchResult) results.next();
                attributes = sr.getAttributes();
                try {
                   String id=(String)attributes.get(ldapOptions.getGroupUniqueAttributeName()).get();
                   if (!id.equals(groupName)){
                      entries.add(id);
                   }
                   if (LDAPClient.groupAttributes.getMaximumSize()>0) {
                      LDAPClient.groupAttributes.put(id, attributes);
                   }
                } catch (Exception ex) {}
             }
         } // if (results.hasNext())
          ldapOptions.setSearchBase(base);
      } // else from if (ldapOptions.getStructureType() == 1
      return entries;
   }

   /**
    * Connects to the LDAP server and retrieves wanted data.
    * All neccessary information to establish connection to the server, and
    * to retrieve data are hold within LDAPOptions.
    */
   public java.util.List getAllImmediateSubOrganizationalUnitEntries (String groupName) throws Throwable {
//System.out.println("method getAllImmediateSubOrganizationalUnitEntries(groupName) for " + groupName);
      if (!doesGroupExist(groupName)){
         throw new Exception("Group " + groupName + "does not exist.");  
      }
      java.util.List entries=new java.util.ArrayList();
      String base = ldapOptions.getSearchBase();
      String filter="";
      String[] objClasses;
      Iterator results;
      SearchResult sr;
      Attributes attributes;
      String dn = null;

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
         filter="(&("+ldapOptions.getGroupUniqueAttributeName()+"=" + ldapOptions.getGroupGroupRelationsName() + ")"+filter+")";
         results=getEntries(filter).iterator();
         if (results.hasNext()) {
            sr = (SearchResult) results.next();
            attributes = sr.getAttributes();
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
            constraints.setSearchScope(1);
            ldapOptions.setSearchBase(dn);

            filter="";
            objClasses=tokenize(ldapOptions.getRelationObjectClasses(),boundary);
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
            filter="(&("+ldapOptions.getRelationUniqueAttributeName()+"="+groupName+")"+filter+")";
            results=getEntries(filter).iterator();
            constraints.setSearchScope(2);
            ldapOptions.setSearchBase(base);

            if (results.hasNext()) {
               filter="";
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
               filter="(&("+ldapOptions.getGroupUniqueAttributeName()+"=*)"+filter+")";
               sr = (SearchResult) results.next();
               attributes = sr.getAttributes();
               Attribute   attribute = attributes.get("member");
               NamingEnumeration ne = attribute.getAll();
               while (ne.hasMore()){
                  String member_dn = (String) ne.next();
                  ldapOptions.setSearchBase(member_dn);
                  constraints.setSearchScope(0);
                  Iterator elems = getEntries(filter).iterator();
                  SearchResult sres = (SearchResult) elems.next();
                  Attributes attrs = sres.getAttributes();
                  try {
                     String id=(String)attrs.get(ldapOptions.getGroupUniqueAttributeName()).get();
                     if (!entries.contains(id)){
                        entries.add(id);
                     }
                     if (LDAPClient.groupAttributes.getMaximumSize()>0) {
                        LDAPClient.groupAttributes.put(id, attrs);
                     }
                  }
                  catch (Exception e){}
               } // while
            } // if (results.hasNext())
         } // if (results.hasNext())
     } // if (ldapOptions.getStructureType() == 1)
     else {
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
        String groupSpecFilter = "(&("+ldapOptions.getGroupUniqueAttributeName()+"="+groupName+")"+filter+")";
        results=getEntries(groupSpecFilter).iterator();
        if (results.hasNext()){
           sr = (SearchResult) results.next();
//               attributes = sr.getAttributes();
           try {
              dn = sr.getName();
           }
           catch (Exception e){}
           constraints.setSearchScope(1);
           if (!dn.equals("")){
              if (!base.equals("")) {
                 dn = dn + "," + base;
              }
           }
           else {
              dn = base;
           }
           ldapOptions.setSearchBase(dn);
           filter = "(&("+ldapOptions.getGroupUniqueAttributeName()+"=*)"+filter+")";
           results=getEntries(filter).iterator();
           while (results.hasNext()) {
              sr = (SearchResult) results.next();
              attributes = sr.getAttributes();
              try {
                 String id=(String)attributes.get(ldapOptions.getGroupUniqueAttributeName()).get();
                 entries.add(id);
                 if (LDAPClient.groupAttributes.getMaximumSize()>0) {
                    LDAPClient.groupAttributes.put(id, attributes);
                 }
              } catch (Exception ex) {}
           }
        } // results.hasNext
     } // else from if (ldapOptions.getStructureType() == 1)
       constraints.setSearchScope(2);
     ldapOptions.setSearchBase(base);
     return entries;
   }

   /**
    * Connects to the LDAP server and retrieves wanted data.
    * All neccessary information to establish connection to the server, and
    * to retrieve data are hold within LDAPOptions.
    */
   public String getUserAttribute (String username,String ldapAttrib) throws Throwable {
//System.out.println("method getUserAttribute for username= " + username);      
      String ret=null;
      if (LDAPClient.userAttributes.getMaximumSize()>0) {
        Attributes attributes = (Attributes)LDAPClient.userAttributes.get(username);
        if (attributes != null) {
          Object o=attributes.get(ldapAttrib).get();
          if (o!=null) ret=o.toString();
          return ret;
        }  
      }

      String filter="";
      String[] objClasses=tokenize(ldapOptions.getUserObjectClasses(),boundary);
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
      filter="(&("+ldapOptions.getUserUniqueAttributeName()+"="+username+")"+filter+")";

      List l=getEntries(filter);
      if (l.size()==0) throw new Exception();
      Iterator results=getEntries(filter).iterator();
      /* For each entry found. */
      while (results.hasNext()) {
         SearchResult sr = (SearchResult) results.next();
         Attributes attributes = sr.getAttributes();
         ret=(String)attributes.get(ldapAttrib).get();
         if (ret!=null) {
            break;
         }
      }
      return ret;
   }

   /**
    * Connects to the LDAP server and retrieves wanted data.
    * All neccessary information to establish connection to the server, and
    * to retrieve data are hold within LDAPOptions.
    */
   public String getGroupAttribute (String groupName,String ldapAttrib) throws Throwable {
//System.out.println("method getGroupAttribute for groupName= " + groupName);      
      String ret=null;
      if (LDAPClient.groupAttributes.getMaximumSize()>0) {
        Attributes attributes = (Attributes)LDAPClient.groupAttributes.get(groupName);
        if (attributes != null) {
          ret=(String)attributes.get(ldapAttrib).get();
          return ret;
        }  
      }
      String filter="";
      String[] objClasses=tokenize(ldapOptions.getGroupObjectClasses(),boundary);
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
      filter="(&("+ldapOptions.getGroupUniqueAttributeName()+"="+groupName+")"+filter+")";

      List l=getEntries(filter);
      if (l.size()==0) throw new Exception();
      Iterator results=getEntries(filter).iterator();
      while (results.hasNext()) {
         SearchResult sr = (SearchResult) results.next();
         Attributes attributes = sr.getAttributes();
         ret=(String)attributes.get(ldapAttrib).get();
         if (ret!=null) {
            break;
         }
      }
      return ret;
   }

   /**
    * Connects to the LDAP server and retrieves wanted data.
    * All neccessary information to establish connection to the server, and
    * to retrieve data are hold within LDAPOptions.
    */
   public boolean doesGroupExist (String groupName) throws Throwable {
//System.out.println("method doesGroupExist(groupName) for " + groupName);
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
         filter="(&("+ldapOptions.getGroupUniqueAttributeName()+"=" + ldapOptions.getGroupGroupsName()+ ")"+filter+")";
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
            filter = "";
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
            filter="(&("+ldapOptions.getGroupUniqueAttributeName()+"="+groupName+")"+filter+")";
            count = getEntries(filter).size();
            ldapOptions.setSearchBase(base);
        }
      } // if (ldapOptions.getStructureType() == 1)      
      else {      
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
         filter="(&("+ldapOptions.getGroupUniqueAttributeName()+"="+groupName+")"+filter+")";
         count = getEntries(filter).size();
      }
      
      return count>0;
   }

   /**
    * Connects to the LDAP server and retrieves wanted data.
    * All neccessary information to establish connection to the server, and
    * to retrieve data are hold within LDAPOptions.
    */
   public boolean doesUserExist (String username) throws Throwable {
//System.out.println("method doesUserExist(username) for " + username);
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
            filter = "";
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
            filter="(&("+ldapOptions.getUserUniqueAttributeName()+"="+username+")"+filter+")";
            count = getEntries(filter).size();
            ldapOptions.setSearchBase(base);
         }   
      }
      else { // if (ldapOptions.getStructureType() == 1)
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
        filter="(&("+ldapOptions.getUserUniqueAttributeName()+"="+username+")"+filter+")";
        count = getEntries(filter).size();
     }
   
     return count>0;
   }

   /**
    * Connects to the LDAP server and retrieves wanted data.
    * All neccessary information to establish connection to the server, and
    * to retrieve data are hold within LDAPOptions.
    */
   public boolean doesUserBelongToGroup (String groupName,String username) throws Throwable {
//System.out.println("method doesUserBelongToGroup for groupName= " + groupName + "and username= " + username);
      if (!doesGroupExist(groupName)){
         throw new Exception("Group " + groupName + "does not exist.");  
      }
      if (!doesUserExist(username)){
         throw new Exception("User " + username + "does not exist.");  
      }
      String base = ldapOptions.getSearchBase();
      String filter="";
      String[] objClasses;
      Iterator results;
      SearchResult sr;
      Attributes attributes;
      String dn = null;

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
         filter="(&("+ldapOptions.getGroupUniqueAttributeName()+"=" + ldapOptions.getGroupUserRelationsName() +")"+filter+")";
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
            constraints.setSearchScope(1);

            filter="";
            objClasses=tokenize(ldapOptions.getRelationObjectClasses(),boundary);
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
            filter="(&("+ldapOptions.getRelationUniqueAttributeName()+"="+groupName+")"+filter+")";
            results=getEntries(filter).iterator();
            constraints.setSearchScope(2);
            ldapOptions.setSearchBase(base);

            if (results.hasNext()) {
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
               filter="(&("+ldapOptions.getUserUniqueAttributeName()+"=*)"+filter+")";

               sr = (SearchResult) results.next();
               attributes = sr.getAttributes();
               Attribute attribute = attributes.get("member");
               NamingEnumeration ne = attribute.getAll();
               while (ne.hasMore()){
                  String member_dn = (String) ne.next();
                  ldapOptions.setSearchBase(member_dn);
                  constraints.setSearchScope(0);
                  Iterator elems = getEntries(filter).iterator();
                  SearchResult sres = (SearchResult) elems.next();
                  Attributes attrs = sres.getAttributes();
                  try {
                     String id=(String)attrs.get(ldapOptions.getUserUniqueAttributeName()).get();
                     if (id.equals(username)){
                        ldapOptions.setSearchBase(base);
                        constraints.setSearchScope(2);
                        return true;
                     }
                     if (LDAPClient.userAttributes.getMaximumSize()>0) {
                        LDAPClient.userAttributes.put(id, attrs);
                     }
                  }
                  catch (Exception e){}
               } // while
            } // if (results.hasNext())
            ldapOptions.setSearchBase(base);
            constraints.setSearchScope(2);
         } // if (results.hasNext())

         filter="";
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
         filter="(&("+ldapOptions.getGroupUniqueAttributeName()+"=" + ldapOptions.getGroupGroupRelationsName() + ")"+filter+")";
         results=getEntries(filter).iterator();
         if (results.hasNext()) {
            sr = (SearchResult) results.next();
            attributes = sr.getAttributes();
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
            constraints.setSearchScope(1);
            ldapOptions.setSearchBase(dn);
            filter="";
            objClasses=tokenize(ldapOptions.getRelationObjectClasses(),boundary);
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
            filter="(&("+ldapOptions.getRelationUniqueAttributeName()+"="+groupName+")"+filter+")";
            results=getEntries(filter).iterator();
            constraints.setSearchScope(2);
            ldapOptions.setSearchBase(base);

            if (results.hasNext()) {
               filter="";
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
               filter="(&("+ldapOptions.getGroupUniqueAttributeName()+"=*)"+filter+")";

               sr = (SearchResult) results.next();
               attributes = sr.getAttributes();
               Attribute attribute = attributes.get("member");
               NamingEnumeration ne = attribute.getAll();
               while (ne.hasMore()){
                  String member_dn = (String) ne.next();
                  ldapOptions.setSearchBase(member_dn);
                  constraints.setSearchScope(0);
                  Iterator elems = getEntries(filter).iterator();
                  SearchResult sres = (SearchResult) elems.next();
                  Attributes attrs = sres.getAttributes();
                  ldapOptions.setSearchBase(base);
                  constraints.setSearchScope(2);
                  try {
                     String id=(String)attrs.get(ldapOptions.getGroupUniqueAttributeName()).get();
                     if (LDAPClient.userAttributes.getMaximumSize()>0) {
                        LDAPClient.userAttributes.put(id, attrs);
                     }
                     if (doesUserBelongToGroup(id,username)){
                        return true;
                     }
                 }
                 catch (Exception e){}
               } // while
            } // if (results.hasNext())
            ldapOptions.setSearchBase(base);
            constraints.setSearchScope(2);
         } // if (results.hasNext())
     } // if (ldapOptions.getStructureType() == 1)
     else {
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
        filter="(&("+ldapOptions.getGroupUniqueAttributeName()+"="+groupName+")"+filter+")";
        results=getEntries(filter).iterator();
        if (results.hasNext()){
           sr = (SearchResult) results.next();
//           attributes = sr.getAttributes();
           try {
              dn = sr.getName();
           }
           catch (Exception e){}
           if (!dn.equals("")){
              if (!base.equals("")) {
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
            filter="(&("+ldapOptions.getUserUniqueAttributeName()+"="+username+")"+filter+")";
            int size = getEntries(filter).size();
            ldapOptions.setSearchBase(base);
            return size>0;
         }
      } // else from if (ldapOptions.getStructureType() == 1)
      return false;
   }

   /**
    * Connects to the LDAP server and retrieves wanted data.
    * All neccessary information to establish connection to the server, and
    * to retrieve data are hold within LDAPOptions.
    */
   public boolean doesGroupBelongToGroup (String groupName,String subgroupName) throws Throwable {
//System.out.println("method doesGroupBelongToGroup for groupName= " + groupName + "and subgroupName= " + subgroupName);
      if (!doesGroupExist(groupName)){
         throw new Exception("Group " + groupName + "does not exist.");  
      }
      if (!doesGroupExist(subgroupName)){
         throw new Exception("Group " + subgroupName + "does not exist.");  
      }
      String base = ldapOptions.getSearchBase();
      String filter="";
      String[] objClasses;
      Iterator results;
      SearchResult sr;
      Attributes attributes;
      String dn = null;

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
         filter="(&("+ldapOptions.getGroupUniqueAttributeName()+"=" + ldapOptions.getGroupGroupRelationsName() + ")"+filter+")";
         results=getEntries(filter).iterator();
            if (results.hasNext()) {
               sr = (SearchResult) results.next();
//         attributes = sr.getAttributes();
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
            constraints.setSearchScope(1);
            ldapOptions.setSearchBase(dn);

            filter="";
            objClasses=tokenize(ldapOptions.getRelationObjectClasses(),boundary);
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
            filter="(&("+ldapOptions.getRelationUniqueAttributeName()+"="+groupName+")"+filter+")";
            results=getEntries(filter).iterator();
            constraints.setSearchScope(2);
            ldapOptions.setSearchBase(base);

            if (results.hasNext()) {
               filter="";
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
               filter="(&("+ldapOptions.getGroupUniqueAttributeName()+"=*)"+filter+")";
               sr = (SearchResult) results.next();
               attributes = sr.getAttributes();
               Attribute attribute = attributes.get("member");

               NamingEnumeration ne = attribute.getAll();
               while (ne.hasMore()){
                  String member_dn = (String) ne.next();
                  ldapOptions.setSearchBase(member_dn);
                  constraints.setSearchScope(0);
                  Iterator elems = getEntries(filter).iterator();
                  SearchResult sres = (SearchResult) elems.next();
                  Attributes attrs = sres.getAttributes();
                  ldapOptions.setSearchBase(base);
                  constraints.setSearchScope(2);
                  try {
                     String id=(String)attrs.get(ldapOptions.getGroupUniqueAttributeName()).get();
                     if (LDAPClient.groupAttributes.getMaximumSize()>0) {
                        LDAPClient.groupAttributes.put(id, attrs);
                     }
                     if (id.equals(subgroupName) || doesGroupBelongToGroup(id, subgroupName)){
                        return true;
                     }
                  }
                  catch (Exception e){}
               } // while
            } // if (results.hasNext())
            ldapOptions.setSearchBase(base);
            constraints.setSearchScope(2);
         } // if (results.hasNext())
     } // if (ldapOptions.getStructureType() == 1)
     else {
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
         filter="(&("+ldapOptions.getGroupUniqueAttributeName()+"="+groupName+")"+filter+")";
         results=getEntries(filter).iterator();
         if (results.hasNext()){
            sr = (SearchResult) results.next();
//            attributes = sr.getAttributes();
            try {
               dn = sr.getName();
            }
            catch (Exception e){}
            if (!dn.equals("")){
               if (!base.equals("")) {
                   dn = dn + "," + base;
                }
             }
             else {
                dn = base;
             }
             ldapOptions.setSearchBase(dn);

             filter="";
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
             filter="(&("+ldapOptions.getGroupUniqueAttributeName()+"="+subgroupName+")"+filter+")";
             int size = getEntries(filter).size();
             ldapOptions.setSearchBase(base);
             return size>0;
          } // if (results.hasNext())
       } // else from if (ldapOptions.getStructureType() == 1)
       return false;
   }

   /**
    * Connects to the LDAP server and retrieves wanted data.
    * All neccessary information to establish connection to the server, and
    * to retrieve data are hold within LDAPOptions.
    */
   public java.util.List getEntries (String filter) throws Throwable {
      java.util.List entries=new java.util.ArrayList();
      DirContext ctx=new InitialDirContext(env);
      try {
//System.out.println("GTQ for "+filter);

         //System.out.println("SB="+ldapOptions.getSearchBase());
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
