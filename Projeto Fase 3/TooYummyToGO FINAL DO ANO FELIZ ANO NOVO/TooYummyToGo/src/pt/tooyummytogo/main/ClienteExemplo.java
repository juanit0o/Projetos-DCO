package pt.tooyummytogo.main;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import pt.portugueseexpress.InvalidCardException;
import pt.tooyummytogo.Sessao;
import pt.tooyummytogo.exception.PagamentoFalhadoException;
import pt.tooyummytogo.exception.SessaoInvalidaException;
import pt.tooyummytogo.facade.TooYummyToGo;
import pt.tooyummytogo.facade.dto.ComercianteInfo;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;
import pt.tooyummytogo.facade.dto.ProdutoInfo;
import pt.tooyummytogo.facade.handlers.AdicionarTipoDeProdutoHandler;
import pt.tooyummytogo.facade.handlers.ColocarProdutoHandler;
import pt.tooyummytogo.facade.handlers.EncomendarHandler;
import pt.tooyummytogo.facade.handlers.RegistarComercianteHandler;
import pt.tooyummytogo.facade.handlers.RegistarUtilizadorHandler;

public class ClienteExemplo {
	public static void main(String[] args) throws SessaoInvalidaException {
		TooYummyToGo ty2g = new TooYummyToGo();

		// UC1
		RegistarUtilizadorHandler regHandler = ty2g.getRegistarUtilizadorHandler();
		regHandler.registarUtilizador("Felismina", "hortadafcul");


		// UC3
		RegistarComercianteHandler regComHandler = ty2g.getRegistarComercianteHandler();

		regComHandler.registarComerciante("Silvino", "bardoc2", new PosicaoCoordenadas(34.5, 45.2));
		regComHandler.registarComerciante("Maribel", "torredotombo", new PosicaoCoordenadas(33.5, 45.2));

		// UC4
		Optional<Sessao> talvezSessao = ty2g.autenticar("Silvino", "bardoc2");
		talvezSessao.ifPresent( (Sessao s) -> {
			AdicionarTipoDeProdutoHandler atp = s.adicionarTipoDeProdutoHandler();
			
			//Adicionado na versao 2
			Random r = new Random();
			for (String tp : new String[] {"Pão", "Pão de Ló", "Mil-folhas"}) {
				atp.registaTipoDeProduto(tp, r.nextDouble() * 10);
			}
		});

		// UC5
		Optional<Sessao> talvezSessao2 = ty2g.autenticar("Silvino", "bardoc2");
		talvezSessao2.ifPresent( (Sessao s) -> {

			ColocarProdutoHandler cpv = s.getColocarProdutoHandler();

			List<String> listaTiposDeProdutos = cpv.inicioDeProdutosHoje();

			cpv.indicaProduto(listaTiposDeProdutos.get(0), 10); // Pão
			cpv.indicaProduto(listaTiposDeProdutos.get(2), 5); // Mil-folhas

			cpv.confirma(LocalDateTime.now(), LocalDateTime.now().plusHours(2));

			System.out.println("Produtos disponíveis");
		});

		// UC6 + UC7
		Optional<Sessao> talvezSessao3 = ty2g.autenticar("Felismina", "hortadafcul");
		talvezSessao3.ifPresent( (Sessao s) -> {
			EncomendarHandler lch = s.getEncomendarComerciantesHandler();
			List<ComercianteInfo> cs = lch.indicaLocalizacaoActual(new PosicaoCoordenadas(34.5, 45.2));

			System.out.println("*** Lista de Comerciantes (5km e disponiveis na proxima hora) ***");
			for (ComercianteInfo i : cs) {		//MOSTRA COMERCIANTES NUM RAIO DE 5km E NA PROXIMA HORA
				System.out.println(i);
			}

			boolean redefineRaio = false;
			if (redefineRaio) {
				System.out.println("*** Lista de Comerciantes (0.1km e disponiveis na proxima hora) ***");
				cs = lch.redefineRaio(100);
				for (ComercianteInfo i : cs) {
					System.out.println(i);
				}
			}

			boolean redefinePeriodo = false;
			if (redefinePeriodo) {
				if(redefineRaio) {
					System.out.println("*** Lista de Comerciantes (0.1km e disponiveis na proxima hora) ***");
				} else {
					System.out.println("*** Lista de Comerciantes (5km e disponiveis na proxima hora) ***");
				}
				cs = lch.redefinePeriodo(LocalDateTime.now(), LocalDateTime.now().plusHours(1));
				for (ComercianteInfo i : cs) {
					System.out.println(i);
				}
			}

			//****************************************************************************************************

			// A partir de agora é UC7
			List<ProdutoInfo> ps = lch.escolheComerciante(cs.get(0));
			System.out.println("*** Carrinho ***");
			for (ProdutoInfo p : ps) {
				lch.indicaProduto(p, 1); // Um de cada
			}
			System.out.println("Total a pagar: " + lch.getPrecoTotal() + "�");

			try {
				String codigoReserva = lch.indicaPagamento("365782312312", "02/21", "764");
				System.out.println("*** Compra finalizada ***");
				System.out.println("Reserva " + codigoReserva + " feita com sucesso"); 
			} catch (PagamentoFalhadoException e) {
				e.printStackTrace();
			} catch (InvalidCardException e) {
				e.printStackTrace();
			}		

		});
		System.out.print("*** Fim do programa ***");

	}
}
