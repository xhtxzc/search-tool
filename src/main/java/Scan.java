import java.io.File;
import java.util.ArrayList;
public class Scan {
    public void allScan(File f, String[] target, boolean doc, boolean pdf , boolean ppt, boolean xls, boolean txt, ArrayList<String> strings,String newp) {
        // 将传入的File对象变成File数组
        File[] lf = f.listFiles();
        // 如果为空则结束这次方法。避免空指针异常
        //int poss = 0;//22222222222222222222222
        if (lf == null) {
            return;
        }

        for (File f1 : lf) {
            String path = f1.getAbsolutePath();
            boolean flag = true;
            for(int i=0;i<path.length();i++) if(path.charAt(i) == '$'){
                flag = false;
                break;
            }
            if(!flag) continue;
            if (f1.isDirectory()) {
                allScan(new File(path),target,doc,pdf,ppt,xls,txt,strings,newp);
            } else {
                boolean pos = true;
                boolean in = false;
                for (String s : target) {
                    if (doc && (path.toLowerCase().endsWith(".doc") || path.toLowerCase().endsWith(".docx"))) {
                        DOCExtractor docExtractor = new DOCExtractor();
                        in=true;
                        if (!docExtractor.doIt(path, s)) {
                            pos = false;
                        }
                    } else if (pdf && (path.toLowerCase().endsWith(".pdf"))) {
                        PDFExtractor pdfExtractor = new PDFExtractor();
                        in=true;
                        if (!pdfExtractor.doIt(path, s)) {
                            pos = false;
                        }
                    } else if (txt && path.toLowerCase().endsWith(".txt")) {
                        TXTExtractor txtExtractor = new TXTExtractor();
                        in=true;
                        if (!txtExtractor.doIt(path, s)) {
                            pos = false;
                        }
                    } else if (xls && (path.toLowerCase().endsWith(".xls") || path.toLowerCase().endsWith(".xlsx"))) {
                        EXCELExtractor excelExtractor = new EXCELExtractor();
                        in=true;
                        if (!excelExtractor.doIt(path, s)) {
                            pos = false;
                        }
                    } else if (ppt && (path.toLowerCase().endsWith(".ppt") || path.toLowerCase().endsWith(".pptx"))) {
                        PPTExtractor pptExtractor = new PPTExtractor();
                        in=true;
                        if (!pptExtractor.doIt(path, s)) {
                            pos = false;
                        }
                    }
                }
                if(in && pos) strings.add(path);
                //EXCELExtractor excelExtractor = new EXCELExtractor();//22222222222222222222222
                //excelExtractor.writeIt(path,target[0], poss++,in,newp);
            }
        }
    }
}

