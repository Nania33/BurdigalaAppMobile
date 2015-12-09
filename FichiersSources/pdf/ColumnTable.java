package com.enseirb.pfa.bastats.pdf;

import android.app.Activity;

import com.enseirb.pfa.bastats.R;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import java.io.FileOutputStream;
import java.io.IOException;
 
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.collection.PdfTargetDictionary;

import java.text.SimpleDateFormat;
import java.util.Date;
 
public class ColumnTable {
 
    /** The resulting PDF file. */
    private final static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
          Font.BOLD);
    private Activity activity;
    /**
     * Creates a PDF with five tables.
     * @param    filename the name of the PDF file that will be created.
     * @throws    DocumentException 
     * @throws    IOException
     */
    public void createPdf(String filename,Activity activity)
        throws IOException, DocumentException {
        new SaveIcon(R.drawable.bastats,"bastats.png",activity);
    	// step 1
        Document document = new Document();
        this.activity = activity;
        // step 2
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        // step 3
        document.open();
        // step 4
        
        
        addHeaderTable(document, 1);


        //step 5
        //addImage(document);
        
        //step 6
        
        PdfPTable table = createTable1();        
        table.setSpacingBefore(150);
        table.setSpacingAfter(50);
        document.add(table);
       
        PdfPTable table1 = createTable2();
        table1.setSpacingBefore(50);
        table1.setSpacingAfter(200);
        document.add(table1);
             
               
        
        // step 7
        document.close();
    }
 
    /**
     * Creates a table; widths are set with setWidths().
     * @return a PdfPTable
     * @throws DocumentException
     */
    public static PdfPTable createTable1() 
            throws DocumentException {
              
        PdfPTable table = new PdfPTable(new float[] { 2, 8, 3, 3, 3, 3, 2, 2, 2,  3, 2, 2, 2, 2, 2, 2, 3 });
        table.setWidthPercentage(110f);
        
        //table.setWidthPercentage(100);
        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);
          
        table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.getDefaultCell().setUseAscender(true);
        table.getDefaultCell().setUseDescender(true);
        
        table.addCell("");
        table.addCell("Team1");
        for(int i = 0; i <15; ++i){
            table.addCell("");            
        }
        
        table.addCell("No");
        table.addCell("Player");
        table.addCell("MIN");
        table.addCell("FG");
        //table.addCell("FGA");
        table.addCell("3P");
        //table.addCell("3PA");
        table.addCell("Ft");
        table.addCell("+/-");
        table.addCell("Off");
        table.addCell("Def");
        table.addCell("Reb");
        table.addCell("A");
        table.addCell("To");
        table.addCell("Stl");
        table.addCell("Bs"); //Blocked shots
        table.addCell("Ba"); //Blocked against
        table.addCell("PF"); //Personnal fouls
        table.addCell("Pts");
                
        table.getDefaultCell().setBackgroundColor(null);
         
      
        
        for(int i=0; i<5; ++i){
            if(i%2 != 0)
                 table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
            else
                 table.getDefaultCell().setBackgroundColor(null);
                            
            table.addCell("10");
            table.addCell("Charlie Brown");
            table.addCell("10:11");
            table.addCell("8-10");
            table.addCell("7-13");
            table.addCell("11-12");
            table.addCell("7");
            table.addCell("5");
            table.addCell("4");
            table.addCell("3");
            table.addCell("2");
            table.addCell("5");
            table.addCell("12");
            table.addCell("4");
            table.addCell("2");
            table.addCell("4");
//            table.addCell("2");
            table.addCell("39");
        }
        return table;
    }

 
    /**
     * Creates a table; widths are set with setWidths().
     * @return a PdfPTable
     * @throws DocumentException
     */
    public static PdfPTable createTable2()
            throws DocumentException {
        PdfPTable table = new PdfPTable(new float[] { 2, 8, 3, 2, 3, 2, 3, 2, 3, 2, 2, 3, 2, 2, 2, 2, 2, 3 });
        table.setWidthPercentage(110f);
        
        //table.setWidthPercentage(100);
        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);
          
      
        table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.getDefaultCell().setUseAscender(true);
        table.getDefaultCell().setUseDescender(true);
        
        table.addCell("");
        table.addCell("Team2");
        for(int i = 0; i <16; ++i){
            table.addCell("");            
        }
        
        
        table.addCell("No");
        table.addCell("Player");
        table.addCell("MIN");
        table.addCell("FG");
        table.addCell("FGA");
        table.addCell("3P");
        table.addCell("3PA");
        table.addCell("FT");
        table.addCell("FTA");
        table.addCell("OR");
        table.addCell("DR");
        table.addCell("TOT");
        table.addCell("A");
        table.addCell("PF");
        table.addCell("ST");
        table.addCell("TO");
        table.addCell("BS");
        table.addCell("PTS");
                
        table.getDefaultCell().setBackgroundColor(null);
                
        
          
    
        
        
        for(int i=0; i<5; ++i){
            if(i%2 != 0)
                 table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
            else
                 table.getDefaultCell().setBackgroundColor(null);
                                    
            table.addCell("10");
            table.addCell("Charlie Brown");
            table.addCell("10:11");
            table.addCell("8");
            table.addCell("7");
            table.addCell("11");
            table.addCell("7");
            table.addCell("5");
            table.addCell("4");
            table.addCell("3");
            table.addCell("2");
            table.addCell("5");
            table.addCell("12");
            table.addCell("4");
            table.addCell("2");
            table.addCell("4");
            table.addCell("2");
            table.addCell("39");
        }
        return table;
    }
 
  

 
  
 
    
     public  PdfPCell addImage()
            throws BadElementException, IOException, DocumentException {


         Image img = Image.getInstance(BrowserFile.path+"/"+"bastats.png");
         //img.setAbsolutePosition(40f, 700f);
         img.scaleAbsolute(50f, 50f);


         PdfPCell pdfPCell = new PdfPCell(img);
         pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
         pdfPCell.setBackgroundColor(new BaseColor(231, 115, 40));
         pdfPCell.setBorder(Rectangle.NO_BORDER);
         return pdfPCell;

     }
    
    
    public float addHeaderTable(Document document, int page)
            throws DocumentException, IOException {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(" dd/MM/yyyy HH:mm");

        PdfPTable header = new PdfPTable(3);

        header.setWidthPercentage(100);

        header.getDefaultCell().setBackgroundColor(new BaseColor(231,115,40));
        header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        Font font = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);

        PdfPCell p = new PdfPCell(new Phrase("Team1 vs Team2", font));
        p.setColspan(3);
        p.setBorder(Rectangle.NO_BORDER);
        p.setHorizontalAlignment(Element.ALIGN_CENTER);
        p.setBackgroundColor(new BaseColor(231, 115, 40));
        header.addCell(p);

        p.setPhrase(new Phrase(sdf.format(date), font));
        p.setColspan(1);
        p.setBorder(Rectangle.NO_BORDER);
        p.setHorizontalAlignment(Element.ALIGN_CENTER);
        p.setVerticalAlignment(Element.ALIGN_CENTER);
        //p.setBackgroundColor(BaseColor.BLACK);
        header.addCell(p);

        p = new PdfPCell(this.addImage());
        p.setVerticalAlignment(Element.ALIGN_CENTER);
        header.addCell(p);

        p.setPhrase(new Phrase(String.format("page %d", page), font));
        p.setBorder(Rectangle.NO_BORDER);
        p.setVerticalAlignment(Element.ALIGN_CENTER);
        p.setHorizontalAlignment(Element.ALIGN_CENTER);
        header.addCell(p);
        document.add(header);
        return header.getTotalHeight();
    }

       private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
      
      
  /*
    /**
     * Main method.
     * @param args no arguments needed
     * @throws DocumentException 
     * @throws IOException
     */
   /* public static void main(String[] args) throws IOException, DocumentException {
        new ColumnTable().createPdf(RESULT);
    }
    */
}