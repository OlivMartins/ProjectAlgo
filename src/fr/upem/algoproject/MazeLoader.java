package fr.upem.algoproject;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
			l.add(r);
		}
		return l;
	}

	public static Maze loadMazeFromFile(Path p) throws IOException,
			ParserConfigurationException, SAXException {
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
		System.out.println("height: " + height + ", width: " + width);
		List<Rectangle> l = getObstacles(((Element) rectangle)
				.getElementsByTagName("obstacles").item(0));
		Node points = doc.getElementsByTagName("points").item(0);
		NamedNodeMap startAttributes = ((Element) points)
				.getElementsByTagName("start").item(0).getAttributes();

		Point start = new Point(Integer.parseInt(startAttributes.getNamedItem(
				"x").getTextContent()), Integer.parseInt(startAttributes
				.getNamedItem("y").getTextContent()));
		NamedNodeMap endAttributes = ((Element) points)
				.getElementsByTagName("end").item(0).getAttributes();

		Point end = new Point(Integer.parseInt(endAttributes.getNamedItem("x")
				.getTextContent()), Integer.parseInt(endAttributes
				.getNamedItem("y").getTextContent()));
		Graph g = new ListGraph(width * height);

		for(int i=0; i < height; ++i) { // h
			for(int j=0; j < width; ++j) { // h * w
				Point point = new Point(j, i);
				if(!Rectangles.contains(l, point)) { // h*w * obstacles
					fr.upem.algoproject.Node n1 = new fr.upem.algoproject.Node(point);
					for (int dx = -1; dx <= 1; ++dx) { // h*w*obstacles *3
						for (int dy = -1; dy <= 1; ++dy) { // h*w*obstacles*3 * 3
							if (j + dx > 0 && j + dx < width && i + dy > 0
									&& i + dy < height) {
								if (!Rectangles.contains(l, i + dx, j + dy)) { // h*w*obstacles*3*3 * obstacles
									Point p2 = new Point(i + dx, j + dy);
									fr.upem.algoproject.Node n2 = new fr.upem.algoproject.Node(p2);
									if(p2.equals(end))
										n2.isEnd(true);
									g.addPath(n1, n2);
								}
							}
						}
					}
				}
			}
		}
		
		System.out.println("resulting g: " + g);
		System.out.println(start);
		System.out.println(end);
		return new GraphMaze(g, start, end);
	}

	public static void main(String[] args) {
		try {
			Maze m = loadMazeFromFile(Paths.get("test1.xml"));
		} catch (SAXException | IOException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
