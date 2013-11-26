package fr.upem.algoproject;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class MazeExport {
	
	private static void setTile(BufferedImage bi, int rgbColor, int img_width, int img_height, int width, int height, int x, int y) {
		for(int i=x*img_width/width; i < (x+1)*img_width/width; ++i) {
			for(int j=y*img_height/height; j < (y+1)*img_height/height; ++j) {
				bi.setRGB(i, j, rgbColor);
			}
		}
	}
	
	public static void saveToPng(Maze m, Path to) throws IOException {
		Logger logger = Logger.getLogger("fr.upem.algoproject");
	    BufferedImage bi = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);
	    logger.finest("Filling image with blue...");
	    for(int i = 0 ; i < 1000 ; i++){
	       for(int j = 0 ; j < 1000 ; j++){
	          bi.setRGB(i, j, Color.BLUE.getRGB());
	       }
	    }
	    logger.finest("Trying to paint white over the blue to represent the pathes");
	    for(Integer i : m.getGraph().getAllNodes()) {
	    	if(m.getGraph().getNeighbours(i).isEmpty()){
	    		logger.finer("Found not pathes going out of (" + new Point(i % m.getWidth(), i / m.getWidth()));
	    		continue;
	    	}
	    	int y = i / m.getWidth();
	    	int x = i % m.getWidth();
	    	System.out.println(x + ", " + y);
	    	setTile(bi, Color.WHITE.getRGB(), 1000, 1000, m.getWidth(), m.getHeight(), x, y);
	    }
	    setTile(bi, Color.RED.getRGB(), 1000, 1000, m.getWidth(), m.getHeight(), m.getStart().x,  m.getStart().y);
	    setTile(bi, Color.RED.getRGB(), 1000, 1000, m.getWidth(), m.getHeight(), m.getEnd().x,  m.getEnd().y);
		int shortestPath[] = m.getShortestPath();
		int end = m.getEnd().y * m.getWidth() + m.getEnd().x;
		int start = m.getStart().y * m.getWidth() + m.getStart().x;
		int u = end;
	    while(shortestPath[u] != -1) {
	    	System.out.println(u);
	    	setTile(bi, Color.GREEN.getRGB(), 1000, 1000, m.getWidth(), m.getHeight(), u%m.getWidth(), u/m.getWidth());
			u = shortestPath[u];
			if(u == start)
				break;
		}
	    File outputfile = to.toFile();
	    ImageIO.write(bi, "png", outputfile);
	    logger.info("Done writing to file" + to.toAbsolutePath());
	}
}
