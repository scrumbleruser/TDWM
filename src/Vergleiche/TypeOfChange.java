package Vergleiche;

public enum TypeOfChange {
	/**
	 * Neues Wissen
	 */
	BIG_NEW_KNOWLEDGE,
	MODERATE_NEW_KNOWLEDGE,
	SMALL_NEW_KNOWLEDGE,
	/**
	 * Wissensverbesserung
	 */
	BIG_IMPROVE_KNOWLEDGE,
	MODERATE_IMPROVE_KNOWLEDGE,
	SMALL_IMPROVE_KNOWLEDGE,
	/**
	 * Wissenskorrektur. Tritt auf, falls die betroffen Revisions nach Analyse mehr Zeilen hat als die "Basisrevisions"
	 */
	CORRECT_KNOWLEDGE,
	/**
	 * GRAMMATIK, RECHTSCHREIBFEHLER
	 */
	GRAMMAR,
	/**
	 * Formatierung
	 */
	FORMAT,
	/**
	 * Keine Änderung
	 */
	NO_CHANGE
}
