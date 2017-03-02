
import java.util.ArrayList;

public class Node implements Comparable<Node>
{
	public ArrayList<Node> neighbors = null;
	public char type = 0;
	public Node parent = null;
	public int x = 0, y = 0;
	public double distance = 0, eCost = 0; //x and y are coordinates, distance is from start, eCost and distance are for pathfinding
	public Node()
	{
	}
	public Node(char type, int x, int y)
	{
		this.type = type;
		this.x = x;
		this.y = y;
		this.neighbors = new ArrayList<Node>(8);
	}
	public Node( char type, int x, int y, double distance, double eCost, Node parent)
	{
		this.type = type;
		this.x = x;
		this.y = y;
		this.distance = distance;
		this.eCost = eCost;
		this.parent = parent;
	}
	public Node(Node o)
	{
		this.type = o.type;
		this.x = o.x;
		this.y = o.y;
		this.parent = o.parent;
		this.neighbors = new ArrayList<Node>(o.neighbors);
	}
	@Override
	public int compareTo(Node o) {
		if(this.eCost<o.eCost)
		{
			return -1;
		}
		else if(this.eCost==o.eCost)
		{
			return 0;
		}
		else
		{
			return 1;
		}
	}
}
