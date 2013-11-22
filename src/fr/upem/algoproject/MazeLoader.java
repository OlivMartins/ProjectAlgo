package fr.upem.algoproject;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

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
							.getTextContent())-1, Integer.parseInt(attributes
							.getNamedItem("toplefty").getTextContent())-1));
			System.out.println("Found " + r);
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

		Point startpoint = new Point(Integer.parseInt(startAttributes
				.getNamedItem("x").getTextContent()),
				Integer.parseInt(startAttributes.getNamedItem("y")
						.getTextContent()));
		NamedNodeMap endAttributes = ((Element) points)
				.getElementsByTagName("end").item(0).getAttributes();
		int start = startpoint.y * width + startpoint.x;

		Point endpoint = new Point(Integer.parseInt(endAttributes.getNamedItem(
				"x").getTextContent()), Integer.parseInt(endAttributes
				.getNamedItem("y").getTextContent()));
		Graph g = new ListGraph(width * height);

		int end = endpoint.y * width + endpoint.x;
		System.out.println("Before loop: " + System.nanoTime());
		for (int i = 0; i < height; ++i) { // h
			for (int j = 0; j < width; ++j) { // h * w
				int curr_point = j * width + i;
				if (!Rectangles.contains(l, j, i)) { // h*w * obstacles
					System.out.println(l + " " + (i) + " " + (j));
					for (int dx = -1; dx <= 1; ++dx) { // h*w*obstacles *3
						for (int dy = -1; dy <= 1; ++dy) { // h*w*obstacles*3 *
															// 3
							if (j + dy > 0 && j + dy < width && i + dx > 0
									&& i + dx < height) {
								if (!Rectangles.contains(l, i + dx, j + dy)) { // h*w*obstacles*3*3
																				// *
																				// obstacles
									g.addPath(curr_point, (j + dy) * width + i
											+ dx); //9hw(obstacles)²
								}
							}
						}
					}
				}
			}
		}

		System.out.println("After loop:" + System.nanoTime());
		System.out.println("Attempting Dijkstra..");
		int previous[] = new int[g.nodeCount()];
		try {
			previous = DijkstraWalker.walk(g, start, end);
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			previous[end] = -1;
		}
		System.out.println("Finished Dijkstra");
		int u = end;
		System.out.println(u);
		while (previous[u] != -1) {
			u = previous[u];
			System.out.println(u);
		}
		Maze m = new Maze(startpoint, endpoint, (ListGraph) g, width, height);
		m.setShortestPath(previous);
		return m;
	}

	public static void main(String[] args) {
		try {

			System.out.println(System.nanoTime());
			Maze m = loadMazeFromFile(Paths.get("test1.xml"));
			System.out.println(m.getGraph());
			MazeExport.saveToPng(m, Paths.get("hello.png"));
			System.out.println(System.nanoTime());
		} catch (SAXException | IOException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
