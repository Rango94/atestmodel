package filehandle;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class rreader {
    int sperate=3;
    public Reader br0;
    public Reader br1;
    String Path;
    public List<String>  stopwords=new ArrayList<String>();
    public HashMap<String,Integer> dictionary=new HashMap<String, Integer>();
    public int totalnum=0;


    public rreader(String path){
        try {
            br0 = new InputStreamReader (new FileInputStream( new File(path)));
            br1 = new InputStreamReader (new FileInputStream( new File(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.Path=path;
        readcorpus();
    }


    private HashMap<String, Integer> readcorpus() {
        try {
            Reader  br = new InputStreamReader (new FileInputStream( new File(Path)));
            int tempbyte;
            String term="";
            while ((tempbyte = br.read()) != -1) {
                char w=(char)tempbyte;
                if(Character.isSpaceChar(w)||tempbyte==10||tempbyte==13){
                    if(term!=""&& !stopwords.contains(term)){
                        totalnum++;
                        if (dictionary.containsKey(term)) {
                            dictionary.put(term, dictionary.get(term) + 1);
                        } else {
                            dictionary.put(term, 1);
                        }
                    }
                    term="";
                }else {
                    term += String.valueOf(w);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dictionary;
    }

    public String[] handlesent(){
        String[] line=new String[sperate];
        try {
            int tempchar;
            String term="";
            int num=0;
            tempchar=br0.read();
            while(tempchar!=-1){
                char w=(char)tempchar;
                if(tempchar==10||tempchar==13||Character.isSpaceChar(w)){
                    if(term!="" && !stopwords.contains(term)){
                        line[num]=term;
                        num+=1;
                    }
                    term="";
                }
                else{
                    term+=String.valueOf(w);
                }
                if(num==sperate){
                    break;
                }
                tempchar=br0.read();
            }
            if(tempchar==-1){
                return new String[]{"-1*end*-1"};
            }
            if(num<sperate-1){
                for(int i=num;i<sperate;i++){
                    line[i]=line[num-1];
                }
            }
            br1.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }



}

