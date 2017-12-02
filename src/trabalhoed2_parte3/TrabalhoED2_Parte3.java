/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoed2_parte3;
import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 *
 * @author gabriel
 */
public class TrabalhoED2_Parte3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  throws IOException{
        ArrayList<Tuite> lista = new ArrayList<>();
        ArrayList<BigInteger> id = new ArrayList<>();

        try {
            String endereco = "C:\\Users\\gabriel\\Documents\\NetBeansProjects\\TrabalhoED2_Parte3\\cargamenor2.txt", linha = null;
            BufferedReader br = new BufferedReader(new FileReader(endereco));
            while ((linha = br.readLine()) != null) {
                String[] cod = linha.split("	");
                Tuite e = new Tuite();
                e.setUserID(cod[0]);
                e.setTweetID(cod[1]);
                e.setTweet(cod[2]);
                e.setDate(cod[3]);
                lista.add(e);
            }
            gravacaoArqTweet(lista);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        for (int i = 0; i < lista.size(); i++) {
            System.out.println(lista.get(i).imprime());
        }
        String caminhoTweets = "C:\\Users\\gabriel\\Documents\\NetBeansProjects\\TrabalhoED2_Parte3\\tweetOrdenado.txt";
        LZ77 compressao1 = new LZ77();
        LZ78 compressao2 = new LZ78();
        //compressao1.compacta(caminhoTweets);
        compressao2.codificacao(caminhoTweets);
    }
    
    /*
        Objetivo desta função é criar um arquivo txt apenas com os tweets extraidos do arquivo cargaMenor2.txt. 
    */
    private static void gravacaoArqTweet(List<Tuite> lstTuite) throws IOException{
        FileWriter arq = new FileWriter("tweetOrdenado.txt");
        PrintWriter gravarArq = new PrintWriter(arq);
        for (int i=0; i < lstTuite.size(); i++) {
            gravarArq.println(
                lstTuite.get(i).getTweet()
            ); 
        }
        arq.close();
    }
    
}
