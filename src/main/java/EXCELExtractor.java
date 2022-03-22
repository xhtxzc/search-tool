import java.io.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class EXCELExtractor {
    public void writeIt(String path,String target,int pos,boolean tt,String newpath){
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
        int position = result.indexOf(target);
        StringBuilder finalString= new StringBuilder();
        if(tt){
            while(result.charAt(position)!='\t'){
                position++;
            }
            position++;
            while(result.charAt(position)!='c') {
                finalString.append(result.charAt(position));
                position++;
            }
        }
        else{
            finalString.append("0");
        }
        String answer = finalString.toString();
        StringBuilder name = new StringBuilder();
        position=0;
        while(path.charAt(position)!='_') position++;
        position++;
        while(path.charAt(position)!='.') name.append(path.charAt(position++));
        OutputStream out = null;
        try {
            Workbook workbook = new XSSFWorkbook(new FileInputStream(newpath));
            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.createRow(pos+1);
            Cell first = row.createCell(0);
            Cell second = row.createCell(1);
            first.setCellValue(name.toString());
            second.setCellValue(answer);
            out = new FileOutputStream(newpath);
            workbook.write(out);
            workbook.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }finally {
            try{
                if(out!=null){
                    out.flush();
                    out.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    private String readExcel(String path){
        String temp = "";
        try {
            StringBuilder temped = new StringBuilder();
            File file = new File(path);
            Workbook workbook;
            if(path.toLowerCase().endsWith(".xls")){
                FileInputStream fileInputStream = new FileInputStream(file);
                workbook = new HSSFWorkbook(fileInputStream);
            }else if(path.toLowerCase().endsWith(".xlsx")){
                workbook = new XSSFWorkbook(new FileInputStream(path));
            }else return null;
            Sheet sheet = workbook.getSheetAt(0);
            int front = sheet.getFirstRowNum();
            int last = sheet.getLastRowNum();
            for(int i=front;i<=last;i++){
                Row row = sheet.getRow(i);
                if(row!=null){
                    int front1=row.getFirstCellNum();
                    int last1 = row.getLastCellNum();
                    for(int j=front1;j<last1;j++){
                        Cell cell = row.getCell(j);
                        if(cell != null){
                            temped.append(cell.toString()).append('\n');
                        }
                    }
                }
            }
            temp = temped.toString();
        } catch (Exception e) {
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
        String temp = readExcel(path);
        for(int i=0;i<temp.length()-target.length();i++) {
            if(target.equals(temp.substring(i,i+target.length()))) return true;
        }
        return false;
    }
    public String see(String path,String target){
        String temp = readExcel(path);
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
