package pt.tooyummytogo.facade.dto;

import pt.tooyummytogo.domain.Comerciante;
import pt.tooyummytogo.domain.Venda;

public class ComercianteInfo {

	private String nome;
	private PosicaoCoordenadas posicaoCoordenadas;
	private Venda venda;

	public ComercianteInfo(Comerciante comerciante) {
		this.nome = comerciante.getNome();
		this.posicaoCoordenadas = comerciante.getLocalizacao();
		this.venda = comerciante.getVenda();
	}

	/**
	 * ToString
	 */
	public String toString() {
		return "Vendedor " + this.nome + " (" + this.posicaoCoordenadas.toString() + 
				") - Tem neste momento " + this.venda.toString() + " produtos.";
	}

	/**
	 * Verifica se o ComercianteInfo é igual
	 * @param comercianteInfo ComercianteInfo para comparação
	 * @return se é ou nao o mesmo ComercianteInfo
	 */
	public boolean equals(ComercianteInfo comercianteInfo) {
		return this.nome == comercianteInfo.nome && 
				this.posicaoCoordenadas == comercianteInfo.posicaoCoordenadas && 
				this.venda == comercianteInfo.venda;
	}

	/**
	 * Verifica se o Comerciante tem uma venda
	 * @return se o Comerciante tem uma venda
	 */
	public boolean existeVenda() {
		return this.venda != null;
	}

	/**
	 * Retorna o Localizacao do Comerciante
	 * @return Localizacao
	 */
	public PosicaoCoordenadas getLocalizacao() {
		return this.posicaoCoordenadas;
	}

	/**
	 * Retorna a venda do Comerciante
	 * @return venda
	 */
	public Venda getVenda() {
		return this.venda;
	}

}
