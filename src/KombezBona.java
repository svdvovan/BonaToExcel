import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * Created by SretenskyVD on 27.08.2018.
 */
public class KombezBona {
       public static void main(String[] args) throws IOException, InvalidFormatException {

//GUI
//           javax.swing.SwingUtilities.invokeLater(new Runnable() {
//               public void run() {
//                   JFrame.setDefaultLookAndFeelDecorated(true);
//                   GuiMenu frame = new GuiMenu();
//                   frame.pack();
//                   frame.setLocationRelativeTo(null);
//                   frame.setVisible(true);
//
//               }
//           });


//GUI



           System.setProperty("javax.net.ssl.trustStore", "S:/ProjectJava/parser/cert/bonafideru.crt.jks");
           int LastPage = 1;

               String CatalogName = "kombinezony";
           // String CatalogName = "losiny";
           //       String CatalogName = "topy";
//           String Path = "https://bonafide.ru/catalog/zhenshchiny/"+CatalogName;
           String Path = "https://bonafide.ru/catalog/zhenshchiny/"+CatalogName +"/";




           Workbook wb = new HSSFWorkbook();
           CreationHelper createHelper = wb.getCreationHelper();
           Sheet sheet1 = wb.createSheet(CatalogName);
           FileOutputStream fileOut = new FileOutputStream("book_"+CatalogName+".xls");


           try {
               wb.write(fileOut);
               fileOut.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
//GUI
           String Hostname = "bonafide.ru";
           JFrame startFrame = new JFrame("Подготовка подключения");
           startFrame.setSize(500,100);
           startFrame.setLocationRelativeTo(null);
           JLabel topLabel = new JLabel("Подождите идет запуск программы",SwingConstants.CENTER);
           startFrame.add(topLabel);
           startFrame.setVisible(true);



           JFrame frame = new JFrame("Идет парсинг сайта " + Hostname + " ,категория: "+ CatalogName);
           frame.setLocationRelativeTo(null);


//GUI
           int Page = 1;
           for (int count = 1; count <= LastPage; count++) {

               FileInputStream inp = new FileInputStream(new File("book_"+CatalogName+".xls"));
               Workbook wb2 = WorkbookFactory.create(inp);
               Sheet sheet = wb2.getSheetAt(0);

               if (Page>1){
                    Path =   "https://bonafide.ru/catalog/zhenshchiny/"+CatalogName +"/?PAGEN_1="+Page;
               }

               Document doc1 = Jsoup.connect(Path).get();
               Elements links1 = doc1.getElementsByClass("product-item__h");
//GUI
               startFrame.dispose();
               JProgressBar progressBar = new JProgressBar();
               progressBar.setIndeterminate(true);
               progressBar.setStringPainted(true);
               String DF = Integer.toString(Page);

               progressBar.setString( "Страница " +DF);
               progressBar.setValue(Page);

               frame.setLocationRelativeTo(null);
               frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               frame.setContentPane(progressBar);
               frame.setPreferredSize(new Dimension(500, 100));
               frame.pack();
               frame.setVisible(true);
//GUI

               int y = 0;
               for (Element link1 : links1) {



                       String addressUrl = (links1.get(y).select("a[href]").attr("abs:href"));
                   System.out.println();
                       System.out.println(addressUrl);
                   try {
                   Document doc2 = Jsoup.connect(addressUrl).get();

 //                  String Nalichie = doc2.getElementsByClass("productinfo__stock").text();
//                   String Categorys = doc2.getElementsByClass("navcat__link navcat__link_active").text();
                   String Categorys = doc1.getElementsByTag("h1").text();
//                   String prices = doc2.getElementsByClass("productinfo__price").text();
                   String prices = doc2.getElementsByClass("pc-info__cart__cost-old").first().text();
//                   String ID = doc2.getElementsByClass("btn productinfo__btn js-add2basket btn_incart_no btn_avail_yes").attr("data-id");
                   String ID = doc2.getElementsByClass("pc-section__btn").select("button").attr("data-offerId");
//                   String Name = doc2.getElementsByClass("productinfo__h").text();
                   String Name = doc2.getElementsByClass("pc-info__cart__h").text();

                   System.out.println("bf-"+ID);
 //                  System.out.println(Nalichie);
                   System.out.println(Categorys);
                   System.out.println(prices);
                   System.out.println(Name);


                   ////*/




                   System.out.println();

                   int rowCount = sheet.getLastRowNum();
                   Row row = sheet.createRow(++rowCount);


try { Elements Mater = doc2.getElementsByClass("pc-info__params__dl").select("dd");
    // int g = 0;
    //for (Element  Maters : Mater) {
    for (int h =1; h<=1;h++ ){
        System.out.println(Mater.get(1).text());
        //     g++;
    }
    String Material = Mater.get(1).text();

    Cell cell6 = row.createCell(17);
    cell6.setCellValue(Material);



}catch (IndexOutOfBoundsException e)
{ e.printStackTrace();}



                   Element Description = doc2.getElementsByClass("pc-section__text").first();
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

//                   Cell cell31 = row.createCell(17);
//                   cell31.setCellValue(Nalichie);

          ;


                   Cell cell5 = row.createCell(50);
                   cell5.setCellValue(Desc);



                   Elements pictures = doc2.getElementsByClass("product-slider__main__item");
                   int z = 0;
                   int y3 = 18;

                   for (Element picture : pictures) {
                       System.out.println("https://bonafide.ru" + pictures.get(z).attr("data-zoom"));
                       String Foto = "https://bonafide.ru" + pictures.get(z).attr("data-zoom");

                       Cell cell11 = row.createCell(y3);
                       cell11.setCellValue(Foto);
                       y3++;

                       z++;

                   }
                   int y4 = 5;
                   int y5 = 6;
                   Elements razmeres = doc2.getElementsByClass("pc-info__cart__form__select js-select js-product-item__select");

                   for (Element razmer : razmeres) {

   //                    Elements data2 = doc2.getElementsByClass("sizes sizes_dark").select("[class^=js-size_change]");
                       Elements data2 = doc2.getElementsByClass("sizes _md _clickable _white").select("li");
                       int t = 0;
                       for (Element data1 : data2) {
                           System.out.print( data2.get(t).text() + " ; " + data2.get(t).attr("data-quantity")+ " ; ");

                           String Razmer = data2.get(t).text();
                           Cell cell20 = row.createCell(y4);
                           cell20.setCellValue(Razmer);
                            y4=y4+2;

                           String RazmerCount = data2.get(t).attr("data-quantity");
                           Cell cell21 = row.createCell(y5);
                           cell21.setCellValue(RazmerCount);
                           y5=y5+2;


                           t++;
                       }

                   }
                   } catch (java.lang.IllegalArgumentException e) {
                       e.printStackTrace();
                   } catch (java.net.SocketTimeoutException e) {
                       e.printStackTrace();
                   } catch (java.lang.IndexOutOfBoundsException e) {
                       e.printStackTrace();
                   } catch (java.lang.NullPointerException e) {
                       e.printStackTrace();
                   }
                   y++;
               }
               try {
              FileOutputStream fileOut1 = new FileOutputStream("book_"+CatalogName+".xls");



               wb2.write(fileOut1);
               fileOut1.close();

           } catch (FileNotFoundException e) {
               e.printStackTrace();

           } catch (IOException e) {
               e.printStackTrace();
           }



               Page++;
           }


           frame.dispose();
           JOptionPane.showMessageDialog(null, "Парсинг завершен. Количество обработанных страниц "+ (Page-1));
       }

}
