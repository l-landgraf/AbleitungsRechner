// package funktionen.einParameter;
//
// import funktionen.EinParameterFunktion;
// import funktionen.Funktion;
// import funktionen.keinParameter.Konstante;
// import funktionen.mehrParameter.Multiplikation;
//
// public class Negation extends EinParameterFunktion {
//
// public Negation(Funktion x) {
// super(x);
// }
//
// @Override
// public String zeichen() {
// return " - ";
// }
//
// @Override
// public Funktion ableitung() {
// return umwandeln().ableitung();
// }
//
// @Override
// public Funktion vereinfachen() {
// return umwandeln().vereinfachen();
// }
//
// private Multiplikation umwandeln() {
// return new Multiplikation(new Konstante(-1), this.a.kopieren());
// }
//
// @Override
// protected Funktion neu(Funktion a) {
// return new Negation(a);
// }
//
// @Override
// public Funktion kopieren() {
// return new Negation(this.a.kopieren());
// }
//
// @Override
// public boolean gleich(Funktion f) {
// return f instanceof Negation && ((Negation) f).a.gleich(this.a);
// }
//
// @Override
// public double wert(double x) {
// return -1 * this.a.wert(x);
// }
// }
