package search;

import search.Search.DeepCopy;

public class StringIdentifier implements DeepCopy<StringIdentifier>
{
	private String id = null;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	@Override
	public void copy(StringIdentifier other)
	{
		other.setId(id);
	}

}
