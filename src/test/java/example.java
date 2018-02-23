import model.Model;

public class example {
    public static void main(String[] a){
        Model md=new Model("E://atestmodel//tet.txt",100);
        md.train();
        md.Savemodel("E://atestmodel//model.model");
        Model md1=new Model();
        md1.Loadmodel("E://atestmodel//model.model");
        String i="mother";
        String j="father";
        System.out.println(md1.getVector(i));
        System.out.println(md1.getVector(j));
        System.out.println(md1.dis(i,j));
    }
}
