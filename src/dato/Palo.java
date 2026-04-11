package dato;

public enum Palo {
    TREBOLES("Clubs"),
    DIAMANTES("Diamonds"),
    CORAZONES("Hearts"),
    PICAS("Spades");

    private final String prefijo;
    Palo(String prefijo) { this.prefijo = prefijo; }
    public String getPrefijo() { return prefijo; }
}
