package gob.pe.sunarp.extranet.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.ImageProducer;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.media.jai.InterpolationNearest;
import javax.media.jai.TiledImage;
import lizard.tiff.Tiff;
import ranab.img.gif.GifImage;
import decoder.image.tiff.TiffDecoderException;
import decoder.image.tiff.TiffImageProducer;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.MemoryCacheSeekableStream;

import gob.pe.sunarp.extranet.common.SunarpBean;
import gob.pe.sunarp.extranet.framework.Loggy;
import gob.pe.sunarp.extranet.publicidad.bean.ImagenBean;

public class PreparaImagen extends SunarpBean{
	private static PreparaImagen singleton = null;
	private static boolean trace = false;
	private PreparaImagen() {
	}

	public static PreparaImagen getInstance() {
		if (singleton == null) {
			singleton = new PreparaImagen();
		}
		return singleton;
	}

	public static final boolean GIF = true;

	public static final int ASIENTO_WIDTH = 2544;
	public static final int ASIENTO_HEIGHT = 3296;
	public static final int FICHA_WIDTH = 3400;
	public static final int FICHA_HEIGHT = 2500;
	public static final int FOLIO_WIDTH = 3000;
	public static final int FOLIO_HEIGHT = 4500;

	public static final int ASIENTO_ZOOM = 35;
	public static final int FICHA_ZOOM = 35;
	public static final int FOLIO_ZOOM = 35;

	public static final String TEXTO_DIAGONAL_1 = "COPIA INFORMATIVA";
	public static final String TEXTO_DIAGONAL_2 = "Emitida a través de Consulta por Internet";
	public static final String TEXTO_DIAGONAL_3 = "No tiene validez para ningún trámite Administrativo, Judicial u otros";
	
	public static final String TEXTO_DIAGONAL_CC_1 = "COPIA CERTIFICADA";
	public static final String TEXTO_DIAGONAL_CC_2 = "Sin Inscripción al Dorso";
	public static final String TEXTO_DIAGONAL_CC_3a = "Existen Títulos Suspendidos y/o Pendientes de Inscripción";
	public static final String TEXTO_DIAGONAL_CC_3b = "No hay Títulos Suspendidos y/o Pendientes de Inscripción";
	
	public static final String GLOSA_1 = "Ahora tenemos un nuevo medio de publicidad registral,";
	public static final String GLOSA_2 = "de libre acceso para todos: www.sunarp.gob.pe.";
	public static final String GLOSA_3a = "Se deja constancia que existen Títulos Pendientes y/o Suspendidos : ";
	public static final String GLOSA_3b = "No hay Títulos Suspendidos y/o Pendientes de Inscripción";
	public static final int fontSize = 15;
	public static final int fontSizeDiagonal1 = 70;
	public static final int fontSizeDiagonal2 = 45;
	public static final String BLANCOS = "        ";

