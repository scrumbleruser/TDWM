package Vergleiche;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Changes { 

	Article article;
	ArrayList<Revision> revisions;

	public Changes(Article article) {
		this.article = article;
	}

	// public static void main(String[] args) {
	// // TODO Auto-generated method stub
	//
	// }

	public void execute() throws IOException {

//		BufferedReader in = new BufferedReader(new FileReader("res/berlin2"));
//		BufferedReader in2 = new BufferedReader(new FileReader("res/berlin1"));
		File file = new File("res/berlin2");
		File file2 = new File("res/berlin1");

		revisions = article.getRevisions();
		
		for (int i = 1; i < revisions.size(); i++) {

			if (checkSize(revisions.get(i-1).getFile(), revisions.get(i).getFile())) {
				System.out.println("Größenunterschied");
				System.out.println("File 1 -> " + revisions.get(i-1).getFile().getName() + ": " + getFileSize(revisions.get(i-1).getFile()));
				System.out.println("File 2 -> " + revisions.get(i).getFile().getName() + ": " + getFileSize(revisions.get(i).getFile()));
				
			}
			
			BufferedReader in = new BufferedReader(new FileReader(revisions.get(i-1).getFile().getPath()));
			BufferedReader in2 = new BufferedReader(new FileReader(revisions.get(i).getFile().getPath()));
			
			String zeile1 = null;
			String zeile2 = null;
			String a = null;
			String b = null;
			while (((a = in.readLine()) != null)
					&& ((b = in2.readLine()) != null)) {
				zeile1 = in.readLine();
				zeile2 = in2.readLine();
				System.out.println("Berlin 2: Gelesene Zeile: " + zeile1);
				System.out.println("Berlin 1: Gelesene Zeile: " + zeile2);
				if (similarLine(zeile1, zeile2)) {
					System.out.println("Identisch!!!");
				} else {
					System.out.println("NICHT Identisch!!!");

				}
				splitString(zeile1);
				splitString(zeile2);

				for (int j = 0; j < splitString(zeile1).length; j++)
					if (splitString(zeile1)[j].equals(splitString(zeile2)[j])) {
						System.out.println(splitString(zeile1)[j]);
						System.out.println(splitString(zeile2)[j]);
						System.out.println("Identische Wörter!!!");
					} else {
						System.out.println(splitString(zeile1)[j]);
						System.out.println(splitString(zeile2)[j]);
						System.out
								.println("Nicht Identisch!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					}

			}

		}
	}

	private boolean isNotChar(char c) {
		if ((c <= 'z' && c >= 'a') || (c <= '9' && c >= '0'))
			return false;
		return true;

	}

	private static boolean similarLine(String str1, String str2) {
		if (str1.equals(str2))
			return true;
		return false;
	}

	private static boolean similarWord(String word1, String word2) {
		if (word1.equals(word2))
			return true;
		return false;
	}

	private static String[] splitString(String str) {
		String[] splitted = str.split(" ");
		// for (int i = 0; i < splitted.length; i++) {
		// System.out.println("splitted[" + i + "] = '" + splitted[i]
		// + "'");
		// }
		return splitted;
	}

	private static boolean grammarChange(String word1, String word2) {
		if (word1.equals(word2))
			return true;
		return false;
	}

	private static boolean charChange(String str) {

		return false;
	}

	/**
	 * Quelle:
	 * http://stackoverflow.com/questions/7355638/java-comparing-bytes-in
	 * -files-that-have-weird-contents
	 * 
	 * @param file
	 * @return The bytes of a file.
	 * @throws IOException
	 */
	public static byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);

		// Get the size of the file
		long length = file.length();

		// You cannot create an array using a long type.
		// It needs to be an int type.
		// Before converting to an int type, check
		// to ensure that file is not larger than Integer.MAX_VALUE.
		if (length > Integer.MAX_VALUE) {
			// File is too large
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file "
					+ file.getName());
		}

		// Close the input stream and return bytes
		is.close();
		return bytes;
	}

	public static boolean checkSize(File file1, File file2) throws IOException {
		byte[] b1 = getBytesFromFile(file1);
		byte[] b2 = getBytesFromFile(file2);
		if (b1.length != b2.length)
			return true;
		return false;
	}
	
	public Integer getFileSize(File file) throws IOException {
		byte[] b1 = getBytesFromFile(file);
		return b1.length;
	}

}
