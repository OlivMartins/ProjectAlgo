package fr.upem.algoproject;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

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
		Node points = doc.getElementsByTagName("points").item(0);
		NamedNodeMap startAttributes = ((Element) points)
				.getElementsByTagName("start").item(0).getAttributes();

		Point start = new Point(Integer.parseInt(startAttributes.getNamedItem(
				"x").getTextContent()), Integer.parseInt(startAttributes
				.getNamedItem("y").getTextContent()));
		NamedNodeMap endAttributes = ((Element) points).getElementsByTagName("end").item(0).getAttributes();
		
		Point end = new Point(Integer.parseInt(endAttributes.getNamedItem(
				"x").getTextContent()), Integer.parseInt(endAttributes
				.getNamedItem("y").getTextContent()));
		
		System.out.println(start);
		System.out.println(end);
		
		
		
		return null; // shut up, linter
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
