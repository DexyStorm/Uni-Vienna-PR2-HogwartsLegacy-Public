package 1337;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 * Wizard class objects are the primary actors in the game. They can use and trade items, provide
 * magic energy, cast spells and also are affected be various magical effects.
 */
public class Wizard implements MagicSource, Trader, MagicEffectRealization {
	/**
	 * Not null not empty
	 */
	private String name; 
	/**
	 * Not null
	 * */
	private MagicLevel level;
	/**
	 * Not negative
	 */
	private int basicHP;
	/**
	 * Not negative; defaults to basicHP
	 */
	private int HP;
	/**
	 * Not less than the manapoints associated with the magic level
	 */
	private int basicMP;
	/**
	 * Not negative; defaults to basicMP
	 */
	private int MP;
	/**
	 * Not negative
	 */
	private int money;
	/**
	 * Not null, may be empty; use HashSet for instantiation
	 */
	private Set<Spell> knownSpells;
	/**
	 * Not null, may be empty; use HashSet for instantiation
	 */
	private Set<AttackingSpell> protectedFrom;
	/**
	 * Not negative
	 */
	private int carryingCapacity;
	/**
	 * Not null, may be empty, use HashSet for instantiation, total weight of inventory
	 * may never exceed carryingCapacity
	 */
	private Set<Tradeable> inventory;

	/**
	 * @param name name
	 * @param level the magic level (proficiency needed to cast spells)
	 * @param basicHP base for percentage health calculations
	 * @param HP current health 
	 * @param basicMP base for percentage mana calculations
	 * @param MP current mana
	 * @param money current money
	 * @param knownSpells set of known spells
	 * @param protectedFrom set of spells the object is protected against
	 * @param carryingCapacity maximum carrying capacity
	 * @param inventory set of items the object is currently carrying
	 */
	//ctor
	public Wizard(String name, MagicLevel level, int basicHP, int HP, int basicMP, int MP, int money,
	Set<Spell> knownSpells, Set<AttackingSpell> protectedFrom, int carryingCapacity,
	Set<Tradeable> inventory) 
	{
		if(name == null)
		{
			throw new IllegalArgumentException("name must not be null. error thrown from Wizard class from ctor");
		}
		if(name.isEmpty())
		{
			throw new IllegalArgumentException("name must not be empty. error thrown from Wizard class from ctor");
		}
		this.name = name;
		
		if(level == null)
		{
			throw new IllegalArgumentException("level must not be null. error thrown from Wizard class from ctor");
		}
		this.level = level;
		
		if(basicHP < 0)
		{
			throw new IllegalArgumentException("basicHP must not be negative. error thrown from Wizard class from ctor");
		}
		this.basicHP = basicHP;
		
		if(HP < 0)
		{
			//HP = basicHP; //hp defaults to basicHP ...?
			throw new IllegalArgumentException("HP must not be negative. error thrown from Wizard class from ctor");
		}
		this.HP = HP;
		
		
		if(basicMP < level.toMana())
		{
			//basicMP = level.MP;
			//or
			throw new IllegalArgumentException("basicMP must not be less then the manapoints of magic level. error thrown from Wizard class from ctor");
		}
		this.basicMP = basicMP;
	
		if(MP < 0)
		{
			throw new IllegalArgumentException("MP must not be negative. error thrown from Wizard class from ctor");
		}
		this.MP = MP;
		
		if(money < 0)
		{
			throw new IllegalArgumentException("money must not be negative. error thrown from Wizard class from ctor");
		}
		this.money = money;
		
		if(knownSpells == null)
		{
			throw new IllegalArgumentException("knownSpells must not be null. error thrown from Wizard class from ctor");
		}
		this.knownSpells = knownSpells;
		
		if(protectedFrom == null)
		{
			throw new IllegalArgumentException("protectedFrom must not be null. error thrown from Wizard class from ctor");
		}
		this.protectedFrom = protectedFrom;
		
		if(carryingCapacity < 0)
		{
			throw new IllegalArgumentException("carryingCapacity must not be negative. error thrown from Wizard class from ctor");
		}
		this.carryingCapacity = carryingCapacity;
		
		if(inventory == null)
		{
			throw new IllegalArgumentException("inventory must not be null. error thrown from Wizard class from ctor");
		}
		this.inventory = inventory;
		
		if(inventoryTotalWeight() > carryingCapacity)
		{
			throw new IllegalArgumentException("inventoryTotalWeight darf nicht groesser sein als carrying capacity. error thrown from Wizard class from ctor");
		}
	}
	//ctor ends here

