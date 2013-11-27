package fr.upem.algoproject;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MazeLoader {

	private static List<Rectangle> getObstacles(Node obstacles) {
		Logger logger = Logger.getLogger("fr.upem.algoproject");
		ArrayList<Rectangle> l = new ArrayList<>();
		NodeList obs = ((Element) obstacles).getElementsByTagName("obstacle");
		for (int i = 0; i < obs.getLength(); ++i) {
			Node n = obs.item(i);
			NamedNodeMap attributes = n.getAttributes();
			Rectangle r = new Rectangle(new Point(Integer.parseInt(attributes
					.getNamedItem("bottomrightx").getTextContent()),
					Integer.parseInt(attributes.getNamedItem("bottomrighty")
							.getTextContent())), new Point(
					Integer.parseInt(attributes.getNamedItem("topleftx")
							.getTextContent()), Integer.parseInt(attributes
							.getNamedItem("toplefty").getTextContent())));
			logger.fine("Found Obstacle " + r);
			l.add(r);
		}
		return l;
	}

	public static Maze loadMazeFromFile(Path p) throws IOException,
			ParserConfigurationException, SAXException {
		Logger logger = Logger.getLogger("fr.upem.algoproject");
		logger.info("Started loading from " + p.toAbsolutePath());
		DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbfactory.newDocumentBuilder();
		Document doc = db.parse(p.toAbsolutePath().toFile());
		Node rectangle = doc.getElementsByTagName("rectangle").item(0);
		Node dimension = ((Element) rectangle)
				.getElementsByTagName("dimension").item(0);
		NamedNodeMap dim = dimension.getAttributes();
		int height = Integer.parseInt(dim.getNamedItem("height")
				.getTextContent());
		int width = Integer
				.parseInt(dim.getNamedItem("width").getTextContent());
		logger.info("height: " + height + ", width: " + width);
		List<Rectangle> l = getObstacles(((Element) rectangle)
				.getElementsByTagName("obstacles").item(0));
		Node points = doc.getElementsByTagName("points").item(0);
		NamedNodeMap startAttributes = ((Element) points)
				.getElementsByTagName("start").item(0).getAttributes();

		Point startpoint = new Point(Integer.parseInt(startAttributes
				.getNamedItem("x").getTextContent()),
				Integer.parseInt(startAttributes.getNamedItem("y")
						.getTextContent()));
		logger.info("Start point: " + startpoint);
		NamedNodeMap endAttributes = ((Element) points)
				.getElementsByTagName("end").item(0).getAttributes();
		//int start = startpoint.y * width + startpoint.x;

		Point endpoint = new Point(Integer.parseInt(endAttributes.getNamedItem(
				"x").getTextContent()), Integer.parseInt(endAttributes
				.getNamedItem("y").getTextContent()));
		logger.info("End point: " + endpoint);
		Graph g = new ListGraph(width * height, l, width);

		//int end = endpoint.y * width + endpoint.x;
		logger.finer("Before load loop: " + System.nanoTime());
		for (int i = 0; i < height; ++i) { // h
			for (int j = 0; j < width; ++j) { // h * w
				int curr_point = i * width + j;
//				if (!Rectangles.contains(l, j, i)) { // h*w * obstacles
					
//					if(!Rectangles.contains(l, i - 1, j) && i-1>=0)
//						g.addPath(curr_point, (i-1)*width + j);
//					if(!Rectangles.contains(l, i, j-1) && j-1>= 0)
//						g.addPath(curr_point, i*width + j - 1);
//					if(!Rectangles.contains(l,  i + 1, j) && i+1 < height)
//						g.addPath(curr_point, (i+1)*width + j);
//					if(!Rectangles.contains(l,  i, j+1) && j+1 < width)
//						g.addPath(curr_point, i*width + j + 1);
//					
					for (int dx = -1; dx <= 1; ++dx) { // h*w*obstacles *3
						for (int dy = -1; dy <= 1; ++dy) { // h*w*obstacles*3 *
															// 3
							if (dx == 0 && dy == 0)
								continue; // we don't want to have loops on
											// ourselves
							if (j + dx >= 0 && j + dx < width && i + dy >= 0
									&& i + dy < height) {
//								if (!Rectangles.contains(l, j + dx, i + dy)) { // h*w*obstacles*3*3
																				// *
																				// obstacles
									g.addPath(curr_point, (i + dy) * width + j
											+ dx); // 9hw(obstacles)²...
//								}
							}
						}
					}
//				}
			}
		}

		logger.finer("After load loop:" + System.nanoTime());
		if(((ListGraph) g).isEmpty()) {
			logger.severe("GRAPH IS NULL! PANIC ALERT!");
			System.exit(666);
		}
		
		Maze m = new Maze(startpoint, endpoint, (ListGraph) g, width, height);
		return m;
	}

	public static void main(String[] args) {
		Logger l = Logger.getLogger("fr.upem.algoproject");
		l.setLevel(Level.ALL);
		try {
			FileHandler fh = new FileHandler("fr.upem.algoproject.log");
			fh.setFormatter(new SimpleFormatter());
			fh.setLevel(Level.FINEST);
			l.addHandler(fh);
		} catch (Exception e) {
			l.severe("Could not open log file: " + e.getMessage());
		}
		long prgrmStart = System.currentTimeMillis();
		l.fine("Programm start: " + prgrmStart);
		try {
			long loadStart = System.currentTimeMillis();
			Maze m = loadMazeFromFile(Paths.get("labygrand1.xml"));
			long loadEnd = System.currentTimeMillis();
			l.info("Load phase took " + (loadEnd - loadStart)+ "ms to complete.");
			

			int width = m.getWidth();
			Point startPoint = m.getStart();
			int start = startPoint.y * width + startPoint.x;
			Point endPoint = m.getEnd();
			int end = endPoint.y * width + endPoint.x;
			l.fine("Attempting Dijkstra..");
			long dijkstraStart = System.currentTimeMillis();
			int previous[] = new int[m.getGraph().nodeCount()];
			
			
			try {
				previous = DijkstraWalker.walk(m.getGraph(), start, end);
			} catch (NoSuchElementException e) {
				l.warning(e.getMessage());
				previous[end] = -1;
			}
			previous[start] = -1;

			m.setShortestPath(previous);
			long dijkstraEnd = System.currentTimeMillis();
			l.info("Phase Dijkstra took " + (dijkstraEnd - dijkstraStart) + "ms to complete.");
			
			MazeExport.saveToPng(m, Paths.get("hello.png"));
		} catch (SAXException | IOException | ParserConfigurationException e) {
			l.severe("Error  while trying to solve the maze: " + e.getMessage());
		}
		
		long prgrmEnd = System.currentTimeMillis();
		l.info("Programm took " + (prgrmEnd - prgrmStart) + "ms to complete.");
	}

}
