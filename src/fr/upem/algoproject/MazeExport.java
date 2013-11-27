package fr.upem.algoproject;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class MazeExport {

	private static void setTile(BufferedImage bi, int rgbColor, int img_width,
			int img_height, int width, int height, int x, int y) {
		for (int i = x * img_width / width; i < (x + 1) * img_width / width; ++i) {
			for (int j = y * img_height / height; j < (y + 1) * img_height
					/ height; ++j) {
				bi.setRGB(i, j, rgbColor);
			}
		}
	}

	public static void saveToPng(Maze m, Path to, boolean withShortestPath)
			throws IOException {
		Logger logger = Logger.getLogger("fr.upem.algoproject");
		int width = m.getWidth();
		int bi_w = Math.max(100, width);
		int bi_h = Math.max(100, m.getHeight());
		BufferedImage bi = new BufferedImage(bi_w, bi_h,
				BufferedImage.TYPE_INT_ARGB);
		logger.finest("Filling image with blue...");
		for (int i = 0; i < bi_w; i++) {
			for (int j = 0; j < bi_h; j++) {
				bi.setRGB(i, j, Color.BLUE.getRGB());
			}
		}
		logger.finest("Trying to paint white over the blue to represent the pathes");
		for (Integer i : m.getGraph().getAllNodes()) {
			if (m.getGraph().getNeighbours(i).isEmpty()) {
				continue;
			}
			int y = i / width;
			int x = i % width;
			setTile(bi, Color.WHITE.getRGB(), bi_w, bi_h, width, m.getHeight(),
					x, y);
		}
		setTile(bi, Color.RED.getRGB(), bi_w, bi_h, width, m.getHeight(),
				m.getStart().x, m.getStart().y);
		setTile(bi, Color.RED.getRGB(), bi_w, bi_h, width, m.getHeight(),
				m.getEnd().x, m.getEnd().y);
		if (withShortestPath) {
			int shortestPath[] = m.getShortestPath();
			int end = m.getEnd().y * width + m.getEnd().x;
			int start = m.getStart().y * width + m.getStart().x;
			int u = end;
			while (shortestPath[u] != -1) {
				setTile(bi, Color.GREEN.getRGB(), bi_w, bi_h, width,
						m.getHeight(), u % width, u / width);
				u = shortestPath[u];
			}
		}
		File outputfile = to.toFile();
		ImageIO.write(bi, "png", outputfile);
		logger.info("Done writing to file" + to.toAbsolutePath());
	}
}
