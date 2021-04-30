package caro;

import java.awt.Image;
import util.ImageCaro;;

public enum Player {
	OPLAYER("black", "BLACK", 1),
	XPLAYER("white", "WHITE", 2);

	private Image image;
	private String typeColor;
	private int hashValue;
	private ImageCaro help = new ImageCaro();
	

	Player(String ImgName, String typeColor, int hashValue) {
		this.image = help.getImage(ImgName);
		this.hashValue = hashValue;
	}

	public Image getImg() {
		return image;
	}

	public String getTypeColor() {
		return typeColor;
	}

	public int getHashValue() {
		return hashValue;
	}

	public Player getOpponent() {
		if (this == OPLAYER)
			return Player.XPLAYER;
		return Player.OPLAYER;
	}
}