import org.apache.poi.POIXMLDocument;
import org.apache.poi.hslf.extractor.PowerPointExtractor;
import org.apache.poi.xslf.extractor.XSLFPowerPointExtractor;

import java.io.FileInputStream;

public class PPTExtractor{
    public String readPPT(String path){
        try {
            if(path.toLowerCase().endsWith(".ppt")) {
                PowerPointExtractor extractor = new PowerPointExtractor(new FileInputStream(path));
                return extractor.getText();
            }
            else if(path.toLowerCase().endsWith(".pptx")){
                return new XSLFPowerPointExtractor(POIXMLDocument.openPackage(path)).getText();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public boolean doIt(String path,String target){
        String temp = readPPT(path);
        for(int i=0;i<temp.length()-target.length();i++) {
            if(target.equals(temp.substring(i,i+target.length()))) return true;
        }
        return false;
    }
    public String see(String path,String target){
        String temp = readPPT(path);
        int left = 0,right = temp.length()-1,pos=0;
        for(int i=0;i<temp.length()-target.length();i++) {
            if(target.equals(temp.substring(i,i+target.length()))) {
                pos = i;
                break;
            }
        }
        if(pos - 20 > 0) left = pos -20;
        if(pos+20+target.length() < right) right = pos + 20+target.length();
        temp = temp.substring(left,right);
        return temp;
    }
}