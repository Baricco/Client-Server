package Manager;

import java.util.*;


public class Decoder extends Manager {
    
    public Decoder() {
        super();
    }    

    private void remFalseChar() {
        BString clearcript = new BString();
        for (int i = 1; i < this.input.length(); i += 2) clearcript.concat(this.input.charAt(i));
        this.input = clearcript;
    }
    
    public String decode(String input) {
        this.input = new BString(input);
        String toReturn = run().getString();
        output = new BString();
        return toReturn;
    }

    private BString run() {
        int j = 0, offset = 0, n1 = -1, n2 = -1, msgdiv;
        Integer keyPos1, keyPos2;
        Reference<Character> c1 = new Reference<Character>();
        Reference<Character> c2 = new Reference<Character>();
        char lett;
        ArrayList<Integer> cod = new ArrayList<Integer>();
        remFalseChar();
        msgdiv = CalcMsgDiv(this.input.length() - 2);
        
        if (msgdiv != 1) {
            keyPos1 = (this.input.length() - 2) / msgdiv;
            keyPos2 = (((this.input.length() - 2) / msgdiv) * (msgdiv - 1)) + 1;
        }
        else { keyPos1 = 0; keyPos2 = this.input.length() - 1; }                
        
        c1.setValue(this.input.charAt(keyPos1));
        c2.setValue(this.input.charAt(keyPos2));

        for (int i = 0; i < num.length; i++) {
            if (c1.isEqual(num[i])) n1 = i;
            if (c2.isEqual(num[i])) n2 = i;
        }
        this.input.removeChar(keyPos1);
        this.input.removeChar(keyPos2 - 1);
        coef = (n1 * 10) + n2;

        for (int i = 0; i < this.input.length(); i++) {
            for (int z = 0; z < str.length; z++) {
                if (this.input.charAt(i) == str[z]) {
                    cod.add(z);
                    break;
                }
            }
        }

        for (int i = 0; i < this.input.length(); i++, j++) {
            offset = coef + j;
            //il range va da offset a offset + 26
            for (int z = offset; z < offset + tab.length; z++) {
                if (cod.get(i) == (z % str.length)) { //hai trovato il numero decodificato
                    cod.set(i, z - offset);
                    break;
                }
            }
            System.out.println("offset index: " + i + " = "+ offset);
    
        }
        for (int i = 0; i < cod.size(); i++) {
            System.out.println("cod[" + i + "] = " + cod.get(i));
            if (cod.get(i) < tab.length && cod.get(i) >= 0) lett = tab[cod.get(i)];
            else lett = '*';
            this.output.concat(lett);
        }
        return this.output;
    }
}