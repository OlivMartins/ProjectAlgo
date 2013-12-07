package fr.upem.algoproject;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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

		Point endpoint = new Point(Integer.parseInt(endAttributes.getNamedItem(
				"x").getTextContent()), Integer.parseInt(endAttributes
				.getNamedItem("y").getTextContent()));
		logger.info("End point: " + endpoint);
		Graph g = new ListGraph(width * height, l, width);

		logger.finer("Before load loop: " + System.nanoTime());
		for (int i = 0; i < height; ++i) { // h
			for (int j = 0; j < width; ++j) { // h * w
				int curr_point = i * width + j;
				for (int dx = -1; dx <= 1; ++dx) { // h*w*obstacles *3
					for (int dy = -1; dy <= 1; ++dy) { // h*w*obstacles*3 *
														// 3
						if (dx == 0 && dy == 0)
							continue; // we don't want to have loops on
										// ourselves
						if (j + dx >= 0 && j + dx < width && i + dy >= 0
								&& i + dy < height) {
							g.addPath(curr_point, (i + dy) * width + j + dx);
						}
					}
				}
			}
		}

		logger.finer("After load loop:" + System.nanoTime());
		if (((ListGraph) g).isEmpty()) {
			logger.severe("GRAPH IS NULL! PANIC ALERT!");
			System.exit(666);
		}

		Maze m = new Maze(startpoint, endpoint, (ListGraph) g, width, height);
		return m;
	}
}
