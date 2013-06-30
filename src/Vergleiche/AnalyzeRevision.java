package Vergleiche;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/**
 * Analyzation of revisions. Two revision getting compared to eachother to find differences 
 * @author Shimal
 *
 */
public class AnalyzeRevision {

	private Revision revision1;
	private Revision revision2;
	
	/**
	 * Constrcutor. Define the parameters
	 * @param revision1 the first revision
	 * @param revision2 the second revision
	 * @throws IOException
	 */
	public AnalyzeRevision(Revision revision1, Revision revision2)
			throws IOException {
		this.revision1 = revision1;
		this.revision2 = revision2;
		execute();
	}
	
	/**
	 * Executes the necessary methods to start the analyzation process
	 * @throws IOException
	 */
	private void execute() throws IOException {
		revisionToList(revision1);
		revisionToList(revision2);
		removeSimilarLines(revision1, revision2);
		splitStringsToWords(revision1);
		splitStringsToWords(revision2);
		checkWords(revision1, revision2);
		analyze(revision1, revision2);

	}

	/**
	 * Put the content of revisin's file to a list
	 * 
	 * @param revision
	 * @throws IOException
	 */
	private void revisionToList(Revision revision) throws IOException {
		@SuppressWarnings("resource")
		BufferedReader input = new BufferedReader(new FileReader(revision
				.getFile().getPath()));
		String string = input.readLine();
		Revision rev = revision;
		while ((string != null)) {
			if (!string.equals(""))
				rev.getLines().add(string);
			string = input.readLine();
		}
		rev.setMaxLines(rev.getLines().size());
	}

	/**
	 * Remove similar lines from the two compared revisions
	 * 
	 * @param revision1
	 * @param revision2
	 * @throws IOException
	 */
	private void removeSimilarLines(Revision revision1, Revision revision2)
			throws IOException {
		ArrayList<String> list = new ArrayList<String>();
		for (int j = 0; j < revision1.getLines().size(); j++)
			for (int k = 0; k < revision2.getLines().size(); k++)
				if (revision1.getLines().get(j)
						.equals(revision2.getLines().get(k)))
					list.add(revision1.getLines().get(j));

		revision1.getLines().removeAll(list);
		revision2.getLines().removeAll(list);
	}

	/**
	 * Split strings to words
	 * @param revisions
	 */
	private void splitStringsToWords(Revision revision) {
		ArrayList<String[]> list1 = new ArrayList<String[]>();
		for (int j = 0; j < revision.getLines().size(); j++)
			list1.add(revision.getLines().get(j).split(" "));
		revision.setWords(list1);
	}

	/**
	 * Remove similar words from the two compared revisions
	 * @param revision1
	 * @param revision2
	 */
	private void checkWords(Revision revision1, Revision revision2) {
		ArrayList<String> list1 = new ArrayList<String>();
		ArrayList<String> list2 = new ArrayList<String>();
		ArrayList<String> similar = new ArrayList<String>();
		for (int j = 0; j < revision1.getWords().size(); j++)
			for (int k = 0; k < revision1.getWords().get(j).length; k++)
				list1.add(revision1.getWords().get(j)[k]);
		for (int j = 0; j < revision2.getWords().size(); j++)
			for (int k = 0; k < revision2.getWords().get(j).length; k++)
				list2.add(revision2.getWords().get(j)[k]);
		for (int k = 0; k < list1.size(); k++)
			for (int l = 0; l < list2.size(); l++)
				if (list1.get(k).equals(list2.get(l)))
					similar.add(list1.get(k));
		list1.removeAll(similar);
		list2.removeAll(similar);
		revision1.setUniqueWords(list1);
		revision2.setUniqueWords(list2);
	}