	/**
	 * Return true, if HP is 0, false otherwise
	 * @return true, if HP is 0, false otherwise
	 */
	public boolean isDead() 
	{
		if(HP == 0)
		{
			return true;
		}
		return false;
	}  
	  
	/**
	 * Calculates and returns the total weight of all the items in the inventory
	 * @return total weight of all items in inventory
	 */
	private int inventoryTotalWeight() 
	{
		int sum = 0;
		
		for(Tradeable item : inventory)
		{
			sum = sum + item.getWeight();
		}
		
		return sum;
	}
	  
	/**
	 * If spell is null, IllegalArgumentException has to be thrown;
	 * if wizard is dead (isDead) no action can be taken and false is returned;
	 * add spell to the set of knownSpells;
	 * returns true, if insertion was successful, false otherwise.
	 * @param s spell to be learned
	 * @return true, if insertion was successful, false otherwise.
	 */
	public boolean learn(Spell s) 
	{
		if(s == null)
		{
			throw new IllegalArgumentException("spell must not be null. error thrown from Wizard class from 'public boolean learn(Spell s)'");
		}
		
		if(this.isDead() == true)
		{
			return false;
		}
		
		boolean successfully_added = knownSpells.add(s);
		if(successfully_added == true)
		{
			return true;
		}
		return false;
	}
	  
