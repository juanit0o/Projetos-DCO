package pt.tooyummytogo.facade.handlers;

import pt.tooyummytogo.domain.Comerciante;

public class AdicionarTipoDeProdutoHandler {

	protected Comerciante comerciante_corrente;

	public AdicionarTipoDeProdutoHandler(Comerciante comerciante_corrente) {
		this.comerciante_corrente = comerciante_corrente;
	}

	/**
	 * Regista um novo Produto no Comerciante
	 * @param nome nome do produto
	 */
	public void registaTipoDeProduto(String nome, double preco) {
		this.comerciante_corrente.addProduto(nome, preco);
	}

}
