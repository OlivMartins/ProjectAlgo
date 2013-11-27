package fr.upem.algoproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

public class ListGraph implements Graph, Iterable<Integer> {
	
	private final List<Integer> array[];
	private static final List<Integer> OBS = new ArrayList<>();
	
	public ListGraph(int nNodes, List<Rectangle> obstacles, int width) {
		Logger log = Logger.getLogger("fr.upem.algoproject");
		this.array = new ArrayList[nNodes];
		for(Rectangle l: obstacles ) {
			for(int i=l.topleft.x; i <= l.bottomright.x; ++i) {
				for(int j = l.topleft.y; j <= l.bottomright.y; ++j) {
					log.finest((j * width + i) + "on rectangle " + l);
					array[j * width + i] = OBS;
				}
			}
		}
		for(int i=0; i < nNodes; ++i) {
			if(array[i] != OBS)
				array[i] = new ArrayList<>();
		}
	}

	@Override
	public boolean hasPath(int node1, int node2) {
		return array[node1].contains(node2);
	}

	@Override
	public void addPath(int node1, int node2) {
		if(array[node1] != OBS && array[node2] != OBS)
			array[node1].add(node2);
	}

	@Override
	public int nodeCount() {
		return array.length;
	}

	@Override
	public List<Integer> getNeighbours(int n) {
		List<Integer> l = new ArrayList<>();
		l.addAll(this.array[n]);
		return l;
	}

	@Override
	public List<Integer> getAllNodes() {
		List<Integer> l = new ArrayList<>();
		for(int i=0; i < array.length; ++i) {
			l.add(i);
		}
		return l;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new Iterator<Integer>() {
			int array_index = 0;
			int list_index = 0;
			
			@Override
			public Integer next() {
				if(hasNext()) {
					Integer i = array[array_index].get(list_index++);
					if(list_index > array[array_index].size()-1){
						array_index++;
						list_index = 0;
					}
					return i;
				}
				throw new NoSuchElementException();
			}
			
			@Override
			public boolean hasNext() {
				if(array_index < array.length) {
					if(list_index < array[array_index].size()) {
						return true;
					}
				}
				return false;
			}

			@Override
			public void remove() {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i < array.length; ++i) {
			sb.append("[" + i + "] -> [");
			for(int j=0; j < array[i].size(); ++j) {
				sb.append(array[i].get(j) + ", ");
			}
			sb.append("]\n");
		}
		return sb.toString();
	}
	
	public boolean isEmpty() {
		for(List<Integer> l: Arrays.asList(this.array)) {
			if(!l.isEmpty()) {
				return false;
			}
		}
		return true;
	}

}
