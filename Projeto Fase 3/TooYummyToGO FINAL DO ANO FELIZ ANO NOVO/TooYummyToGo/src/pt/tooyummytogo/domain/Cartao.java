public abstract class Cartao {

    //private Utilizador user;
    private String numero;
    private String ccv;
    private String dataValidade;
    
    public Cartao (String numero, String ccv, String dataValidade) {
    	this.numero = numero;
    	this.ccv = ccv;
    	this.dataValidade = dataValidade;
    }
}
