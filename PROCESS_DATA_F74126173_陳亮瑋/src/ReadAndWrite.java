import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
 
public class ReadAndWrite {

    public static void main(String[] args) {
        String pathS="C:/Users/User/Documents/Final-project/Google2Notion/data.txt";
        try {
            File path = new File(pathS);
            FileInputStream fis = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
            FileWriter fw = new FileWriter("target.txt");
            String content = "";
            String tag ="標籤:";
            String Finaltag="";
            String Finaltime="";
            while (br.ready()) {
                content = br.readLine();
                System.out.println("Ready... read txt");
                System.out.println("-------------");
                System.out.println(content);
                System.out.println("-------------");
                if(content.contains("==============================================================================================================="))
                {
                    if(tag=="標籤:"){
                        tag+="沒標籤，需要你當第一個給標籤的人，加油吧孩子~~";
                    }
                    for(int i=0;i<tag.length()-1;i++)
                    {
                        Finaltag+=tag.charAt(i);
                    }
                    fw.write(Finaltag);
                    fw.write('\n');
                    fw.write('\n');
                    fw.write((content));
                    fw.write('\n');
                    fw.write('\n');
                    tag ="標籤:";
                    Finaltag="";
                }
                if(content.contains("餐廳名稱")){
                    fw.write(content);
                    fw.write('\n');
                }
                if(content.contains("營業時間")){
                    fw.write('\n');
                    String temp="";
                    String morning="早餐:";
                    String noon="午餐:";
                    String night="晚餐:";
                    String midnight="消夜:";
                    String restday="休息日:";
                    String allhour="24 小時營業:";
                    for (String retval: content.split("\\; |\\. |: |\\、")){
                         if(retval.contains("星期")){
                            temp=retval;
                            temp+="、";
                         }
                        if(retval.contains("10")||retval.contains("11")||retval.contains("12")||retval.contains("13")||retval.contains("14")){
                                noon+=temp;
                        }
                         if(retval.contains("休息")) {
                            restday+=temp;
                        }
                        if(retval.contains("05")||retval.contains("06")||retval.contains("07")||retval.contains("08")||retval.contains("09")){
                            morning+=temp;
                        }
                        if(retval.contains("16")||retval.contains("17")||retval.contains("18")||retval.contains("19")||retval.contains("20")||retval.contains("21")||retval.contains("22")||retval.contains("23")){
                            night+=temp;
                        }
                        if(retval.contains("00:00")||retval.contains("01")||retval.contains("02")||retval.contains("03")||retval.contains("04")){
                            midnight+=temp;
                        }
                        if(retval.contains("24")){
                            allhour+=temp;
                        }
                        
                    }
                    if(allhour.contains("星期五、星期六、星期日、星期一、星期二、星期三、星期四、")){
                        allhour="24 小時營業:每天!";
                    }
                    if(morning.contains("星期五、星期六、星期日、星期一、星期二、星期三、星期四、")){
                        morning="早餐:每天!";
                    }
                    if(noon.contains("星期五、星期六、星期日、星期一、星期二、星期三、星期四、")){
                        noon="午餐:每天!";
                    }
                    if(night.contains("星期五、星期六、星期日、星期一、星期二、星期三、星期四、")){
                        night="晚餐:每天!";
                    }
                    if(midnight.contains("星期五、星期六、星期日、星期一、星期二、星期三、星期四、")){
                        midnight="消夜:每天!";
                    }
                    if(!allhour.equals("24 小時營業:"))
                    {
                        for(int i=0;i<allhour.length()-1;i++)
                    {
                        Finaltime+=allhour.charAt(i);
                    }
                        fw.write(Finaltime);
                        fw.write('\n');
                        fw.write('\n');
                        Finaltime="";
                    }
                    if(!morning.equals("早餐:"))
                    {
                        for(int i=0;i<morning.length()-1;i++)
                        {
                            Finaltime+=morning.charAt(i);
                        }
                            fw.write(Finaltime);
                        fw.write('\n');
                        fw.write('\n');
                        Finaltime="";
                    }
                    if(!noon.equals("午餐:"))
                    {
                        for(int i=0;i<noon.length()-1;i++)
                        {
                            Finaltime+=noon.charAt(i);
                        }
                            fw.write(Finaltime);
                        fw.write('\n');
                        fw.write('\n');
                        Finaltime="";
                    }
                    if(!night.equals("晚餐:"))
                    {
                        for(int i=0;i<night.length()-1;i++)
                        {
                            Finaltime+=night.charAt(i);
                        }
                            fw.write(Finaltime);
                        fw.write('\n');
                        fw.write('\n');
                        Finaltime="";
                    }
                    if(!midnight.equals("消夜:"))
                    {
                        for(int i=0;i<midnight.length()-1;i++)
                        {
                            Finaltime+=midnight.charAt(i);
                        }
                            fw.write(Finaltime);
                        fw.write('\n');
                        fw.write('\n');
                        Finaltime="";
                    }
                    if(!restday.equals("休息日:"))
                    {
                        for(int i=0;i<restday.length()-1;i++)
                        {
                            Finaltime+=restday.charAt(i);
                        }
                            fw.write(Finaltime);
                        fw.write('\n');
                        fw.write('\n');
                        Finaltime="";
                    }
                    Finaltime="";
                     
                }
                if(content.contains("評分: ")){
                   fw.write(content);
                   fw.write('\n');
                }
                if(content.contains("餐廳類型: ")){
                   fw.write(content);
                   fw.write('\n');
                }
                if(content.contains("水餃")||content.contains("鍋貼")||content.contains("餛飩")){
                    if(!tag.contains("水餃/鍋貼、")){
                        tag+="水餃/鍋貼、";
                    }
                    
                }
                if(content.contains("飯糰")){
                    if(!tag.contains("飯糰、")){
                    tag+="飯糰";
                    }
                }
                if(content.contains("蛋餅")){
                    if(!tag.contains("蛋餅、")){
                    tag+="蛋餅、";
                    }
                }
                if(content.contains("拉麵")){
                    if(!tag.contains("拉麵、")){
                    tag+="拉麵、";
                }
                }
                if(content.contains("麵")){
                    if(!tag.contains("拉麵、")&&!tag.contains("麵店、")){
                    tag+="麵店、";
                }
                }
                if(content.contains("便當")&&!content.contains("便當店的")){
                    if(!tag.contains("便當、")){
                    tag+="便當、";
                    }
                }
                if(content.contains("燒臘")){
                    if(!tag.contains("燒臘、")){
                        tag+="燒臘、";
                        }
                    }
                if((content.contains("咖啡")||content.contains("蛋糕")||content.contains("鬆餅")||content.contains("布朗尼"))&&!(content.contains("水餃")||content.contains("鍋貼")||content.contains("餛飩")||content.contains("麵")||content.contains("飯"))){
                    if(!tag.contains("咖啡廳、")){
                        tag+="咖啡廳、";
                        }
                    }
                if(content.contains("茶")||content.contains("奶昔")||content.contains("咖啡")||content.contains("冰沙")){
                    if(!tag.contains("飲料、")){
                        tag+="飲料、";
                        }
                    }
                if(content.contains("綿綿冰")||content.contains("剉冰")||content.contains("枝仔冰")||content.contains("刨冰")){
                    if(!tag.contains("冰品、")){
                        tag+="冰品、";
                        }
                    }
                if(content.contains("豆花")){
                    if(!tag.contains("豆花、")){
                        tag+="豆花、";
                        }
                    }
                if(content.contains("豆花")||content.contains("剉冰")||content.contains("綿綿冰")||content.contains("蛋糕")||content.contains("綿綿冰")||content.contains("派")||content.contains("泡芙")){
                    if(!tag.contains("甜品、")){
                        tag+="甜品、";
                        }
                    }
                if(content.contains("炸")){
                    if(!tag.contains("炸物、")){
                        tag+="炸物、";
                        }
                    }
                if(content.contains("堡")){
                    if(!tag.contains("漢堡、")){
                        tag+="漢堡、";
                        }
                    }
                if(content.contains("炒飯")){
                    if(!tag.contains("炒飯、")){
                        tag+="炒飯、";
                        }
                    }
                if(content.contains("熱炒")){
                    if(!tag.contains("熱炒、")){
                        tag+="熱炒、";
                        }
                    }
                if(content.contains("鍋燒")){
                    if(!tag.contains("鍋燒意麵、")){
                        tag+="鍋燒意麵、";
                        }
                    }
                if(content.contains("火鍋")&&!content.contains("鍋燒")){
                    if(!tag.contains("火鍋、")){
                        tag+="火鍋、";
                        }
                    }
                if(content.contains("丼飯")){
                        if(!tag.contains("丼飯、")){
                            tag+="丼飯、";
                            }
                        }
                if(content.contains("披薩")){
                    if(!tag.contains("披薩、")){
                        tag+="披薩、";
                    }
                }
                if((content.contains("牛排")||content.contains("豬排")||content.contains("雞排"))&&!content.contains("便當")){
                    if(!tag.contains("排餐、")){
                        tag+="排餐、";
                    }
                }        
                if(content.contains("吃到飽")){
                    if(!tag.contains("吃到飽、")){
                        tag+="吃到飽、";
                        }
                    }
                if(content.contains("咖哩")&&content.contains("飯")){
                    if(!tag.contains("咖哩、")){
                        tag+="咖哩、";
                        }
                    }
                if(content.contains("章魚燒")){
                   if(!tag.contains("章魚燒、")){
                       tag+="章魚燒、";
                        }
                    }
                if(content.contains("韓式")){
                    if(!tag.contains("韓式料理、")){
                        tag+="韓式料理、";
                    }
                }
                if(content.contains("鹹酥雞")){
                    if(!tag.contains("鹹酥雞、")){
                        tag+="鹹酥雞、";
                        }
                }
                if(content.contains("臭豆腐")&&!content.contains("鍋")){
                    if(!tag.contains("臭豆腐、")){
                        tag+="臭豆腐、";
                        }
                    }
                if(content.contains("碗粿")){
                    if(!tag.contains("碗粿、")){
                        tag+="碗粿、";
                        }
                    }
                if(content.contains("簡餐")){
                    if(!tag.contains("簡餐、")){
                        tag+="簡餐、";
                        }
                    }
                if(content.contains("涼麵")){
                    if(!tag.contains("涼麵、")){
                        tag+="涼麵、";
                        }
                }
                if(content.contains("米糕")){
                    if(!tag.contains("米糕、")){
                     tag+="米糕、";
                    }
                  }
                if(content.contains("饅頭")){
                    if(!tag.contains("饅頭、")){
                          tag+="饅頭、";
                    }
                }
                if(content.contains("牛肉麵")){
                    if(!tag.contains("牛肉麵、")){
                        tag+="牛肉麵、";
                    }
                }
                if(content.contains("蔥油餅")||content.contains("蔥抓餅")){
                    if(!tag.contains("蔥抓餅、")){
                        tag+="蔥抓餅、";
                        }
                }
                if(content.contains("泡芙")){
                    if(!tag.contains("泡芙、")){
                        tag+="泡芙、";
                        }
                    }
                if(content.contains("鴨肉飯")){
                    if(!tag.contains("鴨肉飯、")){
                        tag+="鴨肉飯、";
                        }
                    }
                }
 
            // Constructs a FileWriter object given a file name.
            
            
 
            fw.flush();
            System.out.println("Write Complete!");
            // After used close.
            br.close();
            fw.close();
        } catch (Exception e) {
            System.out.println("Something Error");
        }
 
    }
}
