
import java.io.FileNotFoundException;
import java.util.*;
public class sortfile {

	public static void main(String[] args) throws FileNotFoundException {
		java.io.File file = new java.io.File("input.txt"); //file location is eclipse's project folder. "C:\Users\[computer name]\workspace\[project name]\"
		Scanner input = new Scanner(file);
		
		List<String> list = new ArrayList<String>();
		//List<String> List1 = Arrays.asList(input.next());
		while(input.hasNext()){
			list.add(input.next());
		}
		input.close();
		System.out.println("Unsorted list:");
		System.out.println(list);
		System.out.println("Sorted list:");
		sort(list);
	}
	public static void sort(List<String> sortedlist){
		Collections.sort(sortedlist);
		System.out.println(sortedlist);
	}

}
