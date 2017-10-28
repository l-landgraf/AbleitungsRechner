package funktionen.mehrParameter;

public enum Vorzeichen {
	PLUS(" + ") {
		@Override
		public Vorzeichen negatiev() {
			return MINUS;
		}
	},
	MINUS(" - ") {
		@Override
		public Vorzeichen negatiev() {
			return PLUS;
		}
	};
	
	final public String zeichen;
	
	private Vorzeichen(String zeichen) {
		this.zeichen = zeichen;
	}
	
	public abstract Vorzeichen negatiev();
}
