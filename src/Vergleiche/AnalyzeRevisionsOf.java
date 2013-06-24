package Vergleiche;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;

import API.GetRevision;
import API.WikiBot;
import SQL.Mysql_connect;
import View.SQLPanel;

public class AnalyzeRevisionsOf {

	private Article article;

	public AnalyzeRevisionsOf(Article article) {
		this.article = article;
	}
	public AnalyzeRevisionsOf() {
	}

	public void execute() throws IOException {

		for (int i = 1; i < article.getRevisions().size(); i++) {
			revisionToList(article.getRevisions().get(i-1));
			revisionToList(article.getRevisions().get(i));
			
			if (differentFileSize(article.getRevisions().get(i-1).getFile(),
					article.getRevisions().get(i).getFile())) {

				splitStringsToWordsAndCountMaxWords(article.getRevisions().get(
						i - 1));
				splitStringsToWordsAndCountMaxWords(article.getRevisions().get(
						i));

				checkLines(article.getRevisions().get(i-1), article
						.getRevisions().get(i));
				splitStringsToWords(article.getRevisions().get(i - 1), article
						.getRevisions().get(i));
				checkWords(article.getRevisions().get(i - 1), article
						.getRevisions().get(i));
				removeEmptyLines(article.getRevisions().get(i - 1), article
						.getRevisions().get(i));
				analyze(article.getRevisions().get(i - 1), article
						.getRevisions().get(i));

				// ///////////////////////////////////////
				// Ausgaben Beginn //
				// ///////////////////////////////////////
/**				System.out
						.println("-------------------------------------------------\n"
								+ "Dissimilar lines: " + article.getRevisions().get(i-1).getFile().getPath() + ": "
								+ article.getRevisions().get(i-1).getLines()
										.size()
								+ "\n-------------------------------------------------");
				for (int m = 0; m < article.getRevisions().get(i-1)
						.getLines().size(); m++) {
					System.out.println(m
							+ ". Dissimilar line: " + article.getRevisions().get(i-1).getFile().getPath() + ": "
							+ article.getRevisions().get(i - 1).getLines()
									.get(m));
				}

				System.out
						.println("-------------------------------------------------\n"
								+ "Dissimilar lines: " + article.getRevisions().get(i).getFile().getPath() + ": " 
								+ article.getRevisions().get(i).getLines()
										.size()
								+ "\n-------------------------------------------------");

				for (int m = 0; m < article.getRevisions().get(i).getLines()
						.size(); m++) {
					System.out.println(m + ". Dissimilar line: " + article.getRevisions().get(i).getFile().getPath() + ": "
							+ article.getRevisions().get(i).getLines().get(m));
				}
*/
				System.out
						.println("###################################################################################");

				System.out.println("Revisions " + (i - 1) + ": Zeilenanzahl: " + article.getRevisions().get(i-1).getFile().getPath() + ": "
						+ article.getRevisions().get(i - 1).getLines().size());
				System.out.println("Revisions "
						+ (i - 1)
						+ ": Anzahl Unique words: " + article.getRevisions().get(i-1).getFile().getPath() + ": "
						+ article.getRevisions().get(i - 1).getUniqueWords()
								.size());

				System.out
						.println("###################################################################################");

				System.out.println("Revisions " + i + ": Zeilenanzahl: " + article.getRevisions().get(i).getFile().getPath() + ": "
						+ article.getRevisions().get(i).getLines().size());
				System.out
						.println("Revisions "
								+ i
								+ " Anzahl Unique words: " + article.getRevisions().get(i).getFile().getPath() + ": "
								+ article.getRevisions().get(i)
										.getUniqueWords().size());

				System.out
						.println("###################################################################################");

				// ///////////////////////////////////////
				// Ausgaben Ende //
				// ///////////////////////////////////////

				System.out.println("maxLine List 1: "
						+ article.getRevisions().get(i-1).getFile().getPath()
						+ " : " + article.getRevisions().get(i-1).getMaxLines());

				System.out.println("maxLine List 2: "
						+ article.getRevisions().get(i).getFile().getPath()
						+ " : " + article.getRevisions().get(i).getMaxLines());

				System.out.println("maxWords List 1: "
						+ article.getRevisions().get(i-1).getFile().getPath()
						+ " : " + article.getRevisions().get(i-1).getMaxWords());

				System.out.println("maxWords List 2: "
						+ article.getRevisions().get(i).getFile().getPath()
						+ " : " + article.getRevisions().get(i).getMaxWords());
				
				System.out.println("Change type -> List 1: "
						+ article.getRevisions().get(i-1).getFile().getPath()
						+ " : "
						+ article.getRevisions().get(i-1).getTypeOfChange());
				
				System.out.println("Change type -> List 2: "
						+ article.getRevisions().get(i).getFile().getPath()
						+ " : " + article.getRevisions().get(i).getTypeOfChange());
			} else {
				System.out.println("Keine Änderung festgestellt");
			}
		}

	}

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

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

