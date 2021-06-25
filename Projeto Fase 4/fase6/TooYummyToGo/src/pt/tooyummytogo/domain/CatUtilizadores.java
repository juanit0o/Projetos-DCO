package pt.tooyummytogo.domain;

import java.util.ArrayList;
import java.util.List;

public class CatUtilizadores {
	
	private List<Utilizador> catUtilizadores;
	
	public CatUtilizadores() {
		this.catUtilizadores = new ArrayList<Utilizador>();
	}

	/**
	 * Adiciona novo Utilizador ao catalogo de Utilizadores
	 * @param utilizador Utilizador a adicionar
	 */
	public void addUtilizador(Utilizador utilizador) {
		this.catUtilizadores.add(utilizador);
	}
	
	/**
	 * Retorna o Utilizador que tenha o username e a password recebida iguais
	 * Caso seja diferente, retorna um Utilizador "null"
	 * @param username username para comparação
	 * @param password password para comparação
	 * @return Utilizador
	 */
	public Utilizador getUtilizador(String username, String password) {
		for (Utilizador ut : this.catUtilizadores) {
			if(ut.isUtilizador(username, password)) {
				return ut;
			}			
		}
		return new Utilizador(null, null);
	}
	
}
