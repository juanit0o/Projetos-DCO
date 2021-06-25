package pt.tooyummytogo.domain;

public class Utilizador {

	private String username;
	private String password;
	
	public Utilizador(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Verifica se o Utilizador é valido, isto é,se existe
	 * um Utilizador com o username e password recebidos
	 * @param username username para comparação
	 * @param password password para comparação
	 * @return se é um Utilizador
	 */
	public boolean isUtilizador(String username, String password) {
		return (username == null || password == null) 
				? false
				: (this.username == username && this.password == password);
	}
	
	/**
	 * Retorna o nome do Utilizador
	 * @return nome do Utilizador
	 */
	public String getName() {
		return this.username;
	}
	
}
