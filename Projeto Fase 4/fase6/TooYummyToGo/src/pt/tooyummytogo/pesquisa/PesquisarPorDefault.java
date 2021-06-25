package pt.tooyummytogo.pesquisa;

import java.util.ArrayList;
import java.util.List;
import pt.tooyummytogo.facade.dto.ComercianteInfo;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;
import pt.tooyummytogo.facade.handlers.EncomendarHandler;

public class PesquisarPorDefault extends PesquisarCondicoes implements PesquisaStrategy {

	@Override
	public List<ComercianteInfo> escolheComerciantes(EncomendarHandler eh) {
		List<ComercianteInfo> resultado = new ArrayList<ComercianteInfo>(); //3.1
		for (ComercianteInfo c : eh.getComercianteInfoList()) {
			if(c.existeVenda()) {
				PosicaoCoordenadas cl = c.getLocalizacao(); //3.2
				if (condicoesHoras(eh.getCompra(), c.getVenda().getInicio(), c.getVenda().getFim()) && 
						condicoesLocalizacao(eh.getCompra(), eh.getPosicao(), cl)) { //3.3
					ComercianteInfo ci = c; //3.4
					resultado.add(ci); //3.4 //3.5
				}
			}
		}
		return resultado;
	}
	
}
