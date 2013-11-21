package fr.upem.algoproject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import fr.upem.algoproject.MazeLoader;

import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.StringParser;
import com.martiansoftware.jsap.Switch;
import com.martiansoftware.jsap.UnflaggedOption;

public class ParsingArguments {
	private final JSAP jsap;

	ParsingArguments() {
		jsap = new JSAP();
	}

	private static Switch createSwitch(String name, char c, String longFlag) {
		return new Switch(name, c, longFlag);
	}

	private static UnflaggedOption createUnflaggedOption(String name,
			StringParser typeParser, String defaut, boolean required,
			boolean greedy) {
		return new UnflaggedOption(name).setStringParser(typeParser)
				.setDefault(defaut).setRequired(required).setGreedy(greedy);
	}

	public void parsing(String[] arguments) {
		Switch firstSwitch = createSwitch("input", 'c', "input");
		Switch secondSwitch = createSwitch("output", 'o', "output");

		try {

			jsap.registerParameter(firstSwitch);
			jsap.registerParameter(secondSwitch);
			if (arguments.length >= 6) {
				Switch shortestPath = createSwitch("shortedPath", 'r',
						"with-shortest-path");
				jsap.registerParameter(shortestPath);
				System.out.println("Option shortestPath ok");
			}
			UnflaggedOption fileXml = createUnflaggedOption("firstArgument",
					JSAP.STRING_PARSER, JSAP.NO_DEFAULT, false, JSAP.NOT_GREEDY);
			jsap.registerParameter(fileXml);

			UnflaggedOption image = createUnflaggedOption("secondArgument",
					JSAP.STRING_PARSER, JSAP.NO_DEFAULT, false, JSAP.NOT_GREEDY);
			jsap.registerParameter(image);

			JSAPResult config = jsap.parse(arguments);

			MazeLoader m = new MazeLoader();

			if (config.getString("firstArgument").contains(".png")) {
				Maze maze = m.loadMazeFromFile(Paths.get(config
						.getString("secondArgument")));
				System.out.println("GENERATE IMAGE with the FIRST ARGUMENT");
			} else {
				Maze maze = m.loadMazeFromFile(Paths.get(config
						.getString("firstArgument")));
				System.out.println("GENERATE IMAGE with the SECOND ARGUMENT");
			}
		
			System.out.println("Calculer le plus court chemin");

		} catch (IOException | ParserConfigurationException | SAXException
				| JSAPException e) {

			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws JSAPException {

		ParsingArguments pa = new ParsingArguments();
		pa.parsing(args);

	}

}
