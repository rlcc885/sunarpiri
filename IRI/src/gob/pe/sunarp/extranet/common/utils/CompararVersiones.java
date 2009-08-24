package gob.pe.sunarp.extranet.common.utils;

import java.util.StringTokenizer;

public class CompararVersiones {
	private static CompararVersiones single;
	public synchronized static CompararVersiones getInstance() {
		if (single == null) {
			single = new CompararVersiones();
		}
		return single;
	}
	private CompararVersiones() {
	}
	
	public boolean esMayorIgual(String v1, String v2) {
		StringTokenizer t1 = new StringTokenizer(v1, ".");
		int[] a1 = new int[t1.countTokens()];
		for (int i = 0; i < a1.length; i++) {
			a1[i] = Integer.parseInt(t1.nextToken());
		}
		t1 = null;

		StringTokenizer t2 = new StringTokenizer(v2, ".");
		int[] a2 = new int[t2.countTokens()];
		for (int i = 0; i < a2.length; i++) {
			a2[i] = Integer.parseInt(t2.nextToken());
		}
		t2 = null;

		for (int i = 0; i < Math.max(a1.length, a2.length); i++) {
			int n1 = 0;
			int n2 = 0;
			if (i < a1.length) {
				n1 = a1[i];
			}
			if (i < a2.length) {
				n2 = a2[i];
			}
			if (n1 < n2) return false;
			if (n1 > n2) return true;
		}

		return true;
	}

	public boolean esMenor(String v1, String v2) {
		return !esMayorIgual(v1, v2);
	}
}

