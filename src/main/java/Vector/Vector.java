package Vector;

public class Vector {
    double[] vec;

    public Vector(int size){
        vec=new double[size];
        for(int i=0;i<size;i++){
            vec[i]=0;
        }
    }

    public Vector(double[] in){
        for(int i=0;i<vec.length;i++){
            vec[i]=in[i];
        }
    }

    public Vector adds(int idx,double a){
        vec[idx]+=a;
        return this;
    }

    public void normal(){
        double max=0;
        for(double e:vec){
            if(max<e){
                max=e;
            }
        }
        for(int i=0;i<vec.length;i++){
            vec[i]=vec[i]/max;
        }
    }
    @Override
    public String toString(){
        String vec_str="";
        for(double e:vec){
            vec_str+=Double.toString(e)+"\t";
        }
        return vec_str;
    }

    public static double dis(Vector a,Vector b){
        double out=0.0;
        double stand=0.0;
        for(int i=0;i<a.vec.length;i++){
            stand+=(Math.abs(a.vec[i])+Math.abs(b.vec[i]))/2;
            out+=Math.pow((a.vec[i]-b.vec[i]),2)/Math.pow(stand,2);
        }
        return Math.pow(out,0.5);
    }
}
