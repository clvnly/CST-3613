import java.io.*;

public class DirectoryNumofFiles {

	
	 public static void main(String[] args) {
			System.out.print("Enter a directory: ");
			java.util.Scanner input = new java.util.Scanner(System.in);
			String s = input.nextLine();
			try {
				//getNumofFile(s);
				System.out.println("Total = " + getFilesCount(new File(s)));
			}
			catch (IOException ex) {
				System.out.println(ex.toString());
			}		    
	  }

	   public static int getFilesCount(File file) 
			   throws java.io.FileNotFoundException {
		   if (!file.exists())
				throw new java.io.FileNotFoundException(file + " not found");		   
		   File[] files = file.listFiles();
		   int localcount = 0;
		   int count = 0;
		   for (File f : files)
		   {
		     if (f.isDirectory()) 
		    	 count += getFilesCount(f);
		     else
		     {
		    	 localcount++;
		    	 count++;
		     }     
		   }
		   System.out.println(file.getPath() + " = " + localcount);
		   return count;
	 }
}