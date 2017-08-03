package cst;

import java.util.*;
import java.io.File;
public class directoryqueue {
	public static void main(String[] args) {
		// Prompt the user to enter a directory or a file
				System.out.print("Enter a directory or a file: ");
				Scanner input = new Scanner(System.in);
				String directory = input.nextLine();
				// Display the size
				System.out.println(getSize(new File(directory)) + " bytes");
			}
	public static long getSize(File file) {
		long size = 0; 
		Queue<File> queue = new LinkedList<File>();
		queue.offer(file);
		
		while(!queue.isEmpty()){
			File t=queue.poll();
			if(t.isFile())
				size += t.length();
			else {
				queue.offer(t);
			}
		}
		return size;
	}
}