	/**
	 * If spell is null, IllegalArgumentException has to be thrown;
	 * if wizard is dead (isDead) no action can be taken and false is returned;
	 * remove spell from the set of knownSpells;
	 * returns true if removal was successful, false otherwise.
	 * @param s spell that the object is about to learn
	 * @return true, if removal was successful, false otherwise.
	 */
	public boolean forget(Spell s) 
	{
		if(s == null)
		{
			throw new IllegalArgumentException("spell must not be null. error thrown from Wizard class from 'public boolean learn(Spell s)'");
		}
		
		if(this.isDead() == true)
		{
			return false;
		}
		
		boolean removed = knownSpells.remove(s);
		
		if(removed == true)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * If s or target is null, IllegalArgumentException has to be thrown;
	 * if wizard is dead (isDead) no action can be taken and false is returned;
	 * if wizard does not know the spell, false is returned;
	 * call cast on s with this as source and parameter target as target
	 * return true, if cast was called;
	 * @param s spell to be cast
	 * @param target target of the spell to cast
	 * @return true, if cast was called, false otherwise;
	 */
	public boolean castSpell(Spell s, MagicEffectRealization target) 
	{
		if(s == null)
		{
			throw new IllegalArgumentException("s must not be null. error thrown from class Wizard from 'public boolean castSpell(Spell s, MagicEffectRealization target)'");
		}
		
		if(target == null)
		{
			throw new IllegalArgumentException("target must not be null. error thrown from class Wizard from 'public boolean castSpell(Spell s, MagicEffectRealization target)'");
		}
		
		if(this.isDead() == true)
		{
			return false;
		}
		
		if(this.knownSpells.contains(s))
		{
			s.cast(this, target);
			return true;
		}
		
		return false;
		
	}
	  
	/**
	 * If this object's knownSpells is empty, return false
	 * otherwise choose a random spell from knownSpells and delegate to
	 * castSpell(Spell, MagicEffectRealization)
	 * @param target target of the spell to cast
	 * @return false, if the object does not know a spell, otherwise the
	 * result of the delegation to castSpell
	 */
	public boolean castRandomSpell(MagicEffectRealization target) 
	{
		if(this.isDead())
		{
			return false;
		}
		if (this.knownSpells.isEmpty() == true)
		{
			return false;
		}
		
		//int size = knownSpells.size();
		int random_index = new Random().nextInt(knownSpells.size());
		Spell random_spell = null;
		int i = 0;
		for(Spell ks : knownSpells)
		{
			if(random_index == i)
			{
				random_spell = ks;
				break;
			}
			++i;
		}
		
		castSpell(random_spell, target);
		
		return true;
		//knownSpells
	}
	  
	/**
	 * If item or target is null, IllegalArgumentException has to be thrown;
	 * if wizard is dead (isDead) no action can be taken and false is returned;
	 * if wizard does not possess the item, false is returned;
	 * call useOn on the item with parameter target as target;
	 * return true, if useOn was called.
	 * @param item item to be used
	 * @param target target on which item is to be used on
	 * @return true, if useOn was called, false otherwise
	 */
	public boolean useItem(Tradeable item, MagicEffectRealization target) 
	{
		if(item == null)
		{
			throw new IllegalArgumentException("item must not be null. error thrown from class Wizard from 'public boolean useItem(Tradeable item, MagicEffectRealization target)'");
		}
		if(target == null)
		{
			throw new IllegalArgumentException("target must not be null. error thrown from class Wizard from 'public boolean useItem(Tradeable item, MagicEffectRealization target)'");
		}
		
		if(this.isDead())
		{
			return false;
		}
		
		if(this.possesses(item))
		{
			item.useOn(target);
			return true;
		}
		return false;
	}

	/**
	 * If this object's inventory is empty, return false;
	 * otherwise choose a random item from inventory and delegate to
	 * useItem(Tradeable, MagicEffectRealization).
	 * @param target target on which item is to be used on
	 * @return false, if the object does not possess any item, otherwise the
	 * result of the delegation to useItem
	 */
	public boolean useRandomItem(MagicEffectRealization target) 
	{
		if(this.inventory.isEmpty())
		{
			return false;
		}
		
		int random_index = new Random().nextInt(inventory.size());
		Tradeable random_item = null;
		int i = 0;
		for(Tradeable item : inventory)
		{
			if(random_index == i)
			{
				random_item = item;
				break;
			}
			++i;
		}
		
		this.useItem(random_item, target);
		return true;
	}
	  
	/**
	 * If item or target is null, IllegalArgumentException has to be thrown;
	 * if wizard is dead (isDead), no action can be taken and false is returned;
	 * call purchase on the item with this as seller and target as buyer;
	 * return true, if purchase was called successfully (returned true), false
	 * otherwise.
	 * @param item item to be sold
	 * @param target object the item is sold to (buyer)
	 * @return true, if purchase was called successfully (returned true), false
	 * otherwise.
	 */ 
	public boolean sellItem(Tradeable item, Trader target) 
	{
		if(item == null)
		{
			throw new IllegalArgumentException("item must not be null. error thrown from class Wizard from 'public boolean sellItem(Tradeable item, Trader target)'");
		}
		if(target == null)
		{
			throw new IllegalArgumentException("target must not be null. error thrown from class Wizard from 'public boolean sellItem(Tradeable item, Trader target)'");
		}
		
		if(this.isDead())
		{
			return false;
		}
		
		item.purchase(this, target);
		return true;
	}

	/**
	 * If this object's inventory is empty, return false,
	 * otherwise choose a random item from inventory and delegate to
	 * sellItem(Tradeable, MagicEffectRealization).
	 * @param target object the item is sold to (buyer)
	 * @return false, if the object does not possess any item, otherwise the
	 * result of the delegation to sellItem
	 */
	public boolean sellRandomItem(Trader target) 
	{
		if(this.inventory.isEmpty())
		{
			return false;
		}
		if(this.isDead())
		{
			return false;
		}
		
		int random_index = new Random().nextInt(inventory.size());
		Tradeable random_item = null;
		int i = 0;
		for(Tradeable item : inventory)
		{
			if(random_index == i)
			{
				random_item = item;
				break;
			}
			++i;
		}
		
		this.sellItem(random_item, target);
		return true;
		
	}

	/**
	 * Returns a string in the format
	 * "['name'('level'): 'HP'/'basicHP' 'MP'/'basicMP'; 'money' 'KnutOrKnuts'; knows 'knownSpells'; carries 'inventory']";
	 * where 'level' is the asterisks representation of the level
	 * (see MagicLevel.toString) and 'knownSpells' and 'inventory' use
	 * the default toString method of Java Set; 'KnutOrKnuts' is Knut
	 * if 'money' is 1, Knuts otherwise.
	 * E.g. [Ignatius(**): 70/100 100/150; 72 Knuts; knows [[Episkey(*): 5 mana; +20 HP], [Confringo: 10 mana; -20 HP]]; carries []]
	 * @return "['name'('level'): 'HP'/'basicHP' 'MP'/'basicMP'; 'money' 'KnutOrKnuts'; knows 'knownSpells'; carries 'inventory']"
	 */ 
	@Override
	public String toString() 
	{
		String s = "";
		s = s + "[" + this.name + "(" + this.level + "): " + this.HP + "/" + this.basicHP + " " + this.MP + "/" + this.basicMP + "; ";
		s = s + this.money;
		if(money == 1)
		{
			s = s + " Knut";
		}
		else
		{
			s = s + " Knuts";
		}
		s = s + "; knows " + knownSpells + "; carries " + inventory + "]";
		
		return s;
	}
	
	//MagicSource Interface
	
	/**
	 * If wizard is dead (isDead) no action can be taken and false is returned:
	 * check if level is at least levelNeeded, return false otherwise;
	 * if MP is less than manaAmount return false;
	 * subtract manaAmount from MP and return true.
	 * @param levelNeeded minimum magic level needed for the action 
	 * @param manaAmount amount of mana needed for the action 
	 * @return true, if mana can be successfully provided, false otherwise
	 */
	@Override
	public boolean provideMana(MagicLevel levelNeeded, int manaAmount) 
	{
		if(levelNeeded == null)
		{
			throw new IllegalArgumentException("levelNeeded must not be null. error thrown from class Wizard from 'public boolean provideMana(MagicLevel levelNeeded, int manaAmount)'");
		}
		if(manaAmount < 0)
		{
			throw new IllegalArgumentException("manaAmount must not be negative. error thrown from class Wizard from 'public boolean provideMana(MagicLevel levelNeeded, int manaAmount)'");
		}
		
		if(this.isDead())
		{
			return false;
		}
		
		if(this.level.toMana() >= levelNeeded.toMana()) //checks if the levels are fine
		{
			if(this.MP >= manaAmount) //checks if provider has enough mana
			{
				MP = MP - manaAmount;
				return true;
			}
		}
		return false;
	}

	//Trader Interface

	/**
	 * Return true, if the item is in the inventory, false otherwise
	 * @param item object is tested, if it possesses this item
	 * @return true, if the item is in the inventory, false otherwise
	 */
	@Override
	public boolean possesses(Tradeable item) 
	{
		if(item == null)
		{
			throw new IllegalArgumentException("item must not be null. error thrown from class Wizard from 'public boolean possesses(Tradeable item)'");
		}
		
		for(Tradeable i : inventory)
		{
			if(i == item)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Return true, if money is greater than or equal to amount, false otherwise
	 * @param amount object is tested, if it owns enough money to pay this amount
	 * @return true, if money is greater than or equal to amount, false otherwise
	 */
	@Override
	public boolean canAfford(int amount) 
	{
		if(amount < 0)
		{
			throw new IllegalArgumentException("amount must not be negative. error thrown from class Wizard from 'public boolean canAfford(int amount)'");
		}
		if(this.money >= amount)
		{
			return true;
		}
		return false;
	}

	/**
	 * Return true, if inventoryTotalWeight+weight is less than or equal to carryingCapacity, false otherwise
	 * @param weight test, if this weight can be added to object's inventory, without exceeding the
	 * carryingCapacity
	 * @return true, if inventoryTotalWeight+weight is less than or equal to carryingCapacity, false otherwise
	 */
	@Override
	public boolean hasCapacity(int weight) 
	{
		if(weight < 0)
		{
			throw new IllegalArgumentException("weight must not be negative. error thrown from class Wizard from 'public boolean hasCapacity(int weight)'");
		}
		
		if(this.inventoryTotalWeight() + weight <= this.carryingCapacity)
		{
			return true;
		}
		return false;
	}

	/**
	 * If wizard is dead (isDead) no action can be taken and false is returned;
	 * if this owns enough money deduct amount from money and return true,
	 * return false otherwise
	 * @param amount to be payed
	 * @return true, if payment succeeds, false otherwise
	 */
	@Override
	public boolean pay(int amount) 
	{
		if(amount < 0)
		{
			throw new IllegalArgumentException("amount must not be negative. error thrown from class Wizard from 'public boolean pay(int amount)'");
		}
		if(this.isDead())
		{
			return false;
		}
		
		if(this.money >= amount)
		{
			money = money - amount;
			return true;
		}
		return false;
	}
	    
	/**
	 * If wizard is dead (isDead), no action can be taken and false is returned;
	 * add amount to this object's money and return true.
	 * @param amount amount to be received
	 * @return true, if reception succeeds, false otherwise 
	 */
	@Override
	public boolean earn(int amount) 
	{
		if(amount < 0)
		{
			throw new IllegalArgumentException("amount must not be negative. error thrown from class Wizard from 'public boolean earn(int amount)'");
		}
		if(this.isDead())
		{
			return false;
		}
		
		this.money = this.money + amount;
		return true;
	}
	    
	/**
	 * Add item to inventory if carryingCapacity is sufficient.
	 * returns true, if item is successfully added, false otherwise
	 * (carrying capacity exceeded or item is already in the inventory)
	 * @param item item to be added to object's inventory
	 * @return true. if item is successfully added, false otherwise
	 */
	@Override
	public boolean addToInventory(Tradeable item) 
	{
		if(item == null)
		{
			throw new IllegalArgumentException("item must nut be null. error thrown from class Wizard from 'public boolean addToInventory(Tradeable item)'");
		}
		
		//check if weight is okay
		if(this.inventoryTotalWeight() + item.getWeight() > this.carryingCapacity)
		{
			return false;
		}
		
		//check if item is in inventory
		for(Tradeable i : inventory)
		{
			if(i == item)
			{
				return false;
			}
		}
		
		//add item
		this.inventory.add(item);
		return true;
	}

	/**
	 * Remove item from inventory.
	 * returns true, if item is successfully removed, false otherwise
	 * (item not in the inventory).
	 * @param item item to be removed from object's inventory
	 * @return true, if item is successfully removed, false otherwise
	 */
	@Override
	public boolean removeFromInventory(Tradeable item) 
	{
		if(item == null)
		{
			throw new IllegalArgumentException("item must not be null. error thrown from class Wizard from 'public boolean removeFromInventory(Tradeable item)'");
		}
		boolean removed = inventory.remove(item);
		
		if(removed == true)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Returns true, if this object's HP are not 0 (alive wizard).
	 * @return true, if the object is alive
	 */
	@Override
	public boolean canSteal() 
	{
		if(this.isDead())
		{
			return false;
		}
		return true;
	}
	
	/**
	 * If thief is null, IllegalArgumentException has to be thrown;
	 * if thief cannot steal (canSteal returns false) no action can be taken
	 * and false is returned;
	 * returns false if, the object's inventory is empty;
	 * otherwise transfers a random item from the this object's inventory into
	 * the thief's inventory;
	 * if the thief's inventory has not enough capacity, the object just vanishes
	 * and false is returned;
	 * returns true, if theft was successful.
	 * @param thief object that is stealing the item from the this-object.
	 * @return true, if theft was successful
	 */
	@Override
	public boolean steal(Trader thief) 
	{
		if(thief == null)
		{
			throw new IllegalArgumentException("thief must not be null. error thrown from class Wizard from 'public boolean steal(Trader thief)'");
		}
		
		if(thief.canSteal() == false)
		{
			return false;
		}
		
		if(this.inventory.isEmpty() == true)
		{
			return false;
		}
		
		//transfers a random item from the this object's inventory into thiefs inventory
		int random_index = new Random().nextInt(this.inventory.size());
		Tradeable random_item = null;
		int i = 0;
		for(Tradeable item : this.inventory)
		{
			if(random_index == i)
			{
				random_item = item;
				break;
			}
			++i;
		}
		
		if(this.removeFromInventory(random_item) && thief.addToInventory(random_item))
		{
			return true;
		}
		return false;
	}

	/**
	 * Returns true if, this object's HP are 0 (dead wizard)
	 * @return true if the object is dead
	 */
	@Override
	public boolean isLootable() 
	{
		if(this.isDead())
		{
			return true;
		}
		return false;
	}

	/**
	 * Returns true if this object's HP are not 0 (alive wizard)
	 * @return true, if the object is alive
	 */
	@Override
	public boolean canLoot() 
	{
		if(this.isDead())
		{
			return false;
		}
		return true;
	}
	
	/**
	 * If looter is null, IllegalArgumentException has to be thrown;
	 * if looter cannot loot (canLoot returns false), no action can be taken
	 * and false is returned;
	 * if the this object can be looted (isLootable), transfer all the items
	 * in the object's inventory into the looter's inventory;
	 * items that don't fit in the looter's inventory because auf the weight
	 * limitation just vanish.
	 * returns true, if at least one item was successfully transferred, false
	 * otherwise.
	 * @param looter object that is looting this-object.
	 * @return true, if looting was successful, false otherwise
	 */
	@Override
	public boolean loot(Trader looter)
	{
		if(looter == null)
		{
			throw new IllegalArgumentException("looter must not be null. error thrown from class Wizard from 'public boolean loot(Trader looter)'");
		}
		
		if(looter.canLoot() == false)
		{
			return false;
		}
		
		if(this.isLootable())
		{
			boolean looted = false;
			boolean looted_at_least_once = false;
			for(Tradeable item : this.inventory)
			{
				looted = looter.addToInventory(item);
				if(looted == true)
				{
					looted_at_least_once = true;
				}
			}
			this.inventory.clear();
			
			if(looted_at_least_once == true)
			{
				return true;
			}
			return false;
		}
		
		return false;
	}
	  
	//MagicEffectRealization Interface
	
	/**
	 * Reduce the object's HP by amount ensuring however that HP does not
	 * become negative.
	 * @param amount amount to be deducted from health 
	 */
	@Override
	public void takeDamage(int amount) 
	{
		if(amount < 0)
		{
			throw new IllegalArgumentException("amount must not be negative. error thrown from class Wizard from 'public void takeDamage(int amount)'");
		}
		this.HP = this.HP - amount;
		
		if(this.HP < 0)
		{
			this.HP = 0;
		}
	}
	    
	/**
	 * Reduce the object's HP by the percentage given of the object's basic
	 * HP value ensuring however, that HP does not become negative.
	 * Do calculations in double truncating to int only for the assignment
	 * @param percentage percentage of damage done
	 */
	@Override
	public void takeDamagePercent(int percentage) 
	{
		if(percentage < 0)
		{
			throw new IllegalArgumentException("percentage must not be negative. error thrown from class Wizard from 'public void takeDamagePercent(int percentage)'");
		}
		if(percentage > 100)
		{
			throw new IllegalArgumentException("percentage must not be over 100. error thrown from class Wizard from 'public void takeDamagePercent(int percentage)'");
		}
		
		//output = input*(1-percentage/100)		
		
		double dmg_output = (this.basicHP*(percentage/100.0));
		this.HP = this.HP - (int) dmg_output;
		if(this.HP < 0)
		{
			this.HP = 0;
		}
	}
	    
	/**
	 * Reduce the object's MP by amount ensuring however that MP does not
	 * become negative.
	 * @param amount amount to be deducted from mana 
	 */
	@Override
	public void weakenMagic(int amount) 
	{
		if(amount < 0)
		{
			throw new IllegalArgumentException("amount must not be negative. error thrown from class Wizard from 'public void weakenMagic(int amount)'");
		}
		
		this.MP = this.MP - amount;
		
		if(this.MP < 0)
		{
			this.MP = 0;
		}
	}
	  
	/**
	 * Reduce the object's MP by the percentage given of the object's basic
	 * MP value ensuring however, that MP does not become negative.
	 * Do calculations in double truncating to int only for the assignment
	 * @param percentage percentage of damage done
	 */
	@Override
	public void weakenMagicPercent(int percentage) 
	{
		if(percentage < 0)
		{
			throw new IllegalArgumentException("percentage must be over 0. error thrown from class Wizard from 'public void weakenMagicPercent(int percentage)'");
		}
		if(percentage > 100)
		{
			throw new IllegalArgumentException("percentage must be under 100. error thrown from class Wizard from 'public void weakenMagicPercent(int percentage)'");
		}
		//output = input*(1-percentage/100)
		double mp_output = (this.basicMP*(percentage/100.0));
		//System.out.println("mp_output is: " + mp_output);
		this.MP = this.MP - (int) mp_output;
		if(this.MP < 0)
		{
			this.MP = 0;
		}
	}
	  
	/**
	 * Increase the object's HP by the amount given.
	 * @param amount amount to increase health
	 */
	@Override
	public void heal(int amount) 
	{
		if(amount < 0)
		{
			throw new IllegalArgumentException("amount must not be negative. error thrown from class Wizard from 'public void heal(int amount)'");
		}
		this.HP = this.HP + amount;
	}
	    
	/**
	 * Increase the object's HP by the percentage given of the object's
	 * basic HP. Do calculations in double truncating to int only for
	 * the assignment
	 * @param percentage percentage of healing done
	 */
	@Override
	public void healPercent(int percentage) 
	{
		if(percentage < 0)
		{
			throw new IllegalArgumentException("percentage must be over 0. error thrown from class Wizard from 'public void healPercent(int percentage)'");
		}
		if(percentage > 100)
		{
			throw new IllegalArgumentException("percentage must be under 100. error thrown from class Wizard from 'public void healPercent(int percentage)'");
		}
		//System.out.println(percentage);
		double output = (this.basicHP/100.0)*percentage+this.HP;
		if(output < 0)
		{
			output = 0;
		}
		
		this.HP = (int) output;
	}

	/**
	 * Increase the object's MP by the amount given.
	 * @param amount amount to increase mana
	 */
	@Override
	public void enforceMagic(int amount) 
	{
		if(amount < 0)
		{
			throw new IllegalArgumentException("amount must not be negative. error thrown from class Wizard from 'public void enforceMagic(int amount)'");
		}
		
		this.MP = this.MP + amount;
	}
	  
	/**
	 * Increase the object's MP by the percentage given of the object's
	 * basic MP. Do calculations in double truncating to int only for
	 * the assignment
	 * @param percentage percentage of mana increase
	 */
	@Override
	public void enforceMagicPercent(int percentage) 
	{
		if(percentage < 0)
		{
			throw new IllegalArgumentException("percentage must be over 0. error thrown from class Wizard from 'public void healPercent(int percentage)'");
		}
		if(percentage > 100)
		{
			throw new IllegalArgumentException("percentage must be under 100. error thrown from class Wizard from 'public void healPercent(int percentage)'");
		}
		//output = input*(1-percentage/100)
		double mp_as_double = ((this.basicMP/100.0)*percentage)+this.MP;
		if(mp_as_double < 0)
		{
			mp_as_double = 0;
		}
		this.MP = (int) mp_as_double;
	}
	    
	/**
	 * Return true, if s is contained in instance variable protectedFrom
	 * @param s spell that is tested for
	 * @return true, if object is protected against spell s, false otherwise
	 */
	@Override
	public boolean isProtected(Spell s) 
	{
		if(s == null)
		{
			throw new IllegalArgumentException("s must not be null. error thrown from class Wizard from 'public boolean isProtected(Spell s)'");
		}
		
		for(AttackingSpell sp : protectedFrom)
		{
			if(sp == s)
			{
				return true;
			}
		}
		return false;
	}
	    
	/**
	 * Add all spells from attacks to instance variable protectedFrom
	 * @param attacks spells against which protection is provided
	 */
	@Override
	public void setProtection(Set<AttackingSpell> attacks) 
	{
		if(attacks == null)
		{
			throw new IllegalArgumentException("attacks must not be null. error thrown from class Wizard from 'public void setProtection(Set<AttackingSpell> attacks)'");
		}
		
		for(AttackingSpell spell : attacks)
		{
			protectedFrom.add(spell);
		}
	}

	/**
	 * Remove all spells from 'attacks' from instance variable protectedFrom
	 * @param attacks spells against which protection is removed 
	 */
	@Override
	public void removeProtection(Set<AttackingSpell> attacks) 
	{
		if(attacks == null)
		{
			throw new IllegalArgumentException("attacks must not be null. error thrown from class Wizard from 'public void removeProtection(Set<AttackingSpell> attacks)'");
		}
		for(AttackingSpell attack : attacks)
		{
			protectedFrom.remove(attack); 	//dunno if this works or if i need another loop
		}									//for(AttackingSpell protedcted : protectedFrom)
	}										//to check if the 2 spells are the same and then remove it
	
	public int getNumberOfKnownSpells()
	{
		return this.knownSpells.size();
	}
	
	public void senseAttack(Wizard target)
	{
		for(Spell s : this.knownSpells)
		{
			if(s.isAttack())
			{
				if(target.isProtected(s) == false)
				{
					castSpell(s, target);
				}
			}
		}
	}
}







