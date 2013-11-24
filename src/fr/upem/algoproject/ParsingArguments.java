package fr.upem.algoproject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import fr.upem.algoproject.MazeLoader;

import com.martiansoftware.jsap.FlaggedOption;
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

	public void parsing(String[] arguments) throws JSAPException, IOException, ParserConfigurationException, SAXException {
		
		JSAP jsap = new JSAP();

		FlaggedOption input = new FlaggedOption("fileXml")
				.setStringParser(JSAP.STRING_PARSER).setShortFlag('i')
				.setRequired(true).setLongFlag(JSAP.NO_LONGFLAG);
		
		jsap.registerParameter(input);

		FlaggedOption output = new FlaggedOption("image")
				.setStringParser(JSAP.STRING_PARSER).setShortFlag('o')
				.setRequired(true).setLongFlag(JSAP.NO_LONGFLAG);
		
		jsap.registerParameter(output);
		
		JSAPResult config = jsap.parse(arguments);
		
		MazeLoader m = new MazeLoader();
		Maze maze = m.loadMazeFromFile(Paths.get(config
				.getString("fileXml")));
		
		System.out.println("Name image : "+config.getString("image"));


	}

	public static void main(String[] args) throws JSAPException {

		ParsingArguments pa = new ParsingArguments();
		try {
			pa.parsing(args);
		} catch (IOException | ParserConfigurationException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
