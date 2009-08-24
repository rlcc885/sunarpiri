package gob.pe.sunarp.extranet.util;

import gob.pe.sunarp.extranet.common.SunarpBean;

import java.awt.Image;

import ranab.img.gif.GifImage;

public class GifBean extends SunarpBean{
	
	private GifImage gifImage;
	private Image image;
	
	public GifBean(Image image, float scale) {
		this.gifImage = new GifImage((int)(image.getWidth(null) * scale), (int)(image.getHeight(null) * scale));		
		this.image = image;
	}
	
	public GifImage getGifImage() {
		return gifImage;
	}
	public void setGifImage(GifImage gifImage) {
		this.gifImage = gifImage;
	}

	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}

}

