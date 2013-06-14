package Vergleiche;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Changes {

	private Article article;
	private ArrayList<Revision> revisions;
	private ArrayList<String> oldRevision = new ArrayList<String>();
	private ArrayList<String> newRevision = new ArrayList<String>();

	/**
	 * Wörter des zu untersuchendes Strings
	 */
	private int wordsCounter = 0;
	private int similarWordsCounter = 0;
	private int dissimilarWordsCounter = 0;
	private int newWordsCounter = 0;
	private int newLinesCounter = 0;
	private int stringsCounter = 0;
	private int similarStringsCounter = 0;
	private int dissimilarStringsCounter = 0;
	private int charChangesCounter = 0;

	private int counter = 0;

	public Changes(Article article) {
		this.article = article;
	}

	public void execute() throws IOException {

		// File file = new File("res/berlin2");
		// File file2 = new File("res/berlin1");

		revisions = article.getRevisions();

		for (int i = 1; i < revisions.size(); i++) {

			if (differentFileSize(revisions.get(i - 1).getFile(), revisions
					.get(i).getFile())) {
				/**
				 * System.out.println("Größenunterschied");
				 * System.out.println("File 1 -> " + revisions.get(i -
				 * 1).getFile().getName() + ": " + getFileSize(revisions.get(i -
				 * 1).getFile())); System.out.println("File 2 -> " +
				 * revisions.get(i).getFile().getName() + ": " +
				 * getFileSize(revisions.get(i).getFile()));
				 */

				BufferedReader inputOld = new BufferedReader(new FileReader(
						revisions.get(i - 1).getFile().getPath()));
				BufferedReader inputNew = new BufferedReader(new FileReader(
						revisions.get(i).getFile().getPath()));

				String oldLine = null;
				String newLine = null;

				while (((oldLine = inputOld.readLine()) != null)) {
					oldRevision.add(oldLine);
				}

				while ((newLine = inputNew.readLine()) != null) {
					newRevision.add(newLine);
				}

				for (int j = 0; j < isSmallestList(oldRevision, newRevision)
						.size(); j++) {
					if ((!oldRevision.get(j).equals(newRevision.get(j))) && 
							(oldRevision.get(j).length() != newRevision.get(j).length())) {
						int subcounter = 0;
						String[] oldWord = oldRevision.get(j).split(" ");
						String[] newWord = newRevision.get(j).split(" ");
						for (int k = 0; k < getSizeOfSmallestString(
								oldWord.length, newWord.length); k++) {
							if (!oldWord[k].equals(newWord[k]))
								dissimilarWordsCounter++;

							subcounter++;
						}

						for (int k = subcounter; k < getSizeOfBiggestString(
								oldWord.length, newWord.length); k++) {
							dissimilarWordsCounter++;
							newWordsCounter++;
						}
					}
					counter = j;
				}

				for (int j = counter; j < isBiggestList(oldRevision,
						newRevision).size(); j++) {
					newLinesCounter++;
//					System.out.println(isBiggestList(oldRevision,newRevision).get(j));
					String[] words = getBiggestString(oldRevision.get(j), newRevision.get(j)).split(" ");
					for (int k = 0; k < words.length; k++) { 
						newWordsCounter++;
					}
				}

				counter = 0;

				System.out.println("Old Revision Lines: " + oldRevision.size());
				System.out.println("New Revision Lines: " + newRevision.size());
				System.out.println("DissimilarWordCount: " + dissimilarWordsCounter);
				System.out.println("StringCount: " + stringsCounter);
				System.out.println("SimilarStringCount: " + similarStringsCounter);
				System.out.println("DissimilarStringCount: " + dissimilarStringsCounter);
				System.out.println("CharChangeCount: " + charChangesCounter);
				System.out.println("NewWord: " + newWordsCounter);
				System.out.println("NewLinesCounter: " + newLinesCounter);

			} else {
				System.out.println("Keine Änderung festgestellt!!!");
			}

		}

	}

	private ArrayList<String> isSmallestList(ArrayList<String> oldList,
			ArrayList<String> newList) {
		if (oldList.size() < newList.size())
			return oldList;
		return newList;
	}

	private ArrayList<String> isBiggestList(ArrayList<String> oldList,
			ArrayList<String> newList) {
		if (oldList.size() < newList.size())
			return oldList;
		return newList;
	}

//	private String[] splitString(String str) {
//		String[] splitted = str.split(" ");
//		return splitted;
//	}

	// ***************************************************************************************************************
	// File Size
	public boolean differentFileSize(File file1, File file2) throws IOException {
		byte[] b1 = getBytesFromFile(file1);
		byte[] b2 = getBytesFromFile(file2);
		if (b1.length != b2.length)
			return true;
		return false;
	}

	public static byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);
		long length = file.length();

		if (length > Integer.MAX_VALUE) {
			// File is too large
		}
		byte[] bytes = new byte[(int) length];

		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file "
					+ file.getName());
		}
		is.close();
		return bytes;
	}

	public Integer getFileSize(File file) throws IOException {
		byte[] b1 = getBytesFromFile(file);
		return b1.length;
	}

	private Integer getSizeOfBiggestString(int str1, int str2) {
		if (str1 > str2)
			return str1;
		return str2;
	}

	private Integer getSizeOfSmallestString(int str1, int str2) {
		if (str1 < str2)
			return str1;
		return str2;
	}
	
	private String getBiggestString(String str1, String str2) {
		if (str1.length() > str2.length())
			return str1;
		return str2;
	}

}
