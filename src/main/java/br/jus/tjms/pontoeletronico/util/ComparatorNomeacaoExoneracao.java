package br.jus.tjms.pontoeletronico.util;

import java.util.Comparator;

import br.jus.tjms.organizacional.beans.NomeacaoExoneracao;

@SuppressWarnings("unchecked")
public class ComparatorNomeacaoExoneracao implements Comparator {
	
	//@Override
	public int compare(Object nomeacaoExoneracao1, Object nomeacaoExoneracao2) {
		
		NomeacaoExoneracao ne1 = (NomeacaoExoneracao) nomeacaoExoneracao1;
		NomeacaoExoneracao ne2 = (NomeacaoExoneracao) nomeacaoExoneracao2;		
		
		if (ne1.getMatricula() < ne2.getMatricula()) {
			return -1;
		}
		
		if (ne1.getMatricula() > ne2.getMatricula()) {
			return 1;
		}
		
		if (ne1.getDataInicial().compareTo(ne2.getDataInicial()) < 0) {
			return -1;
		} 
		
		if ((ne1.getDataInicial().compareTo(ne2.getDataInicial()) > 0)) {
			return 1;
		}

		if ((ne1.getDataFinal() == null) && (ne2.getDataFinal() == null)) {
			return 0;
		}
		
		if (ne1.getDataFinal() == null) {
			return -1;
		}
		
		if (ne2.getDataFinal() == null) {
			return 1;
		} 
		
		if (ne1.getDataFinal().compareTo(ne2.getDataFinal()) < 0) {
			return -1;
		} 
		
		if ((ne1.getDataFinal().compareTo(ne2.getDataFinal()) > 0)) {
			return 1;
		}		
		
		return 0;
	}

}
