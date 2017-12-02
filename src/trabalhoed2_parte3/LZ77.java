/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoed2_parte3;
import java.io.*;

/**
 *
 * @author gabriel
 */
public class LZ77 {
  public static final int TAM_PADRAO_BUFFER = 1024;
  protected int tamBuffer;
  protected Reader arqEntrada;
  protected PrintWriter arqSaida;
  protected StringBuffer auxPesquisa; 

  public LZ77() {
    this(TAM_PADRAO_BUFFER);
  }
  
  public LZ77(int buffSize) {
    tamBuffer = buffSize;
    auxPesquisa = new StringBuffer(tamBuffer);
  }

  private void organizaBufferPesquisa() {
    if (auxPesquisa.length() > tamBuffer) {
      auxPesquisa = 
  auxPesquisa.delete(0,  auxPesquisa.length() - tamBuffer);
    }
  }

  /**
   * Uncompress method
   *
   * @param infile the name of the file to uncompress - automatically appends
   * a ".lz77" extension to the supplied filename
   * @exception IOException if an error occurs during file processing
   */
  public void descompacta(String infile) throws IOException {
    arqEntrada = new BufferedReader(new FileReader(infile+".lz77"));
    StreamTokenizer st = new StreamTokenizer(arqEntrada);

    st.ordinaryChar((int)' ');
    st.ordinaryChar((int)'.');
    st.ordinaryChar((int)'-');
    st.ordinaryChar((int)'\n');
    st.wordChars((int)'\n', (int)'\n');
    st.wordChars((int)' ', (int)'}');

    int offset, tamanho;
    while (st.nextToken() != StreamTokenizer.TT_EOF) {
      switch (st.ttype) {
      case StreamTokenizer.TT_WORD:
	auxPesquisa.append(st.sval);
	System.out.print(st.sval);
	organizaBufferPesquisa();
	break;
      case StreamTokenizer.TT_NUMBER:
	offset = (int)st.nval; 
	st.nextToken();
	if (st.ttype == StreamTokenizer.TT_WORD) {
	auxPesquisa.append(offset+st.sval);
	  System.out.print(offset+st.sval);
	  break; 
	}
	st.nextToken(); 
	tamanho = (int)st.nval;
	String output = auxPesquisa.substring(offset, offset+tamanho);
	System.out.print(output);
	auxPesquisa.append(output);
	organizaBufferPesquisa();
	break;
      default:
      }
    }
    arqEntrada.close();
  }

  public void compacta(String arqTxt) throws IOException {
    arqEntrada = new BufferedReader(new FileReader(arqTxt));
    arqSaida = new PrintWriter(new BufferedWriter(new FileWriter(arqTxt+".lz77")));
    int proxCaracter;
    String aux = "";
    int auxIndex = 0, tempIndex = 0;
    while ((proxCaracter = arqEntrada.read()) != -1) {
      tempIndex = auxPesquisa.indexOf(aux + (char)proxCaracter);
      if (tempIndex != -1){
	aux += (char)proxCaracter;
	auxIndex = tempIndex;
      }else{
	String codedString = 
	  "~"+auxIndex+"~"+aux.length()+"~"+(char)proxCaracter;
	String concat = aux + (char)proxCaracter;
	if (codedString.length() <= concat.length()) {
	  arqSaida.print(codedString);
	  auxPesquisa.append(concat); 
	  aux = "";
	  auxIndex = 0;
	} else {
	  aux = concat; auxIndex = -1;
	  while (aux.length() > 1 && auxIndex == -1) {
	    arqSaida.print(aux.charAt(0));
	    auxPesquisa.append(aux.charAt(0));
	    aux = aux.substring(1, aux.length());
	    auxIndex = auxPesquisa.indexOf(aux);
	  }
	}
	organizaBufferPesquisa();
      }
    }
    if (auxIndex != -1) {
      String codedString = 
	"~"+auxIndex+"~"+aux.length();
      if (codedString.length() <= aux.length()) {
	arqSaida.print("~"+auxIndex+"~"+aux.length());
      } else {
	arqSaida.print(aux);
      }
    }
    arqEntrada.close();
    arqSaida.flush(); arqSaida.close();
  }
}
