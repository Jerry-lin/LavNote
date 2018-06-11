package cn.edu.hust.uilts

import java.text.SimpleDateFormat
import java.util.Locale

import org.apache.commons.lang3.time.FastDateFormat

object FilterUtils {


  var threadSDF= FastDateFormat.getInstance("yyyy年MM月dd日,E,HH:mm:ss")


  //根据事件过滤出符合条件的日志记录
  def filterByTime(field:Array[String],startTime:Long,endTime:Long):Boolean={
    val time=threadSDF.parse(field(1)).getTime
    time>=startTime&&time<endTime
  }

  //根据字段过滤掉条件
  def filterByEventType(field:Array[String],eventType:String):Boolean={
      field(0)==eventType
  }

  //根据事件类型过滤出符合条件的日志记录
  def filterByEventTypes(field:Array[String],eventTypes:String*): Boolean =
  {
    val fieldType=field(0)
    for(_type <- eventTypes)
    {
      if(_type==fieldType) return true
    }
    false
  }

  //根据事件和时间过滤
  def filterByEventTypeAndTime(field:Array[String],eventType:String,startTime:Long,endTime:Long):Boolean={
      val time=threadSDF.parse(field(1)).getTime
      val _type=field(0)
      _type==eventType&&time>=startTime&&time<endTime
  }
}
