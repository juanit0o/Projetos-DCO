package pt.tooyummytogo.domain;

public class Produto {

	private String codigo;
	private String nome;
	private String ingredientes;
	private double preco;
	
	public Produto(String nome, String codigo, double preco) {
		this.nome = nome;
		this.codigo = codigo;
		this.preco = preco;
	}
	
	/**
	 * Retorna o nome do Produto
	 * @return nome do produto
	 */
	public String getNome() {
		return this.nome;
	}
	
	/**
	 * Retorna o codigo do Produto
	 * @return codigo do produto
	 */
	public String getCodigo() {
		return this.codigo;
	}
	
	/**
	 * Retorna o preco do Produto
	 * @return preco do produto
	 */
	public double getPreco() {
		return this.preco;
	}
	
}
