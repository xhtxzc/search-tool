import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class TXTExtractor {
    private String readTxt(String path){
        BufferedReader bufferedReader;
        StringBuilder result = new StringBuilder();
        try {
            FileInputStream inputStream = new FileInputStream(path);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            String txt;
            while((txt = bufferedReader.readLine())!=null){
                result.append(txt);
            }
            bufferedReader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }
    public boolean doIt(String path,String target){
        String temp = readTxt(path);
        for(int i=0;i<temp.length()-target.length();i++) {
            if(target.equals(temp.substring(i,i+target.length()))) return true;
        }
        return false;
    }
    public String see(String path,String target){
        String temp = readTxt(path);
        int left = 0,right = temp.length()-1,pos=0;
        for(int i=0;i<temp.length()-target.length();i++) {
            if(target.equals(temp.substring(i,i+target.length()))) {
                pos = i;
                break;
            }
        }
        if(pos - 10 > 0) left = pos -10;
        if(pos+10+target.length() < right) right = pos + 10+target.length();
        temp = temp.substring(left,right);
        return temp;
    }
}