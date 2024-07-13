package 1337;

/**
 * Tradeable objects can be traded and stored in inventories 
 */
public interface Tradeable {
    /**
     * Returns price of the object
     * @return price of the object
     */
	int getPrice();

    /**
     * Returns weight of the object
     * @return weight of the object
     */
	int getWeight();

    /**
     * Caution: this method transfers the item from from's inventory to to's inventory
     * without any checks. It has to be ensured that all necessary conditions for the
     * transfer are met before calling this function-
     * The default implementation calls removeFromInventory on from and addToInventory
     * on to and returns true if both calls succeeded (returned true)
     * @param from object is taken from from's inventory
     * @param to object is transferre to to's inventory
     * @return true, if transfer succeeds, false otherwise
     */
	private boolean transfer(Trader from, Trader to) 
	{
		if(from.removeFromInventory(this) && to.addToInventory(this))
		{
			return true;
		}
		return false;
	}

    /**
     * If giver or taker is null or they are the same object,
     * an IllegalArgumentException must be thrown;
     * <p>
     * giver gives the object away for free. Default implementation checks if the giver
     * has the object (possesses method) and the taker has enough capacity in the
     * inventory (hasCapacity). If any of these checks fail, the method returns false.
     * <p>
     * Otherwise the item is transferred from the giver's inventory to the taker's
     * inventory (transfer method) and the return value of the transfer call is returned
     * @param giver the one who gives the object away
     * @param taker the one who receives the object
     * @return true, if transfer was successful, false otherwise
     */
	default boolean give(Trader giver, Trader taker) 
	{
		if(giver == null)
		{
			throw new IllegalArgumentException("giver must not be null. error thrown from Tradable interface from 'default boolean give(Trader giver, Trader taker)'");
		}
		if(taker == null)
		{
			throw new IllegalArgumentException("taker must not be null. error thrown from Tradable interface from 'default boolean give(Trader giver, Trader taker)'");
		}
		if(giver == taker)
		{
			throw new IllegalArgumentException("giver is same person as taker. error thrown from Tradable interface from 'default boolean give(Trader giver, Trader taker)'");
		}
		
		if(taker.hasCapacity(this.getWeight()) && giver.possesses(this))
		{
			if(this.transfer(giver, taker) == true)
			{
				return true;
			}
			return false;
		}
		return false;
	}

    /**
     * If seller or buyer is null or they are the same object, an IllegalArgumentException
     * must be thrown;
     * <p>
     * default implement)ation checks if the seller has the object (possesses method), the
     * buyer can afford the object (canAfford method) and the buyer has enough capacity
     * in the inventory (hasCapacity). If any of these checks fail, the method returns
     * false.
     * <p>
     * Otherwise the buyer pays the price (pay method), the seller receives the price paid
     * (earn method), The item is transferred from the seller's inventory to the buyer's
     * inventory (transfer method) and the return value of the transfer call is returned
     * @param seller the one who sells the object
     * @param buyer the one who buys the object
     * @return true, if the purchase is successful, false otherwise
     */
	default boolean purchase(Trader seller, Trader buyer) 
	{
		if(seller == null)
		{
			throw new IllegalArgumentException("seller must not be null. error thrown from Tradable interface from 'default boolean purchase(Trader seller, Trader buyer)'");
		}
		if(buyer == null)
		{
			throw new IllegalArgumentException("buyer must not be null. error thrown from Tradable interface from 'default boolean purchase(Trader seller, Trader buyer)'");
		}
		if(seller == buyer)
		{
			throw new IllegalArgumentException("giver is same person as taker. error thrown from Tradable interface from 'default boolean purchase(Trader seller, Trader buyer)'");
		}
		
		if(seller.possesses(this) && buyer.canAfford(this.getPrice()) && buyer.hasCapacity(this.getWeight()))
		{
			if(this.transfer(seller, buyer) && buyer.pay(this.getPrice()) && seller.earn(this.getPrice()))
			{
				return true;
			}
			return false;
		}
		return false;
	}

    /**
     * If target is null, an IllegalArgumentException must be thrown;
     * use the object on the target
     * @param target target of the object's usage (which will typically inflicted by the magic
     * effects)
     */
	void useOn(MagicEffectRealization target);
}