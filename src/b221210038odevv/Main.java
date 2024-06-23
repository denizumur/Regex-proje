package b221210038odevv;

/**
*
* @author B221210038 DENİZ UMUR 

* @since 07.04.2024
* <p>
* Sınıf ile ilgili açıklama: Program açıldığında kullanıcıdan github depo linkini isteyecektir. Daha sonra bu git deposunu klonlayıp
içinde *.java uzantılı tüm dosyaları getirecektir. Bu dosyalardan içinde sadece sınıf olanları
ayıklayacak ve bu sınıfları aşağıdaki analize tabi tutacaktır
* </p>
*/


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

   public static void main(String[] args) {
   	try (BufferedReader reader = new BufferedReader(new InputStreamReader(Syst…
[23:09, 07.04.2024] Deniz: soooon
[23:09, 07.04.2024] Deniz: /**
*
* @author B221210038 DENİZ UMUR 

* @since 07.04.2024
* <p>
* Sınıf ile ilgili açıklama: Program açıldığında kullanıcıdan github depo linkini isteyecektir. Daha sonra bu git deposunu klonlayıp
içinde *.java uzantılı tüm dosyaları getirecektir. Bu dosyalardan içinde sadece sınıf olanları
ayıklayacak ve bu sınıfları aşağıdaki analize tabi tutacaktır
* </p>
*/

package b221210038odev1;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

   public static void main(String[] args) {
   	try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
           // Kullanıcıdan GitHub deposu URL'sini al
           System.out.print("GitHub deposu URL'sini girin: ");
           String repoLink = reader.readLine().trim();

           // Depoyu klonla ve klonlanan depo dizinini temsil eden File nesnesini al
           File repoDir = klonRepo(repoLink);

           // Klonlanan depodan *.java uzantılı dosyaları al
           List<File> javaFiles = javaGetir(repoDir);

           // Sınıf dosyalarını ayıkla ve analiz et
           if (javaFiles.isEmpty()) {
               System.out.println("Hic java dosyası bulunamadı.");
           } else {
               for (File javaFile : javaFiles) {
                   System.out.println("Sinif adi " + javaFile.getName());
                   islemler(javaGetir(repoDir));
                   System.out.println("-------------------------------------");
               }
               silici(repoDir);
           }

       } catch (IOException e) {
           e.printStackTrace();
       }
   	 
		
   }

   private static File klonRepo(String repoLink) {
       try {
           ProcessBuilder builder = new ProcessBuilder("git", "clone", repoLink);
           Process process = builder.start();
           process.waitFor();
           System.out.println("Repo klonlandı.");

           // Klonlanan depo dizinini temsil eden File nesnesini döndür
           String repoName = repoLink.substring(repoLink.lastIndexOf('/') + 1).replace(".git", "");
           return new File(repoName);
       } catch (IOException | InterruptedException e) {
           e.printStackTrace();
           return null;
       }
   }
   private static List<File> javaGetir(File directory) {
   	  List<File> javaFiles = new ArrayList<>();
         
         File[] files= directory.listFiles();
         if (files != null) {
             for (File file:files) {
                 if (file.isFile() && file.getName().endsWith(".java") && !file.getName().startsWith("I")) { //Sonu .java olanlar ve interface olmayanları getir.
                 	javaFiles.add(file);
                 } else if (file.isDirectory()) {
                 	javaFiles.addAll(javaGetir(file));
                 }
             }
         }
         return javaFiles;
   }

   private static void islemler(List<File> javaFiles) {
   	
        
        
       if (javaFiles != null) {
           for (File file : javaFiles) {
               if (file.isFile() && file.getName().endsWith(".java") && !file.getName().startsWith("I")) {
                   System.out.println("***************");
                   System.out.println("Sınıf bulundu: " + file.getName());
                   System.out.println("Javadoc satır sayısı: " + javadocBul(file));
                   System.out.println("Diğer yorum satırı sayısı: " + yorumSatiriBul(file));
                   System.out.println("Fonksiyon sayısı: " + fonkBul(file));
                   System.out.println("LOC: " + locBul(file));
                   System.out.println("Kod satırı sayısı: " + kodSatirSayisiBul(file));
                   double yg;
                   yg=(((javadocBul(file)+yorumSatiriBul(file))*0.8)/ fonkBul(file));
                   double yh;
                   yh=((kodSatirSayisiBul(file)/fonkBul(file))*0.3);
                   System.out.println("YORUM SAPMA yzdesi "+(((100*yg)/yh)-100));
               } else {
                   System.out.println("Sınıf bulunamadı ");
               }
           }
       }
   }


   private static boolean sinifVarMi(File file) {
       // Dosyayı aç ve içinde "class" anahtar kelimesinin olup olmadığını kontrol et
       try {
           String content = new String(Files.readAllBytes(Paths.get(file.getPath())));
           return content.contains("class");
       } catch (IOException e) {
           e.printStackTrace();
           return false;
       }
   }

   private static double javadocBul(File file) {
   	 double javadocSayi = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String str;
            boolean javadocMu = false;

            while ((str = reader.readLine()) != null) {
                str = str.trim();
                if (str.startsWith("/**")) {
               	 javadocMu = true;
                    
                } else if (str.startsWith("*/")) {
               	 javadocMu = false;
                } else if (javadocMu==true&& str.startsWith("*")) {
               	 javadocSayi++;
                }else if (javadocMu==true && str.startsWith("*")) {
               	 javadocSayi++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return javadocSayi;
   }
   private static double yorumSatiriBul(File file) {
   	double yorumSatiriSayisi= 0;

       try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
           String str;
           Pattern pattern = Pattern.compile("\\s*//.|/\\(.|\\n)?\\/");
           
           while ((str = reader.readLine()) != null) {
               Matcher matcher = pattern.matcher(str);
               
               // Eşleşmeyi bulduğumuzda, yorum satırı olduğunu belirle
               if (matcher.find()) {
               	yorumSatiriSayisi++;
               }
           }
       } catch (IOException e) {
           e.printStackTrace();
       }

       return yorumSatiriSayisi;
   }
   private static double fonkBul(File file) {
       try {
           String str = new String(Files.readAllBytes(Paths.get(file.getPath())));

           Pattern fonkPattern = Pattern.compile("(?s)(?:public|private|protected)\\s+(?:static\\s+)?\\w+\\s+\\w+\\([^)]\\)\\s\\{[^{}]*\\}");
           Matcher fonkMatcher = fonkPattern.matcher(str);

           double fonkSayi = 0;
           while (fonkMatcher.find()) {
           	fonkSayi++;
           }

          return fonkSayi;
       } catch (IOException e) {
           e.printStackTrace();
       }
       return 0;
   }
   private static double locBul(File file) {
       try {
           double satirSayisi = Files.readAllLines(Paths.get(file.getPath())).size();
           return satirSayisi;
          
       } catch (IOException e) {
           e.printStackTrace();
       }
       return 0;
   }
   private static double kodSatirSayisiBul(File file) {
       try {
           String str = new String(Files.readAllBytes(Paths.get(file.getPath())));

           double kodSatiriSayisi = 0;
           boolean cokluYorum = false;
           for (String satir : str.split("\n")) {
               satir = satir.trim();
               if (satir.isEmpty()) {
                   continue; // Boş satırları atla
               }
               if (satir.startsWith("//")) {
                   continue; // Tek satırlık yorum satırlarını atla
               }
               if (satir.startsWith("/*")) {
               	cokluYorum = true; // Çok satırlı yorum bloğu başladı
               }
               if (satir.endsWith("*/")) {
               	cokluYorum = false; // Çok satırlı yorum bloğu bitti
                   continue;
               }
               if (cokluYorum) {
                   continue; // Yorum bloğu içindeki satırları atla
               }
               kodSatiriSayisi++;
           }
           return kodSatiriSayisi;
           
       } catch (IOException e) {
           e.printStackTrace();
       }
       return 0;
   }
   private static void silici(File directory) {
       File[] files = directory.listFiles();
       if (files != null) {
           for (File file : files) {
               if (file.isDirectory()) {
                   silici(file);
               } else {
                   file.delete();
               }
           }
       }
       directory.delete();
   }
}