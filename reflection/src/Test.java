import java.text.Format;

public class Test {
    private int a;
    public Test(){
        a=10;
    }
    private Test(int a){
        this.a=a;
    }

    @Override
    public String toString() {
        return String.format("%d",a);
    }
}
