import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by SretenskyVD on 27.08.2018.
 */
public class KombezBona {
       public static void main(String[] args) throws IOException {
           System.setProperty("javax.net.ssl.trustStore", "S:/ProjectJava/parser/cert/bonafideru.crt.jks");

      //   String CatalogName = "kombinezony";
           String CatalogName = "losiny";
    //       String CatalogName = "topy";
           String Path = "https://bonafide.ru/catalog/"+CatalogName;


           int Page = 1;
           for (int count = 1; count <= 1; count++) {

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
                   Elements Categorys = doc2.getElementsByClass("navcat__link navcat__link_active");
                   Elements prices = doc2.getElementsByClass("productinfo__price");
                   String ID = doc2.getElementsByClass("btn productinfo__btn js-add2basket btn_incart_no btn_avail_yes").attr("data-id");

                   System.out.println("bf-"+ID);
                   System.out.println(Nalichie.text());
                   System.out.println(Categorys.text());
                   System.out.println(prices.text());

                   Elements razmeres = doc2.getElementsByClass("sizes sizes_dark");

                   for (Element razmer : razmeres) {
                   Elements data2 = doc2.getElementsByClass("sizes sizes_dark").select("[class^=js-size_change]");
                       int t = 0;
                       for (Element data1 : data2) {
                           System.out.print( data2.get(t).text() + " ; " + data2.get(t).attr("data-count")+ " ; ");
                           t++;
                       }
                   }
                   System.out.println();
                   Elements pictures = doc2.getElementsByClass("productphoto__photo owl-lazy");
                   int z = 0;
                   for (Element picture : pictures) {
                       System.out.println("https://bonafide.ru" + pictures.get(z).attr("src"));
                       z++;
                   }

                   Elements Mater = doc2.getElementsByClass("paramsmost__param");
                  // int g = 0;
                   //for (Element  Maters : Mater) {
                   for (int h =1; h<=1;h++ ){
                       System.out.println(Mater.get(1).text());
                  //     g++;
                   }

                   Elements Description = doc2.getElementsByClass("defaulttext");
                   System.out.println(Description.html());

                   y++;
               }

               Page++;
           }
       }

}
