/* Generated By:JJTree: Do not edit this line. ASTmessage.java Version 4.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY= */
package org.dhbw.ka.ml.generated;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ASTmessage extends SimpleNode {
  private String ident;
  private String parent;
  public ASTmessage(int id) {
    super(id);
  }

  public ASTmessage(Petri p, int id) {
    super(p, id);
  }

}
/* JavaCC - OriginalChecksum=d12386a20e6b230c648b29973f0d4fc1 (do not edit this line) */
