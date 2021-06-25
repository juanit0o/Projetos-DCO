package pt.tooyummytogo.facade.handlers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.monstercard.Card;
import com.monstercard.MonsterCardAPI;
import pt.portugueseexpress.InvalidCardException;
import pt.portugueseexpress.PortugueseExpress;
import pt.tooyummytogo.domain.Compra;
import pt.tooyummytogo.domain.Utilizador;
import pt.tooyummytogo.exception.PagamentoFalhadoException;
import pt.tooyummytogo.facade.dto.ComercianteInfo;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;
import pt.tooyummytogo.facade.dto.ProdutoInfo;

public class EncomendarHandler {

	private int id;
	private Utilizador utilizador_corrente;
	private List<ComercianteInfo> comercianteInfoList;
	private Compra compra;
	private PosicaoCoordenadas posicao;
	private double precoTotal;

	public EncomendarHandler(Utilizador utilizador_corrente, RegistarComercianteHandler registarComercianteHandler) {
		this.utilizador_corrente = utilizador_corrente;
		this.comercianteInfoList = registarComercianteHandler.getComercianteInfoLista();
		this.compra = new Compra(this.utilizador_corrente);
		this.id = 0;
		this.precoTotal = 0;
	}

	/**
	 * Verifica se o Utilizador e se o Comerciante tem horarios compativeis
	 * @param inicio inicio do Comerciante
	 * @param fim fim do Comerciante
	 * @return se o Utilizador e se o Comerciante tem horarios compativeis
	 */
	private boolean condicoesHoras(LocalDateTime inicio, LocalDateTime fim) {
		return ((inicio.isBefore(this.compra.getInicio()) && this.compra.getFim().isBefore(fim)) || 
				(this.compra.getInicio().isBefore(inicio) && fim.isBefore(this.compra.getFim())) || 
				(this.compra.getInicio().isBefore(inicio) && this.compra.getFim().isBefore(fim)) || 
				(inicio.isBefore(this.compra.getInicio()) && fim.isBefore(this.compra.getFim())) ||
				(inicio.isEqual(this.compra.getInicio()) && fim.isEqual(this.compra.getFim())));
	}
	
	/**
	 * Retorna o preço total da compra
	 * @return precoTotal
	 */
	public double getPrecoTotal() {
		return Math.floor(this.precoTotal*100)/100;
	}

	/**
	 * Verifica se o Comerciante está no raio do Utilizador
	 * @param posicaoCoordenadas posição do Comerciante
	 * @return se o Comerciante está no raio do Utilizador
	 */
	private boolean condicoesLocalizacao(PosicaoCoordenadas posicaoCoordenadas) {
		return this.posicao.distanciaEmMetros(posicaoCoordenadas) <= this.compra.getRaio();
	}

	/**
	 * Retorna lista de ComercianteInfo que tem horarios compativeis
	 * e no raio da Compra
	 * @return lista de ComercianteInfo
	 */
	private List<ComercianteInfo> listaComercianteDisponiveis(){
		List<ComercianteInfo> resultado = new ArrayList<ComercianteInfo>(); //3.1
		for (ComercianteInfo c : this.comercianteInfoList) {
			if(c.existeVenda()) {
				PosicaoCoordenadas cl = c.getLocalizacao(); //3.2
				if (condicoesHoras(c.getVenda().getInicio(), c.getVenda().getFim()) && 
						condicoesLocalizacao(cl)) { //3.3
					ComercianteInfo ci = c; //3.4
					resultado.add(ci); //3.4 //3.5
				}
			}
		}
		return resultado;
	}

	/**
	 * Define a localizacao e retorna uma nova lista
	 * de ComercianteInfo 
	 * @param posicaoCoordenadas posicaoCoordenads do Utilizador
	 * @return lista de ComercianteInfo
	 */
	public List<ComercianteInfo> indicaLocalizacaoActual(PosicaoCoordenadas posicaoCoordenadas) {
		this.posicao = posicaoCoordenadas;
		return listaComercianteDisponiveis();
	}

	/**
	 * Define o raio da Compra e retorna uma nova lista
	 * de ComercianteInfo
	 * @param raio novo raio
	 * @return lista de ComercianteInfo
	 */
	public List<ComercianteInfo> redefineRaio(int raio) {
		this.compra.setRaio(raio*1000); //De Kilometros para metros
		List<ComercianteInfo> output = listaComercianteDisponiveis();
		if(output.isEmpty()) {
			System.out.println("Não existe comerciantes num raio de " + raio + "m.");
			return null;
		} else {
			return output;
		}
	}

