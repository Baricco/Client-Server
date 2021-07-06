package Manager;

import java.util.*;

public class Coder extends Manager {
    private Time time;
    char dt[];
    Random random = new Random();
    
    public Coder() {
        super();
        this.time = new Time();
        dt = new char[] {' ', ' '};
    }

    public String code(String input) {
        this.input = new BString(input);
        String toReturn = run().getString();
        output = new BString();
        return toReturn;
    }
   
   public BString run() {
        int n1, n2, msgdiv;
		Reference<Integer> newPos1 = new Reference<Integer>();
		Reference<Integer> newPos2 = new Reference<Integer>();
        ArrayList<Integer> cod = new ArrayList<Integer>();
        this.coef = (int)Math.rint((time.day + time.month + time.year + time.hour + time.minute + time.second) / 4.4);
        for (int i = 0; i < input.length(); i++) {
            for (int z = 0; z < tab.length; z++) if (input.charAt(i) == tab[z]) cod.add(z + coef + i);
            cod.set(i, Math.abs(cod.get(i) % str.length));
        }
        for (int i = 0; i < input.length(); i++) output.concat(str[cod.get(i)]); 
        n1 = (int)(coef / 10);
        n2 = coef - (n1 * 10);
        dt[0] = num[n1];
        dt[1] = num[n2];
        msgdiv = CalcMsgDiv(this.output.length());
        newPos2.setValue(((output.length() / msgdiv)) * (msgdiv - 1));
        newPos1.setValue((output.length() / msgdiv));
        if (msgdiv == 1) newPos1.swap(newPos2);
        output.concat(dt[1], newPos2.getValue());
        output.concat(dt[0], newPos1.getValue());
        addFalseChar(); 
        return output;
    }

    void addFalseChar() {
        random.setSeed((System.currentTimeMillis() % coef) * coef);
        for (int i = 0; i < output.length(); i += 2) output.concat(str[random.nextInt(str.length)], i);
    }


}