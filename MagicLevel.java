package 1337;

/**
 * Defines the various magic levels.
 * wizards have a magic level.
 * in order to be able to use specific spells a minimum magic level is necessary.
 * Note: the compiler generated default constructor may not be sufficient for your implementation
 */
public enum MagicLevel 
{
	NOOB(50) 
	{
		public String toString()
		{
			return "*";
		}
	},
	ADEPT(100)
	{
		public String toString()
		{
			return "**";
		}
	},
	STUDENT(200)
	{
		public String toString()
		{
			return "***";
		}
	},
	EXPERT(500)
	{
		public String toString()
		{
			return "****";
		}
	},
	MASTER(1000)
	{
		public String toString()
		{
			return "*****";
		}
	};

	//instance variable
	private int MP;
	
	//ctor
	MagicLevel(int MP) 
	{
		this.MP = MP;
	}
	
	//ugh... idk
	public int toMana()
	{
		return MP;
	}
}