package processors;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.twelvemonkeys.image.ResampleOp;

import config.ConfigurationProvider;

public class ImageProcessor {
	
	private ConfigurationProvider cp;
	
	public ImageProcessor(ConfigurationProvider cp) {
		this.cp = cp;
	}
	
	public void transform(File f) throws IOException {
		
		BufferedImage image = ImageIO.read(f);
		BufferedImageOp resampler = new ResampleOp(100, 100, ResampleOp.FILTER_LANCZOS);
		BufferedImage resampledImage = resampler.filter(image, null);
		ImageIO.write(resampledImage, "jpeg", new File (cp.getPath("path.refactor") + f.getName()));							
		
	}

}
