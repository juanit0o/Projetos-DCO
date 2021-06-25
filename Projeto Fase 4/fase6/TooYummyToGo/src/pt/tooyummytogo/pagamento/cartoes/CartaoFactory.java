package pt.tooyummytogo.pagamento.cartoes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CartaoFactory {
	
	private static CartaoFactory INSTANCE = null;
	private List<CartaoInterface> cartoesInterface;
	
	protected CartaoFactory() {
		this.cartoesInterface = new ArrayList<>();
		
		Properties cartaoProp = new Properties();
		
		try {
			cartaoProp.load(new FileInputStream(new File("cartao.properties")));
			
			String cartoes = cartaoProp.getProperty("cartao");
			for (String cartaoNome : cartoes.split(",")) {
				try {
					@SuppressWarnings("unchecked")
					Class<CartaoInterface> klass = (Class<CartaoInterface>) Class.forName(cartaoNome);
					Constructor<CartaoInterface> cons = klass.getConstructor();
					CartaoInterface p = cons.newInstance();
					cartoesInterface.add(p);
					
				} catch (ClassNotFoundException e) {
					// Do nothing, just ignore
				} catch (NoSuchMethodException e) {
					// Do nothing, just ignore
				} catch (SecurityException e) {
					// Do nothing, just ignore
				} catch (InstantiationException e) {
					// Do nothing, just ignore
				} catch (IllegalAccessException e) {
					// Do nothing, just ignore
				} catch (IllegalArgumentException e) {
					// Do nothing, just ignore
				} catch (InvocationTargetException e) {
					// Do nothing, just ignore
				}
			}
				
		} catch (FileNotFoundException e) {
			//Do nothing, just ignore
		} catch (IOException e) {
			//Do nothing, just ignore
		}

	}
	
	/**
	 * getInstance
	 * @return instance
	 */
	public static CartaoFactory getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CartaoFactory();
		}
		return INSTANCE;
	}

	/**
	 * Retorna a lista do cartao interface
	 * @return cartao interface
	 */
	public List<CartaoInterface> getCartaoList() {
		return this.cartoesInterface;
	}
	
}
