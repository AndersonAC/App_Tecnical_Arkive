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
import java.util.ArrayList;
import java.util.Scanner;

public class Principal {

	String nome;

	int regime;// 0 = simples , 1 = Presumido, 2 = real
	int vencimento;

	double valorHonorario;
	double pis;
	double cofins;
	double irpj;
	double cssl;

	boolean posto = false;
	boolean ativo = true;

	public static void main(String args[]) {
		Scanner scan = new Scanner(System.in);
		char opcao;
		
		Arquivo empresa = new Arquivo();
		
		do {
			menu();
			opcao = scan.next().charAt(0);
			opcao = Character.toUpperCase(opcao);

			switch (opcao) {
			case 'C':
				//empresa.cadastrarEmpresa("teste", 1, 5, 500.1, 1.5, 2.5, 1.5, 1.7, 1.3, 1.8, false, true);
				//empresa.Detalhar("teste");
				break;
				
			case 'V':
				System.out.println("Retornando ao menu anteior");
			}
		} while (opcao != 'V');
			
		
	}
	
	public static void menu()
	{
		System.out.println("Escolha um Opção abaixo");
		System.out.println("C - Cadastrar Empresa");
		System.out.println("V- Voltar ao Menu Anterior");
	}
}
