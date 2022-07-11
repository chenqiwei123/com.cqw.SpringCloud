import java.time.ZonedDateTime;


public class T2
{
    public static void main(String[] args)
    {
        ZonedDateTime zbj = ZonedDateTime.now(); // 默认时区
        System.out.println(zbj);
        //2022-07-11T17:46:33.864+08:00[Asia/Shanghai]
        //2021-02-22T15:51:37.485+08:00[Asia/Shanghai]
    }
}


