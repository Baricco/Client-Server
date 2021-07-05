package Manager;

public class BString {
    private String string;

    public BString(String string) { this.string = string; }

    public BString() { this.string = ""; }

    public void setString(String newString) { this.string = newString; }

    public String getString() { return this.string; }

    @Override public String toString() { return this.string; }

    public void concat(char newChar, int pos) { this.string = string.substring(0, pos) + newChar + string.substring(pos); }

    public void concat(char newChar) { this.string += newChar; }

    public int charAtValue(int pos) { return (int)this.string.charAt(pos); }

    public void replace(char newChar, int index) { System.out.println(string); this.string = string.substring(0, index) + newChar + string.substring(index + 1); System.out.println(string); } 

    public void removeChar(int index) { System.out.println(string); this.string = string.substring(0, index) + string.substring(index + 1); System.out.println(string); } 

    public char charAt(int pos) { return this.string.charAt(pos); } 

    public int length() { return this.string.length(); }

    public boolean equals(BString otherString) { return this.string.equals(otherString.string); }

    public boolean equals(String otherString) { return this.string.equals(otherString); }

    public boolean isEmpty() { return this.string.isEmpty(); }

    public void swapCharacters(int firstIndex, int secondIndex) { 
        char array[] = this.string.toCharArray();

        char temp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = temp;

        this.string = "";
        for (int i = 0; i < array.length; i++) this.string += array[i];
    }

    public void swap(BString otherString) {
        BString temp = new BString(otherString.string);
        otherString.string = this.string;
        this.string = temp.string;
    }

}
