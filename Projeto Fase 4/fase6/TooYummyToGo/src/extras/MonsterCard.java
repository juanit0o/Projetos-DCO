package extras;

import com.monstercard.Card;
import com.monstercard.MonsterCardAPI;

public class MonsterCard extends Cartao {

	private MonsterCardAPI mc;
	private Card card;
	
	public MonsterCard (String cartao, String ccv, int mes, int ano) {
		super(cartao, ccv, mes, ano);
		mc = new MonsterCardAPI();
		card = new Card(super.cartao, super.ccv, String.valueOf(super.mes), "20" + ano);
	}

	@Override
	public void pagar(double value) {
		mc.block(card, value);
		mc.charge(card, value);
	}
	
	@Override
	protected String getType() {
		if(mc.isValid(card)) {
			return "MonsterCard";
		} else {
			return "";
		}
		
	}
	
}
