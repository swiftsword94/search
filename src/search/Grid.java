package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Grid
{
	int width = 0;
	int height = 0;
	HashMap<Integer ,Node<intIdentifier, Coord>> graph;
	public Grid()
	{
		graph = new HashMap<Integer ,Node<intIdentifier, Coord>>();
	}
	public boolean isInBounds(int x, int y)
	{
		return (y < this.height && x < this.width && (x >= 0 && y >= 0))? true : false;
	}
	public boolean createGrid(ArrayList<ArrayList<Node<intIdentifier, Coord>>> graph)
	{
		this.graph = new HashMap<Integer ,Node<intIdentifier, Coord>>();
		width = graph.get(0).size();
		height = graph.size();
		return true;
	}
	public boolean createGrid(int x, int y)
	{
		this.graph = new HashMap<Integer ,Node<intIdentifier, Coord>>();
		width = x;
		height = y;
		return true;
	}
	public void add(Node<intIdentifier, Coord> node)
	{
		if(isInBounds(node.getData().x, node.getData().y))
		{
			graph.put(node.getIdentifier().getId(), node);
		}
	}
	public void remove(Integer id)
	{
		if(graph.containsKey(id))
		{
			graph.remove(id);
		}
	}
	public void remove(Node<intIdentifier, Coord> node)
	{
		if(graph.containsKey(node.getIdentifier().getId()))
		{
			graph.remove(node);
		}
	}
	public void createGrid(File file) throws FileNotFoundException
	{
		Scanner scan = new Scanner(file);
		int cityID = -1, x = -1, y = -1;
		
		for(String str = null ;scan.hasNext();)
		{
			//get city data
			str = scan.next();
			cityID = Integer.parseInt(str);
			str = scan.next();
			x = Integer.parseInt(str);
			str = scan.next();
			y = Integer.parseInt(str);
			add(new Node<intIdentifier, Coord>(new intIdentifier(cityID), new Coord(x, y)));
		}
	}
	public void exportGrid(File file) throws FileNotFoundException
	{
		
	}
}
