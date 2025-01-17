package 1337;

/**
 * MagicItems are items that can cause magic effects on other objects. So they are a source
 * of magic. As items they can be traded and they also can be the target of magic effects  
 */
public abstract class MagicItem implements Tradeable, MagicEffectRealization, MagicSource {
	/**
	 * Must not be null or empty
	 * */
	private String name;
	/**
	 * Number of usages remaining; must not be negative
	 */
	private int usages;
	/**
	 * Must not be negative
	 */
	private int price;
	/**
	 * must not be negative
	 */
	private int weight;

	/**
	 * @param name name
	 * @param usages number of usages still left
	 * @param price price
	 * @param weight weight
	 */
	//ctor
	public MagicItem(String name, int usages, int price, int weight) 
	{
		if(name == null)
		{
			throw new IllegalArgumentException("name must not be null. error thrown from MagicItem class from ctor");
		}
		if(name.isEmpty())
		{
			throw new IllegalArgumentException("name must not be empty or null. error thrown from MagicItem class from ctor");
		}
		if(usages < 0)
		{
			throw new IllegalArgumentException("usages must not be negative. error thrown from MagicItem class from ctor");
		}
		if(price < 0)
		{
			throw new IllegalArgumentException("price must not be negative. error thrown from MagicItem class from ctor");
		}
		if(weight < 0)
		{
			throw new IllegalArgumentException("weight must not be negative. error thrown from MagicItem class from ctor");
		}
		
		this.name = name;
		this.usages = usages;
		this.price = price;
		this.weight = weight;
	}

    /**
     * Returns value of usages (for access from deriving classes)
     * @return value of instance variable usages
     */
	public int getUsages() 
	{
		return this.usages;
	}

    /**
     * If usages > 0 reduce usage by 1 and return true, otherwise return false
     * @return returns true if usage is still possible
     */
	public boolean tryUsage() 
	{
		if(usages > 0)
		{
			--usages;
			return true;
		}
		return false;
	}

    /**
     * Returns "use" if usages is equal to 1, "uses" otherwise
     * @return "use" or "uses" depending on the value of usages
     */
	public String usageString() 
	{
		if(usages == 1)
		{
			return "use";
		}
		return "uses";
	}

    /**
     * returns empty string. Is overridden in deriving classes as needed
     * @return ""
     */
	public String additionalOutputString() 
	{
		return "";
	}

    /**
     * Formats this object according to
     * "['name'; 'weight' g; 'price' 'currencyString'; 'usages' 'usageString''additionalOutputString']"
     * 'currencyString' is "Knut" if price is 1, "Knuts" otherwise
     * e.g. (when additionalOutput() returns an empty string)
     * "[Accio Scroll; 1 g; 1 Knut; 5 uses]" or "[Alohomora Scroll; 1 g; 10 Knuts; 1 use]"
     * @return "['name'; 'weight' g; 'price' 'currencyString'; 'usages' 'usageString''additionalOutputString']"
     */
	@Override 
	public String toString() 
	{
		String s = null;
		
		//"[Accio Scroll; 1 g; 1 Knut; 5 uses]" or "[Alohomora Scroll; 1 g; 10 Knuts; 1 use]"
		s = "[" + this.name + "; " + this.weight + " g; " + this.price;
		if(this.price == 1) // == ?
		{
			s = s + " Knut; ";
		}
		else
		{
			s = s + " Knuts; ";
		}
		s = s + this.usages + " " + this.usageString() + additionalOutputString() + "]";
				
		return s;
	}

	//Tradeable Interface:

	/**
	 * Returns price of the object
	 * @return value of instance variable price
	 */
	@Override
	public int getPrice() 
	{
		return this.price;
	}

    /**
     * Returns weight of the object
     * @return value of instance variable weight
     */
	@Override    
	public int getWeight() 
	{
		return this.weight;
	}
	  
	//MagicSource Interface:

    /**
     * Always returns true; no Exceptions needed
     */
	@Override
	public boolean provideMana(MagicLevel levelNeeded, int amount) 
	{
		if(levelNeeded == null)
		{
			throw new IllegalArgumentException("levelNeeded must not be null. error thrown from class Magicitem from 'public boolean provideMana(MagicLevel levelNeeded, int amount)'");
		}
		if(amount < 0)
		{
			throw new IllegalArgumentException("levelNeeded must not be negative. error thrown from class Magicitem from 'public boolean provideMana(MagicLevel levelNeeded, int amount)'");
		}
		return true;
	}

	//MagicEffectRealization Interface:
	
	/**
	 * Reduce usages to usages*(1-percentage/100.)
	 */
	@Override
	public void takeDamagePercent(int percentage) 
	{
		if(percentage < 0)
		{
			throw new IllegalArgumentException("percentage must not be negative. error thrown from class MagicItem from 'public void takeDamagePercent(int percentage)'");
		}
		if(percentage > 100)
		{
			throw new IllegalArgumentException("percentage must not be above 100. error thrown from class MagicItem from 'public void takeDamagePercent(int percentage)'");
		}
		
		double usages_as_double = this.usages;
		usages_as_double = usages;
		usages_as_double = usages_as_double*(1-percentage/100.0);
		if(usages_as_double < 0)
		{
			usages_as_double = 0;
		}
		this.usages = (int) usages_as_double;
	}
	
	public String getName()
	{
		return this.name;
	}
}