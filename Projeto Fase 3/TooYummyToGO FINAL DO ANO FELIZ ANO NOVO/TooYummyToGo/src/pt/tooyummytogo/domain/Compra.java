package pt.tooyummytogo.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import pt.tooyummytogo.facade.dto.ProdutoInfo;

public class Compra {

	private Utilizador utilizador;
	private int raio;
	private LocalDateTime res_inicio;
	private LocalDateTime res_fim;
	private String estado;
	private List<ProdutoInfo> carrinho;
	
	public Compra(Utilizador utilizador) {
		this.utilizador = utilizador;
		this.raio = 5000; //5km
		this.res_inicio = LocalDateTime.now();
		this.res_fim = LocalDateTime.now().plusHours(1);
		this.estado = "pendente";
		this.carrinho = new ArrayList<ProdutoInfo>();
	}
	
	/**
	 * Define o raio da Compra
	 * @param raio raio da Compra
	 */
	public void setRaio(int raio) {
		this.raio = raio;
	}
	
	/**
	 * Retorna o raio da Compra
	 * @return raio da Compra
	 */
	public int getRaio() {
		return this.raio;
	}
	
	/**
	 * Retorna o inicio da Compra
	 * @return inicio da Compra
	 */
	public LocalDateTime getInicio() {
		return this.res_inicio;
	}
	
	/**
	 * Retorna o fim da Compra
	 * @return fim da Compra
	 */
	public LocalDateTime getFim() {
		return this.res_fim;
	}
	
	/**
	 * Define o inicio e o fim da Compra
	 * @param res_inicio inicio da Compra
	 * @param res_fim fim da Compra
	 */
	public void setTime(LocalDateTime res_inicio, LocalDateTime res_fim) {
		this.res_inicio = res_inicio;
		this.res_fim = res_fim;
	}
	
	/**
	 * Adiciona ao Carrinho o produto e a quantidade que o Utilizador
	 * quer comprar
	 * @param produtoInfo produtoInfo do produto a adicionar
	 * @param quantidade quantidade que o Utilizador quer comprar
	 */
	public void addCarrinho(ProdutoInfo produtoInfo, int quantidade) {
		this.carrinho.add(new ProdutoInfo(produtoInfo.getNome(), quantidade, produtoInfo.getComercianteInfo(), produtoInfo.getPreco()));
	}
	
	/**
	 * Retorna o Carrinho
	 * @return carrinho
	 */
	public List<ProdutoInfo> getCarrinho(){
		return this.carrinho;
	}
	
	/**
	 * Define o estado
	 * @param estado estado a definir
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}
