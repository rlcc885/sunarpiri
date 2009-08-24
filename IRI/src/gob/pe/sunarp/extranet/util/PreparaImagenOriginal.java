package gob.pe.sunarp.extranet.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.text.SimpleDateFormat;
import java.util.Date;
import gob.pe.sunarp.extranet.framework.Loggy;

public class PreparaImagenOriginal {
	private static PreparaImagenOriginal singleton = null;
	private static boolean trace = false;
	private PreparaImagenOriginal() {
	}

	public static PreparaImagenOriginal getInstance() {
		if (singleton == null) {
			singleton = new PreparaImagenOriginal();
		}
		return singleton;
	}

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
	public static final String GLOSA_1 = "Ahora tenemos un nuevo medio de publicidad registral,";
	public static final String GLOSA_2 = "de libre acceso para todos: www.sunarp.gob.pe.";
	public static final int fontSize = 15;
	public static final int fontSizeDiagonal1 = 70;
	public static final int fontSizeDiagonal2 = 45;
	public static final String BLANCOS = "        ";

	public void preparar(Graphics2D graphics, Image image, int imageWidth, int imageHeight, double tarifa, String TMSTMP_SYNCHRO, String usuario, gob.pe.sunarp.extranet.publicidad.bean.ImagenBean imagenPag) throws java.text.ParseException {

		if (Loggy.isTrace(this)) System.out.println(now() + " Width: " + imageWidth + " Height: " + imageHeight);

		//graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
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
		graphics.drawImage(image, 0, 0, imageWidth, imageHeight, null);
		if (Loggy.isTrace(this)) System.out.println(now() + " fin draw image");

		graphics.setColor(Color.black);
		graphics.setFont(new Font("SansSerif", Font.BOLD, fontSize));

		// fecha ultima synchronizacion
		if (imagenPag == null) {
			graphics.drawString("Última actualización: " + FechaFormatter.deDBOaFechaHoraSegundosWeb(TMSTMP_SYNCHRO), 10, fontSize);
		} else {
			graphics.drawString("Oficina: " + imagenPag.getNomOficina() + ". Partida: " + imagenPag.getNumPartida() + ". Pag. " + imagenPag.getNumPagina() + "/" + imagenPag.getTotPaginas(), 30, fontSize);
		}
		
		// String vertical
		AffineTransform transform = new AffineTransform();
		transform.rotate(Math.PI/2, imageWidth, fontSize);
		graphics.setTransform(transform);
		
		graphics.drawString( "Costo por imagen:" , imageWidth, fontSize*2);
		graphics.drawString( "Usuario:" , imageWidth + 225, fontSize*2);
		graphics.drawString( "Fecha Actual:" , imageWidth + 420, fontSize*2);
		graphics.drawString( GLOSA_1 , imageWidth + 700, fontSize*2);
		
		graphics.drawString( "S/." + tarifa , imageWidth + 50, fontSize*3);
		graphics.drawString( usuario , imageWidth + 225, fontSize*3);
		graphics.drawString( FechaFormatter.deDateAFechaHoraWeb(new java.util.Date()) , imageWidth + 420, fontSize*3);
		graphics.drawString( GLOSA_2 , imageWidth + 700, fontSize*3);

		double ratio = -Math.atan((double)imageHeight / (double)imageWidth);
		Color stringColor = new Color(0x20, 0x20, 0x20, 0x4F);
		graphics.setColor(stringColor);
		graphics.setFont(new Font("SansSerif", Font.BOLD, fontSizeDiagonal1));
		
		transform = new AffineTransform();
		transform.rotate(ratio, imageWidth / 2, imageHeight / 2);
		graphics.setTransform(transform);
		FontMetrics fontMetrics = graphics.getFontMetrics();
		graphics.drawString(
			TEXTO_DIAGONAL_1,
			(imageWidth - fontMetrics.stringWidth(TEXTO_DIAGONAL_1)) / 2,
			imageHeight / 2 - fontSizeDiagonal1
		);
		graphics.setFont(new Font("SansSerif", Font.BOLD, fontSizeDiagonal2));		
		fontMetrics = graphics.getFontMetrics();
		graphics.drawString(
			TEXTO_DIAGONAL_2,
			(imageWidth - fontMetrics.stringWidth(TEXTO_DIAGONAL_2)) / 2,
			imageHeight / 2
		);
		graphics.drawString(
			TEXTO_DIAGONAL_3,
			(imageWidth - fontMetrics.stringWidth(TEXTO_DIAGONAL_3)) / 2,
			imageHeight / 2 + fontSizeDiagonal2
		);
	}


	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd.HH:mm:ss.SSS");
	public static String now() {
		return formatter.format(new Date());
	}

	public static void main(String[] args) {
		try {
/*			// Get Image
			//if (Loggy.isTrace(this)) System.out.println(now() + " inicio");
			lizard.tiff.Tiff tiff = new lizard.tiff.Tiff();
			tiff.readInputStream(new java.io.FileInputStream("d:\\18.tif"));
			//if (Loggy.isTrace(this)) System.out.println(now() + " Cargada imagen. Paginas: " + tiff.getPageCount());
			Image image = tiff.getImage(0);
			int imageWidthOriginal = image.getWidth(null);
			int imageHeightOriginal = image.getHeight(null);
			//if (Loggy.isTrace(this)) System.out.println(now() + " Imagen decodificada. Ancho y Alto original: " + imageWidthOriginal + " " + imageHeightOriginal);

			int imageWidth = (int) Math.round(imageWidthOriginal * Propiedades.getInstance().getImageScaleFolio());
			int imageHeight = (int) Math.round(imageHeightOriginal * Propiedades.getInstance().getImageScaleFolio());
/*
			int imageWidth = (int) Math.round(imageWidthOriginal * PreparaImagen.IMAGE_SCALE);
			int imageHeight = (int) Math.round(imageHeightOriginal * PreparaImagen.IMAGE_SCALE);
*//*
			ranab.img.gif.GifImage gifImage = new ranab.img.gif.GifImage(imageWidth, imageHeight);
			//gifImage.setTransparency(Color.white);
			Graphics2D gifGraphics = gifImage.getGraphics();
			
			
			//if (Loggy.isTrace(this)) System.out.println(now() + " antes de prepara imagen");
			PreparaImagenOriginal.getInstance().preparar(gifGraphics, image, imageWidth, imageHeight, 4.0, "2002-05-05 17:12:10.0", "SUNARP001", null);
			//if (Loggy.isTrace(this)) System.out.println(now() + " despues de prepara imagen");
			
			java.io.FileOutputStream fos = new java.io.FileOutputStream("d:\\grandesint.gif");
			gifImage.encode(fos);
			fos.close();
			//if (Loggy.isTrace(this)) System.out.println(now() + " Done.");
*/
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

}