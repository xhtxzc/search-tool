import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

import java.io.FileInputStream;

public class DOCExtractor {
    private String readWord(String path){
        String temp="";
        try{
            if(path.toLowerCase().endsWith(".doc")){
                FileInputStream inputStream = new FileInputStream(path);
                WordExtractor wordExtractor = new WordExtractor(inputStream);
                temp = wordExtractor.getText();

            }
            else if(path.toLowerCase().endsWith(".docx")){
                FileInputStream inputStream = new FileInputStream(path);
                XWPFWordExtractor xwpfWordExtractor = new XWPFWordExtractor(OPCPackage.open(inputStream));
                temp = xwpfWordExtractor.getText();

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        StringBuilder result= new StringBuilder();
        for(int i=0;i<temp.length();i++){
            if(temp.charAt(i)!=' '&&temp.charAt(i)!='\r'){
                result.append(temp.charAt(i));
            }
        }
        return result.toString();
    }
    public boolean doIt(String path,String target){
        String temp = readWord(path);
        for(int i=0;i<temp.length()-target.length();i++) {
            if(target.equals(temp.substring(i,i+target.length()))) return true;
        }
        return false;
    }
    public String see(String path,String target){
        String temp = readWord(path);
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
