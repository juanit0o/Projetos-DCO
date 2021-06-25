package pt.tooyummytogo.facade;

import java.util.Optional;
import pt.tooyummytogo.Sessao;
import pt.tooyummytogo.exception.SessaoInvalidaException;
import pt.tooyummytogo.facade.handlers.RegistarComercianteHandler;
import pt.tooyummytogo.facade.handlers.RegistarUtilizadorHandler;

/**
 * Esta é a classe do sistema.
 */
public class TooYummyToGo {

	private RegistarUtilizadorHandler registarUtilizadorHandler;
	private RegistarComercianteHandler registarComercianteHandler;

	public TooYummyToGo() {
		this.registarUtilizadorHandler = new RegistarUtilizadorHandler();
		this.registarComercianteHandler = new RegistarComercianteHandler();
	}

	/**
	 * Retorna o RegistarUtilizadorHandler
	 * @return RegistarUtilizadorHandler
	 */
	public RegistarUtilizadorHandler getRegistarUtilizadorHandler() {
		return this.registarUtilizadorHandler;
	}

	/**
	 * Returns an optional Session representing the authenticated user.
	 * @param username
	 * @param password
	 * @return
	 * 
	 * UC2
	 * @throws SessaoInvalidaException 
	 */
	public Optional<Sessao> autenticar(String username, String password) throws SessaoInvalidaException {
		return Optional.of(new Sessao(username, 
				password, 
				this.registarUtilizadorHandler,
				this.registarComercianteHandler));
	}

	/**
	 * Retorna o RegistarComercianteHandler
	 * @return RegistarComercianteHandler
	 */
	public RegistarComercianteHandler getRegistarComercianteHandler() {
		return this.registarComercianteHandler;
	}

}
