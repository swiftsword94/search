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
		int cityID = -1, x = -1, y = -1, maxWidth = 0, maxHeight = 0;
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
			maxWidth = (x > maxWidth) ? x : maxWidth;
			str = scan.next();
			y = Integer.parseInt(str);
			maxHeight = (y > maxHeight) ? y : maxHeight;
			add(new Node<intIdentifier, Coord>(new intIdentifier(cityID), new Coord(x, y)));
		}
		width = maxWidth;
		height = maxHeight;
	}
	public void exportGrid(File file) throws FileNotFoundException
	{
		
	}
	
	/**
	 * Gets the straight line distance from start to end.
	 * Avoids using Math.pow for efficiency(it uses some sort of edge case checking)
	 * @param start the starting node
	 * @param end the ending node
	 * @return
	 */
	public static Double euclidDistance(Node<intIdentifier, Coord> start, Node<intIdentifier, Coord> end)
	{
		return Math.sqrt(new Double((start.getData().x-end.getData().x*start.getData().x-end.getData().x))+((start.getData().y-end.getData().y*start.getData().y-end.getData().y)));
	}
	public void updateFringe()
	{
		
	}
	public ArrayList<Node<intIdentifier, Coord>> minimumSpaningTree(Node<intIdentifier, Coord> start)
	{
		if(graph == null)
		{
			return null;
		}
		ArrayList<Node<intIdentifier, Coord>> res = new ArrayList<Node<intIdentifier, Coord>>();
		PriorityQueue<Node<intIdentifier, Coord>> fringe = new PriorityQueue<>();
		HashSet<Node<intIdentifier, Coord>> closed = new HashSet<Node<intIdentifier, Coord>>();
		fringe.add(new Node<intIdentifier, Coord>(start.getIdentifier(), start.getData()));
		Node<intIdentifier, Coord> ptr = null;
		boolean inFringe = false, inClosed = false;
		//for all nodes in the fringe
		while(!fringe.isEmpty())
		{
			ptr = fringe.poll();
			//for each neighbor of the cheapset node
			
			Node<intIdentifier, Coord> insert;
			for(Node<intIdentifier, Coord> neigh : ptr.getNeighbors())
			{
				inFringe = false;
				inClosed = false;
				insert = neigh;
				//if the neighbor is in the fringe
				for(Node<intIdentifier, Coord> indexed : fringe)
				{
					if(neigh.getData().x == indexed.getData().x && neigh.getData().y == indexed.getData().y)//the fringe is limited to only one copy of each node in the graph
					{
						inFringe = true;
						insert = indexed;
						break;
					}
				}
				//if the neighbor is in closed
				for(Node<intIdentifier, Coord> indexed : closed)
				{
					if(neigh.getData().x == indexed.getData().x && neigh.getData().y == indexed.getData().y)//the fringe is limited to only one copy of each node in the graph
					{
						inClosed = true;
						insert = indexed;
						break;
					}
				}
				//if it is not in the fringe or closed make a new node to put in the fringe
				if(!inFringe && !inClosed)
				{
					insert = new Node<intIdentifier, Coord>(neigh.getIdentifier(), neigh.getData());
					for(Node<intIdentifier, Coord> neighbor : neigh.getNeighbors())
					{
						//copy down all the neighbors
						if(insert.getNeighbors().contains(neighbor))
						{
							insert.getNeighbors().add(neigh);
							neigh.getNeighbors().add(insert);
						}
					}
					insert.setDistance(Double.POSITIVE_INFINITY);
					insert.setParent(null);
				}
				Double dist = ptr.getDistance()+euclidDistance(ptr, insert);
				if(insert.getDistance() > dist)
				{
					insert.setParent(ptr);
					insert.setDistance(dist);
					if(!inClosed)
					{
						insert.seteCost(dist);
						//if insert is in the fringe then remove and insert to avoid duplicates
						if(inFringe)
						{
							fringe.remove(insert);
							fringe.add(insert);
						}
						else
						{
							fringe.add(insert);
						}
					}
				}
			}
		}
		return null;
	}
	public ArrayList<Node<intIdentifier, Coord>> shortestHamiltonianCycle() throws Exception
	{
		if(graph == null)
		{
			throw new Exception("Graph uninitialized");
		}
		//make a minheap of Cycles in the graph
		PriorityQueue<Node<intIdentifier, Coord>> fringe = new PriorityQueue<>();//how do i want to compare the outcomes
		HashSet<Node<intIdentifier, Coord>> closed = new HashSet<Node<intIdentifier, Coord>>();
		Node<intIdentifier, Coord> tmp = null;
		new Cycle();
		tmp = graph.get(graph.keySet().iterator().next());//current and fringe pointer nodes
		tmp.setParent(tmp);
		while(!fringe.isEmpty())
		{
			
		}
		return null;
	}
}
