package cst;
import java.util.*;
public class arraylistgeneric {
	public static void main(String[] args){
		//Integer[] array1 = {1,2,3,4,1};
		ArrayList<Integer> array1 = new ArrayList<Integer>();
		array1.add(1);
		array1.add(2);
		array1.add(3);
		array1.add(4);
		array1.add(1);
		ArrayList<Number> array2 = new ArrayList<Number>();
		array2.add(1.2);
		array2.add(0);
		array2.add(12.0);
		array2.add(2.22222);
		array2.add(0.5);
		
		add(array1, array2);
		print(array2);
	}
	public static <T> void add(ArrayList<T> array1, ArrayList<? super T> array2){
		//while(!array1.isEmpty())
			//array2.add(array1);
		array2.addAll(0, array1);
	}
	public static void print(ArrayList<?> array){
		System.out.println(Arrays.toString(array.toArray()));
	}
}
