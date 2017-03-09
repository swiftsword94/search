package search;

import java.util.ArrayList;

public class Grid
{
	int width = 0;
	int height = 0;
	ArrayList<ArrayList<Node<StringIdentifier, Coord>>> graph;
	public Grid()
	{
		graph = new ArrayList<ArrayList<Node<StringIdentifier, Coord>>>();
	}
	public boolean isInBounds(int x, int y)
	{
		return (y < this.height && x < this.width && (x >= 0 && y >= 0))? true : false;
	}
	public boolean createGrid(ArrayList<ArrayList<Node<StringIdentifier, Coord>>> graph)
	{
		this.graph = new ArrayList<ArrayList<Node<StringIdentifier, Coord>>>(graph);
		width = graph.get(0).size();
		height = graph.size();
		return true;
	}
	public boolean createGrid(int x, int y)
	{
		this.graph = new ArrayList<ArrayList<Node<StringIdentifier, Coord>>>();
		width = x;
		height = y;
		return true;
	}
	public void add(Node<StringIdentifier, Coord> node)
	{
		if(isInBounds(node.getData().x, node.getData().y))
		{
			graph.get(node.getData().x).set(node.getData().x, node);
		}
	}
	public void remove(int x, int y)
	{
		if(isInBounds(x, y))
		{
			graph.get(y).remove(x);
		}
	}
	public void remove(Node<StringIdentifier, Coord> node)
	{
		if(isInBounds(node.getData().x, node.getData().y))
		if(graph.get(node.getData().y).contains(node))
		{
			graph.get(node.getData().y).remove(node);
		}
	}
}
