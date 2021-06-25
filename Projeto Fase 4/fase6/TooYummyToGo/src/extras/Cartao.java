package extras;



public abstract class Cartao implements MetodoPagamento{

	protected String cartao;
	protected String ccv;
	protected int mes;
	protected int ano;
	protected String type;
	
	public Cartao(String cartao, String ccv, int mes, int ano) {
		this.cartao = cartao;
		this.ccv = ccv;
		this.mes = mes;
		this.ano = ano;
	}
	
	
	@Override
	public void pagar(double value) {
		//SYSTEM OUT
	}
	
	protected abstract String getType();
}
