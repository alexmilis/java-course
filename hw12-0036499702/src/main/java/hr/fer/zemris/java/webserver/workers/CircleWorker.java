package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Implementation of {@link IWebWorker} that draws an image 
 * of size 200x200 with a filled circle in center.
 * 
 * @author Alex
 *
 */
public class CircleWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		BufferedImage bim = new BufferedImage(200, 200, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2d = bim.createGraphics();
		g2d.setColor(Color.CYAN);
		g2d.fillOval(0, 0, 200, 200);
		g2d.dispose();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			context.setMimeType("image/png");
			ImageIO.write(bim, "png", bos);
			context.write(bos.toByteArray());
			bos.close();
		} catch (IOException e) {
			System.err.println("An error occured while creating request for circle image!");
		}
	}

}
