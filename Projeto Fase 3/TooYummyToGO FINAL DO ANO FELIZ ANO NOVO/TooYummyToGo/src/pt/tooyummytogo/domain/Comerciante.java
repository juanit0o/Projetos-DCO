package pt.tooyummytogo.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;

public class Comerciante {

	private String username;
	private String password;
	private PosicaoCoordenadas posicaoCoordenadas;
	private List<Produto> listProdutos;
	private Venda venda;
	
	public Comerciante(String username, String password, PosicaoCoordenadas posicaoCoordenadas) {
		this.username = username;
		this.password = password;
		this.posicaoCoordenadas = posicaoCoordenadas;
		this.listProdutos = new ArrayList<Produto>();
	}
	
	/**
	 * Verifica se o Comerciante é valido, isto é,se existe
	 * um Comerciante com o username e password recebidos
	 * @param username username para comparação
	 * @param password password para comparação
	 * @return se é um Comerciante
	 */
	public boolean isComerciante(String username, String password) {
		return (username == null || password == null) 
				? false
				: (this.username == username && this.password == password);
	}
	
	/**
	 * Adiciona à lista de Produtos um novo Produto
	 * @param nome nome do produto
	 */
	public void addProduto(String nome, double preco) {
		this.listProdutos.add(new Produto(nome, String.valueOf(this.listProdutos.size()), preco));
	}
	
	
	/**
	 * Retorna a lista de Produtos deste Comerciante
	 * @return Lista de Produtos
	 */
	public List<Produto> getListaProduto(){
		return this.listProdutos;
	}
	
	/**
	 * Retorna a quantidade do produto nome
	 * @param nome nome do produto
	 * @return quantidade
	 */
	public int getQuantidade(String nome) {
		return this.venda.getQuantidade(nome);
	}
	
	/**
	 * Retorna o username do Comerciante
	 * @return username
	 */
	public String getNome() {
		return this.username;
	}
	
	/**
	 * Retorna a posição do Comerciante
	 * @return posicaoCoordenadas
	 */
	public PosicaoCoordenadas getLocalizacao() {
		return this.posicaoCoordenadas;
	}
	
	/**
	 * Inicia um nova venda
	 */
	public void iniciaVenda() {
		this.venda = new Venda();
	}
	
	/**
	 * Adiciona um Produto à venda
	 * @param nome nome do produto
	 * @param quantidade quantidade do produto
	 */
	public void addProdutoVenda(String codigo, int quantidade) {
		boolean existe = false;
		String nome = "";
		double preco = 0;
		for (Produto produto : listProdutos) {
			if(produto.getCodigo() == codigo) {
				existe = true;
				nome = produto.getNome();
				preco = produto.getPreco();
				break;
			}
		}
		if(existe) {
			this.venda.addProduto(nome, quantidade, preco);
		} else {
			//EXTENSAO 3a
			addProduto(codigo, Math.random()*10); //Preco aleatorio de 0 a 10
			System.out.println(codigo + " nao existe na lista de produtos do comerciante.\n" + 
								"Foi adicionado a lista de produtos do comerciante.");		
		}		
	}
	
	/**
	 * Confirma a Venda
	 * @param inicio hora do inicio
	 * @param fim hora do fim
	 */
	public void confirmaDisponibilidade(LocalDateTime inicio, LocalDateTime fim) {
		this.venda.confirma(inicio, fim);
	}
	
	/**
	 * Retorna a venda do Comerciante
	 * @return venda
	 */
	public Venda getVenda() {
		return this.venda;
	}
	
}
