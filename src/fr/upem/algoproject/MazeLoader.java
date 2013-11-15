package fr.upem.algoproject;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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
		System.out.println(obstacles);
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

		ArrayMaze maze = new ArrayMaze(height, l, start, end);
		System.out.println("resulting Maze: " + maze);

		System.out.println(start);
		System.out.println(end);

		return MazeFactory.fromArrayMaze(maze);
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
