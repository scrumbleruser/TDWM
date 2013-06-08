package Vergleiche;

public enum TypeOfChange {
	BIG,	// Grosse Aenderung
	SMALL,		// Kleine Aenderung
	GRAMMAR,	// GRAMMATIK, RECHTSCHREIBFEHLER
	CHARACTER, 		// ZEICHENFEHLER
	PARAGRAPH_NEW,		// ABSATZ hinzufuegen
	PARAGRAPH_EDIT,		// ABSATZ editieren
	PARAGRAPH_REMOVE	// ABSATZ entfernen
}
