package pt.tooyummytogo.pagamento.cartoes;

import com.monstercard.Card;
import com.monstercard.MonsterCardAPI;

import pt.tooyummytogo.exception.PagamentoFalhadoException;

public class MonsterCardCartao implements CartaoInterface{

	MonsterCardAPI mc = new MonsterCardAPI();
	
	@Override
	public boolean isValid(String cartao, String validadeMes, String validadeAno, String ccv) {
		try {
			Card card = new Card(cartao, ccv, validadeMes, validadeAno);
			return mc.isValid(card);
		} catch(Exception e) {
			return false;
		}
	}

	@Override
	public String getNome() {
		return "MonsterCard";
	}

	@Override
	public void pagar(String cartao, String validadeMes, String validadeAno, String ccv, double precoTotal) throws PagamentoFalhadoException {
		try {
			Card card = new Card(cartao, ccv, validadeMes, validadeAno);
			boolean blocked = mc.block(card, precoTotal);
			boolean charged = mc.charge(card, precoTotal);
			if(!blocked || !charged) {
				throw new PagamentoFalhadoException();
			}
		} catch(Exception e) {
			throw new PagamentoFalhadoException();
		}	
	}

}
