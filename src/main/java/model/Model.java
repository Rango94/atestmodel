package model;

import Vector.Vector;
import filehandle.myFilehandler;
import filehandle.rreader;
import java.io.*;
import java.util.*;
import java.io.BufferedReader;
import java.util.HashMap;

public class Model {

    HashMap<String,Vector> mod=new HashMap<String, Vector>();
    rreader rd;
    HashMap<String,Integer> terms;
    int totalnum;
    int size;
    List<String> termslist=new ArrayList<String>();

    public Model(){

    }
    public Model(String path,int size){
        System.out.println("kaishigoujian");
        rd=new rreader(path);
        System.out.println("end");
        terms=rd.dictionary;
        totalnum=rd.totalnum;
        this.size=size;
        System.out.println("intal model");
        for(String term:rd.dictionary.keySet()){
            mod.put(term,new Vector(size));
            termslist.add(term);
        }

        Collections.sort(termslist,new Comparator<String>() {
            public int compare(String o1, String o2) {
                if(o1 == null || o2 == null){
                    return -1;
                }
                if(o1.compareTo(o2) > 0){
                    return 1;
                }
                if(o1.compareTo(o2) < 0){
                    return -1;
                }
                if(o1.compareTo(o2) == 0){
                    return 0;
                }
                return 0;
            }
        });
//        for(String k:termslist){
//            System.out.println(k);
//        }
        System.out.println("end");
    }
    public void train(){
        String[] line=rd.handlesent();
        int k=0;
        String[] tmpline=new String[line.length];
        System.out.println("start train");
        while(!line[0].equals("-1*end*-1")){
            k++;
            for(int i=1;i<line.length;i++){
                System.out.println(line[0]);
                Vector tmp=mod.get(line[0]).adds(findterms(line[i]),(double)1/i);
                mod.put(line[0],tmp);
            }
            for(int i=1;i<line.length-1;i++){
                for(int j=0;j<i;j++){
                    Vector tmp=mod.get(tmpline[i]).adds(findterms(line[j]),(double)1/(line.length-i));
                    mod.put(tmpline[i+1],tmp);
                }
            }
            line=rd.handlesent();
            if(k==200000){
                break;
            }
            if(k%1000==0){
                System.out.println(getVector("to"));
                System.out.println(k);
            }
        }
        for(String e:mod.keySet()){
            mod.get(e).normal();
        }
    }

    HashMap<String,Integer> tmp=new HashMap<String, Integer>();

    public int findterms(String term){
        if(tmp.containsKey(term)){
            return tmp.get(term);
        }
        int cont=0;
        for(String e:termslist){
            if(!e.equals(term)){
                cont+=terms.get(e);
            }else{
                break;
            }
        }
        tmp.put(term,(int)(size*((double)cont/totalnum)));
        return (int)(size*((double)cont/totalnum));
    }

    public void Loadmodel(String path){
        mod = new HashMap<String, Vector>();
        while (true) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        new FileInputStream(new File(path)), "utf-8"));
                String line = br.readLine();
                if (!line.equals("this a word2vec model created by WNZ")) {
                    System.out.println("this is not a model");
                    br.close();
                    break;
                }
                line = br.readLine();
                while (line != null) {
                    String[] Line=line.split(":");
                    setVector(Line[0],toVector(Line[1]));
                    line = br.readLine();
                }
                br.close();
            } catch(IOException e){
                e.printStackTrace();
            }
            break;
        }
    }

    public Vector getVector(String term){
        return mod.get(term);
    }


    public void setVector(String term,Vector vec){
        mod.put(term,vec);
    }



    private Vector toVector(String sent){
        String[] vecstr=sent.split("\t");
        double[] vecdou=new double[vecstr.length];
        for(int i=0;i<vecdou.length;i++){
            vecdou[i]=Double.parseDouble(vecstr[i]);
        }
        return new Vector(vecdou);
    }

    public void Savemodel(String path){
        myFilehandler file =new myFilehandler(path);
        Set<String> term=mod.keySet();
        file.writer("this a word2vec model created by WNZ"+"\n");
        for(String e:term){
            file.writer(e+":"+getVector(e).toString()+"\n");
        }
        file.close();
    }

    public double dis(String a,String b){
        return Vector.dis(mod.get(a),mod.get(b));
    }
}