	private void revisionToList(Revision rev) throws IOException {
		BufferedReader input = new BufferedReader(new FileReader(rev.getFile()
				.getPath()));
		String string = input.readLine();
		Revision revision = rev;
		while ((string != null)) {
			revision.getLines().add(string);
			string = input.readLine();
		}
		revision.setMaxLines(revision.getLines().size());
	}

	// Hinweis: Leerzeichen können auch als Wörter gezählt werden!!!
	private void splitStringsToWordsAndCountMaxWords(Revision revision) {
		ArrayList<String[]> temp1 = new ArrayList<String[]>();
		int counter = 0;
		for (int j = 0; j < revision.getLines().size(); j++)
			temp1.add(revision.getLines().get(j).split(" "));
		for (int j = 0; j < temp1.size(); j++) {
//			revision.setMaxWords(temp1.get(j).length);
			counter += temp1.get(j).length;
		}
		
		revision.setMaxWords(counter);
		
	} // Ende splitStringsToWordsAndCountMaxWords

	/**
	 * Remove similar lines from the two compared revisions
	 * @param revisions
	 */
	private void checkLines(Revision revision1, Revision revision2) {
	ArrayList<String> list = new ArrayList<String>();
	
		for (int j = 0; j < revision1.getLines().size(); j++) {
			for (int k = 0; k < revision2.getLines().size(); k++) {
				if (revision1.getLines().get(j).equals(revision2.getLines().get(k))) {
					list.add(revision1.getLines().get(j));
				}
			}
		}
		revision1.getLines().removeAll(list);
		revision2.getLines().removeAll(list);		
	} // Ende checkLines

	/**
	 * Remove similar words from the two compared revisions
	 * @param revisions
	 */
	private void splitStringsToWords(Revision revision1, Revision revision2) {
		ArrayList<String[]> list1 = new ArrayList<String[]>();
		ArrayList<String[]> list2 = new ArrayList<String[]>();
		for (int j = 0; j < revision1.getLines().size(); j++) {
			list1.add(revision1.getLines().get(j).split(" "));
		}
		for (int j = 0; j < revision2.getLines().size(); j++) {
			list2.add(revision2.getLines().get(j).split(" "));
		}
		revision1.setWords(list1);
		revision2.setWords(list2);
	} // Ende splitStringsToWords

	/**
	 * 
	 * @param revision1
	 * @param revision2
	 */
	private void checkWords(Revision revision1, Revision revision2) {
		ArrayList<String> list1 = new ArrayList<String>();
		ArrayList<String> list2 = new ArrayList<String>();
		ArrayList<String> similar = new ArrayList<String>();
		for (int j = 0; j < revision1.getWords().size(); j++) {
			for (int k = 0; k < revision1.getWords().get(j).length; k++) {
				list1.add(revision1.getWords().get(j)[k]);
			}
		}
		for (int j = 0; j < revision2.getWords().size(); j++) {
			for (int k = 0; k < revision2.getWords().get(j).length; k++) {
				list2.add(revision2.getWords().get(j)[k]);
			}
		}
		for (int k = 0; k < list1.size(); k++) {
			for (int l = 0; l < list2.size(); l++) {
				if (list1.get(k).equals(list2.get(l))) {
					similar.add(list1.get(k)); //list1.remove(k);
//					list2.remove(l);
//					l--;
				}
			}
		}
		list1.removeAll(similar);
		list2.removeAll(similar);
		revision1.setUniqueWords(list1);
		revision2.setUniqueWords(list2);

		// Ab hier to Delete
//		for (int x = 0; x < revision1.getUniqueWords().size(); x++)
//			System.out.println("Unique Words: -> List1: "
//					+ revision1.getUniqueWords().get(x));
//		for (int y = 0; y < revision2.getUniqueWords().size(); y++)
//			System.out.println("Unique Words: ->List2: "
//					+ revision2.getUniqueWords().get(y));
	} // Ende checkWords

	private void removeEmptyLines(Revision revision1, Revision revision2) {
		for (int j = 0; j < revision1.getLines().size(); j++) {
			if ((revision1.getLines().get(j).equals(" "))
					|| (revision1.getLines().get(j).isEmpty())) {
				revision1.getLines().remove(j);
			}
		}
		for (int j = 0; j < revision2.getLines().size(); j++) {
			if ((revision2.getLines().get(j).equals(" "))
					|| (revision2.getLines().get(j).isEmpty())) {
				revision2.getLines().remove(j);
			}
		}
	}

