package Manager;

import java.util.Random;

public class Manager {
    
    public final char tab[] = new char[(0x7F - 0x20) + (0x100 - 0xA1)];
    public final char str[] = new char[0xFFFF];
    public final char num[] = { 'P', 'U', '1', '5', 'j', 'r', '^', '{', '}', '_' };
    
    protected BString input, output;
    protected int coef;

    public Manager() {
        initArray();
        this.input = new BString();
        this.output = new BString();
    }  

    public void initArray() {
      //str array init
      for(int i = 0; i < 0xFFFF; i++) str[i] = (char)i;

      //tab array init
      for(int i = 0x20, j = 0; i < 0x7F; i++, j++) tab[j] = (char)i;
      for(int i = 0xA1, j = (0x7F - 0x20); i < 0x100; i++, j++) tab[j] = (char)i;

      Random random = new Random();

      //str array mix
      random.setSeed(0x422);
      for (int i = 0; i < str.length; i++) swap(str, str[i], str[random.nextInt(str.length)]); 

      random.setSeed(0x1F0);
      for (int i = str.length - 1; i >= 0 ; i--) swap(str, str[i], str[random.nextInt(str.length)]); 


      //tab array mix

      random.setSeed(0x310);
      for (int i = 0; i < tab.length; i++) swap(tab, tab[i], tab[random.nextInt(tab.length)]); 

      random.setSeed(0x1B86);
      for (int i = tab.length - 1; i >= 0 ; i--) swap(tab, tab[i], tab[random.nextInt(tab.length)]);
    }

    public void swap(char array[], char e1, char e2) {
      char temp = e1;
      e1 = e2;
      e2 = temp;
    }

    public int CalcMsgDiv(int length) {
      int temp = 1;
      for (int i = 1; i < length; i++) if (length % i == 0) temp = i;
      return temp;
    }

    private BString run() throws NullInputException { throw new NullInputException(); }
}
