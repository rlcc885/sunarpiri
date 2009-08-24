package gob.pe.sunarp.extranet.common;

import java.io.*;

public class Serializacion {
	private static Serializacion serializacion;

	public static Serializacion getInstance() {
		if (serializacion == null) {
			serializacion = new Serializacion();
		}
		return serializacion;
	}

	private Serializacion() {
	}
	
	public byte[] serializar(Serializable obj) throws IOException {
		ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
		ObjectOutputStream objOutput = new ObjectOutputStream(new BufferedOutputStream(byteOutput));
		objOutput.writeObject(obj);
		objOutput.close();
		return byteOutput.toByteArray();
	}

	public Serializable deserializar(byte[] obj) throws IOException, ClassNotFoundException {
		ByteArrayInputStream byteInput = new ByteArrayInputStream(obj);
		ObjectInputStream objInput = new ObjectInputStream(new BufferedInputStream(byteInput));
		Serializable resultado = (Serializable) objInput.readObject();
		objInput.close();
		return resultado;
	}

	public ByteArrayInputStream serializarAStream(Serializable obj) throws IOException {
		return new ByteArrayInputStream(serializar(obj));
	}

	public Serializable deserializar(InputStream in) throws IOException, ClassNotFoundException {
		ObjectInputStream objInput = new ObjectInputStream(new BufferedInputStream(in));
		Serializable resultado = (Serializable) objInput.readObject();
		objInput.close();
		return resultado;
	}
}

