/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoed2_parte3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gabriel
 */
public class LZ78 {
  List<String> dicionario;

public LZ78() {
    dicionario = new ArrayList<String>();
    dicionario.add(null);
}

public List<Character> codificar(List<Integer> listaNumeros){
    String codigo="";
    List<Character> listaC =new ArrayList<Character>();
    String letra = "";
    boolean parada = false;
    int posicao=0;
    for(int i=0;i<=listaNumeros.size()+1;i++){
        int num=listaNumeros.get(i);
        letra+=(char)num;
        if(!dicionario.contains(letra)){   
            dicionario.add(letra);
            codigo+=0;
            listaC.add('0');
            codigo+=letra;
            char paso=letra.charAt(0);
            listaC.add(paso);
            letra="";
        }else{
            while(dicionario.contains(letra)){
                i++;
                if(i>=listaNumeros.size()){
                    parada=true;
                    if(!dicionario.contains(letra)){
                    listaC.add('0');
                    char paso2=letra.charAt(0);
                    listaC.add(paso2);
                    }else{
                        int index=dicionario.indexOf(letra);
                        String paso3=""+index;
                        for (int j = 0; j < paso3.length(); j++) {
                            listaC.add(paso3.charAt(j));
                        }
                    }
                    System.out.println("BREAK!");
                    break;
                }
                posicao=dicionario.indexOf(letra);
                num=listaNumeros.get(i);
                letra+=(char)num;
            }
            if(parada){
                break;
            }
            i++;
            dicionario.add(letra);
            letra=""+letra.charAt(letra.length()-1);    
            codigo+=posicao;

            String numero=""+posicao;
            for (int j = 0; j < numero.length(); j++) {
                listaC.add(numero.charAt(j));
            }
            codigo+=letra;   
            char paso=letra.charAt(0);
            listaC.add(paso);
            letra="";
            i--;
        }
    }
    System.out.println(codigo);
    return listaC;
}

public void codificacao(String tweetsTxT) throws IOException {
    BufferedReader arqEntradaOriginal = new BufferedReader(new FileReader(tweetsTxT));
    BufferedWriter arqSaidaCodificacao = new BufferedWriter(new FileWriter(tweetsTxT+".lz78"));
    List<Integer> list = new ArrayList<Integer>();
    int i = 0;
    int aux = arqEntradaOriginal.read();
    while(aux!=-1) {
        list.add(aux);
        i++;
        aux = arqEntradaOriginal.read();
    }
    List<Character> cod = codificar(list);
    char[] codigo=new char[cod.size()];
    for (int j = 0; j < cod.size(); j++) {
        codigo[j]=cod.get(j);
        arqSaidaCodificacao.write(codigo[j]);
    }
    arqSaidaCodificacao.close();
}

public void decodificacao(String caminhoTxt) throws IOException{
    BufferedReader arqEntradaCodificado = new BufferedReader(new FileReader(caminhoTxt));
    BufferedWriter arqSaidaOriginal = new BufferedWriter(new FileWriter(caminhoTxt+".original"));

    List<Character> list = new ArrayList<Character>();
    int i = 0;
    int aux = arqEntradaCodificado.read();
    while(aux!=-1) {
        list.add((char)aux);
        i++;
        aux=arqEntradaCodificado.read();
    }

    System.out.println(list);

    //List<Integer> list2 =decodificar(list);
    /*
    List<Character> code=codificar(list);
    char[] codigo=new char[code.size()];
    for (int j = 0; j < code.size()-1; j++) {
        codigo[j]=code.get(j);

        bufEscritura.write(codigo[j]);
    }
    System.out.println("FIN codificacao");
    bufEscritura.close();
    */
    }
}