	private boolean differentListSize(ArrayList<String> list1,
			ArrayList<String> list2) {
		if (list1.size() != list2.size())
			return true;
		return false;
	}

	private void analyze(Revision revision1, Revision revision2) {
		if (differentListSize(revision1.getLines(), revision2.getLines())) {
			
			if(revision1.getLines().size() != 0) {
			if (correctKnowledgeChange(revision1.getLines(),
					revision2.getLines())) {
				revision2.setTypeOfChange(TypeOfChange.CORRECT_KNOWLEDGE);
//				article.getTypeOfChange().add(TypeOfChange.CORRECT_KNOWLEDGE);
			}
			
			if (improveKnowledge5pChange(revision1.getLines(),
					revision2.getLines(), revision1.getUniqueWords(),
					revision2.getUniqueWords())) {
				revision2.setTypeOfChange(TypeOfChange.SMALL_IMPROVE_KNOWLEDGE);
//				article.getTypeOfChange().add(TypeOfChange.SMALL_IMPROVE_KNOWLEDGE);
			}
			if (improveKnowledge10pChange(revision1.getLines(), 
					revision2.getLines(), revision1.getUniqueWords(),
					revision2.getUniqueWords())) {
				revision2.setTypeOfChange(TypeOfChange.MODERATE_IMPROVE_KNOWLEDGE);
//				article.getTypeOfChange().add(TypeOfChange.MODERATE_IMPROVE_KNOWLEDGE);
			}
			if (improveKnowledge15pChange(revision1.getLines(),
					revision2.getLines(), revision1.getUniqueWords(),
					revision2.getUniqueWords())) {
				revision2.setTypeOfChange(TypeOfChange.BIG_IMPROVE_KNOWLEDGE);
//				article.getTypeOfChange().add(TypeOfChange.BIG_IMPROVE_KNOWLEDGE);
			}

			if (newKnowledge20pChange(revision1.getLines(),
					revision2.getLines(), revision1.getUniqueWords(),
					revision2.getUniqueWords())) {
				revision2.setTypeOfChange(TypeOfChange.SMALL_NEW_KNOWLEDGE);
//				article.getTypeOfChange().add(TypeOfChange.SMALL_NEW_KNOWLEDGE);
			}
			if (newKnowledge30pChange(revision1.getLines(),
					revision2.getLines(), revision1.getUniqueWords(),
					revision2.getUniqueWords())) {
				revision2.setTypeOfChange(TypeOfChange.MODERATE_NEW_KNOWLEDGE);
//				article.getTypeOfChange().add(TypeOfChange.MODERATE_NEW_KNOWLEDGE);
			}
//			if (newKnowledge100pChange(revision1.getLines(),
//					revision2.getLines(), revision1.getUniqueWords(),
//					revision2.getUniqueWords())) {
//				revision2.setTypeOfChange(TypeOfChange.BIG_NEW_KNOWLEDGE);
////				article.getTypeOfChange().add(TypeOfChange.BIG_NEW_KNOWLEDGE);
//			}
			

		} else {
			revision2.setTypeOfChange(TypeOfChange.NO_CHANGE);
//			article.getTypeOfChange().add(TypeOfChange.NO_CHANGE);
		}
		} else {
			revision2.setTypeOfChange(TypeOfChange.BIG_NEW_KNOWLEDGE);
		}
	}

	// Change types
	private boolean correctKnowledgeChange(ArrayList<String> base,
			ArrayList<String> next) {
		if (base.size() > next.size())
			return true;
		return false;
	}

	private boolean improveKnowledge5pChange(ArrayList<String> base, ArrayList<String> next, 
			ArrayList<String> baseWords, ArrayList<String> nextWords) {
		double percent = (next.size() * 100) / base.size();
		if (base.size() < next.size() && baseWords.size() < nextWords.size()
				&& (percent <= 5.0 && percent > 1.0))
			return true;
		return false;
	}

	private boolean improveKnowledge10pChange(ArrayList<String> base,
			ArrayList<String> next, ArrayList<String> baseWords,
			ArrayList<String> nextWords) {
		double percent = (next.size() * 100) / base.size();
		if (base.size() < next.size() && baseWords.size() < nextWords.size()
				&& (percent <= 10.0 && percent > 5.0))
			return true;
		return false;
	}

	private boolean improveKnowledge15pChange(ArrayList<String> base,
			ArrayList<String> next, ArrayList<String> baseWords,
			ArrayList<String> nextWords) {
		double percent = (next.size() * 100) / base.size();
		if (base.size() < next.size() && baseWords.size() < nextWords.size()
				&& (percent <= 15.0 && percent > 10.0))
			return true;
		return false;
	}
	
