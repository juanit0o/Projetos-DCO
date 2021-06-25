package pt.tooyummytogo.facade.handlers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import pt.tooyummytogo.domain.Comerciante;
import pt.tooyummytogo.domain.Produto;

public class ColocarProdutoHandler {

	private Comerciante comerciante_corrente;

	public ColocarProdutoHandler(Comerciante comerciante_corrente) {
		this.comerciante_corrente = comerciante_corrente;
		this.comerciante_corrente.iniciaVenda();
	}

	/**
	 * Devolve a lista de produtos do Comerciante
	 * @return lista de produtos do Comerciant
	 */
	public List<String> inicioDeProdutosHoje() {
		List<String> output = new ArrayList<String>();
		for (Produto produto : this.comerciante_corrente.getListaProduto()) {
			output.add(produto.getCodigo());
		}
		return output;
	}

	/**
	 * Adiciona à Venda Produto codigo e a sua quantidade
	 * @param codigo codigo do produto
	 * @param quantidade quantidade do produto a venda
	 */
	public void indicaProduto(String codigo, int quantidade) {
		this.comerciante_corrente.addProdutoVenda(codigo, quantidade);
	}

	/**
	 * Confirma a venda
	 * @param inicio inicio da venda
	 * @param fim fim da venda
	 */
	public void confirma(LocalDateTime inicio, LocalDateTime fim) {
		this.comerciante_corrente.confirmaDisponibilidade(inicio, fim);
	}

}
