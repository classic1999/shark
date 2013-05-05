package org.enhydra.shark.api.internal.assignment;

public final class PerformerData {

   public String pkgId = null;

   public String pDefId = null;

   public String participantIdOrExpression = null;

   public boolean isUnresolvedExpressionParticipant=false;

   public String participantType;

   public PerformerData () {
   }

   public PerformerData (String _pkgId,
                         String _pDefId,
                         String _participantIdOrExpression,
                         boolean _isUnresolvedExpressionParticipant,
                         String _participantType) {
      this.pkgId=_pkgId;
      this.pDefId=_pDefId;
      this.participantIdOrExpression=_participantIdOrExpression;
      this.isUnresolvedExpressionParticipant=_isUnresolvedExpressionParticipant;
      this.participantType=_participantType;
   }


}
