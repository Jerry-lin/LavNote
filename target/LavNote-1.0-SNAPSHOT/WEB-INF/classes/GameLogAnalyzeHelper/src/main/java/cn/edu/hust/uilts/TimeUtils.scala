package cn.edu.hust.uilts

import java.text.SimpleDateFormat
import java.util.{Calendar, Date, Locale}

object TimeUtils {
  val sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
  val calendar=Calendar.getInstance()

  //解析时间字符
  def parseTime(startTime:String):Date={
    sdf.parse(startTime)
  }
  //直接解析
  def apply(time:String):Long={
    calendar.setTime(parseTime(time))
    calendar.getTimeInMillis
  }

  //确定下一次时间
  def getCertainDay(day:Int):Long={
    calendar.add(Calendar.DATE,day)
    val time=calendar.getTimeInMillis
    calendar.add(Calendar.DATE,-day)
    time
  }
}
