import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

public class Cadre extends JFrame implements ActionListener {

	private final PanDessin panneau = new PanDessin();

	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu fichierMenu = new JMenu("Fichiers");
	private final JMenuItem ouvrirMenu = new JMenuItem("Ouvrir", 'O');
	private final JMenu filtreMenu = new JMenu("Filtres");
	private final JMenu convolutionMenu = new JMenu("Convolution");

	private final JMenuItem enregistrerImageMenu = new JMenuItem(
			"Enregistrer Image", 'S');
	private final JMenuItem enregistrerPanneauMenu = new JMenuItem(
			"Enregistrer Panneau", 'P');
	private final JMenuItem niveauGrisMenu = new JMenuItem("Niveau en Gris",
			'G');
	private final JMenuItem assombrirMenu = new JMenuItem("Assombrir", 'A');
	private final JMenuItem brillanceMenu = new JMenuItem("Briller", 'B');
	private final JMenuItem binarisationMenu = new JMenuItem("Binariser", 'I');
	private final JMenuItem flouteMenu = new JMenuItem("Flouter", 'F');
	private final JMenuItem contourVertMenu = new JMenuItem(
			"Contours Verticaux", 'V');
	private final JMenuItem contourHorzMenu = new JMenuItem(
			"Contours Horizontaux", 'H');

	private final JMenuItem miroirVerticalMenu = new JMenuItem(
			"Miroir Vertical");
	private final JMenuItem miroirHorizontalMenu = new JMenuItem(
			"Miroir Horizontal");

	private final JMenu retaillerMenu = new JMenu("Zoom");
	private final JMenuItem agrandirMenu = new JMenuItem("Avant (200 %)", 'N');
	private final JMenuItem reduireMenu = new JMenuItem("Arri�re (50 %)", 'R');

	private final JMenu tpMenu = new JMenu("TP Etudiant");
	private final JMenu miroirMenu = new JMenu("Effet miroir");

	private final JMenuItem rotationMenu = new JMenuItem("Rotation de pi/2");
	private final JMenuItem mozaiqueMenu = new JMenuItem("Vue en moza�que");
	private final JMenuItem superposerMenu = new JMenuItem(
			"Superposition de deux images");
	private final JMenuItem InverserMenu = new JMenuItem(
			"Inversion des couleurs");

