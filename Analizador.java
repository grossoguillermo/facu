package DCI_TL3;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Analizador {
  final String NOMBRE = "codigo.txt";
  FileInputStream fis;
  Scanner scanner;
  StringTokenizer tokenizer;
  String lineaActual;
  String elementoActual;
  String restanActual;
  int numCaracteresElemento;
  int numCaracterActual;
  int numSigToken;
  String sigToken;
  ArrayList<String> tokens;
  // Expresiones regulares
  Pattern p_and = Pattern.compile("AND");
  Pattern p_or = Pattern.compile("OR");
  Pattern p_not = Pattern.compile("NOT");
  Pattern p_if = Pattern.compile("IF");
  Pattern p_then = Pattern.compile("THEN");
  Pattern p_else = Pattern.compile("ELSE");
  Pattern p_true = Pattern.compile("TRUE");
  Pattern p_false = Pattern.compile("FALSE");
  Pattern p_var = Pattern.compile("VAR");
  Pattern p_integer = Pattern.compile("INTEGER");
  Pattern p_boolean = Pattern.compile("BOOLEAN");
  Pattern p_program = Pattern.compile("PROGRAM");
  Pattern p_procedure = Pattern.compile("PROCEDURE");
  Pattern p_read = Pattern.compile("READ");
  Pattern p_function = Pattern.compile("FUNCTION");
  Pattern p_begin = Pattern.compile("BEGIN");
  Pattern p_end = Pattern.compile("END");
  Pattern p_write = Pattern.compile("WRITE");
  Pattern p_while = Pattern.compile("WHILE");
  Pattern p_do = Pattern.compile("DO");
  Pattern p_alfas = Pattern.compile("[a-zA-Z0-9]+");
  Pattern p_num = Pattern.compile("[0-9]+");
  Pattern p_fin_com = Pattern.compile("}");
  boolean enComentario = false;
  boolean eraComentario = false;

  public static void main(String[] args) {
    Analizador analizador = new Analizador();
    try {
      analizador.iniciar();
    } catch (TokenInvalidoExcepcion e) {
      System.err.println("Token cbaiiiiiiinvalido");
    }
  }

  Analizador() {
    try {
      fis = new FileInputStream(NOMBRE);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    scanner = new Scanner(fis);
    tokens = new ArrayList<String>();
  }

  void iniciar() throws TokenInvalidoExcepcion {
    while (scanner.hasNextLine()) {
      lineaActual = scanner.nextLine();
      tokenizer = new StringTokenizer(lineaActual);
      while (tokenizer.hasMoreTokens()) {
        if (!eraComentario) {
          elementoActual = tokenizer.nextToken();
          numCaracterActual = 0;
          numCaracteresElemento = elementoActual.length();
        } else {
          eraComentario = false;
        }
        if (!enComentario) {
          while (!enComentario && numCaracterActual < numCaracteresElemento) {
            restanActual = elementoActual.substring(numCaracterActual);
            Matcher m_id = p_alfas.matcher(restanActual);
            switch (elementoActual.charAt(numCaracterActual)) {
              case 'I':
                if (p_integer.matcher(restanActual).lookingAt()) {
                  numSigToken = numCaracterActual + 7;
                  sigToken = elementoActual.substring(numSigToken);
                  if (p_alfas.matcher(sigToken).lookingAt()) {
                    id(m_id);
                  } else {
                    tokens.add("INTEGER");
                    numCaracterActual += 7;
                  }
                } else if (p_if.matcher(restanActual).lookingAt()) {
                  numSigToken = numCaracterActual + 2;
                  sigToken = elementoActual.substring(numSigToken);
                  if (p_alfas.matcher(sigToken).lookingAt()) {
                    id(m_id);
                  } else {
                    tokens.add("IF");
                    numCaracterActual += 2;
                  }
                } else {
                  id(m_id);
                }
                break;
              case 'T':
                if (p_then.matcher(restanActual).lookingAt()) {
                  numSigToken = numCaracterActual + 4;
                  sigToken = elementoActual.substring(numSigToken);
                  if (p_alfas.matcher(sigToken).lookingAt()) {
                    id(m_id);
                  } else {
                    tokens.add("THEN");
                    numCaracterActual += 4;
                  }
                } else if (p_true.matcher(restanActual).lookingAt()) {
                  numSigToken = numCaracterActual + 4;
                  sigToken = elementoActual.substring(numSigToken);
                  if (p_alfas.matcher(sigToken).lookingAt()) {
                    id(m_id);
                  } else {
                    tokens.add("TRUE");
                    numCaracterActual += 4;
                  }
                } else {
                  id(m_id);
                }
                break;
              case 'E':
                if (p_else.matcher(restanActual).lookingAt()) {
                  numSigToken = numCaracterActual + 4;
                  sigToken = elementoActual.substring(numSigToken);
                  if (p_alfas.matcher(sigToken).lookingAt()) {
                    id(m_id);
                  } else {
                    tokens.add("ELSE");
                    numCaracterActual += 4;
                  }
                } else if (p_end.matcher(restanActual).lookingAt()) {
                  numSigToken = numCaracterActual + 3;
                  sigToken = elementoActual.substring(numSigToken);
                  if (p_alfas.matcher(sigToken).lookingAt()) {
                    id(m_id);
                  } else {
                    tokens.add("END");
                    numCaracterActual += 3;
                  }
                } else {
                  id(m_id);
                }
                break;
              case 'P':
                if (p_procedure.matcher(restanActual).lookingAt()) {
                  numSigToken = numCaracterActual + 9;
                  sigToken = elementoActual.substring(numSigToken);
                  if (p_alfas.matcher(sigToken).lookingAt()) {
                    id(m_id);
                  } else {
                    tokens.add("PROCEDURE");
                    numCaracterActual += 9;
                  }
                } else if (p_program.matcher(restanActual).lookingAt()) {
                  numSigToken = numCaracterActual + 7;
                  sigToken = elementoActual.substring(numSigToken);
                  if (p_alfas.matcher(sigToken).lookingAt()) {
                    id(m_id);
                  } else {
                    tokens.add("PROGRAM");
                    numCaracterActual += 7;
                  }
                } else {
                  id(m_id);
                }
                break;
              case 'F':
                if (p_function.matcher(restanActual).lookingAt()) {
                  numSigToken = numCaracterActual + 8;
                  sigToken = elementoActual.substring(numSigToken);
                  if (p_alfas.matcher(sigToken).lookingAt()) {
                    id(m_id);
                  } else {
                    tokens.add("FUNCTION");
                    numCaracterActual += 8;
                  }
                } else if (p_false.matcher(restanActual).lookingAt()) {
                  numSigToken = numCaracterActual + 5;
                  sigToken = elementoActual.substring(numSigToken);
                  if (p_alfas.matcher(sigToken).lookingAt()) {
                    id(m_id);
                  } else {
                    tokens.add("FALSE");
                    numCaracterActual += 5;
                  }
                } else {
                  id(m_id);
                }
                break;
              case 'B':
                if (p_boolean.matcher(restanActual).lookingAt()) {
                  numSigToken = numCaracterActual + 7;
                  sigToken = elementoActual.substring(numSigToken);
                  if (p_alfas.matcher(sigToken).lookingAt()) {
                    id(m_id);
                  } else {
                    tokens.add("BOOLEAN");
                    numCaracterActual += 7;
                  }
                } else if (p_begin.matcher(restanActual).lookingAt()) {
                  numSigToken = numCaracterActual + 5;
                  sigToken = elementoActual.substring(numSigToken);
                  if (p_alfas.matcher(sigToken).lookingAt()) {
                    id(m_id);
                  } else {
                    tokens.add("BEGIN");
                    numCaracterActual += 5;
                  }
                } else {
                  id(m_id);
                }
                break;
              case 'W':
                if (p_write.matcher(restanActual).lookingAt()) {
                  numSigToken = numCaracterActual + 5;
                  sigToken = elementoActual.substring(numSigToken);
                  if (p_alfas.matcher(sigToken).lookingAt()) {
                    id(m_id);
                  } else {
                    tokens.add("WRITE");
                    numCaracterActual += 5;
                  }
                } else if (p_while.matcher(restanActual).lookingAt()) {
                  numSigToken = numCaracterActual + 5;
                  sigToken = elementoActual.substring(numSigToken);
                  if (p_alfas.matcher(sigToken).lookingAt()) {
                    id(m_id);
                  } else {
                    tokens.add("WHILE");
                    numCaracterActual += 5;
                  }
                } else {
                  id(m_id);
                }
                break;
              case 'R':
                if (p_read.matcher(restanActual).lookingAt()) {
                  numSigToken = numCaracterActual + 4;
                  sigToken = elementoActual.substring(numSigToken);
                  if (p_alfas.matcher(sigToken).lookingAt()) {
                    id(m_id);
                  } else {
                    tokens.add("READ");
                    numCaracterActual += 4;
                  }
                } else if (p_while.matcher(restanActual).lookingAt()) {
                  numSigToken = numCaracterActual + 5;
                  sigToken = elementoActual.substring(numSigToken);
                  if (p_alfas.matcher(sigToken).lookingAt()) {
                    id(m_id);
                  } else {
                    tokens.add("WHILE");
                    numCaracterActual += 5;
                  }
                } else {
                  id(m_id);
                }
                break;
              case 'V':
                if (p_var.matcher(restanActual).lookingAt()) {
                  numSigToken = numCaracterActual + 3;
                  sigToken = elementoActual.substring(numSigToken);
                  if (p_alfas.matcher(sigToken).lookingAt()) {
                    id(m_id);
                  } else {
                    tokens.add("VAR");
                    numCaracterActual += 3;
                  }
                } else {
                  id(m_id);
                }
                break;
              case 'A':
                if (p_and.matcher(restanActual).lookingAt()) {
                  numSigToken = numCaracterActual + 3;
                  sigToken = elementoActual.substring(numSigToken);
                  if (p_alfas.matcher(sigToken).lookingAt()) {
                    id(m_id);
                  } else {
                    tokens.add("op_log,AND");
                    numCaracterActual += 3;
                  }
                } else {
                  id(m_id);
                }
                break;
              case 'N':
                if (p_not.matcher(restanActual).lookingAt()) {
                  numSigToken = numCaracterActual + 3;
                  sigToken = elementoActual.substring(numSigToken);
                  if (p_alfas.matcher(sigToken).lookingAt()) {
                    id(m_id);
                  } else {
                    tokens.add("op_log,NOT");
                    numCaracterActual += 3;
                  }
                } else {
                  id(m_id);
                }
                break;
              case 'O':
                if (p_or.matcher(restanActual).lookingAt()) {
                  numSigToken = numCaracterActual + 2;
                  sigToken = elementoActual.substring(numSigToken);
                  if (p_alfas.matcher(sigToken).lookingAt()) {
                    id(m_id);
                  } else {
                    tokens.add("op_log,OR");
                    numCaracterActual += 2;
                  }
                } else {
                  id(m_id);
                }
                break;
              case 'D':
                if (p_do.matcher(restanActual).lookingAt()) {
                  numSigToken = numCaracterActual + 2;
                  sigToken = elementoActual.substring(numSigToken);
                  if (p_alfas.matcher(sigToken).lookingAt()) {
                    id(m_id);
                  } else {
                    tokens.add("DO");
                    numCaracterActual += 2;
                  }
                } else {
                  id(m_id);
                }
                break;
              case '/':
                tokens.add("op_div");
                numCaracterActual++;
                break;
              case '*':
                tokens.add("op_mul");
                numCaracterActual++;
                break;
              case '-':
                tokens.add("op_men");
                numCaracterActual++;
                break;
              case '+':
                tokens.add("op_mas");
                numCaracterActual++;
                break;
              case '(':
                tokens.add("par_abre");
                numCaracterActual++;
                break;
              case ')':
                tokens.add("par_cierra");
                numCaracterActual++;
                break;
              case ',':
                tokens.add("coma");
                numCaracterActual++;
                break;
              case ';':
                tokens.add("punto_coma");
                numCaracterActual++;
                break;
              case '=':
                tokens.add("op_rel,IGU");
                numCaracterActual++;
                break;
              case ':':
                if (restanActual.length() > 1 && restanActual.charAt(1) == '=') {
                  tokens.add("op_asi");
                  numCaracterActual += 2;
                } else {
                  throw new TokenInvalidoExcepcion();
                }
                break;
              case '>':
                if (restanActual.length() > 1 && restanActual.charAt(1) == '=') {
                  tokens.add("op_rel,MAI");
                  numCaracterActual += 2;
                } else {
                  tokens.add("op_rel,MAY");
                  numCaracterActual++;
                }
                break;
              case '<':
                if (restanActual.length() > 1 && restanActual.charAt(1) == '=') {
                  tokens.add("op_rel,MEI");
                  numCaracterActual += 2;
                } else if (restanActual.length() > 1 && restanActual.charAt(1) == '>') {
                  tokens.add("op_rel,DIS");
                  numCaracterActual += 2;
                } else {
                  tokens.add("op_rel,MEN");
                  numCaracterActual++;
                }
                break;
              case '{':
                enComentario = true;
                break;
              default:
                Matcher m_num = p_num.matcher(restanActual);
                if (m_num.lookingAt()) {
                  tokens.add("num");
                  numCaracterActual += m_num.end();
                } else if (m_id.lookingAt()) {
                  id(m_id);
                } else {
                  throw new TokenInvalidoExcepcion();
                }
            }
          }
        } else {
          Matcher m_fin_com = p_fin_com.matcher(lineaActual);
          if (m_fin_com.find()) {
            numCaracterActual = m_fin_com.start() + 1;
            enComentario = false;
            eraComentario = true;
          }
        }
      }
    }
    if (enComentario) {
      throw new TokenInvalidoExcepcion();
    }
    mostrarTokens();
  }

  void id(Matcher m_id) {
    m_id.lookingAt();
    tokens.add("id");
    numCaracterActual += m_id.end();
  }

  void mostrarTokens() {
    for (String token : tokens) {
      System.out.println(token);
    }
  }

}
