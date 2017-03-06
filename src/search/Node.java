package search;

import java.util.ArrayList;

import search.Search.DeepCopy;

public class Node<ID extends DeepCopy<ID>, Data extends DeepCopy<Data>> implements Comparable<Node<ID, Data>>
{
	private ArrayList<Node<ID, Data>> neighbors = null;
	private ID id = null;
	private Data data = null; 
	private Node<ID, Data> parent = null;
	private double distance = 0, eCost = 0;
	public Node()
	{
		
	}
	public Node(ID id, Data data)
	{
		this.id.copy(id);
		this.data = data;
		this.neighbors = new ArrayList<Node<ID, Data>>();
	}
	public Node(ID id, Data data, double distance, double eCost, Node<ID, Data> parent)
	{
		this.id.copy(id);
		this.data = data;
		this.distance = distance;
		this.eCost = eCost;
		this.parent = parent;
	}
	/**
	 * Copies all the fields of a Node<ID, Data> except Node<ID, Data>.distance and Node<ID, Data>.eCost. Only the references for node.neighbors are copied, not the nodes in memory
	 * @param node
	 */
	public Node(Node<ID, Data> node)
	{
		this.id.copy(node.id);
		this.data = node.data;
		this.parent = node.parent;
		this.neighbors = new ArrayList<Node<ID, Data>>(node.neighbors);
	}
	@Override
	public int compareTo(Node<ID, Data> o) {
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
