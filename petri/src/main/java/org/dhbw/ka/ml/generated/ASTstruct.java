/* Generated By:JJTree: Do not edit this line. ASTstruct.java Version 4.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY= */
package org.dhbw.ka.ml.generated;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ASTstruct extends SimpleNode {
  ASTIdentifier ident;
  ASTIdentifier parent;
  public ASTstruct(int id) {
    super(id);
  }

  public ASTstruct(Petri p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(PetriVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=b76c48ffe12d4aedc0bd9ef74585fd16 (do not edit this line) */
