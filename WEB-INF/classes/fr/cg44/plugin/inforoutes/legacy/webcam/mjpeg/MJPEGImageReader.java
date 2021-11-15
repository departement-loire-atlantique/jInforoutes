package fr.cg44.plugin.inforoutes.legacy.webcam.mjpeg;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.apache.log4j.Logger;

import com.jalios.util.Util;

public class MJPEGImageReader {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MJPEGImageReader.class);

	private static final String IMAGE_STREAM_LINE_SEPARRATOR = "--multipartBoundary";

	private DataInputStream currentStream = null;
	private String currentLine = null;
	byte[] cbuf = null;

	/**
	 * extraction des meta données du flux
	 * 
	 * @param in
	 * @throws IOException
	 */
	public MJPEGImageReader(DataInputStream in) throws IOException {
		this.currentStream = in;
	}

	private String getLineFromInputStream(InputStream in) throws IOException {		
		StringBuffer buf = new StringBuffer();
		int c;

		// Dans le cas où il y a une difficulté réseau le code peut bloquer ici :
		// - le socket reste ouvert mais plus aucune données n'est envoyées
		// - in.read => pas de timeout de lecture donc attente à l'infini
		int delay = 2000;
		Long date = new Date().getTime(); 
		
		while ((c = in.read()) != -1) {
			if (c == '\n') {
				break;
			} else {
				buf.append(new Character((char) c));
			}
			if(new Date().getTime() >  date + delay){
				logger.debug("getLineFromInputStream - temps de boucle dépassé");
				return null;				
			}
		}

		String returnString = buf.toString();

		return returnString;
	}

	public boolean hasNext() throws IOException {
		logger.debug("hasNext - start");
		this.currentLine = getLineFromInputStream(this.currentStream);
		// lecture de l'image pour supprimer les retours chariots
		while(this.currentLine != null && currentLine.equalsIgnoreCase("\r")){			
			this.currentLine = getLineFromInputStream(this.currentStream);
		}
		if (this.currentLine != null && this.currentLine.lastIndexOf(IMAGE_STREAM_LINE_SEPARRATOR) > -1) {			
			// extraction de la ligne 1
			String firstLine = this.getLineFromInputStream(currentStream);
			String[] ligne = firstLine.split(":");
			// extraction de la ligne 1
			String secondLine = this.getLineFromInputStream(currentStream);
			ligne = secondLine.split(":");
			final int size = Integer.parseInt(ligne[1].trim());
			// supression de la ligne vide
			this.getLineFromInputStream(this.currentStream);

			// lecture de l'image complete
			logger.debug("hasNext line : " + firstLine);
			logger.debug("hasNext size : " + size);
			cbuf= new byte[size];
			this.currentStream.readFully(cbuf);
		}
		logger.debug("hasNext - end");
		return (this.currentLine != null && Util.notEmpty(this.currentLine));
	}

	public boolean isNextImage() {
		return this.currentLine.lastIndexOf(IMAGE_STREAM_LINE_SEPARRATOR) > -1;
	}

	/**
	 * lecture du flux et ecriture dans un fichier
	 * 
	 * @param in
	 *            le flux courant
	 * @param fileName
	 *            le nom du fichier
	 * @throws IOException
	 */
	public void writeImage(DataInputStream in, String fileName) throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("writeImage(DataInputStream, String) - start");
		}

		FileOutputStream fos = new FileOutputStream(fileName);
		writeImage(in, fos);

		if (logger.isDebugEnabled()) {
			logger.debug("writeImage(DataInputStream, String) - end");
		}
	}

	/**
	 * lecture du flux et ecriture dans un fichier
	 * 
	 * @param in
	 *            le flux courant
	 * @param fileName
	 *            le nom du fichier
	 * @throws IOException
	 */
	public boolean writeImage(String fileName) throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("writeImage(DataInputStream, String) - start");
		}
		boolean write = false;
		//File f = new File(fileName);
		//if (!f.exists()) {
			FileOutputStream fos = new FileOutputStream(fileName);
			writeImage(this.currentStream, fos);
			write = true;
		//} else {
			//logger.warn("double ecriture");
		//}
		if (logger.isDebugEnabled()) {
			logger.debug("writeImage(DataInputStream, String) - end");
		}
		return write;
	}

	/**
	 * lecture du flux et ecriture dans un fichier
	 * 
	 * @param in
	 *            le flux courant
	 * @param out
	 *            le flux de sortie
	 * @throws IOException
	 */
	public void writeImage(DataInputStream in, OutputStream out) throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("writeImage(DataInputStream, OutputStream) - start");
		}

		// ecriture de l'image
		out.write(cbuf);
		out.close();

		if (logger.isDebugEnabled()) {
			logger.debug("writeImage(DataInputStream, OutputStream) - end");
		}
	}

}
