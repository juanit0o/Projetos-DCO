package pt.tooyummytogo.pagamento.cartoes;

import pt.tooyummytogo.exception.PagamentoFalhadoException;

public interface CartaoInterface {
	
	public boolean isValid(String cartao, String validadeMes, String validadeAno, String ccv);
	
	public void pagar(String cartao, String validadeMes, String validadeAno, String ccv, double precoTotal) throws PagamentoFalhadoException;
	
	public String getNome();

}
