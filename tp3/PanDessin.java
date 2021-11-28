import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PanDessin extends JPanel {

	public static int decalageSuperposition = 10;

	BufferedImage monImage = null;

	public PanDessin() {
		super();

	}

	protected void paintComponent(Graphics g) {

		if (monImage != null) {

			Dimension dim = new Dimension(monImage.getWidth(),
					monImage.getHeight());
			if (!dim.equals(this.getPreferredSize()))
				this.setPreferredSize(dim);

			super.paintComponent(g);
			g.drawImage(monImage, 0, 0, null);
			// g.drawImage(monImage, 0, 0, getWidth(), getHeight(), null);

			/*
			 * int x = 30, y = 20 ; Raster tramePixel = monImage.getRaster();
			 * ColorModel modeleCouleur = monImage.getColorModel();
			 * 
			 * //tramePixel.getNumDataElements() ; // nombre d'�l�ments de
			 * donn�es repr�sentant un pixel //tramePixel.getTransferType() ;
			 * //type de transfert utilis� pour trasf�rer les donn�es en
			 * utilisant // les m�thodes getDataElements et setDataElements
			 * 
			 * Object objCouleur = tramePixel.getDataElements(x, y, null);
			 * System.out.println("teinte rouge = "+
			 * modeleCouleur.getRed(objCouleur));
			 * System.out.println("teinte verte = "+
			 * modeleCouleur.getGreen(objCouleur)) ;
			 * System.out.println("teinte bleu = "+
			 * modeleCouleur.getBlue(objCouleur));
			 * 
			 * 
			 * int xPixel = 100 ; int yPixel = 100 ; WritableRaster
			 * trameModifiable = monImage.getRaster(); ColorModel
			 * modeleDeCouleur = monImage.getColorModel(); int rgb =
			 * Color.white.getRGB(); Object couleurBlanc =
			 * modeleDeCouleur.getDataElements(rgb, null); // la pixel qui se
			 * trouve � la position (100,100) va prendre la couleur blanche for
			 * (int i =0; i < 100; ++i) for (int j = 0 ; j < 100 ; ++j)
			 * trameModifiable.setDataElements(xPixel+i, yPixel+j,
			 * couleurBlanc);
			 */
		}
		// else this.setPreferredSize(new Dimension (10, 10)) ;

	}

	protected void zoomerImage(double coef) {
		BufferedImage imageModifiee = new BufferedImage(
				(int) (monImage.getWidth() * coef),
				(int) (monImage.getHeight() * coef), monImage.getType());
		AffineTransform zoom = AffineTransform.getScaleInstance(coef, coef);
		int interpolation = AffineTransformOp.TYPE_BICUBIC;
		AffineTransformOp retaillerImage = new AffineTransformOp(zoom,
				interpolation);
		retaillerImage.filter(monImage, imageModifiee);
		monImage = imageModifiee; // change l�image � afficher dans mon panneau
		repaint(); // r�afficher le panneau

		/*
		 * // ou bien BufferedImage imageModifiee = new BufferedImage
		 * ((int)(monImage.getWidth()*coef), (int)(monImage.getHeight()*coef),
		 * monImage.getType()); // new AffineTransform (double m00, double m10,
		 * double m01, double m11, double m02, double m12) AffineTransform zoom
		 * = new AffineTransform (coef, 0., 0., coef, 0., 0.) ;
		 * 
		 * int interpolation = AffineTransformOp.TYPE_BICUBIC ;
		 * AffineTransformOp retaillerImage = new AffineTransformOp (zoom,
		 * interpolation); retaillerImage.filter(monImage, imageModifiee );
		 * monImage = imageModifiee ; //change l�image � afficher dans mon
		 * panneau
		 * 
		 * repaint(); //r�afficher le panneau
		 */
	}

	protected void imageConvolue(float[] masqueConvolution, int nbLines) { // on
																			// va
																			// utiliser
																			// le
																			// masque
																			// flou
		BufferedImage imageFlou = new BufferedImage(monImage.getWidth(),
				monImage.getHeight(), monImage.getType());
		/*
		 * float[ ] masqueConvolution = { 0.25f, 0.f, 0.25f, 0.f , 0.f, 0.f,
		 * 0.25f, 0.f, 0.25f };
		 */

		Kernel masque = new Kernel(nbLines, nbLines, masqueConvolution);
		ConvolveOp operation = new ConvolveOp(masque);
		operation.filter(monImage, imageFlou);
		monImage = imageFlou;
		repaint();

	}

	protected void imageEclaircie() {
		/*
		 * RescaleOp brillance = new RescaleOp(A, K, null); 1. A< 1, l�image
		 * devient plus sombre. 2. A > 1, l�image devient plus brillante. 3. K
		 * est compris entre -256 et 256 et ajoute un �claircissement .
		 */
		BufferedImage imgBrillant = new BufferedImage(monImage.getWidth(),
				monImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		RescaleOp brillance = new RescaleOp(1 / .7f, -30, null);
		brillance.filter(monImage, imgBrillant);
		monImage = imgBrillant;
		repaint();

		/*
		 * // ou bien float [] masqueConvolution = {(float)(1./0.7)} ;
		 * BufferedImage imageFlou = new
		 * BufferedImage(monImage.getWidth(),monImage.getHeight(),
		 * monImage.getType()); Kernel masque = new Kernel (1, 1,
		 * masqueConvolution); ConvolveOp operation = new ConvolveOp (masque);
		 * operation.filter(monImage, imageFlou); monImage = imageFlou;
		 * repaint();
		 */

	}

	protected void imageSombre() {
		/*
		 * RescaleOp assombrir = new RescaleOp(A, K, null);
		 * 
		 * 1. A < 1, l�image devient plus sombre. 2. A > 1, l�image devient plus
		 * brillante. 3. K est compris entre -256 et 256 et ajoute un
		 * �clairecissement .
		 */
		BufferedImage imgSombre = new BufferedImage(monImage.getWidth(),
				monImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		RescaleOp assombrir = new RescaleOp(0.7f, 30, null);
		assombrir.filter(monImage, imgSombre);
		monImage = imgSombre;
		repaint();
	}

	protected void imageBinaire() {
		BufferedImage imgBinaire = new BufferedImage(monImage.getWidth(),
				monImage.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
		Graphics2D surfaceImg = imgBinaire.createGraphics();
		surfaceImg.drawImage(monImage, null, null);
		monImage = imgBinaire;
		repaint();
	}

	protected void imageEnNiveauGris() {
		BufferedImage imageGris = new BufferedImage(monImage.getWidth(),
				monImage.getHeight(), BufferedImage.TYPE_USHORT_GRAY);
		Graphics2D surfaceImg = imageGris.createGraphics();
		surfaceImg.drawImage(monImage, null, null);
		monImage = imageGris;
		repaint();
	}

	protected void ajouterImage(File fichierImage) { // dessiner une image �
														// l'ecran
		try {
			monImage = ImageIO.read(fichierImage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		repaint();
	}

	protected BufferedImage getImagePanneau() {
		// r�cup�rer une image du panneau
		int width = this.getWidth();
		int height = this.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();

		this.paintAll(g);
		g.dispose();
		return image;
	}

	/*
	 * protected BufferedImage createImage(){ int width_image = 600,
	 * height_image = 400 ; BufferedImage image = new BufferedImage(width_image,
	 * height_image, BufferedImage.TYPE_INT_RGB); // on r�cup�re le contexte
	 * graphique de la BufferedImage Graphics2D g = image.createGraphics(); //
	 * la couleur de dessin est le bleu g.setColor (Color.blue); // on dessine
	 * un cercle rempli de rayon 200 dont le centre est de coordonn�es (30,30)
	 * int Rayon = 200 ; g.fillOval( 50, 50, Rayon, Rayon ); // on lib�re la
	 * m�moire utilis�e pour le contexte graphique g.dispose(); return image; }
	 */
	protected void enregistrerImage(File fichierImage) {
		String format = "JPG";
		if (monImage == null)
			return;
		try {
			ImageIO.write(monImage, format, fichierImage);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	protected void enregistrerPanneau(File fichierImage) {
		String format = "JPG";
		BufferedImage image = getImagePanneau();
		try {
			ImageIO.write(image, format, fichierImage);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	protected void miroirVerticalImage () {
		BufferedImage imgmiv = new BufferedImage(monImage.getWidth(),monImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		AffineTransform miroirV = new AffineTransform (1, 0., 0.,-1, 0., monImage.getHeight()) ;
		Graphics2D surfaceImg = imgmiv.createGraphics();
		surfaceImg.drawImage(monImage, miroirV,null);
		monImage = imgmiv;

		repaint(); //r�afficher le panneau
	}

	protected void miroirHorizontalMenu () {
		BufferedImage imgmih = new BufferedImage(monImage.getWidth(),monImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		AffineTransform miroirh = new AffineTransform (-1, 0., 0.,1,monImage.getWidth(),0.) ;
		Graphics2D surfaceImg = imgmih.createGraphics();
		surfaceImg.drawImage(monImage, miroirh,null);
		monImage = imgmih;

		repaint(); //r�afficher le panneau
	}

	protected void rotationMenu () {//rotation par rapport au centre
		BufferedImage imgmrot = new BufferedImage(monImage.getWidth(),monImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		AffineTransform rotation = new AffineTransform();
		double k=Math.PI/2;
		rotation = AffineTransform.getRotateInstance(k,monImage.getWidth()/2,monImage.getHeight()/2);
		Graphics2D surfaceImg = imgmrot.createGraphics();
		surfaceImg.drawImage(monImage, rotation,null);
		monImage = imgmrot;

		repaint(); //r�afficher le panneau
	}

	protected void superpositionMenu () {
		BufferedImage imageModifiee = new BufferedImage (monImage.getWidth()+3, monImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = imageModifiee.createGraphics() ;
		
		Composite newComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER);
        graphics.drawImage(monImage, 0, 0, null) ;
		
		graphics.setComposite(newComposite);
	  
	
		
		graphics.dispose () ;

		monImage = imageModifiee ; 

		repaint(); 
	}

	protected void inverserCouleurs (){		
		BufferedImage imageModifiee = new BufferedImage(monImage.getWidth(),monImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		RescaleOp inverserCouleurs = new RescaleOp(-0.7f ,100f,null);
		inverserCouleurs.filter(monImage, imageModifiee);
		monImage = imageModifiee ;
		repaint();
	}

	protected void vueMozaiqueImage () {
		BufferedImage imagesMozaique = new BufferedImage ((monImage.getWidth()/2)*2+5, (monImage.getHeight()/2)*2+5, monImage.getType());
		BufferedImage imageEnCellule = new BufferedImage (monImage.getWidth()/2, monImage.getHeight()/2, monImage.getType());
		AffineTransform zoom = AffineTransform.getScaleInstance (.5, .5);
		int interpolation = AffineTransformOp.TYPE_BICUBIC ;
		AffineTransformOp retaillerImage = new AffineTransformOp (zoom, interpolation) ;
		retaillerImage.filter(monImage, imageEnCellule) ;
		
		Graphics mozaiqueGraphics = imagesMozaique.createGraphics() ; // cr�er un contexte graphique pour d�ssiner des images
		
	
		mozaiqueGraphics.drawImage(imageEnCellule, 0, 0, null) ;
		mozaiqueGraphics.translate(monImage.getWidth()/2+5, 0) ; //changement d'�chelle (d'origine uniquement <==> translation)
		mozaiqueGraphics.drawImage(imageEnCellule, 0, 0, null) ;
		mozaiqueGraphics.translate(0, monImage.getHeight()/2+5) ; //changement d'�chelle (d'origine uniquement <==> translation)
		mozaiqueGraphics.drawImage(imageEnCellule, 0, 0, null) ;
		mozaiqueGraphics.translate(-monImage.getWidth()/2-5, 0) ; //changement d'�chelle (d'origine uniquement <==> translation)
		mozaiqueGraphics.drawImage(imageEnCellule, 0, 0, null) ;
		
		mozaiqueGraphics.dispose () ; 
		
		
		monImage = imagesMozaique ; 
		repaint(); 
	}

}
