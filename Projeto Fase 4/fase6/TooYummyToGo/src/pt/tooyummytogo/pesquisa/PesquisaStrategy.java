package pt.tooyummytogo.pesquisa;

import java.util.List;
import pt.tooyummytogo.facade.dto.ComercianteInfo;
import pt.tooyummytogo.facade.handlers.EncomendarHandler;

public interface PesquisaStrategy {
	
	public List<ComercianteInfo> escolheComerciantes(EncomendarHandler encomendarHandler);
	
}
