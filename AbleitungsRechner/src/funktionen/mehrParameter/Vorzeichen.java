package funktionen.mehrParameter;

public enum Vorzeichen {
	PLUS(" + "), MINUS(" - ");

	final public String zeichen;

	private Vorzeichen(String zeichen) {
		this.zeichen = zeichen;
	}
}
