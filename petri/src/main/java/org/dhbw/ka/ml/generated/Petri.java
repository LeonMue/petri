/* Generated By:JJTree&JavaCC: Do not edit this line. Petri.java */
package org.dhbw.ka.ml.generated;

public class Petri/*@bgen(jjtree)*/implements PetriTreeConstants, PetriConstants {/*@bgen(jjtree)*/
  protected JJTPetriState jjtree = new JJTPetriState();

  final public ASTroot root() throws ParseException {
 /*@bgen(jjtree) root */
  ASTroot jjtn000 = new ASTroot(JJTROOT);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      ASTComplexTypes jjtn001 = new ASTComplexTypes(JJTCOMPLEXTYPES);
      boolean jjtc001 = true;
      jjtree.openNodeScope(jjtn001);
      try {
        label_1:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case STRUCT:
            ;
            break;
          default:
            jj_la1[0] = jj_gen;
            break label_1;
          }
          complex_type();
        }
      } catch (Throwable jjte001) {
      if (jjtc001) {
        jjtree.clearNodeScope(jjtn001);
        jjtc001 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte001 instanceof RuntimeException) {
        {if (true) throw (RuntimeException)jjte001;}
      }
      if (jjte001 instanceof ParseException) {
        {if (true) throw (ParseException)jjte001;}
      }
      {if (true) throw (Error)jjte001;}
      } finally {
      if (jjtc001) {
        jjtree.closeNodeScope(jjtn001, true);
      }
      }
      jj_consume_token(0);
                                            jjtree.closeNodeScope(jjtn000, true);
                                            jjtc000 = false;
                                            {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
      if (jjtc000) {
        jjtree.clearNodeScope(jjtn000);
        jjtc000 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte000 instanceof RuntimeException) {
        {if (true) throw (RuntimeException)jjte000;}
      }
      if (jjte000 instanceof ParseException) {
        {if (true) throw (ParseException)jjte000;}
      }
      {if (true) throw (Error)jjte000;}
    } finally {
      if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, true);
      }
    }
    throw new Error("Missing return statement in function");
  }

  final public void complex_type() throws ParseException {
    struct();
  }

  final public void struct() throws ParseException {
 /*@bgen(jjtree) struct */
    ASTstruct jjtn000 = new ASTstruct(JJTSTRUCT);
    boolean jjtc000 = true;
    jjtree.openNodeScope(jjtn000);ASTIdentifier ident;
    ASTIdentifier parent = null;
    try {
      jj_consume_token(STRUCT);
      ident = identifier();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 19:
        jj_consume_token(19);
        parent = identifier();
        break;
      default:
        jj_la1[1] = jj_gen;
        ;
      }
      jj_consume_token(20);
                                                                   ASTfields jjtn001 = new ASTfields(JJTFIELDS);
                                                                   boolean jjtc001 = true;
                                                                   jjtree.openNodeScope(jjtn001);
      try {
        label_2:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case BOOL:
          case INT32:
          case INT64:
          case FLOAT32:
          case FLOAT64:
          case STRING:
          case DELETED:
          case ID:
            ;
            break;
          default:
            jj_la1[2] = jj_gen;
            break label_2;
          }
          field();
        }
      } catch (Throwable jjte001) {
                                                                   if (jjtc001) {
                                                                     jjtree.clearNodeScope(jjtn001);
                                                                     jjtc001 = false;
                                                                   } else {
                                                                     jjtree.popNode();
                                                                   }
                                                                   if (jjte001 instanceof RuntimeException) {
                                                                     {if (true) throw (RuntimeException)jjte001;}
                                                                   }
                                                                   if (jjte001 instanceof ParseException) {
                                                                     {if (true) throw (ParseException)jjte001;}
                                                                   }
                                                                   {if (true) throw (Error)jjte001;}
      } finally {
                                                                   if (jjtc001) {
                                                                     jjtree.closeNodeScope(jjtn001, true);
                                                                   }
      }
      jj_consume_token(21);
      jjtree.closeNodeScope(jjtn000, true);
      jjtc000 = false;
        jjtn000.setIdent(ident);
        jjtn000.setParent(parent);
    } catch (Throwable jjte000) {
      if (jjtc000) {
        jjtree.clearNodeScope(jjtn000);
        jjtc000 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte000 instanceof RuntimeException) {
        {if (true) throw (RuntimeException)jjte000;}
      }
      if (jjte000 instanceof ParseException) {
        {if (true) throw (ParseException)jjte000;}
      }
      {if (true) throw (Error)jjte000;}
    } finally {
      if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, true);
      }
    }
  }

  final public void field() throws ParseException {
 /*@bgen(jjtree) field */
    ASTfield jjtn000 = new ASTfield(JJTFIELD);
    boolean jjtc000 = true;
    jjtree.openNodeScope(jjtn000);Token t = null;
    SimpleNode type;
    ASTIdentifier ident;
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case DELETED:
        t = field_annotation();
        break;
      default:
        jj_la1[3] = jj_gen;
        ;
      }
      type = type_identifier();
      ident = identifier();
      jj_consume_token(22);
      jjtree.closeNodeScope(jjtn000, true);
      jjtc000 = false;
        jjtn000.setDeleted(t != null);
        jjtn000.setType(type);
        jjtn000.setIdent(ident);
    } catch (Throwable jjte000) {
      if (jjtc000) {
        jjtree.clearNodeScope(jjtn000);
        jjtc000 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte000 instanceof RuntimeException) {
        {if (true) throw (RuntimeException)jjte000;}
      }
      if (jjte000 instanceof ParseException) {
        {if (true) throw (ParseException)jjte000;}
      }
      {if (true) throw (Error)jjte000;}
    } finally {
      if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, true);
      }
    }
  }

  final public SimpleNode type_identifier() throws ParseException {
    SimpleNode t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case BOOL:
    case INT32:
    case INT64:
    case FLOAT32:
    case FLOAT64:
    case STRING:
      t = primitive_type();
      break;
    case ID:
      t = identifier();
      break;
    default:
      jj_la1[4] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
        {if (true) return t;}
    throw new Error("Missing return statement in function");
  }

  final public Token field_annotation() throws ParseException {
    Token t;
    t = jj_consume_token(DELETED);
                    {if (true) return t;}
    throw new Error("Missing return statement in function");
  }

  final public ASTPrimitiveType primitive_type() throws ParseException {
 /*@bgen(jjtree) PrimitiveType */
    ASTPrimitiveType jjtn000 = new ASTPrimitiveType(JJTPRIMITIVETYPE);
    boolean jjtc000 = true;
    jjtree.openNodeScope(jjtn000);Token t;
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case BOOL:
        t = jj_consume_token(BOOL);
        break;
      case INT32:
        t = jj_consume_token(INT32);
        break;
      case INT64:
        t = jj_consume_token(INT64);
        break;
      case FLOAT32:
        t = jj_consume_token(FLOAT32);
        break;
      case FLOAT64:
        t = jj_consume_token(FLOAT64);
        break;
      case STRING:
        t = jj_consume_token(STRING);
        break;
      default:
        jj_la1[5] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      jjtree.closeNodeScope(jjtn000, true);
      jjtc000 = false;
        jjtn000.setType(t.image);
        jjtn000.setBeginColumn(t.beginColumn);
        jjtn000.setBeginLine(t.beginLine);
        {if (true) return jjtn000;}
    } finally {
      if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, true);
      }
    }
    throw new Error("Missing return statement in function");
  }

  final public ASTIdentifier identifier() throws ParseException {
 /*@bgen(jjtree) Identifier */
    ASTIdentifier jjtn000 = new ASTIdentifier(JJTIDENTIFIER);
    boolean jjtc000 = true;
    jjtree.openNodeScope(jjtn000);Token t;
    try {
      t = jj_consume_token(ID);
      jjtree.closeNodeScope(jjtn000, true);
      jjtc000 = false;
        jjtn000.setIdent(t.image);
        jjtn000.setBeginColumn(t.beginColumn);
        jjtn000.setBeginLine(t.beginLine);
        {if (true) return jjtn000;}
    } finally {
      if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, true);
      }
    }
    throw new Error("Missing return statement in function");
  }

  /** Generated Token Manager. */
  public PetriTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[6];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x40,0x80000,0x43f80,0x2000,0x41f80,0x1f80,};
   }

  /** Constructor with InputStream. */
  public Petri(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public Petri(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new PetriTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public Petri(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new PetriTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public Petri(PetriTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(PetriTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List jj_expentries = new java.util.ArrayList();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[23];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 6; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 23; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = (int[])jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
