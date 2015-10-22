/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app_tecnical_arkive;

/**
 *
 * @author Andy
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class Arquivo 
{
    //Cadastrar Empresa em Arquivo Individual
    public void cadastrarEmpresa (int cod, String nome, int regime, int diaVencimento,double valorHonorario,
                    double pis, double cofins, double irpj_1, double irpj_2, double cssl_1, 
                    double cssl_2, boolean posto, boolean status)
    {
        try
        {   
            File arquivo = new File(cod+"-"+nome+".bitae");

            boolean ok = arquivo.createNewFile();		
            if(ok){
                criarArquivoEmpresa(cod, nome, regime, diaVencimento, valorHonorario, pis, cofins,
                    irpj_1, irpj_2, cssl_1, cssl_2, posto, status, arquivo);
            }
            else
                System.out.println("Empresa já foi cadastrada!");
        }
        catch(IOException e)
        {
                System.out.println("Falha da criação do arquivo! "+e);
        }
    }

    private void criarArquivoEmpresa(int cod, String nome, int regime, int diaVencimento,
                    double valorHonorario, double pis, double cofins, double irpj_1, 
                    double irpj_2, double cssl_1, double cssl_2, boolean posto, 
                    boolean status, File arquivo) throws FileNotFoundException, IOException {

        FileOutputStream fos = new FileOutputStream(arquivo);
        DataOutputStream dos = new DataOutputStream(fos);

        dos.writeInt(cod);                          //00\\Código ++ em arquivo
        dos.writeUTF(String.format("%-100s", nome));//01\\Ate 100 de tamanho de nome
        dos.writeInt(regime);                       //02\\int 1-Simples, 2-Presumido, 3-Real
        dos.writeInt(diaVencimento);                //11\\int 1-31 fazer tratamento para dias 31-30-29-28
        dos.writeDouble(valorHonorario);            //10\\double a pagar
        dos.writeDouble(pis);                       //04\\double porcentagem
        dos.writeDouble(cofins);                    //05\\double porcentagem
        dos.writeDouble(irpj_1);                    //06\\IRPJ1 double porcentagem
        dos.writeDouble(irpj_2);                    //07\\IRPJ2 double porcentagem
        dos.writeDouble(cssl_1);                    //08\\CSSL1 double porcentagem
        dos.writeDouble(cssl_2);                    //09\\CSSL2 double porcentagem
        dos.writeBoolean(posto);                    //03\\Posto Combustível T or F
        dos.writeBoolean(status);                   //12\\Ativado ou desativado -|- T or F

        dos.close();
    }
    
    //Cadastrar Empresas em um arquivo de Lista de Empresas
    public void cadastrarLista (int cod, String nome, boolean status)
    {
        try
        {
            File arquivo = new File("ListaEmpresas.bitae");
            arquivo.createNewFile();

            criarArquivoMesclado(cod, nome, status, arquivo);

        }
        catch(IOException e)
        {
                System.out.println("Falha da criação do arquivo Lista! "+e);
        }
    }
/*
    private void criarArquivoLista(int cod, String nome,boolean status, File arquivo) 
            throws FileNotFoundException, IOException {

        FileOutputStream fos = new FileOutputStream(arquivo);
        DataOutputStream dos = new DataOutputStream(fos);


        dos.writeInt(cod);                          //00\\Código ++ em arquivo
        dos.writeUTF(String.format("%-100s", nome));//01\\Ate 100 de tamanho de nome
        dos.writeBoolean(status);                   //02\\Ativado ou desativado -|- T or F

        dos.close();
    }
*/  
    private void criarArquivoMesclado(int cod, String nome,boolean status, File arquivo) 
            throws FileNotFoundException, IOException {
        File arquivoTemp = new File("arquivoTemp.bitae");
        arquivoTemp.createNewFile();
        
        int codAux;
        String nomeAux;
        boolean statusAux;
        
        FileInputStream fis = new FileInputStream(arquivo);
        DataInputStream dis = new DataInputStream(fis);
        
        FileOutputStream fos = new FileOutputStream(arquivoTemp);
        DataOutputStream dos = new DataOutputStream(fos);
        int qualquerVarialvel = dis.readInt();
        System.out.println("inteiro: "+qualquerVarialvel);
        while((codAux = dis.readInt()) != -1){
            nomeAux = dis.readUTF();
            statusAux = dis.readBoolean();
            
            dos.writeInt(codAux);                          //00\\Código ++ em arquivo
            dos.writeUTF(String.format("%-100s", nomeAux));//01\\Ate 100 de tamanho de nome
            dos.writeBoolean(statusAux);                   //02\\Ativado ou desativado -|- T or F
        }
        fis = new FileInputStream(arquivoTemp);
        dis = new DataInputStream(fis);
        
        fos = new FileOutputStream(arquivo);
        dos = new DataOutputStream(fos);
        
        while((codAux = dis.readInt()) != -1){
            nomeAux = dis.readUTF();
            statusAux = dis.readBoolean();
            
            dos.writeInt(codAux);                          //00\\Código ++ em arquivo
            dos.writeUTF(String.format("%-100s", nomeAux));//01\\Ate 100 de tamanho de nome
            dos.writeBoolean(statusAux);                   //02\\Ativado ou desativado -|- T or F
        }
        
        dos.writeInt(cod);                          //00\\Código ++ em arquivo
        dos.writeUTF(String.format("%-100s", nome));//01\\Ate 100 de tamanho de nome
        dos.writeBoolean(status);                   //02\\Ativado ou desativado -|- T or F

        dos.close();
        
        arquivoTemp.deleteOnExit();
        
    }

    //Cria um Arquivo de Código da Empresa
    public void criarCodigoEmpresa (int cod)
        throws FileNotFoundException, IOException {
        
        File arquivo = new File("CodigoEmpresas.bitae");
        
        FileOutputStream fos = new FileOutputStream(arquivo);
        DataOutputStream dos = new DataOutputStream(fos);
        
        dos.writeInt(cod);
        dos.close();     
    }

    //Ler o Ultimo Código Lançado Para Acrescentar o Novo a Empresa
    public int lerCodigoEmpresa()
            throws FileNotFoundException, IOException {
        File arquivo = new File("CodigoEmpresas.bitae");
        FileInputStream fis = new FileInputStream(arquivo);
        DataInputStream dis = new DataInputStream(fis);
        
        int cod = dis.readInt();
        
        return cod+1;
    }
    
    //Mostrar Conteudo em TEXTO
    public void detalharUnicaEmpresa(String nome,int cod)
    {
        File arquivo = new File(cod+"-"+nome+".bitae");
        try {
                FileInputStream fis = new FileInputStream(arquivo);
                DataInputStream dis = new DataInputStream(fis);

                System.out.println(dis.readInt());      //Código

                String nomeAux;

                nomeAux = dis.readUTF();
                System.out.println(nomeAux);            //Nome Arquivo

                //System.out.println(dis.readInt());
                int regime = dis.readInt();             //Regime
                if(regime==1){
                    System.out.println("Simples");
                }else if(regime==2){
                    System.out.println("Presumido");
                }else if(regime==3){
                    System.out.println("Real");
                }
                System.out.println(dis.readInt());      //Dia

                System.out.println(dis.readDouble());   //Valor
                System.out.println(dis.readDouble());   //Alíquotas-PIS
                System.out.println(dis.readDouble());   //Alíquotas-COFINS
                System.out.println(dis.readDouble());   //Alíquotas-IRPJ_1
                System.out.println(dis.readDouble());   //Alíquotas-IRPJ_2
                System.out.println(dis.readDouble());   //Alíquotas-CSSL_1
                System.out.println(dis.readDouble());   //Alíquotas-CSSL_2

                System.out.println(dis.readBoolean());  //Posto
                System.out.println(dis.readBoolean());  //Status

        }  catch (IOException e) {
                System.out.println("Error: " + e);
        }
    }
    //Mostrar Conteudo em TEXTO
    public void detalharArquivoLista()
    {
        File arquivo = new File("ListaEmpresas.bitae");
        try {
            FileInputStream fis = new FileInputStream(arquivo);
            DataInputStream dis = new DataInputStream(fis);

            int codAux;

            while((codAux = dis.readInt()) != -1){

                System.out.println(codAux);             //Código
                System.out.println(dis.readUTF());      //Nome Arquivo
                System.out.println(dis.readBoolean());  //Status
            }
        }  catch (IOException e) {
            System.out.println("Error: detalharArquivoLista -_- " + e);
        }
    }
    //////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    //Cadatrar Lista Anderson's Style
    public void cadastrarMyList(int cod, String nome, boolean status){
        try {
            File arquivo = new File("ListaEmpresas.bitae");
            if(arquivo.exists()){
               System.out.println("O arquivo "+arquivo+" EXISTE");
               criarArquivoListaMesclado(cod, nome, status);
            }
            else{
                System.out.println("O arquivo "+arquivo+" NÂO EXISTE");
                criarArquivoLista(cod, nome, status, arquivo);
            }
            
        } catch (Exception e) {
            System.out.println("Erro no cadastro da lista: "+e.toString());
        }
    }
    //Cria o primeiro arquivo da lista
    private void criarArquivoLista(int cod, String nome,boolean status, File arquivo){
        try{
            FileOutputStream fos = new FileOutputStream(arquivo);
            DataOutputStream dos = new DataOutputStream(fos);


            dos.writeInt(cod);                          //00\\Código ++ em arquivo
            dos.writeUTF(String.format("%-100s", nome));//01\\Ate 100 de tamanho de nome
            dos.writeBoolean(status);                   //02\\Ativado ou desativado -|- T or F

            dos.close();
        }catch(Exception e){
            System.out.println("Erro na criação da primeira lista: "+e.toString());
        }
    }
    ////////////////////////////////////////////////////////////
    private void criarArquivoListaMesclado(int cod, String nome,boolean status)throws Exception{
        File arquivo = new File("ListaEmpresas.bitae");
        File arquivoTemp = new File("arquivoTemp.bitae");
        
        int codAux;
        String nomeAux;
        boolean statusAux;
        
        FileInputStream fis1 = new FileInputStream(arquivo);
        DataInputStream dis1 = new DataInputStream(fis1);
        
        FileOutputStream fos2 = new FileOutputStream(arquivoTemp);
        DataOutputStream dos2 = new DataOutputStream(fos2);
        
        ///2ª parte
        FileInputStream fis2 = new FileInputStream(arquivoTemp);
        DataInputStream dis2 = new DataInputStream(fis2);
        
        FileOutputStream fos1 = new FileOutputStream(arquivo);
        DataOutputStream dos1 = new DataOutputStream(fos1);
        
        codAux = dis1.readInt();
        dos2.writeInt(codAux);
        while((nomeAux = dis1.readUTF()) != " "){
            dos2.writeUTF(String.format("%-100s", nomeAux));
            statusAux = dis1.readBoolean();
            dos2.writeBoolean(statusAux);
            codAux = dis1.readInt();
            dos2.writeInt(codAux);
            
        }
        dos2.close();
        
        while((codAux = dis2.readInt()) != -1){
            dos1.writeInt(codAux);                          //00\\Código ++ em arquivo
            nomeAux = dis2.readUTF();
            dos1.writeUTF(String.format("%-100s", nomeAux));//01\\Ate 100 de tamanho de nome
            statusAux = dis2.readBoolean();
            dos1.writeBoolean(statusAux);                   //02\\Ativado ou desativado -|- T or F   
        }
        
        dos1.writeInt(cod);                          //00\\Código ++ em arquivo
        dos1.writeUTF(String.format("%-100s", nome));//01\\Ate 100 de tamanho de nome
        dos1.writeBoolean(status);                   //02\\Ativado ou desativado -|- T or F

        dos1.close();
        
        arquivoTemp.deleteOnExit();
    }
}
