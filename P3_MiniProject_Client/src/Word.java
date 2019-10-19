import java.io.Serializable;

public class Word implements Serializable {
    String text;
    int number = 1;
    boolean hah = false;

    Word() {
        text = "hej";
    }

    public void setHah(boolean hah) { this.hah = hah;
    }

    public String getText() {
        return text;
    }
}
