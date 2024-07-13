package 1337;

import java.lang.reflect.Array;

public class smoll_test 
{

	public static void main(String[] args) 
	{
		String s = "test";
		boolean first = true;
		String output = new String();
		
		for(char letter : s.toCharArray())
		{
			if(first == true)
			{
				first = false;
				letter = Character.toUpperCase(letter);
				output = output + letter;
			}
			else
			{
				output = output + letter;
			}
			
		}
		
		System.out.println(output);
		
	}

}
