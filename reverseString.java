package cst;
import java.util.*;

public class reverseString {

	public static void main(String[] args) {
		int count;
		Scanner input = new Scanner(System.in);
		System.out.print("Enter string: ");
		String a = input.nextLine();
		count=a.length();
		System.out.print(reverseDisplay(a));
	}

	public static String reverseDisplay(String a){
		char b = a.charAt(a.length()-1);
		if(a.length() == 1)
			return Character.toString(b);   

		return b + reverseDisplay(a.substring(0,a.length()-1));
	}
}
