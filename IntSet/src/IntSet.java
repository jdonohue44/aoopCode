import java.util.NoSuchElementException;

/**
 * Representation of a finite set of integers.
 * 
 * @invariant getCount() >= 0
 * @invariant getCount() <= getCapacity()
 */
public class IntSet {

	
	/**
	 * Creates a new set with 0 elements.
	 * 
	 * @param capacity
	 *            the maximal number of elements this set can have
	 * @pre capacity >= 0
	 * @post getCount() == 0
	 * @post getCapacity() == capacity
	 */
	
	int[] array; 
	int capacity;
	int count; 
	
	public IntSet(int capacity) {
		this.capacity = (capacity >= 0) ? capacity : 10;
		this.array = new int[capacity];
		this.count = 0;
	}

	/**
	 * Test whether the set is empty.
	 * 
	 * @return getCount() == 0
	 */
	public boolean isEmpty() {
		return (this.getCount() == 0);
	}

	/**
	 * Test whether a value is in the set
	 * 
	 * @return exists int v in getArray() such that v == value
	 */
	public boolean has(int value) {
		for(int i = 0; i < this.getCount(); i++){
			if(this.getArray()[i] == value){
				return true;
			}
		}
		return false;
	}

	/**
	 * Adds a value to the set.
	 * 
	 * @pre getCount() < getCapacity()
	 * @post has(value)
	 * @post !this@pre.has(value) implies (getCount() == this@pre.getCount() + 1)
	 * @post this@pre.has(value) implies (getCount() == this@pre.getCount())
	 */
	public void add(int value) {
		for(int i = 0; i < this.getCount(); i++){
			if(this.getArray()[i] == value){
				throw new UnsupportedOperationException ("This number is already in the set.");
			}
		}
		if(this.getCount() < this.getCapacity()){
			this.getArray()[this.getCount()] = value;
			this.count ++;
		}
		else{
			throw new UnsupportedOperationException ("Sorry, this set is full.");
		}
	}

	/**
	 * Removes a value from the set.
	 * 
	 * @post !has(value)
	 * @post this@pre.has(value) implies (getCount() == this@pre.getCount() - 1)
	 * @post !this@pre.has(value) implies (getCount() == this@pre.getCount())
	 */
	public void remove(int value) {
		if(this.has(value)){
			for(int i = 0; i < this.getCount(); i++){
				if(this.getArray()[i] == value){
					for(int j = i; j < this.getCount(); j++){
						this.getArray()[j] = this.getArray()[j+1];
					}
					this.count --;
					break;
				}
			}
		}
		else{
		throw new NoSuchElementException ("That element is not in this set.") ;
		}
	}

	/**
	 * Returns the intersection of this set and another set.
	 * 
	 * @param other
	 *            the set to intersect this set with
	 * @return the intersection
	 * @pre other != null
	 * @post forall int v: (has(v) and other.has(v)) implies return.has(v)
	 * @post forall int v: return.has(v) implies (has(v) and other.has(v))
	 */
	public IntSet intersect(IntSet other) {
		if(other == null){return new IntSet(10);}
		int newCapacity = (this.getCapacity() < other.getCapacity()) ? this.getCapacity() : other.getCapacity();
		IntSet newSet = new IntSet(newCapacity);
		
		for(int i = 0; i <this.getCount(); i++){
			for(int j = 0; j < other.getCount(); j++){
				if(this.getArray()[i] == other.getArray()[j]){
					newSet.add(this.getArray()[i]);
				}
			}
		}
		return newSet;
	}

	/**
	 * Returns the union of this set and another set.
	 * 
	 * @param other
	 *            the set to union this set with
	 * @return the union
	 * @pre other != null
	 * @post forall int v: has(v) implies return.has(v)
	 * @post forall int v: other.has(v) implies return.has(v)
	 * @post forall int v: return.has(v) implies (has(v) or other.has(v))
	 */
	public IntSet union(IntSet other) {
		if(other == null){return this;}
		IntSet newSet = new IntSet(other.getCount() + this.getCount());
		
		for(int i = 0; i <this.getCount(); i++){
				newSet.add(this.getArray()[i]);
		}
		
		for(int j = 0; j <other.getCount(); j++){
			if(!newSet.has(other.getArray()[j])){
				newSet.add(other.getArray()[j]);
			}
		}
		return newSet;
	}

	/**
	 * Returns a representation of this set as an array
	 * 
	 * @post return.length == getCount()
	 * @post forall int v in return: has(v)
	 */
	public int[] getArray() {
		return this.array;
	}

	/**
	 * Returns the number of elements in the set.
	 */
	public int getCount() {
		return this.count;
	}

	/**
	 * Returns the maximal number of elements in the set.
	 */
	public int getCapacity() {
		return this.capacity;
	}

	/**
	 * Returns a string representation of the set. The empty set is represented
	 * as {}, a singleton set as {x}, a set with more than one element like {x,
	 * y, z}.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		for(int i=0; i <this.getCount(); i++){
			sb.append(this.getArray()[i]);
			if(i < this.getCount() -1){
				sb.append(",");
			}
		}
		sb.append("}");
		return sb.toString();
	}

}