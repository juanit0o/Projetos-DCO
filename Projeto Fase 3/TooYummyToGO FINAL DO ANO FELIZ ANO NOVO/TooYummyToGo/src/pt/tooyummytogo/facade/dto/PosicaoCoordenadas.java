package pt.tooyummytogo.facade.dto;

import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.GeodesicData;

public class PosicaoCoordenadas {
	
	private double latitude;
	private double longitude;

	public PosicaoCoordenadas(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	/**
	 * ToString
	 */
	public String toString() {
		return this.latitude + "° " + this.longitude + "°";
	}
	
	/**
	 * Retorna a latitude
	 * @return latitude
	 */
	public double getLatitude() {
		return this.latitude;
	}

	/**
	 * Retorna a longitude
	 * @return longitude
	 */
	public double getLongitude() {
		return this.longitude;
	}
	
	/**
	 * Retorna a distancia em metros entre dois posicaoCoordenadas
	 * @param posicaoCoordenadas posicaoCoordenadas para comparação
	 * @return distancia entre as posicaoCoordenadas
	 */
	public double distanciaEmMetros(PosicaoCoordenadas posicaoCoordenadas) {
		GeodesicData gd = Geodesic.WGS84.Inverse(this.latitude, this.longitude, posicaoCoordenadas.latitude, posicaoCoordenadas.longitude);
        return gd.s12;
	}
	
}
