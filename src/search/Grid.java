package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
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
		 if(scan.hasNext())
		 {
			 int size = Integer.parseInt(scan.nextLine());
			 if(graph == null)
			 {
				 graph = (size>0)?new HashMap<Integer, Node<intIdentifier, Coord>>(size):new HashMap<Integer, Node<intIdentifier, Coord>>(0);
			 }
		 }
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
		//Todo
	}
	public void updateFringe()
	{
		
	}
	public void nearestInsertion(ArrayList<Node<intIdentifier, Coord>> current)
	{
		/*1. Select the shortest edge, and make a subtour of it.
2. Select a city not in the subtour, having the shortest distance to any one of the cities in the subtour.
3. Find an edge in the subtour such that the cost of inserting the selected city between the edge’s cities will be minimal.*/
		
	}
	public ArrayList<Node<intIdentifier, Coord>> shortestHamiltonianCycle() throws Exception
	{
		if(graph == null)
		{
			throw new Exception("Graph uninitialized");
		}
		PriorityQueue<ArrayList<Node<intIdentifier, Coord>>> fringe = new PriorityQueue<>();//how do i want to compare the outcomes
		HashSet<Node<intIdentifier, Coord>> closed = new HashSet<Node<intIdentifier, Coord>>();
		
		while(!fringe.isEmpty())
		{
			
		}
		return null;
	}
}
