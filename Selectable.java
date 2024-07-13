package 1337;

public interface Selectable {
	
	int getSelectionValue();
	
	default boolean select(int digit)
	{
		String value_as_string = Integer.toString(getSelectionValue());
		String digit_as_string = Integer.toString(digit);
		
		if(value_as_string.contains(digit_as_string))
		{
			return true;
		}
		return false;
	}
}