	private void preparar(Graphics2D graphics, int imageWidth, int imageHeight, double tarifa, String TMSTMP_SYNCHRO, String usuario, ImagenBean imagenBean) throws java.text.ParseException {

		if (Loggy.isTrace(this)) System.out.println(now() + " Width: " + imageWidth + " Height: " + imageHeight);
		AffineTransform transform;

		graphics.setColor(new Color(0x00, 0x00, 0x00, 0xFE));
		graphics.setFont(new Font("SansSerif", Font.BOLD, fontSize));

		// string horizontal
		/*
		if (imagenBean == null) {
			graphics.drawString("Última actualización: " + FechaFormatter.deDBOaFechaHoraSegundosWeb(TMSTMP_SYNCHRO), 10, fontSize);
		} else {
			*/
		graphics.drawString("Oficina: " + imagenBean.getNomOficina() + ". Partida: " + imagenBean.getNumPartida() + ". Pag. " + imagenBean.getNumPagina() + "/" + imagenBean.getTotPaginas(), 30, fontSize);
		if((imagenBean.getTitulosPendientes()!=null)&&(!imagenBean.getTitulosPendientes().trim().equals("")))
			graphics.drawString( GLOSA_3a + imagenBean.getTitulosPendientes(), 390, fontSize);
		else if(!imagenBean.getTipoImpresion().equalsIgnoreCase(Constantes.IMPRESION_COPIA_SIMPLE))
			graphics.drawString( GLOSA_3b, 390, fontSize);
		//}
		
		// String vertical
		transform = new AffineTransform();
		transform.rotate(Math.PI/2);
		graphics.setTransform(transform);

		
		if(imagenBean.getTipoImpresion().equalsIgnoreCase(Constantes.IMPRESION_COPIA_SIMPLE)){
			graphics.drawString( "Costo por imagen:" , 5, fontSize*2-imageWidth);
			graphics.drawString( "Usuario:" , 225, fontSize*2-imageWidth);
			graphics.drawString( "Fecha Actual:" , 420, fontSize*2-imageWidth);
			graphics.drawString( GLOSA_1 , 700, fontSize*2-imageWidth);
		
			graphics.drawString( "S/." + tarifa , 50, fontSize*3-imageWidth);
			graphics.drawString( usuario , 225, fontSize*3-imageWidth);
			graphics.drawString( FechaFormatter.deDateAFechaHoraWeb(new java.util.Date()) , 420, fontSize*3-imageWidth);
			graphics.drawString( GLOSA_2 , 700, fontSize*3-imageWidth);

		} else if (imagenBean.getTipoImpresion().equalsIgnoreCase(Constantes.IMPRESION_COPIA_CERTIFICADA)){

			//graphics.drawString( "Costo por imagen:" , 5, fontSize*2-imageWidth);
			graphics.drawString( "Usuario:" , 5, fontSize*2-imageWidth);
			graphics.drawString( "Fecha Actual:" , 225, fontSize*2-imageWidth);
			graphics.drawString( GLOSA_1 , 420, fontSize*2-imageWidth);
		
			//graphics.drawString( "S/." + tarifa , 50, fontSize*3-imageWidth);
			graphics.drawString( usuario , 5, fontSize*3-imageWidth);
			graphics.drawString( FechaFormatter.deDateAFechaHoraWeb(new java.util.Date()) , 225, fontSize*3-imageWidth);
			graphics.drawString( GLOSA_2 , 420, fontSize*3-imageWidth);


		}
		int diagonal = (int)Math.sqrt(Math.pow(imageHeight, 2) + Math.pow(imageWidth, 2));
		double ratio = -Math.atan((double)imageHeight / (double)imageWidth);
		int sinHeight = (int)(imageHeight*Math.sin(ratio));
		int cosHeight = (int)(imageHeight*Math.cos(ratio));

		transform = new AffineTransform();
		transform.rotate(ratio);
		graphics.setTransform(transform);
		Color stringColor = new Color(0x30, 0x30, 0x30, 0x4F);
		graphics.setColor(stringColor);

		if(imagenBean.getTipoImpresion().equalsIgnoreCase(Constantes.IMPRESION_COPIA_SIMPLE)){
			graphics.setFont(new Font("SansSerif", Font.BOLD, fontSizeDiagonal1));
			FontMetrics fontMetrics = graphics.getFontMetrics();
			graphics.drawString(
				TEXTO_DIAGONAL_1,
				sinHeight + (diagonal-fontMetrics.stringWidth(TEXTO_DIAGONAL_1))/2,
				cosHeight - fontSizeDiagonal1
			);
			graphics.setFont(new Font("SansSerif", Font.BOLD, fontSizeDiagonal2));		
			fontMetrics = graphics.getFontMetrics();
			graphics.drawString(
				TEXTO_DIAGONAL_2,
				sinHeight + (diagonal-fontMetrics.stringWidth(TEXTO_DIAGONAL_2))/2,
				cosHeight
			);
			graphics.drawString(
				TEXTO_DIAGONAL_3,
				sinHeight + (diagonal-fontMetrics.stringWidth(TEXTO_DIAGONAL_3))/2,
				cosHeight + fontSizeDiagonal2
			);
		} else if (imagenBean.getTipoImpresion().equalsIgnoreCase(Constantes.IMPRESION_COPIA_CERTIFICADA)){
			graphics.setFont(new Font("SansSerif", Font.BOLD, fontSizeDiagonal1));
			FontMetrics fontMetrics = graphics.getFontMetrics();
			graphics.drawString(
				TEXTO_DIAGONAL_CC_1,
				sinHeight + (diagonal-fontMetrics.stringWidth(TEXTO_DIAGONAL_1))/2,
				cosHeight - fontSizeDiagonal1
			);
			graphics.setFont(new Font("SansSerif", Font.BOLD, fontSizeDiagonal2));		
			fontMetrics = graphics.getFontMetrics();
			graphics.drawString(
				TEXTO_DIAGONAL_CC_2,
				sinHeight + (diagonal-fontMetrics.stringWidth(TEXTO_DIAGONAL_2))/2,
				cosHeight
			);
			if((imagenBean.getTitulosPendientes()!=null)&&(!imagenBean.getTitulosPendientes().trim().equals("")))
			graphics.drawString(
				TEXTO_DIAGONAL_CC_3a,
				sinHeight + (diagonal-fontMetrics.stringWidth(TEXTO_DIAGONAL_CC_3a))/2,
				cosHeight + fontSizeDiagonal2
			);
			else
			graphics.drawString(
				TEXTO_DIAGONAL_CC_3b,
				sinHeight + (diagonal-fontMetrics.stringWidth(TEXTO_DIAGONAL_CC_3b))/2,
				cosHeight + fontSizeDiagonal2
			);
			graphics.drawString(
				imagenBean.getHoraTitulosPendientes(),
				sinHeight + (diagonal-fontMetrics.stringWidth(imagenBean.getHoraTitulosPendientes()))/2,
				cosHeight + (2 * fontSizeDiagonal2)
			);
		}
	}


