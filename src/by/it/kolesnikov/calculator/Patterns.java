package by.it.kolesnikov.calculator;

public interface Patterns {
    String OPERATION = "[-+/*]";
    String SCALAR ="-?[0-9]+(\\.[0-9]+)?";
    String VECTOR ="\\{"+SCALAR+"?(,"+SCALAR+")*}";
    String MATRIX ="\\{"+VECTOR+"?(,"+SCALAR+")*}";
}