import java.io.Serializable;

public class Cards {

    private String name;
    private int number;
    //number values are:
    // 0 is death (only 1 card)
    // 1 is neutral (7 cards)
    // 2 is BLUE (8 cards)
    // 3 is RED (9 cards)

    private boolean isPlayed = false;

    public String getName() { return name; }
    public int getNumber() { return number; }

    public void setName(String name) { this.name = name; }
    public void setNumber(int number) { this.number = number; }
    public void setPlayed(boolean played) { isPlayed = played; }
}

/*public class Word implements Serializable {
    String text;
    int number = 0;
    boolean hah = false;

    Word() {
        text = "hej";
    }

    public void setHah(boolean hah) { this.hah = hah;
    }

    public String getText() {
        return text;
    }
} */
