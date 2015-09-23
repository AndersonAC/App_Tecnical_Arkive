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


public class Empresa 
{
	

	public void cadastrar (String nome, int regime, int vencimento,double valorHonorario,
			double pis, double cofins, double irpj,double cssl,boolean posto,boolean ativo )
	{
		try
		{
			File arquivo = new File(nome+".bitae");
			
			boolean ok = arquivo.createNewFile();		
			if(ok)
			CriarArquivo(nome, regime, vencimento, valorHonorario, pis, cofins, irpj, cssl, posto, ativo, arquivo);
			else
				System.out.println("Empresa já foi cadastrada!");
		}
		catch(IOException e)
		{
			System.out.println("Falha da criação do arquivo! "+e);
		}
	}

	private void CriarArquivo(String nome, int regime, int vencimento,
			double valorHonorario, double pis, double cofins, double irpj,
			double cssl, boolean posto, boolean ativo, File arquivo)
			throws FileNotFoundException, IOException {
		
		FileOutputStream fos = new FileOutputStream(arquivo);
		DataOutputStream dos = new DataOutputStream(fos);

		dos.writeUTF(String.format("%-100s", nome));
		dos.writeInt(regime);
		dos.writeInt(vencimento);
		dos.writeDouble(valorHonorario);
		dos.writeDouble(pis);
		dos.writeDouble(cofins);
		dos.writeDouble(irpj);
		dos.writeDouble(cssl);
		dos.writeBoolean(posto);
		dos.writeBoolean(ativo);
		
		dos.close();
	}
	
	public void Detalhar(String nome)
	{
		File arquivo = new File(nome+".bitae");
		try {
			FileInputStream fis = new FileInputStream(arquivo);
			DataInputStream dis = new DataInputStream(fis);

			String nomeAux;

			nomeAux = dis.readUTF();
			System.out.println(nomeAux);
			
			System.out.println(dis.readInt());
			System.out.println(dis.readInt());
			
			System.out.println(dis.readDouble());
			System.out.println(dis.readDouble());
			System.out.println(dis.readDouble());
			System.out.println(dis.readDouble());
			System.out.println(dis.readDouble());

			System.out.println(dis.readBoolean());
			System.out.println(dis.readBoolean());
			

		}  catch (IOException e) {
			System.out.println("Error: " + e);
		}
	}
	
}
