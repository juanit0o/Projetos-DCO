package pt.tooyummytogo;

import pt.tooyummytogo.domain.Comerciante;
import pt.tooyummytogo.domain.Utilizador;
import pt.tooyummytogo.exception.SessaoInvalidaException;
import pt.tooyummytogo.facade.handlers.AdicionarTipoDeProdutoHandler;
import pt.tooyummytogo.facade.handlers.ColocarProdutoHandler;
import pt.tooyummytogo.facade.handlers.EncomendarHandler;
import pt.tooyummytogo.facade.handlers.RegistarComercianteHandler;
import pt.tooyummytogo.facade.handlers.RegistarUtilizadorHandler;

public class Sessao {

	private Utilizador utilizador_corrente;
	private Comerciante comerciante_corrente;

	private AdicionarTipoDeProdutoHandler adicionarTipoDeProdutoHandler;
	private ColocarProdutoHandler colocarProdutoHandler;
	private EncomendarHandler encomendarHandler;

	public Sessao(String u, 
			String p, 
			RegistarUtilizadorHandler registarUtilizadorHandler, 
			RegistarComercianteHandler registarComercianteHandler) throws SessaoInvalidaException {
		Utilizador ut = registarUtilizadorHandler.getUtilizador(u, p);
		if(ut.isUtilizador(u, p)) {
			this.utilizador_corrente = ut;
			this.encomendarHandler = new EncomendarHandler(this.utilizador_corrente, registarComercianteHandler);
		} else {
			Comerciante cc = registarComercianteHandler.getComerciante(u, p);
			if(cc.isComerciante(u, p)) {
				this.comerciante_corrente = cc;
				this.adicionarTipoDeProdutoHandler = new AdicionarTipoDeProdutoHandler(this.comerciante_corrente);
				this.colocarProdutoHandler = new ColocarProdutoHandler(this.comerciante_corrente);
			} else {
				throw new SessaoInvalidaException();
			}
		}
	}

	/**
	 * Retorna o adicionarTipoDeProdutoHandler
	 * @return adicionarTipoDeProdutoHandler
	 */
	public AdicionarTipoDeProdutoHandler adicionarTipoDeProdutoHandler() {
		return adicionarTipoDeProdutoHandler;
	}

	/**
	 * Retorna a ColocarProdutoHandler
	 * @return ColocarProdutoHandler
	 */
	public ColocarProdutoHandler getColocarProdutoHandler() {
		return colocarProdutoHandler;
	}

	/**
	 * Retorna a EncomendarComerciantesHandler
	 * @return EncomendarComerciantesHandler
	 */
	public EncomendarHandler getEncomendarComerciantesHandler() {
		return encomendarHandler;
	}

}
