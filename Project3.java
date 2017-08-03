import java.util.ArrayList;

public class Project3 {
	
	public static void main(String[] args) {
		ArrayList<Integer> arrayInt = new ArrayList<Integer>();
		ArrayList<Number> arrayNum = new ArrayList<Number>();
		
		arrayInt.add(1);
		arrayInt.add(2);
		arrayInt.add(3);
		arrayInt.add(4);
		
		arrayNum.add(1.2);	
		arrayNum.add(0.5);
		arrayNum.add(67.76);
						
	
		System.out.println("Array of Integers: ");
		System.out.println(arrayInt);
		System.out.println("Array of Numbers: ");
		System.out.println(arrayNum); 
		
		add(arrayInt, arrayNum);
		
		System.out.println("Combined Array: ");
		print(arrayNum);

	}
	
	public static <T> void add(ArrayList<T> array1, ArrayList<? super T> array2) {	
		
		 for(int i=0; i<array1.size();i++){
			 array2.add(0,array1.get(i)); 
		 }
	}
	
	public static <T> void print(ArrayList<T> array){		
			System.out.println(array);			
	}
	
}
	




	



	