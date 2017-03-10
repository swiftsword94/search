package search;

import java.util.List;

public class Cycle implements Comparable<Cycle>
{
	private List<Node<intIdentifier, Coord>> cycle;
	private Double length = new Double(0);
	public Cycle()
	{
		cycle = null;
	}
	private static Double euclidDistance(Node<intIdentifier, Coord> start, Node<intIdentifier, Coord> end)
	{
		return Math.sqrt(new Double((start.getData().x-end.getData().x*start.getData().x-end.getData().x))+((start.getData().y-end.getData().y*start.getData().y-end.getData().y)));
	}
	public void add(Node<intIdentifier, Coord> node)
	{
		this.cycle.add(node);
		this.length = computeLength();
	}
	public void remove(Node<intIdentifier, Coord> node)
	{
		this.cycle.add(node);
		this.length = computeLength();
	}
	private Double computeLength()
	{
		Double length = new Double(0); 
		Node<intIdentifier, Coord> node = null;
		for(int i = 0; i < cycle.size()-1;i++)
		{
			node = cycle.get(i);
			length += euclidDistance(node, cycle.get(i+1));
		}
		if(cycle.size()>0)
		{
			length += euclidDistance(cycle.get(cycle.size()), cycle.get(0));
		}
		return length;
	}
	@Override
	public int compareTo(Cycle o)
	{
		if(this.length < o.length)
		{
			return -1;
		}
		else if(this.length == o.length)
		{
			return 0;
		}
		else
		{
			return 1;
		}
	}
	
}
