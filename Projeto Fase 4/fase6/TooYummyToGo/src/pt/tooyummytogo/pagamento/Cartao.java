package pt.tooyummytogo.pagamento;

import java.util.List;

import pt.tooyummytogo.exception.PagamentoFalhadoException;
import pt.tooyummytogo.pagamento.cartoes.CartaoFactory;
import pt.tooyummytogo.pagamento.cartoes.CartaoInterface;

public class Cartao {
	
	private List<CartaoInterface> cartaoFactory;
	private String cartao;
	private String validadeMes;
	private String validadeAno;
	private String ccv;

	public Cartao(String cartao, String validadeMes, String validadeAno, String ccv) {
		this.cartaoFactory = CartaoFactory.getInstance().getCartaoList();
		this.cartao = cartao;
		this.validadeMes = validadeMes;
		this.validadeAno = validadeAno;
		this.ccv = ccv;
	}
	
	/**
	 * Retorna o tipo do cartao
	 * @return tipo do cartao
	 */
	private String getTipo() {
		for(CartaoInterface c : cartaoFactory) {
			if(c.isValid(cartao, validadeMes, validadeAno, ccv)) {
				return c.getNome();
			}
		}
		return null;
	}

	/**
	 * Debita do cartao o valor a pagar
	 * @param precoTotal preco a pagar
	 * @throws PagamentoFalhadoException cartao invalido
	 */
	public void pagar(double precoTotal) throws PagamentoFalhadoException{
		try {
			for(CartaoInterface c : cartaoFactory) {
				if(c.getNome() == getTipo()) {
					c.pagar(cartao, validadeMes, validadeAno, ccv, precoTotal);
					return;
				}
			}	
			throw new PagamentoFalhadoException();
		} catch(Exception e) {
			throw new PagamentoFalhadoException();
		}		
	}

}
