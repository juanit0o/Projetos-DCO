package pt.tooyummytogo.pesquisa;

import java.time.LocalDateTime;
import extras.Compra;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;

public class PesquisarCondicoes {

	/**
	 * Verifica se os horarios do comerciante e do utilizador sao compativeis
	 * @param compra compra do utilizador
	 * @param inicio inicio da venda do comerciante
	 * @param fim inicio da venda do comerciante
	 * @return se os horarios do comerciante e do utilizador sao compativeis
	 */
	protected boolean condicoesHoras(Compra compra, LocalDateTime inicio, LocalDateTime fim) {
		return ((inicio.isBefore(compra.getInicio()) && compra.getFim().isBefore(fim)) || 
				(compra.getInicio().isBefore(inicio) && fim.isBefore(compra.getFim())) || 
				(compra.getInicio().isBefore(inicio) && compra.getFim().isBefore(fim)) || 
				(inicio.isBefore(compra.getInicio()) && fim.isBefore(compra.getFim())) ||
				(inicio.isEqual(compra.getInicio()) && fim.isEqual(compra.getFim())));
	}
	
	/**
	 * Verifica se a posicao do comerciante está no raio da compra da posicao do utilizador
	 * @param compra compra corrente
	 * @param posUt posicao do utilizador
	 * @param posCc posicao do comerciante
	 * @return se a posicao do comerciante está no raio da compra da posicao do utilizador
	 */
	protected boolean condicoesLocalizacao(Compra compra, PosicaoCoordenadas posUt, PosicaoCoordenadas posCc) {
		return posUt.distanciaEmMetros(posCc) <= compra.getRaio();
	}
	
}
