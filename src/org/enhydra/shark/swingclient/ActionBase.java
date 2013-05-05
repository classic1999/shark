package org.enhydra.shark.swingclient;

import javax.swing.*;

/**
* The base class for actions.
*/
public abstract class ActionBase extends AbstractAction {

   /** The panel for this action. */
   protected ActionPanel actionPanel;

   /**
    * The Abstract action uses unqualified class name as action name.
    *
    * @param actionPanel The reference to the panel for this action
    */
   public ActionBase(ActionPanel actionPanel) {
      this.actionPanel=actionPanel;
      putValue(Action.NAME,Utils.getUnqualifiedClassName(getClass()));
   }

   /**
    * Constructor which accepts the action name.
    *
    * @param actionPanel The reference to the panel for this action
    * @param name Name of this action
    */
   public ActionBase(ActionPanel actionPanel,String name) {
      super(name);
      this.actionPanel=actionPanel;
   }

}
