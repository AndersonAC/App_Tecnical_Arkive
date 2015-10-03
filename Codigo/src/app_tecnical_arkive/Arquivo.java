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
	

	public void cadastrarEmpresa (int cod, String nome, int regime, int diaVencimento,double valorHonorario,
			double pis, double cofins, double irpj_1, double irpj_2, double cssl_1, 
                        double cssl_2, boolean posto, boolean status)
	{
		try
		{   
                    File arquivo = new File(cod+"-"+nome+".bitae");

                    boolean ok = arquivo.createNewFile();		
                    if(ok){
                        CriarArquivoEmpresa(cod, nome, regime, diaVencimento, valorHonorario, pis, cofins,
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

	public void cadastrarLista (int cod, String nome, boolean status)
	{
		try
		{
			File arquivo = new File("ListaEmpresas.bitae");
			
			boolean ok = arquivo.createNewFile();		
			if(ok)
                            CriarArquivoLista(cod, nome, status, arquivo);
		}
		catch(IOException e)
		{
			System.out.println("Falha da criação do arquivo! "+e);
		}
	}
        
	private void CriarArquivoEmpresa(int cod, String nome, int regime, int diaVencimento,
			double valorHonorario, double pis, double cofins, double irpj_1, 
                        double irpj_2, double cssl_1, double cssl_2, boolean posto, 
                        boolean status, File arquivo) throws FileNotFoundException, IOException {
		
		FileOutputStream fos = new FileOutputStream(arquivo);
		DataOutputStream dos = new DataOutputStream(fos);

		dos.writeInt(cod);
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
	
        private void CriarArquivoLista(int cod, String nome,boolean status, File arquivo) 
                throws FileNotFoundException, IOException {
		
		FileOutputStream fos = new FileOutputStream(arquivo);
		DataOutputStream dos = new DataOutputStream(fos);

		dos.writeInt(cod);
                dos.writeUTF(String.format("%-100s", nome));//01\\Ate 100 de tamanho de nome
		dos.writeBoolean(status);                   //12\\Ativado ou desativado -|- T or F
		
		dos.close();
	}
        
	public void Detalhar(String nome,int cod)
	{
		File arquivo = new File(cod+"-"+nome+".bitae");
		try {
			FileInputStream fis = new FileInputStream(arquivo);
			DataInputStream dis = new DataInputStream(fis);

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
	
        public void CriarCodigoEmpresa (int cod)
            throws FileNotFoundException, IOException {
            File arquivo = new File("CodigoEmpresas.bitae");
            FileOutputStream fos = new FileOutputStream(arquivo);
            DataOutputStream dos = new DataOutputStream(fos);
            dos.writeInt(cod);
            dos.close();     
        }
        
        public int LerCodigoEmpresa()
                throws FileNotFoundException, IOException {
            File arquivo = new File("CodigoEmpresas.bitae");
            FileInputStream fis = new FileInputStream(arquivo);
            DataInputStream dis = new DataInputStream(fis);
            
            int cod = dis.readInt();
            
            return cod+1;
        }
        
         /*public void cadastrarCodigoEmpresa ()
        {
            try
            {
                File arquivo = new File("CodigoEmprsas.bitae");

                boolean ok = arquivo.createNewFile();		
                if(ok)
                {
                    CriarCodigoEmpresa(0, arquivo);
                }
            }
            catch(IOException e)
            {
                    System.out.println("Falha da criação do arquivo! "+e);
            }            
        }*/
}
