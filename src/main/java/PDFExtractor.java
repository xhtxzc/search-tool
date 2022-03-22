import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFExtractor {
    private String parsePDF(String path){
        String result = "";
        try{
            PDDocument document = PDDocument.load(new File(path));
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setSortByPosition(true);
            String text = stripper.getText(document);
            result += text;
            document.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    private String getInformation(String s){
        StringBuilder temp = new StringBuilder();
        for(int i=0;i<s.length();i++) if(s.charAt(i)!=' ' && s.charAt(i)!='\r') temp.append(s.charAt(i));
        return temp.toString();
    }
    public boolean doIt(String path,String target){
        String temp = getInformation(parsePDF(path));
        for(int i=0;i<temp.length()-target.length();i++) {
            if(target.equals(temp.substring(i,i+target.length()))) {
                return true;
            }
        }
        return false;
    }
    public String see(String path,String target){
        String temp = parsePDF(path);
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