	/**
	 * Define o inicio e o fim da Compra e retorna uma nova lista 
	 * de ComercianteInfo
	 * @param inicio inicio da Compra
	 * @param fim fim da Compra
	 * @return lista de ComercianteInfo
	 */
	public List<ComercianteInfo> redefinePeriodo(LocalDateTime inicio, LocalDateTime fim) {	
		this.compra.setTime(inicio, fim);
		return listaComercianteDisponiveis();
	}

	/**
	 * Retorna a lista de ProdutoInfo do ComercianteInfo
	 * @param comercianteInfo comerciante escolhido pelo Utilizador
	 * @return lista de ProdutoInfo
	 */
	public List<ProdutoInfo> escolheComerciante(ComercianteInfo comercianteInfo) {
		List<ProdutoInfo> output = new ArrayList<ProdutoInfo>();
		for (ComercianteInfo c : this.comercianteInfoList) {
			if(c.equals(comercianteInfo)) {
				for(Map.Entry<String, Integer> p : c.getVenda().getListaProdutoVenda().entrySet()) {
					output.add(new ProdutoInfo(p.getKey(), p.getValue(), comercianteInfo, c.getVenda().getPreco(p.getKey()) ));	
				}	
				break;
			}
		}
		return output;
	}

	/**
	 * Adiciona ao Carrinho o produto e a quantidade pedida pelo Utilizador
	 * @param produtoInfo produto a adicionar
	 * @param quantidade quantidade a adicionar
	 */
	public void indicaProduto(ProdutoInfo produtoInfo, int quantidade) {
		if(quantidade > 0) {
			for (ComercianteInfo c : this.comercianteInfoList) {
				if(c.equals(produtoInfo.getComercianteInfo())) {
					int quantDisp = c.getVenda().getQuantidade(produtoInfo.getNome());
					if(quantDisp >= quantidade) {
						compra.addCarrinho(produtoInfo, quantidade);
						this.precoTotal += produtoInfo.getPreco();
						System.out.println("Adicionado ao carrinho - " + produtoInfo.getNome() + " x" + quantidade);
					} else {
						System.out.println("Apenas existem " + quantDisp + " (< " + quantidade + ") unidade(s) de " + produtoInfo.getNome());
					}
					break;
				}
			}	
		} else {
			System.out.println("Não é possivel adicionar ao Carrinho 0 unidades");	
		}
	}

	/**
	 * Realiza o pagamento da compra
	 * @param cartao numero do cartao
	 * @param validade validade do cartao
	 * @param vcc codigo do cartao
	 * @return codigo da reserva
	 * @throws PagamentoFalhadoException 
	 * @throws InvalidCardException 
	 */
	public String indicaPagamento(String cartao, String validade, String ccv) throws PagamentoFalhadoException, InvalidCardException {
		
		/*
		MonsterCardAPI mc = new MonsterCardAPI();
		Card card = new Card(cartao, ccv, validade.split("/")[0], "20" + validade.split("/")[1]);
		//mc.isValid(card)
		//mc.charge(card, 0);
		 */
		
		PortugueseExpress pe = new PortugueseExpress();
		pe.setNumber(cartao);
		pe.setCcv(Integer.parseInt(ccv));
		pe.setMonth(Integer.parseInt(validade.split("/")[0]));
		pe.setYear(2000 + Integer.parseInt(validade.split("/")[1]));	
	
		if(pe.validate()) {
			//PRODUTOS NAO TEM PRECO, LOGO NAO E COBRADO (0)
			pe.block(this.precoTotal);
			pe.charge(this.precoTotal);

			for(ProdutoInfo pi : this.compra.getCarrinho()) {
				int quantidade = pi.getQuantidade();
				String nome = pi.getNome();
				for (ComercianteInfo c : this.comercianteInfoList) {
					if(c.equals(pi.getComercianteInfo())) {
						c.getVenda().removeQuantidade(nome, quantidade);
					}
				}
			}
			this.compra.setEstado("finalizada");
			return String.format("%05d", id++);
		} else {
			throw new PagamentoFalhadoException();
		}
	}

}