	private boolean newKnowledge20pChange(ArrayList<String> base,
			ArrayList<String> next, ArrayList<String> baseWords,
			ArrayList<String> nextWords) {
		double percent = (next.size() * 100) / base.size();
		if (base.size() < next.size() && baseWords.size() < nextWords.size()
				&& (percent <= 20.0 && percent > 15.0))
			return true;
		return false;
	}

	private boolean newKnowledge30pChange(ArrayList<String> base,
			ArrayList<String> next, ArrayList<String> baseWords,
			ArrayList<String> nextWords) {
		double percent = (next.size() * 100) / base.size();
		if (base.size() < next.size() && baseWords.size() < nextWords.size()
				&& (percent <= 30.0 && percent > 20.0))
			return true;
		return false;
	}

	private boolean newKnowledge100pChange(ArrayList<String> base,
			ArrayList<String> next, ArrayList<String> baseWords,
			ArrayList<String> nextWords) {
		double percent = (next.size() * 100) / base.size();
		if (base.size() < next.size() && baseWords.size() < nextWords.size()
				&& (percent > 30.0))
			return true;
		return false;
	}
	
	
	// Ab hier Methoden um die Fragen zubeantworten --------------------------------------------------------------------
	
	/**
	 * 
	 * @return all change types of the Revisions
	 */
	public ArrayList<TypeOfChange> getAllChangeTypes() {
		ArrayList<TypeOfChange> types = new ArrayList<TypeOfChange>();
		for(int i = 1; i < this.article.getRevisions().size(); i++) {
			types.add(this.article.getRevisions().get(i).getTypeOfChange());
		}
		return types;
	}
	/**
	 * Alle TypeOfChanges eines Users in ALLE Revisionen EINES Artikels werden zurückgegeben
	 * @param user
	 * @return type of changes
	 */
	public ArrayList<TypeOfChange> getAllTypeOfChangesOf(String user) {
		ArrayList<TypeOfChange> types = new ArrayList<TypeOfChange>();
		for(int i = 1; i < this.article.getRevisions().size(); i++) {
			if(this.article.getRevisions().get(i).getAuthor().getName().equals(user))
				types.add(this.article.getRevisions().get(i).getTypeOfChange());

		}
		return types;
	}
	
	
	/**
	 * UserType in EINEM Artikel
	 * @param user
	 * @return User type
	 */
	public UserType getTypeOf(String user) {
		ArrayList<TypeOfChange> userChangeTypes = getAllTypeOfChangesOf(user);
		int correcting = 0;
		int improving = 0;
		int newKnowledge = 0;
	
		for (int i = userChangeTypes.size(); i < 0; i--) {
				System.out.println("Usertyp size: " + userChangeTypes.size());
			if (userChangeTypes.get(i).equals(TypeOfChange.CORRECT_KNOWLEDGE))
				correcting++;
			if (userChangeTypes.get(i).equals(TypeOfChange.BIG_IMPROVE_KNOWLEDGE))
				improving++;
			if (userChangeTypes.get(i).equals(TypeOfChange.MODERATE_IMPROVE_KNOWLEDGE))
				improving++;
			if (userChangeTypes.get(i).equals(TypeOfChange.SMALL_IMPROVE_KNOWLEDGE))
				improving++;
			if (userChangeTypes.get(i).equals(TypeOfChange.BIG_NEW_KNOWLEDGE))
				newKnowledge++;
			if (userChangeTypes.get(i).equals(TypeOfChange.MODERATE_NEW_KNOWLEDGE))
				newKnowledge++;
			if (userChangeTypes.get(i).equals(TypeOfChange.SMALL_NEW_KNOWLEDGE))
				newKnowledge++;
		}
		
		if(correcting > improving && correcting > newKnowledge) {
			return UserType.KORREKTUR;
		} else if (improving > correcting && improving > newKnowledge) {
			return UserType.VERBESSERUNG;
		} else if(newKnowledge > correcting && newKnowledge > improving) {
			return UserType.NEUES_WISSEN;
		} else {
			return UserType.KEINE_KLASSIFIZIERUNG;
		}
	}
	
	/**
	 * Alle Autoren/User von ALLEN Revisionsen EINES Artikels
	 * @return authors
	 */
	public ArrayList<User> getAllAuthorsOfAllRevisions() {
		ArrayList<User> authors = new ArrayList<User>();
		for(int i=0; i<this.article.getRevisions().size(); i++) {
			authors.add(this.article.getRevisions().get(i).getAuthor());
		}
		return authors;
	}
	
}
