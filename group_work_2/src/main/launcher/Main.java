package launcher;
import com.google.common.base.Joiner;

public class Main {
  public static void main(String[] args) {
    Joiner joiner = Joiner.on(", ").skipNulls();
    System.out.println(joiner.join("Harry","Ron","Hermione"));
  }
}
