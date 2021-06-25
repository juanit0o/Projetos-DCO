package pt.tooyummytogo.facade.handlers;

import java.util.ArrayList;
import java.util.List;
import pt.tooyummytogo.domain.CatComerciantes;
import pt.tooyummytogo.domain.Comerciante;
import pt.tooyummytogo.facade.dto.ComercianteInfo;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;

public class RegistarComercianteHandler {

	private CatComerciantes catComerciantes;

	public RegistarComercianteHandler() {
		this.catComerciantes = new CatComerciantes();
	}

	/**
	 * Adiciona um novo comerciante ao catalogo de comerciante
	 * @param username username do comerciante
	 * @param password password do comerciante
	 * @param localizacao localizacao do comerciante
	 */
	public void registarComerciante(String username, String password, PosicaoCoordenadas localizacao) {
		this.catComerciantes.addComerciante(new Comerciante(username, password, localizacao));
	}

	/**
	 * Retorna o Comerciante com username e password iguais
	 * @param username username do comerciante
	 * @param password password do comerciante
	 * @return Comerciante com username e password iguais
	 */
	public Comerciante getComerciante(String username, String password) {
		return this.catComerciantes.getComerciante(username, password);
	}

	/**
	 * Retorna a lista de comerciante Info
	 * @return lista de comerciante Info
	 */
	public List<ComercianteInfo> getComercianteInfoLista() {
		List<ComercianteInfo> output = new ArrayList<ComercianteInfo>();
		for(Comerciante c : this.catComerciantes.getComerciantesLista()){
			output.add(new ComercianteInfo(c));
		}
		return output;
	}

}
