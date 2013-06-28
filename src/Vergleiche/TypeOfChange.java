package Vergleiche;
/**
 * Defines the type of changes
 * @author Shimal
 */
public enum TypeOfChange {
	/**
	 * Previous revision get corrected through removing lines or words 
	 */
	KORREKTUR,
	/**
	 * The actual revision get reformatted 
	 */
	FORMATIERUNG,
	/**
	 * The actual revision get improved through adding lines or words 
	 */
	VERBESSERUNG,
	/**
	 * The actual revision get new knowledge through adding lines or words 
	 */
	WISSENSPRODUKTION,
	/**
	 * The actual revision get reworked through adding a lot of lines or words 
	 */
	UEBERARBEITUNG,
	/**
	 * The actual revision and the previous revision do not indicate any changes
	 */
	KEINE_AENDERUNG
}
