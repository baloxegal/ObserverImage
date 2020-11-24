package processors;

import java.io.File;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import javax.imageio.ImageIO;
import com.twelvemonkeys.image.ResampleOp;
import java.io.IOException;

import config.ConfigurationProvider;

public class FileProcessor {
	
	private ConfigurationProvider cp;
	
	public FileProcessor(ConfigurationProvider cp) {
		this.cp = cp;
	}
	
	public void transform(File f) throws IOException {		
		if(cp.getTypeFile().equals("image")) {
			BufferedImage image = ImageIO.read(f);
			BufferedImageOp resampler = new ResampleOp(100, 100, ResampleOp.FILTER_LANCZOS);
			BufferedImage resampledImage = resampler.filter(image, null);
			ImageIO.write(resampledImage, cp.getExtensions().get(0), new File (cp.getPath("path.refactor") + f.getName()));
		}
		else if(cp.getTypeFile().equals("music")) {
			new File (cp.getPath("path.refactor") + f.getName()).createNewFile();
		}
	}
}
