package gob.pe.sunarp.extranet.common;

import java.io.OutputStream;
import java.io.PrintStream;

public class DoublePrintStream extends PrintStream {

	/**
	 * Constructor for DoublePrintStream
	 */
	public DoublePrintStream(OutputStream arg0) {
		super(arg0);
	}

	/**
	 * Constructor for DoublePrintStream
	 */
	public DoublePrintStream(OutputStream arg0, boolean arg1) {
		super(arg0, arg1);
	}

}

