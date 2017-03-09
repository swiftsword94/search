package search;



import java.util.Collection;
/**
 * An interface for objects used to path through a graph of <A>. It is expected to be used with Objects that use the Java Collection interface.
 * @author Scott Reyes
 *
 * @param <A>
 */
public interface Search <A>
{
	/**
	 * An Interface for ensuring that any T can be copied
	 * @author Scott Reyes
	 *
	 * @param <T>
	 */
	public interface DeepCopy<T> 
	{
		/**
		 * Performs a deep copy of the contents from other to the current object 
		 * @param other
		 */
		public void copy(T other);
	}
	/**
	 * Gets the shortest path from start to finish
	 * @param start a starting node in a graph
	 * @param end an ending node in a graph
	 * @return An ordered Collection of type A 
	 */
	public Collection<? extends A> path(A start, A end);
}
