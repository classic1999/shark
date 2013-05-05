package org.enhydra.shark.api.client.timebase;

/**
 * The pure Java definition of data type defined by
 * the OMG Time Service. It is used in Event audits
 * for recording the time of the event.
 */
public final class UtcT
{
   public long time = (long)0;

   // 8 octets
   public int inacclo = (int)0;

   // 4 octets
   public short inacchi = (short)0;

   // 2 octets
   public short tdf = (short)0;

   public UtcT ()
   {
   } // ctor

   public UtcT (long _time, int _inacclo, short _inacchi, short _tdf)
   {
      time = _time;
      inacclo = _inacclo;
      inacchi = _inacchi;
      tdf = _tdf;
   } // ctor

   public long getTime() {
      return this.time;
   }

   public int getInacclo() {
      return this.inacclo;
   }

   public short getInacchi() {
      return this.inacchi;
   }

   public short getTdf() {
      return this.tdf;
   }

   public java.sql.Timestamp getTimestamp() {
      return new java.sql.Timestamp(time);
   }

}
