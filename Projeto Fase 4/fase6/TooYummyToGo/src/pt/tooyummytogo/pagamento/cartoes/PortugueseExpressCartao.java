package pt.tooyummytogo.pagamento.cartoes;

import pt.portugueseexpress.PortugueseExpress;
import pt.tooyummytogo.exception.PagamentoFalhadoException;

public class PortugueseExpressCartao implements CartaoInterface {
	
	@Override
	public boolean isValid(String cartao, String validadeMes, String validadeAno, String ccv) {
		try {
			PortugueseExpress pe = new PortugueseExpress();
			pe.setNumber(cartao);
			pe.setCcv(Integer.parseInt(ccv));
			pe.setMonth(Integer.parseInt(validadeMes));
			pe.setYear(Integer.parseInt(validadeAno));	
			return pe.validate();
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public String getNome() {
		return "PortugueseExpress";
	}

	@Override
	public void pagar(String cartao, String validadeMes, String validadeAno, String ccv, double precoTotal) throws PagamentoFalhadoException {
		try {
			PortugueseExpress pe = new PortugueseExpress();
			pe.setNumber(cartao);
			pe.setCcv(Integer.parseInt(ccv));
			pe.setMonth(Integer.parseInt(validadeMes));
			pe.setYear(Integer.parseInt(validadeAno));	
			pe.block(precoTotal);
			pe.charge(precoTotal);
		} catch(Exception e) {
			throw new PagamentoFalhadoException();
		}
		
	}

}
