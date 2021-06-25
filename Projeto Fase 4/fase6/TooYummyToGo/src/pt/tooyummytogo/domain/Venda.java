package pt.tooyummytogo.domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import extras.Compra;

public class Venda extends Observable implements Observer  {
	
	private String estado;
	private HashMap<String, Integer> listaProdutoVenda; // CODIGO + QUANTIDADE
	private HashMap<String, Double> listaPreco; // CODIGO + PRECO
	private LocalDateTime inicio;
	private LocalDateTime fim;
	
	public Venda(Comerciante cc) {
		this.estado = "pendente";
		this.listaProdutoVenda = new HashMap<String, Integer>();
		this.listaPreco = new HashMap<String, Double>();
		this.addObserver(cc);
	}
	
	/**
	 * ToString
	 */
	public String toString() {
		return String.valueOf(this.listaProdutoVenda.size());
	}
	
	/**
	 * Retorna a Lista de Produtos Venda 
	 * @return Lista de Produtos Venda 
	 */
	public HashMap<String, Integer> getListaProdutoVenda(){
		return this.listaProdutoVenda;
	}
	
	/**
	 * Retorna o preço do produto indicado
	 * @param nome nome do produto
	 * @return o preco
	 */
	public double getPreco(String nome) {
		double output = 0;
		for(Map.Entry<String, Double> p : this.listaPreco.entrySet()) {
			if(p.getKey() == nome) {
				output = p.getValue();
				break;
			}
		}
		return output;	
	}
	
	/**
	 * Adiciona à LPV o nome e quantidade do produto
	 * @param nome nome do produto
	 * @param quantidade quantidade do produto
	 */
	protected void addProduto(String codigo, int quantidade, double preco) {
		this.listaProdutoVenda.put(codigo, quantidade);
		this.listaPreco.put(codigo, preco);
	}
	
	/**
	 * Confirma a Venda, definindo o seu horario e estado
	 * @param inicio inicio da venda
	 * @param fim fim da venda
	 */
	protected void confirma(LocalDateTime inicio, LocalDateTime fim) {
		this.inicio = inicio;
		this.fim = fim;
		this.estado = "finalizada";
	}
	
	/**
	 * Retorna o inicio da venda
	 * @return inicio da venda
	 */
	public LocalDateTime getInicio() {
		return this.inicio;
	}
	
	/**
	 * Retorna o fim da venda
	 * @return fim da venda
	 */
	public LocalDateTime getFim() {
		return this.fim;
	}

	/**
	 * Retorna a quantidade do produto nome
	 * Caso a quantidade não tenha sido ainda definida
	 * retorna 0
	 * @param nome nome do produto
	 * @return quantidade de nome
	 */
	public int getQuantidade(String nome) {
		return this.listaProdutoVenda.get(nome) != null ? this.listaProdutoVenda.get(nome) : 0;
	}

	/**
	 * Remove a quantidade recebida da quantidade do produto nome
	 * @param nome nome do produto
	 * @param quantidade quantidade a remover
	 */
	public void removeQuantidade(String nome, int quantidade) {
		this.listaProdutoVenda.replace(nome, this.listaProdutoVenda.get(nome) - quantidade); 	
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Compra && arg instanceof String) {
			Compra c = (Compra) o;
			String st = (String) arg;
			this.setChanged();
			this.notifyObservers(c);
		}
		
	}

}
