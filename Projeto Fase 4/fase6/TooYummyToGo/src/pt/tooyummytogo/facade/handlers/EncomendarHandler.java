package pt.tooyummytogo.facade.handlers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import extras.Compra;
import pt.tooyummytogo.domain.Utilizador;
import pt.tooyummytogo.exception.PagamentoFalhadoException;
import pt.tooyummytogo.facade.dto.ComercianteInfo;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;
import pt.tooyummytogo.facade.dto.ProdutoInfo;
import pt.tooyummytogo.pagamento.Cartao;
import pt.tooyummytogo.pesquisa.PesquisaStrategy;

public class EncomendarHandler {

	private int id;
	private Utilizador utilizador_corrente;
	private List<ComercianteInfo> comercianteInfoList;
	private Compra compra;
	private PosicaoCoordenadas posicao;
	private double precoTotal;
	
	private List<ComercianteInfo> output;

	public EncomendarHandler(Utilizador utilizador_corrente, RegistarComercianteHandler registarComercianteHandler) {
		this.utilizador_corrente = utilizador_corrente;
		this.comercianteInfoList = registarComercianteHandler.getComercianteInfoLista();
		this.compra = new Compra(this.utilizador_corrente);
		this.id = 0;
		this.precoTotal = 0;
	}
	
	/**
	 * Retorna o preço total da compra
	 * @return precoTotal
	 */
	public double getPrecoTotal() {
		return Math.floor(this.precoTotal*100)/100;
	}
	
	/**
	 * Retorna a lista de comerciantes info
	 * @return lista de comerciantes info
	 */
	public List<ComercianteInfo> getComercianteInfoList(){
		return this.comercianteInfoList;
	}
	
	/**
	 * Retorna a compra
	 * @return compra
	 */
	public Compra getCompra() {
		return this.compra;
	}
	
	/**
	 * Retorna a posicao do utilizador
	 * @return posicao do utilizador
	 */
	public PosicaoCoordenadas getPosicao() {
		return this.posicao;
	}

	/**
	 * Define a localizacao e retorna uma nova lista
	 * de ComercianteInfo 
	 * @param posicaoCoordenadas posicaoCoordenads do Utilizador
	 * @return lista de ComercianteInfo
	 */
	public List<ComercianteInfo> indicaLocalizacaoActual(PosicaoCoordenadas posicaoCoordenadas) {
		this.posicao = posicaoCoordenadas;
		return pesquisar("pesquisardefault");
	}

	/**
	 * Define o raio da Compra e retorna uma nova lista
	 * de ComercianteInfo
	 * @param raio novo raio
	 * @return lista de ComercianteInfo
	 */
	public List<ComercianteInfo> redefineRaio(int raio) {
		this.compra.setRaio(raio*1000); //De Kilometros para metros
		return pesquisar("pesquisarraio");
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
		return pesquisar("pesquisarperiodo");
	}

	/**
	 * Realizar uma pesquisa por estrategia e retornar
	 * o return dessa pesquisa
	 * @param st estrategia
	 * @return lista de comerciante info
	 */
	private List<ComercianteInfo> pesquisar(String st) {
		output = new ArrayList<>();
		try {
			Properties pesProp = new Properties();
			pesProp.load(new FileInputStream(new File("pesquisa.properties")));
			
			Optional<Object> s = createInstanceOfClass(pesProp.getProperty(st));
			s.ifPresent((o) -> {
				PesquisaStrategy ps = (PesquisaStrategy) o;
				this.output = ps.escolheComerciantes(this);
			});
		} catch (FileNotFoundException e) {
			//Do nothing, just ignore
		} catch (IOException e) {
			//Do nothing, just ignore
		}				
		return output;
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
	public String indicaPagamento(String cartao, String validade, String ccv) throws PagamentoFalhadoException {
		
		try {
			Cartao card = new Cartao(cartao, validade.split("/")[0], "20" + validade.split("/")[1], ccv);
			card.pagar(Math.floor(this.precoTotal * 100)/100);
		} catch (Exception e) {
			System.out.println("Pagamento/Cartao Invalido - Tente novamente");
			return null;
		}

		for(ProdutoInfo pi : this.compra.getCarrinho()) {
			int quantidade = pi.getQuantidade();
			String nome = pi.getNome();
			for (ComercianteInfo c : this.comercianteInfoList) {
				if(c.equals(pi.getComercianteInfo())) {
					this.compra.addObserver(c.getVenda());
					c.getVenda().removeQuantidade(nome, quantidade);
				}
			}
		}
		
		
		this.compra.compraFinalizada(String.format("%05d", id++));
		return String.format("%05d", id++);

	}
	
	private static Optional<Object> createInstanceOfClass(String nomeDaClass) {
		try {
			Class<?> c = Class.forName(nomeDaClass);
			return Optional.of(c.getConstructor().newInstance());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}


}
