package pt.tooyummytogo.facade.handlers;

import pt.tooyummytogo.domain.CatUtilizadores;
import pt.tooyummytogo.domain.Utilizador;

public class RegistarUtilizadorHandler {

	private CatUtilizadores catUtilizadores;

	public RegistarUtilizadorHandler() {	
		this.catUtilizadores = new CatUtilizadores();
	}

	/**
	 * Regista um Utilizador
	 * @param Username username do utilizador
	 * @param Password password do utilizador
	 * @ensures existe um utilizador com esse username
	 */
	public void registarUtilizador(String username, String password) {
		this.catUtilizadores.addUtilizador(new Utilizador(username, password));
	}

	/**
	 * Retorna o Utilizador com username e password igual
	 * @param username username do utilizador
	 * @param password password do utilizador
	 * @return Utilizador com username e password igual
	 */
	public Utilizador getUtilizador(String username, String password) {
		return this.catUtilizadores.getUtilizador(username, password);
	}

}
