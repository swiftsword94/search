package search;

import search.Search.DeepCopy;

public class intIdentifier implements DeepCopy<intIdentifier>
{
	private Integer id = null;
	public intIdentifier(int id)
	{
		id = new Integer(id);
	}
	public int getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	@Override
	public void copy(intIdentifier other)
	{
		this.setId(other.id);//creates a new one in memory?
	}

}
