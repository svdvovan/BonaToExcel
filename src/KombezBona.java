import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;

/**
 * Created by SretenskyVD on 27.08.2018.
 */
public class KombezBona {
       public static void main(String[] args) throws IOException, InvalidFormatException {
           System.setProperty("javax.net.ssl.trustStore", "S:/ProjectJava/parser/cert/bonafideru.crt.jks");


           Workbook wb = new HSSFWorkbook();
           CreationHelper createHelper = wb.getCreationHelper();
           Sheet sheet1 = wb.createSheet("1лист");
           FileOutputStream fileOut = new FileOutputStream("book.xls");


           try {
               wb.write(fileOut);
               fileOut.close();
           } catch (IOException e) {
               e.printStackTrace();


           }


     //    String CatalogName = "kombinezony";
           String CatalogName = "losiny";
    //       String CatalogName = "topy";
           String Path = "https://bonafide.ru/catalog/"+CatalogName;


           int Page = 1;
           for (int count = 1; count <= 5; count++) {

               FileInputStream inp = new FileInputStream(new File("book.xls"));
               Workbook wb2 = WorkbookFactory.create(inp);
               Sheet sheet = wb2.getSheetAt(0);

               if (Page>1){
                    Path =   "https://bonafide.ru/catalog/"+CatalogName +"/?PAGEN_1="+Page;
               }

               Document doc1 = Jsoup.connect(Path).get();
               Elements links1 = doc1.getElementsByClass("product__mid");


               int y = 0;
               for (Element link1 : links1) {



                       String addressUrl = (links1.get(y).select("a[href]").attr("abs:href"));
                   System.out.println();
                       System.out.println(addressUrl);

                   Document doc2 = Jsoup.connect(addressUrl).get();

                   Elements Nalichie = doc2.getElementsByClass("productinfo__stock");
                   String Categorys = doc2.getElementsByClass("navcat__link navcat__link_active").text();
                   String prices = doc2.getElementsByClass("productinfo__price").text();
                   String ID = doc2.getElementsByClass("btn productinfo__btn js-add2basket btn_incart_no btn_avail_yes").attr("data-id");
                   String Name = doc2.getElementsByClass("productinfo__h").text();

                   System.out.println("bf-"+ID);
                   System.out.println(Nalichie.text());
                   System.out.println(Categorys);
                   System.out.println(prices);
                   System.out.println(Name);


                   ////*/




                   System.out.println();

                   int rowCount = sheet.getLastRowNum();
                   Row row = sheet.createRow(++rowCount);


try { Elements Mater = doc2.getElementsByClass("paramsmost__param");
    // int g = 0;
    //for (Element  Maters : Mater) {
    for (int h =1; h<=1;h++ ){
        System.out.println(Mater.get(1).text());
        //     g++;
    }
    String Material = Mater.get(1).text();

    Cell cell6 = row.createCell(4);
    cell6.setCellValue(Material);

}catch (IndexOutOfBoundsException e)
{ e.printStackTrace();}



                   Elements Description = doc2.getElementsByClass("defaulttext");
                   String Desc = Description.html();
                   System.out.println(Description.html());



                   Cell cell = row.createCell(1);
                   cell.setCellValue("bf-"+ID);

                   Cell cell13 = row.createCell(0);
                   cell13.setCellValue(Categorys);

                   Cell cell1 = row.createCell(2);
                   cell1.setCellValue(Name);

                   Cell cell2 = row.createCell(3);
                   cell2.setCellValue(prices);

          ;


                   Cell cell5 = row.createCell(50);
                   cell5.setCellValue(Desc);



                   Elements pictures = doc2.getElementsByClass("productphoto__photo owl-lazy");
                   int z = 0;
                   int y3 = 15;

                   for (Element picture : pictures) {
                       System.out.println("https://bonafide.ru" + pictures.get(z).attr("src"));
                       String Foto = "https://bonafide.ru" + pictures.get(z).attr("src");

                       Cell cell11 = row.createCell(y3);
                       cell11.setCellValue(Foto);
                       y3++;

                       z++;

                   }
                   int y4 = 5;
                   int y5 = 6;
                   Elements razmeres = doc2.getElementsByClass("sizes sizes_dark");

                   for (Element razmer : razmeres) {

                       Elements data2 = doc2.getElementsByClass("sizes sizes_dark").select("[class^=js-size_change]");
                       int t = 0;
                       for (Element data1 : data2) {
                           System.out.print( data2.get(t).text() + " ; " + data2.get(t).attr("data-count")+ " ; ");

                           String Razmer = data2.get(t).text();
                           Cell cell20 = row.createCell(y4);
                           cell20.setCellValue(Razmer);
                            y4=y4+2;

                           String RazmerCount = data2.get(t).attr("data-count");
                           Cell cell21 = row.createCell(y5);
                           cell21.setCellValue(RazmerCount);
                           y5=y5+2;


                           t++;
                       }

                   }

                   y++;
               }
               try {
              FileOutputStream fileOut1 = new FileOutputStream("book.xls");



               wb2.write(fileOut1);
               fileOut1.close();

           } catch (FileNotFoundException e) {
               e.printStackTrace();

           } catch (IOException e) {
               e.printStackTrace();
           }



               Page++;
           }

       }

}
