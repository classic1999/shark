package org.enhydra.shark.xpdl;

/**
 *  XPDLConstants
 *  @author Sasa Bojanic
 */
public final class XPDLConstants  {

   public static final String BASIC_TYPE_STRING="STRING";
   public static final String BASIC_TYPE_FLOAT="FLOAT";
   public static final String BASIC_TYPE_INTEGER="INTEGER";
   public static final String BASIC_TYPE_REFERENCE="REFERENCE";
   public static final String BASIC_TYPE_DATETIME="DATETIME";
   public static final String BASIC_TYPE_BOOLEAN="BOOLEAN";
   public static final String BASIC_TYPE_PERFORMER="PERFORMER";

   public static final String CONDITION_TYPE_CONDITION="CONDITION";
   public static final String CONDITION_TYPE_OTHERWISE="OTHERWISE";
   public static final String CONDITION_TYPE_EXCEPTION="EXCEPTION";
   public static final String CONDITION_TYPE_DEFAULTEXCEPTION="DEFAULTEXCEPTION";

   public static final String GRAPH_CONFORMANCE_FULL_BLOCKED="FULL_BLOCKED";
   public static final String GRAPH_CONFORMANCE_LOOP_BLOCKED="LOOP_BLOCKED";
   public static final String GRAPH_CONFORMANCE_NON_BLOCKED="NON_BLOCKED";

   public static final String DATA_FIELD_IS_ARRAY_TRUE="TRUE";
   public static final String DATA_FIELD_IS_ARRAY_FALSE="FALSE";

   public static final String EXECUTION_ASYNCHR="ASYNCHR";
   public static final String EXECUTION_SYNCHR="SYNCHR";

   public static final String FORMAL_PARAMETER_MODE_IN="IN";
   public static final String FORMAL_PARAMETER_MODE_OUT="OUT";
   public static final String FORMAL_PARAMETER_MODE_INOUT="INOUT";

   public static final String JOIN_SPLIT_TYPE_AND="AND";
   public static final String JOIN_SPLIT_TYPE_XOR="XOR";

   public static final String PARTICIPANT_TYPE_RESOURCE_SET="RESOURCE_SET";
   public static final String PARTICIPANT_TYPE_RESOURCE="RESOURCE";
   public static final String PARTICIPANT_TYPE_ROLE="ROLE";
   public static final String PARTICIPANT_TYPE_ORGANIZATIONAL_UNIT="ORGANIZATIONAL_UNIT";
   public static final String PARTICIPANT_TYPE_HUMAN="HUMAN";
   public static final String PARTICIPANT_TYPE_SYSTEM="SYSTEM";

   public static final String DURATION_UNIT_Y="Y";
   public static final String DURATION_UNIT_M="M";
   public static final String DURATION_UNIT_D="D";
   public static final String DURATION_UNIT_h="h";
   public static final String DURATION_UNIT_m="m";
   public static final String DURATION_UNIT_s="s";

   public static final String PUBLICATION_STATUS_UNDER_REVISION="UNDER_REVISION";
   public static final String PUBLICATION_STATUS_RELEASED="RELEASED";
   public static final String PUBLICATION_STATUS_UNDER_TEST="UNDER_TEST";

   public static final String INSTANTIATION_ONCE="ONCE";
   public static final String INSTANTIATION_MULTIPLE="MULTIPLE";

   public static final String TOOL_TYPE_APPLICATION="APPLICATION";
   public static final String TOOL_TYPE_PROCEDURE="PROCEDURE";

   public static final String ACCESS_LEVEL_PRIVATE = "PRIVATE";
   public static final String ACCESS_LEVEL_PUBLIC = "PUBLIC";

   public static final int ACTIVITY_MODE_AUTOMATIC=0;
   public static final int ACTIVITY_MODE_MANUAL=1;

   public static final int ACTIVITY_TYPE_ROUTE = 0;
   public static final int ACTIVITY_TYPE_NO = 1;
   public static final int ACTIVITY_TYPE_TOOL = 2;
   public static final int ACTIVITY_TYPE_SUBFLOW = 3;
   public static final int ACTIVITY_TYPE_BLOCK = 4;

}