	public Cadre() {
		super();
		setBounds(100, 100, 500, 375);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		try {
			creerMenu();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		//
	}

	private void creerMenu() throws Exception {

		// construction du menu
		setJMenuBar(menuBar);
		menuBar.add(fichierMenu);

		fichierMenu.add(ouvrirMenu);
		ouvrirMenu.addActionListener(this);

		fichierMenu.add(enregistrerImageMenu);
		enregistrerImageMenu.addActionListener(this);

		fichierMenu.add(enregistrerPanneauMenu);
		enregistrerPanneauMenu.addActionListener(this);

		menuBar.add(filtreMenu);

		filtreMenu.add(niveauGrisMenu);
		niveauGrisMenu.addActionListener(this);

		filtreMenu.add(binarisationMenu);
		binarisationMenu.addActionListener(this);

		filtreMenu.add(assombrirMenu);
		assombrirMenu.addActionListener(this);

		filtreMenu.add(brillanceMenu);
		brillanceMenu.addActionListener(this);

		filtreMenu.add(convolutionMenu);

		convolutionMenu.add(flouteMenu);
		flouteMenu.addActionListener(this);

		convolutionMenu.add(contourVertMenu);
		contourVertMenu.addActionListener(this);

		convolutionMenu.add(contourHorzMenu);
		contourHorzMenu.addActionListener(this);

		menuBar.add(retaillerMenu);

		retaillerMenu.add(agrandirMenu);
		agrandirMenu.addActionListener(this);

		retaillerMenu.add(reduireMenu);
		reduireMenu.addActionListener(this);

		menuBar.add(tpMenu);
		tpMenu.add(miroirMenu);

		miroirMenu.add(miroirVerticalMenu);
		miroirVerticalMenu.addActionListener(this);

		miroirMenu.add(miroirHorizontalMenu);
		miroirHorizontalMenu.addActionListener(this);

		tpMenu.add(rotationMenu);
		rotationMenu.addActionListener(this);

		tpMenu.add(mozaiqueMenu);
		mozaiqueMenu.addActionListener(this);

		tpMenu.add(superposerMenu);
		superposerMenu.addActionListener(this);

		tpMenu.add(InverserMenu);
		InverserMenu.addActionListener(this);

		JScrollPane scrollPane = new JScrollPane(panneau);

		// ajouter le panneau de dessin
		getContentPane().add(scrollPane);
	}

	public void actionPerformed(ActionEvent cliqueMenu) {
		if (cliqueMenu.getSource().equals(ouvrirMenu)) {
			JFileChooser fileOuvrirImage = new JFileChooser();
			if (fileOuvrirImage.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				panneau.ajouterImage(new File(fileOuvrirImage.getSelectedFile()
						.getAbsolutePath()));
			}
		} else if (cliqueMenu.getSource().equals(enregistrerImageMenu)) {
			JFileChooser fileEnregistrerImage = new JFileChooser();
			if (fileEnregistrerImage.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				File fichierEnregistrement = new File(fileEnregistrerImage
						.getSelectedFile().getAbsolutePath() + ".JPG");
				panneau.enregistrerImage(fichierEnregistrement);
			}
		} else if (cliqueMenu.getSource().equals(enregistrerPanneauMenu)) {
			JFileChooser fileEnregistrerImage = new JFileChooser();
			if (fileEnregistrerImage.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				File fichierEnregistrement = new File(fileEnregistrerImage
						.getSelectedFile().getAbsolutePath() + ".JPG");
				panneau.enregistrerPanneau(fichierEnregistrement);
			}
		} else if (cliqueMenu.getSource().equals(niveauGrisMenu)) {
			panneau.imageEnNiveauGris();
		} else if (cliqueMenu.getSource().equals(brillanceMenu)) {
			panneau.imageEclaircie();
		} else if (cliqueMenu.getSource().equals(binarisationMenu)) {
			panneau.imageBinaire();
		} else if (cliqueMenu.getSource().equals(flouteMenu)) {
			float[] masqueConvolution = { 0.25f, 0.f, 0.25f, 0.f, 0.f, 0.f,
					0.25f, 0.f, 0.25f };
			panneau.imageConvolue(masqueConvolution, 3);
			System.out.println("flouter l'image");
		} else if (cliqueMenu.getSource().equals(contourVertMenu)) {
			panneau.imageEnNiveauGris();
			float[] masqueConvolution =
			/*
			 * { 1/30.f, 1/30.f, 0.f, -1/30.f, -1/30.f, 1/30.f, 2/30.f, 0.f,
			 * -2/30.f, -1/30.f, 1/30.f, 3/30.f, 0.f, -3/30.f, -1/30.f, 1/30.f,
			 * 2/30.f, 0.f, -2/30.f, -1/30.f, 1/30.f, 1/30.f, 0.f, -1/30.f,
			 * -1/30.f, } ;
			 */

			{ 1 / 8.f, 0.f, -1 / 8.f, 2 / 8.f, 0.f, -2 / 8.f, 1 / 8.f, 0.f,
					-1 / 8.f };
			panneau.imageConvolue(masqueConvolution, 3);
			System.out.println("contours verticaux");
		} else if (cliqueMenu.getSource().equals(contourHorzMenu)) {
			panneau.imageEnNiveauGris();
			float[] masqueConvolution = { 1 / 8.f, 1 / 8.f, 1 / 8.f, 0.f, 0.f,
					0.f, -1 / 8.f, -1 / 8.f, -1 / 8.f };
			panneau.imageConvolue(masqueConvolution, 3);
			System.out.println("contours horizontaux");
		}

		else if (cliqueMenu.getSource().equals(agrandirMenu)) {
			panneau.zoomerImage(2.);
			System.out.println("Zoom");
		} else if (cliqueMenu.getSource().equals(reduireMenu)) {
			panneau.zoomerImage(0.5);
			System.out.println("R�duction");
		} else if (cliqueMenu.getSource().equals(assombrirMenu)) {
			panneau.imageSombre();
			System.out.println("Assombrir");
		} else if (cliqueMenu.getSource().equals(miroirVerticalMenu)) {
			panneau.miroirVerticalImage();
			System.out.println("Miroir vertical");
		} else if (cliqueMenu.getSource().equals(miroirHorizontalMenu)) {
			panneau.miroirHorizontalMenu();
			System.out.println("Miroir horizontal");
		} else if (cliqueMenu.getSource().equals(rotationMenu)) {
			panneau.rotationMenu();
			System.out.println("Rotation a gauche");
		} else if (cliqueMenu.getSource().equals(mozaiqueMenu)) {
			panneau.vueMozaiqueImage();
			System.out.println("vue en mozaique");
		} else if (cliqueMenu.getSource().equals(superposerMenu)) {
			panneau.superpositionMenu();
			System.out.println("superposition des images");
		} else if (cliqueMenu.getSource().equals(InverserMenu)) {
			panneau.inverserCouleurs();
			System.out.println("Inversion des couleurs");
		}
	}

	public static void main(String args[]) {
		try {
			Cadre frame = new Cadre();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
