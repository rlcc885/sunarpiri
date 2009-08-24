package gob.pe.sunarp.extranet.framework;

import java.text.*;

public class Formatters {
	public static final SimpleDateFormat fechaWebFormatter = new SimpleDateFormat("dd/MM/yyyy");
	public static final DecimalFormat decimalWebFormatter = new DecimalFormat();

	static {
		DecimalFormatSymbols symb = decimalWebFormatter.getDecimalFormatSymbols();
		symb.setDecimalSeparator(',');
		symb.setGroupingSeparator('.');
		decimalWebFormatter.setMaximumFractionDigits(2);
		decimalWebFormatter.setDecimalFormatSymbols(symb);
	}

	/**
	 * Formatters constructor comment.
	 */
	public Formatters() {
		super();
	}
}