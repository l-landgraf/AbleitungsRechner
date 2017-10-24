// package funktionen.mehrParameter;
//
// import java.util.ArrayList;
// import java.util.List;
//
// import funktionen.Funktion;
// import funktionen.MehrParameterFunktion;
// import funktionen.keinParameter.Konstante;
//
// public class Subtraktion extends MehrParameterFunktion {
//
// public Subtraktion(Funktion... f) {
// super(f);
// }
//
// public Subtraktio n(List<Funktion> f) {
// super(f);
// }
//
// @Override
// public Funktion neu(List<Funktion> ersetzt) {
// return new Subtraktion(ersetzt);
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
// public Funktion zusammenFuegen() {
// Subtraktion add = new Subtraktion();
// for (Funktion funkt : this.f) {
// if (funkt instanceof Subtraktion) {
// add.f.addAll(((Subtraktion) funkt).f);
// } else {
// add.f.add(funkt);
// }
// }
// return add;
// }
//
// @Override
// public Funktion vereinfachen() {
// return umwandeln().vereinfachen();
// }
//
// private Addition umwandeln() {
// Addition add = new Addition();
// boolean first = true;
// for (Funktion f : this.f) {
// if (first) {
// add.f.add(f);
// first = false;
// } else {
// add.f.add(new Multiplikation(new Konstante(-1), f));
// }
// }
// return add;
// }
//
// @Override
// public Funktion kopieren() {
// List<Funktion> u = new ArrayList<>();
// for (Funktion t : this.f) {
// u.add(t.kopieren());
// }
// return new Subtraktion(u);
// }
//
// @Override
// public boolean gleich(Funktion f) {
// if (!(f instanceof Subtraktion)) {
// return false;
// }
//
// Subtraktion add = ((Subtraktion) f);
// if (add.f.size() != this.f.size()) {
// return false;
// }
//
// List<Funktion> liste = new ArrayList<>();
// for (Funktion funk : add.f) {
// liste.add(funk.kopieren());
// }
//
// label: for (Funktion funk : this.f) {
// for (int i = 0; i < add.f.size(); i++) {
// if (add.f.get(i).gleich(funk)) {
// liste.remove(add.f.get(i));
// continue label;
// }
// }
// return false;
// }
//
// return true;
// }
//
// @Override
// public double wert(double x) {
// double i = 0;
// for (Funktion f : this.f) {
// i -= f.wert(x);
// }
// return i;
// }
//
// }
