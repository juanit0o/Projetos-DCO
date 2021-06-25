package pt.tooyummytogo.facade.dto;

public class ProdutoInfo {
	
	private String nome;
	private int quantidade;
	private ComercianteInfo comercianteInfo;
	private double preco;
	
	public ProdutoInfo(String nome, int quantidade, ComercianteInfo comercianteInfo, double preco) {
		this.nome = nome;
		this.quantidade = quantidade;
		this.comercianteInfo = comercianteInfo;
		this.preco = preco;
	}
	
	/**
	 * Retorna o Comerciante do ProdutoInfo
	 * @return comerciante
	 */
	public ComercianteInfo getComercianteInfo() {
		return this.comercianteInfo;
	}
	
	/**
	 * Retorna o nome do ProdutoInfo
	 * @return nome
	 */
	public String getNome() {
		return this.nome;
	}
	
	/**
	 * Retorna a quantidade do ProdutoInfo
	 * @return quantidade
	 */
	public int getQuantidade() {
		return this.quantidade;
	}
	
	/**
	 * Retorna o preco do ProdutoInfo
	 * @return preco
	 */
	public double getPreco() {
		return this.preco;
	}
	
	/**
	 * Define a quantidade do ProdutoInfo
	 * @param quantidade quantidade a definir
	 */
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

}