	public int getNumPagesTIFF(byte[] content) throws IOException {
		MemoryCacheSeekableStream ss = new MemoryCacheSeekableStream(new ByteArrayInputStream(content));
		ImageDecoder decoder = ImageCodec.createImageDecoder("tiff", ss, null);
		return decoder.getNumPages();
	}

	public int getWidth(Object image) {
		if (GIF) {
			return ((GifBean)image).getGifImage().getWidth();
		} else {
			return ((TiledImage)image).getWidth();
		}
	}
	
	public int getHeight(Object image) {
		if (GIF) {
			return ((GifBean)image).getGifImage().getHeight();
		} else {
			return ((TiledImage)image).getHeight();
		}
	}

	private Image leerTIFF(byte[] contents, int page) throws java.awt.AWTException, IOException {

		Image img;
		try {
			ImageProducer imgp = TiffImageProducer.getImageProducer(contents, page + 1);
			Toolkit tk = Toolkit.getDefaultToolkit();
			img = tk.createImage(imgp);
		} catch (TiffDecoderException e) {
			System.err.println(e.toString() + " Usando libreria alternativa.");
			Tiff tiff = new Tiff();
			tiff.read(contents);
			img = tiff.getImage(page);
		}

		return img;
	}

	public Object leerTIFF(byte[] contents, float scale, int page) throws java.awt.AWTException, IOException {

		Image img = leerTIFF(contents, page);

		if (GIF) {
			
			return new GifBean(img, scale);
			
		} else {
			RenderedImage image = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_BYTE_GRAY);
			Graphics2D gbi = ((BufferedImage)image).createGraphics();
			gbi.drawImage(img, 0, 0, img.getWidth(null), img.getHeight(null), null);

			if (scale != 1) {
				ParameterBlock params = new ParameterBlock();
				params.addSource(image);
				params.add(scale);
				params.add(scale);
				params.add(0.0F);
				params.add(0.0F);
				params.add(new InterpolationNearest());
				image = javax.media.jai.JAI.create("scale", params);
			}

			return new TiledImage(image, true);
		}
	}
	
	public void ponerTextosYMandarImagenAStream(OutputStream out, Object image, double tarifa, String TMSTMP_SYNCHRO, String usuario, ImagenBean imagenBean) throws java.text.ParseException, IOException {
		if (GIF) {

			GifBean gb = (GifBean)image;
			GifImage gifImage = gb.getGifImage();
			Image img = gb.getImage();

			Graphics2D graphics = createGraphicsGIF(gifImage, img);
			preparar(graphics, gifImage.getWidth(), gifImage.getHeight(), tarifa, TMSTMP_SYNCHRO, usuario, imagenBean);
			gifImage.encode(out);

		} else {
			TiledImage tiledImage = (TiledImage)image;
			preparar(tiledImage.createGraphics(), tiledImage.getWidth(), tiledImage.getHeight(), tarifa, TMSTMP_SYNCHRO, usuario, imagenBean);
			javax.media.jai.JAI.create("encode", tiledImage, out, "PNG");
		}
	}
	
	public void mandarImagenAStream(OutputStream out, Object image) throws IOException {
		if (GIF) {
			GifBean gb = (GifBean)image;
			GifImage gifImage = gb.getGifImage();
			Image img = gb.getImage();

			Graphics2D graphics = createGraphicsGIF(gifImage, img);
			gifImage.encode(out);
		} else {
			TiledImage tiledImage = (TiledImage)image;
			javax.media.jai.JAI.create("encode", tiledImage, out, "PNG");
		}
	}
	
	private Graphics2D createGraphicsGIF(GifImage gifImage, Image img) {
		Graphics2D graphics = gifImage.getGraphics();

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		graphics.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
		graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
		graphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
		graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		if (Loggy.isTrace(this)) System.out.println(now() + " draw image");
		graphics.drawImage(img, 0, 0, gifImage.getWidth(), gifImage.getHeight(), null);
		if (Loggy.isTrace(this)) System.out.println(now() + " fin draw image");
		
		return graphics;
	}
	
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd.HH:mm:ss.SSS");
	public static String now() {
		return formatter.format(new Date());
	}

	public static void main(String[] args) {
		try {
			java.io.File file = new java.io.File("G:\\proyectos\\desarrollo\\sunarp\\tiffVehicular\\UNDE0007.TIF");
			java.io.FileInputStream in = new java.io.FileInputStream(file);
			byte[] contents = new byte[(int)file.length()];
			for (int i = 0; i < contents.length; i++) {
				contents[i] = (byte)in.read();
			}
			float scale = (float)Propiedades.getInstance().getImageScaleAsiento();

			System.out.println("num pags: " + PreparaImagen.getInstance().getNumPagesTIFF(contents));
			Object image = PreparaImagen.getInstance().leerTIFF(contents, scale, 0);

			System.out.println(
				"Width: " + PreparaImagen.getInstance().getWidth(image)
				+ " Height: " + PreparaImagen.getInstance().getHeight(image)
			);
/*
			java.io.FileOutputStream fos = new java.io.FileOutputStream("G:\\proyectos\\desarrollo\\sunarp\\tiffVehicular\\UNDE0007.GIF");
			PreparaImagen.getInstance().ponerTextosYMandarImagenAStream(fos, image, 4.0, "2002-05-05 17:12:10.0", "SUNARP001", null);
			fos.close();
*/
			java.io.FileOutputStream fos = new java.io.FileOutputStream("G:\\proyectos\\desarrollo\\sunarp\\tiffVehicular\\_UNDE0007.GIF");
			PreparaImagen.getInstance().mandarImagenAStream(fos, image);
			fos.close();



		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

/*
	public TiledImage leerTIFF(InputStream in, float scale, int page) throws IOException {
		MemoryCacheSeekableStream ss = new MemoryCacheSeekableStream(in);
		ImageDecoder decoder = ImageCodec.createImageDecoder("tiff", ss, null);

		RenderedImage image = decoder.decodeAsRenderedImage(page); 

		if (scale != 1) {
			ParameterBlock params = new ParameterBlock();
			params.addSource(image);
			params.add(scale);
			params.add(scale);
			params.add(0.0F);
			params.add(0.0F);
			params.add(new InterpolationNearest());
			image = javax.media.jai.JAI.create("scale", params);
		}

		return new TiledImage(image, true);
	}
*/
/*
	public TiledImage leerTIFF(InputStream in, float scale) {
		MemoryCacheSeekableStream stream = new MemoryCacheSeekableStream(in);
		ParameterBlock params = new ParameterBlock();
		params.add(stream);
		RenderedOp image1 = JAI.create("tiff", params);
		int dataType = image1.getSampleModel().getDataType();
		RenderedOp image = null;
		if (dataType == DataBuffer.TYPE_BYTE) {
			if (Loggy.isTrace(this)) System.out.println("TIFF image is type byte.");
			image = image1;
		} else if (dataType == DataBuffer.TYPE_USHORT) {
			if (Loggy.isTrace(this)) System.out.println("TIFF image is type ushort.");
			byte[] tableData = new byte[0x10000];
			for (int i = 0; i < 0x10000; i++) {
				tableData[i] = (byte)(i >> 8);
			}
			LookupTableJAI table = new LookupTableJAI(tableData);
			image = JAI.create("lookup", image1, table);
		} else {
			throw new IllegalArgumentException("TIFF image es tipo " + dataType + ", y no es soportado.");
		}
		
		params = new ParameterBlock();
		params.addSource(image);
		params.add(scale);
		params.add(scale);
		params.add(0.0F);
		params.add(0.0F);
		params.add(new InterpolationNearest());
		image = javax.media.jai.JAI.create("scale", params);

		return new TiledImage(image, true);
	}
*/
/*
	public TiledImage leerTIFF(InputStream in, float scale, int page) throws IOException {
		MemoryCacheSeekableStream ss = new MemoryCacheSeekableStream(in);
		TIFFImageDecoder decoder = new TIFFImageDecoder(ss, null);

		RenderedImage image = decoder.decodeAsRenderedImage(page); 

		if (scale != 1) {
			ParameterBlock params = new ParameterBlock();
			params.addSource(image);
			params.add(scale);
			params.add(scale);
			params.add(0.0F);
			params.add(0.0F);
			params.add(new InterpolationNearest());
			image = javax.media.jai.JAI.create("scale", params);
		}

		return new TiledImage(image, true);
	}
*/
}