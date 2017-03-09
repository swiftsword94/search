package search;
import search.Search.DeepCopy;

public class Coord implements DeepCopy<Coord>
{
	int x;
	int y;
	public Coord()
	{
		x = 0;
		y = 0;
	}
	public Coord(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	@Override
	public void copy(Coord other) 
	{
		this.x = other.x;
		this.y = other.y;
	}
}
