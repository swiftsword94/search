package search;

import search.Search.DeepCopy;

public class Graph
{
	public class ID implements DeepCopy<ID>
	{
		public class Coord implements DeepCopy<Coord>
		{
			int x;
			int y;
			@Override
			public void copy(Coord other) 
			{
				this.x = other.x;
				this.y = other.y;
			}
		}
		public void copy(ID other) 
		{
			return;
		}
		
	}
	
	
}