	/**
	 * Analyze the revisions and set the type of change
	 * @param revision1
	 * @param revision2
	 */
	private void analyze(Revision revision1, Revision revision2) {
		if (((revision1.getLines().size() != revision2.getLines().size()) || (revision1
				.getUniqueWords().size() != revision2.getUniqueWords().size()))
				&& (revision1.getLines().size() != 0 || revision1
						.getUniqueWords().size() != 0)) {
			if (correctRevision(revision1.getLines(), revision2.getLines(),
					revision1.getUniqueWords(), revision2.getUniqueWords())) {
				revision2.setTypeOfChange(TypeOfChange.KORREKTUR);
				revision2.getAuthor().setTypeOfChange(TypeOfChange.KORREKTUR);
			} else if (improveRevision(revision1.getLines(),
					revision2.getLines(), revision1.getUniqueWords(),
					revision2.getUniqueWords())) {
				revision2.setTypeOfChange(TypeOfChange.VERBESSERUNG);
				revision2.getAuthor().setTypeOfChange(TypeOfChange.VERBESSERUNG);
			} else if (produceKnowledgeInRevision(revision1.getLines(),
					revision2.getLines(), revision1.getUniqueWords(),
					revision2.getUniqueWords())) {
				revision2.setTypeOfChange(TypeOfChange.WISSENSPRODUKTION);
				revision2.getAuthor().setTypeOfChange(TypeOfChange.WISSENSPRODUKTION);
			} else if (reworkRevision(revision1.getLines(),
					revision2.getLines(), revision1.getUniqueWords(),
					revision2.getUniqueWords())) {
				revision2.setTypeOfChange(TypeOfChange.UEBERARBEITUNG);
				revision2.getAuthor().setTypeOfChange(TypeOfChange.UEBERARBEITUNG);
			}
		} else if (((revision1.getLines().size() != revision2.getLines().size()) || (revision1
				.getUniqueWords().size() != revision2.getUniqueWords().size()))
				&& (revision1.getLines().size() == 0 && revision1
						.getUniqueWords().size() == 0)) {

			double sizeW = revision2.getMaxWords() - revision1.getMaxWords();
			double sizeL = revision2.getMaxLines() - revision1.getMaxLines();
			if ((calcPercent(sizeW, revision1.getMaxWords()) <= 15.0)
					|| (calcPercent(sizeL, revision1.getMaxLines()) <= 15.0)) {
				revision2.setTypeOfChange(TypeOfChange.VERBESSERUNG);
				revision2.getAuthor().setTypeOfChange(TypeOfChange.VERBESSERUNG);
			} else if (((calcPercent(sizeW, revision1.getMaxWords()) > 15.0) && (calcPercent(
					sizeW, revision1.getMaxWords()) < 100.0))
					|| ((calcPercent(sizeL, revision1.getMaxLines()) <= 15.0) && (calcPercent(
							sizeL, revision1.getMaxLines()) < 100.0))) {
				revision2.setTypeOfChange(TypeOfChange.WISSENSPRODUKTION);
				revision2.getAuthor().setTypeOfChange(TypeOfChange.WISSENSPRODUKTION);
			} else if ((calcPercent(sizeW, revision1.getMaxWords()) > 100.0)
					|| (calcPercent(sizeL, revision1.getMaxLines()) > 100.0)) {
				revision2.setTypeOfChange(TypeOfChange.UEBERARBEITUNG);
				revision2.getAuthor().setTypeOfChange(TypeOfChange.UEBERARBEITUNG);
			}
		} else if (((revision1.getLines().size() == revision2.getLines().size()) && (revision1
				.getUniqueWords().size() == revision2.getUniqueWords().size()))
				&& (revision1.getLines().size() != 0 || revision1
						.getUniqueWords().size() != 0)) {
			revision2.setTypeOfChange(TypeOfChange.FORMATIERUNG);
			revision2.getAuthor().setTypeOfChange(TypeOfChange.FORMATIERUNG);
		} else {
			revision2.setTypeOfChange(TypeOfChange.KEINE_AENDERUNG);
			revision2.getAuthor().setTypeOfChange(TypeOfChange.KEINE_AENDERUNG);
		}
	}

	// Help methods
	private double calcPercent(double v1, double maxWords) {
		double p = (v1 / maxWords) * 100;
		return p;
	}

	// Change types
	private boolean correctRevision(ArrayList<String> base,
			ArrayList<String> next, ArrayList<String> baseWords,
			ArrayList<String> nextWords) {
		if (   ((base.size() > next.size())
				&& (baseWords.size() > nextWords.size())) || ((base.size() > next.size())
						|| (baseWords.size() > nextWords.size())))
			return true;
		return false;
	}

	private boolean improveRevision(ArrayList<String> base,
			ArrayList<String> next, ArrayList<String> baseWords,
			ArrayList<String> nextWords) {
		if(baseWords.size() != 0 && base.size() != 0) {
			double percent1 = (next.size() / base.size()) * 100;
			double percent2 = (nextWords.size() / baseWords.size()) * 100;
			if ((percent1 <= 15.0 && percent1 > 0.0) || (percent2 <= 15.0 && percent2 > 0.0))
				return true;
		} else {
			double percent1 = (next.size() / notZero(base.size(), baseWords.size())) * 100;
			if (percent1 <= 15.0 && percent1 > 0.0)
				return true;
		}
		return false;
	}
	
	private double notZero(double value1, double value2) {
		if(value1 == 0)
			return value2;
		return value1;
	}
	
	private boolean produceKnowledgeInRevision(ArrayList<String> base,
			ArrayList<String> next, ArrayList<String> baseWords,
			ArrayList<String> nextWords) {
		if(baseWords.size() != 0 && base.size() != 0) {
			double percent1 = (next.size() / base.size()) * 100;
			double percent2 = (nextWords.size() / baseWords.size()) * 100;
			if ((percent1 <= 100.0 && percent1 > 15.0) || (percent2 <= 100.0 && percent2 > 15.0))
				return true;
		} else {
			double percent1 = (next.size() / notZero(base.size(), baseWords.size())) * 100;
			if (percent1 <= 100.0 && percent1 > 15.0)
				return true;
		}
		return false;
	}

	private boolean reworkRevision(ArrayList<String> base,
			ArrayList<String> next, ArrayList<String> baseWords,
			ArrayList<String> nextWords) {
		if(baseWords.size() != 0 && base.size() != 0) {
			double percent1 = (next.size() / base.size()) * 100;
			double percent2 = (nextWords.size() / baseWords.size()) * 100;
			if (percent1 > 100.0 || percent2 > 100.0)
				return true;
		} else {
			double percent1 = (next.size() / notZero(base.size(), baseWords.size())) * 100;
			if (percent1 > 100.0)
				return true;
		}
		return false;
	}

}
