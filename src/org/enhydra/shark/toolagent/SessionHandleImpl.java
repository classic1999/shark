package org.enhydra.shark.toolagent;


import org.enhydra.shark.api.internal.toolagent.*;


public class SessionHandleImpl implements SessionHandle {
   private long handle;
   private String info;

   public SessionHandleImpl (long handle,String info) {
      this.handle=handle;
      this.info=info;
   }

   public long getHandle () {
      return handle;
   }

   public String getInfo () {
      return info;
   }

}
