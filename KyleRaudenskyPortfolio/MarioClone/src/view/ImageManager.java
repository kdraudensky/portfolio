package view;
//
// ImageManager
// Image Caching Utility Class
// (c) 2013 Chase and the Cat Daddiez
//
// This class handles all imaging caching operations. Without it, every
// time a VisibleObject is loaded, graphics memory will be wasted.
//
// TODO: JUnit Tests
//       Try/Catch Stuff
//
// Citations: http://stackoverflow.com/questions/6098472/pass-a-local-file-in-to-url-in-java
//

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.io.File;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ImageManager {

    private static HashMap<String, Image> imageMap;

    public static Image loadImage(String resource) {
	
	// add proper directory
	resource = "img/"+resource;
	
	if (imageMap == null) {
	    imageMap = new HashMap<String, Image>();
	}

	Image i = imageMap.get(resource);

	if (i == null) { // If the image is not in the Map, load it.

	    // Load the file into a normal image at first.

	    Image temp = null;

	    try {

		URL u = new File(resource).toURI().toURL();
		System.out.print(u);
		temp = ImageIO.read(u);

	    }
	    catch (Exception e) {
		System.out.println("Error");
	    }

	    // Ensure that this image receives hardware acceleration. :D

	    GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();

	    i = gc.createCompatibleImage(temp.getWidth(null), temp.getHeight(null), Transparency.BITMASK);

	    i.getGraphics().drawImage(temp, 0, 0, null);

	    // Add the new image to the HashMap.

	    imageMap.put(resource, i);

	}

	System.out.println(imageMap.size());

	return i;

    }

}