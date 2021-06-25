package pt.tooyummytogo.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import pt.tooyummytogo.Sessao;
import pt.tooyummytogo.exception.SessaoInvalidaException;
import pt.tooyummytogo.facade.TooYummyToGo;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;
import pt.tooyummytogo.facade.handlers.RegistarComercianteHandler;
import pt.tooyummytogo.facade.handlers.RegistarUtilizadorHandler;

class TestLogin {

	/*
	 * 	private Utilizador utilizador_corrente;
	private Comerciante comerciante_corrente;

	private AdicionarTipoDeProdutoHandler adicionarTipoDeProdutoHandler;
	private ColocarProdutoHandler colocarProdutoHandler;
	private EncomendarHandler encomendarHandler;
	 */

	@Test
	void testeLoginComerciante() {
		try {
			TooYummyToGo ty2g = new TooYummyToGo();
			RegistarComercianteHandler regComHandler = ty2g.getRegistarComercianteHandler();
			regComHandler.registarComerciante("Silvino", "bardoc2", new PosicaoCoordenadas(34.5, 45.2));

			Optional<Sessao> result = ty2g.autenticar("Silvino", "bardoc2");
			result.ifPresent( (Sessao s) -> {
				if(s.adicionarTipoDeProdutoHandler() == null) {
					fail("adicionarTipoDeProdutoHandler() is null");
				}
				if(s.getColocarProdutoHandler() == null) {
					fail("getColocarProdutoHandler() is null");
				}
				if(s.getEncomendarComerciantesHandler() != null) {
					fail("getEncomendarComerciantesHandler() is not null");
				}
			});
			assertTrue(result != null);
		} catch (Exception e) {
			fail("FATAL ERROR! - Something is wrong");
		}
	}

	@Test
	void testeLoginUtilizador() {
		try {
			TooYummyToGo ty2g = new TooYummyToGo();
			RegistarUtilizadorHandler regHandler = ty2g.getRegistarUtilizadorHandler();
			regHandler.registarUtilizador("Felismina", "hortadafcul");

			Optional<Sessao> result = ty2g.autenticar("Felismina", "hortadafcul");
			result.ifPresent( (Sessao s) -> {
				if(s.adicionarTipoDeProdutoHandler() != null) {
					fail("adicionarTipoDeProdutoHandler() is not null");
				}
				if(s.getColocarProdutoHandler() != null) {
					fail("getColocarProdutoHandler() is not null");
				}
				if(s.getEncomendarComerciantesHandler() == null) {
					fail("getEncomendarComerciantesHandler() is null");
				}
			});
			assertTrue(result != null);
		} catch (Exception e) {
			fail("FATAL ERROR! - Something is wrong");
		}
	}

	@Test
	void testeLoginNaoExiste() {
		try {
			TooYummyToGo ty2g = new TooYummyToGo();
			RegistarUtilizadorHandler regHandler = ty2g.getRegistarUtilizadorHandler();
			regHandler.registarUtilizador("Felismina", "hortadafcul");
			try {
				Optional<Sessao> result = ty2g.autenticar("Diogo", "diogopinto123");
			} catch (Exception e) {
				assertTrue(true);
				return;
			}
			fail("Sessao must be invalid");
		} catch (Exception e) {
			fail("FATAL ERROR! - Something is wrong");
		}
	}

	@Test
	void testeComercianteMultiplos() throws SessaoInvalidaException {
		try {
			TooYummyToGo ty2g = new TooYummyToGo();
			RegistarComercianteHandler regComHandler = ty2g.getRegistarComercianteHandler();
			regComHandler.registarComerciante("Silvino", "bardoc2", new PosicaoCoordenadas(34.5, 45.2));
			regComHandler.registarComerciante("Silvino2", "bardoc22", new PosicaoCoordenadas(342.5, 452.2));

			RegistarUtilizadorHandler regHandler = ty2g.getRegistarUtilizadorHandler();
			regHandler.registarUtilizador("Felismina", "hortadafcul");
			regHandler.registarUtilizador("Felismina2", "hortadafcul2");

			Optional<Sessao> result = ty2g.autenticar("Silvino", "bardoc2");
			result.ifPresent( (Sessao s) -> {
				if(s.adicionarTipoDeProdutoHandler() == null) {
					fail("adicionarTipoDeProdutoHandler() is null");
				}
				if(s.getColocarProdutoHandler() == null) {
					fail("getColocarProdutoHandler() is null");
				}
				if(s.getEncomendarComerciantesHandler() != null) {
					fail("getEncomendarComerciantesHandler() is not null");
				}
			});
			assertTrue(result != null);
		} catch (Exception e) {
			fail("FATAL ERROR! - Something is wrong");
		}
	}

	@Test
	void testeUtilizadorMultiplos() {
		try {
			TooYummyToGo ty2g = new TooYummyToGo();
			RegistarComercianteHandler regComHandler = ty2g.getRegistarComercianteHandler();
			regComHandler.registarComerciante("Silvino", "bardoc2", new PosicaoCoordenadas(34.5, 45.2));
			regComHandler.registarComerciante("Silvino2", "bardoc22", new PosicaoCoordenadas(342.5, 452.2));

			RegistarUtilizadorHandler regHandler = ty2g.getRegistarUtilizadorHandler();
			regHandler.registarUtilizador("Felismina", "hortadafcul");
			regHandler.registarUtilizador("Felismina2", "hortadafcul2");

			Optional<Sessao> result = ty2g.autenticar("Felismina", "hortadafcul");
			result.ifPresent( (Sessao s) -> {
				if(s.adicionarTipoDeProdutoHandler() != null) {
					fail("adicionarTipoDeProdutoHandler() is not null");
				}
				if(s.getColocarProdutoHandler() != null) {
					fail("getColocarProdutoHandler() is not null");
				}
				if(s.getEncomendarComerciantesHandler() == null) {
					fail("getEncomendarComerciantesHandler() is null");
				}
			});
			assertTrue(result != null);
		} catch (Exception e) {
			fail("FATAL ERROR! - Something is wrong");
		}
	}

}
