import java.io.Serializable;

public class Word implements Serializable {
    String hej = "hej";
    int number = 1;
    boolean hah = true;
    Object obj = 10;

    Word() {
    }

    public void setHah(boolean hah) {
        this.hah = hah;
    }
}
