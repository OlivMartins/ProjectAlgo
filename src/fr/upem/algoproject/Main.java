package fr.upem.algoproject;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.Switch;


public class Main {
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
			
			JSAP jsap = new JSAP();

			FlaggedOption input = new FlaggedOption("XMLFile")
					.setStringParser(JSAP.STRING_PARSER).setShortFlag('i')
					.setRequired(true).setLongFlag(JSAP.NO_LONGFLAG);

			FlaggedOption output = new FlaggedOption("Image")
					.setStringParser(JSAP.STRING_PARSER).setShortFlag('o')
					.setRequired(true).setLongFlag(JSAP.NO_LONGFLAG);
			Switch shortestPath = new Switch("withShortestPath").setShortFlag('p').setLongFlag("with-shortest-path");
			try {
				jsap.registerParameter(shortestPath);
				jsap.registerParameter(input);
				jsap.registerParameter(output);
			} catch (JSAPException e1) {
				l.warning("Could not register CLI options!");
			}
			
			JSAPResult config = jsap.parse(args);
			if(!config.success()){
				System.out.println("java " + Main.class.getCanonicalName() + " " +jsap.getUsage());
				System.exit(1);
			}
			
			try {
				long loadStart = System.currentTimeMillis();
				Maze m = MazeLoader.loadMazeFromFile(Paths.get(config.getString("XMLFile")));
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
				
				MazeExport.saveToPng(m, Paths.get(config.getString("Image")), config.getBoolean("withShortestPath"));
			} catch (SAXException | IOException | ParserConfigurationException e) {
				l.severe("Error  while trying to solve the maze: " + e.getMessage());
			}
			
			long prgrmEnd = System.currentTimeMillis();
			l.info("Programm took " + (prgrmEnd - prgrmStart) + "ms to complete.");
	}

}
