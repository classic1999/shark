package org.enhydra.shark.api.internal.toolagent;

/**
 * This class represents the parameter passed to tool agent application.
 * It holds all neccessary information about shark's activity variable.
 * It differs from original WfMC spec in the following:
 * <ul>
 * <li> it defines two fields for parameter name (instead of one as it is in spec):
 * the actual one, and the formal one
 * <li> it defines additional field which determines the mode of parameter
 * (it can be input, output or input and output parameter).
 * <li> it defined additional field which determines the Java class
 * for the parameter
 * </ul>
 */
public final class AppParameter {

   /**
    * The name of the actual parameter (shark variable). This is the Id of
    * the FormalParameter or DataField from XPDL definition, if mode of
    * corresponding FormalParameter from XPDL Application Definition is OUT or
    * IN_OUT, otherwise it is the expression which is evaluated to get the
    * value contained in this object.
    */
   public String the_actual_name = null;

   /**
    * The name of the formal parameter of XPDL application definition
    * that corresponds to this AppParameter instance.
    */
   public String the_formal_name = null;

   /**
    * The mode of the formal parameter, as defined in its XPDL application
    * definition. It can be:
    * <ul>
    * <li> "IN" - then shark doesn't take into account the value of this
    * parameter after execution of tool agent.
    * <li> "OUT" - then shark takes into account the value of this parameter
    * after execution, but tool agent application should not care about
    * this parameter value when it gets it.
    * <li> "INOUT" - then both, shark and tool agent application take into
    * account the value of this parameter.
    * </ul>
    */
   public String the_mode = null;

   /**
    * The value of the parameter - this is a value of a shark variable
    * if mode of corresponding FormalParameter from XPDL Application Definition
    * is OUT or IN_OUT, otherwise it is the value of evaluated expression for
    * the actual parameter in XPDL.
    */
   public Object the_value = null;

   /**
    * The java class of parameter.
    */
   public Class the_class = null;

   /**
    * The length of parameter value. This is not used in standard shark kernel
    * implementation, and it is defined only to stay as close as possible
    * to WfMC spec.
    */
   public long the_length = -1;

   /**
    * The type of parameter. This is not used in standard shark kernel
    * implementation, and it is defined only to stay as close as possible
    * to WfMC spec.
    */
   public long the_type = -1;

   /**
    * Creates instance with all object fields initialized to null possible
    * and primitive type fields to -1.
    */
   public AppParameter () {
   }

   /**
    * Creates an instance with fields set to the given parameter values
    * (the_length and the_type fields are initialize to -1).
    */
   public AppParameter (String _the_actual_name,
                        String _the_formal_name,
                        String _the_mode,
                        Object _the_value,
                        Class _the_class) {

      the_actual_name = _the_actual_name;
      the_formal_name = _the_formal_name;
      the_mode = _the_mode;
      the_value = _the_value;
      the_class = _the_class;
   } // ctor

   /**
    * Creates an instance with fields set to the given parameter values.
    */
   public AppParameter (String _the_actual_name,
                        String _the_formal_name,
                        String _the_mode,
                        Object _the_value,
                        Class _the_class,
                        long _the_length_,
                        long _the_type_) {
      the_actual_name = _the_actual_name;
      the_formal_name = _the_formal_name;
      the_mode = _the_mode;
      the_value = _the_value;
      the_class = _the_class;
      the_length = _the_length_;
      the_type = _the_type_;
   } // ctor

   public String toString () {
      return "[APN="+the_actual_name+", FPN="+the_formal_name+", MODE="+the_mode+", VAL="+the_value+", CLS="+the_class+", LNG="+the_length+", TYP="+the_type+"]";
   }
}
