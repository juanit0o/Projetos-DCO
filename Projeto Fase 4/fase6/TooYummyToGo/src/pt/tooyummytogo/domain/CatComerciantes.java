package pt.tooyummytogo.domain;

import java.util.ArrayList;
import java.util.List;

public class CatComerciantes {

	private List<Comerciante> catComerciantes;

	public CatComerciantes() {
		this.catComerciantes = new ArrayList<Comerciante>();
	}

	/**
	 * Adiciona o novo Comerciante ao catalogo de Comerciantes
	 * @param comerciante Comerciante a adicionar
	 */
	public void addComerciante(Comerciante comerciante) {
		this.catComerciantes.add(comerciante);
	}

	/**
	 * Retorna o Comerciante que tenha o username e a password recebida iguais
	 * Caso seja diferente, retorna um Comerciante "null"
	 * @param username username para comparação
	 * @param password password para comparação
	 * @return Comerciante
	 */
	public Comerciante getComerciante(String username, String password) {
		for (Comerciante cc : this.catComerciantes) {
			if(cc.isComerciante(username, password)) {
				return cc;
			}			
		}
		return new Comerciante(null, null, null);
	}

	/**
	 * Retorna o catalogo de Comerciantes
	 * @return Catalogo de Comerciantes
	 */
	public List<Comerciante> getComerciantesLista(){
		return this.catComerciantes;
	}

}
