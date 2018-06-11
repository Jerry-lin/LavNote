package cn.edu.hust.kpi

import cn.edu.hust.constant.Constant
import cn.edu.hust.uilts.{FilterUtils, TimeUtils}
import org.apache.spark.{SparkConf, SparkContext}

object GameKPI {
  def main(args: Array[String]): Unit = {
    val conf=new SparkConf().setAppName("GameKPI").setMaster("local[*]");
    val sc=new SparkContext(conf)
    //根据分隔符切分
    val fields=sc.textFile("/Users/youyujie/Downloads/GameLog.txt").map(_.split("\\|"))
    //需要计算指标的时间范围
    val startTime=TimeUtils("2016-02-02 00:00:00")
    //
    val endTime=TimeUtils.getCertainDay(+1)
    //通过过滤符合条件的记录,并将很符合条件的记录缓存，方便各个指标的计算
    val filterFields=fields.filter(field=>FilterUtils.filterByTime(field,startTime,endTime))

    //日新增用户 Daily New Users 缩写 DNU
    val DNU=filterFields.filter(field=>FilterUtils.filterByEventType(field,Constant.LOG_FIRST_LOGIN)).count()
    println(DNU)
    //计算当日的活跃玩家，DAU,这里我们将上线和注册也算是活跃的
    val DAU=filterFields.filter(field=>FilterUtils.filterByEventTypes(field,Constant.LOG_FIRST_LOGIN,Constant.LOG_LOGIN_IN)).map(_(3)).distinct().count()
    println(DAU)

    //  留存率：某段时间的新增用户数记为A，经过一段时间后，仍然使用的用户占新增用户A的比例即为留存率
    //前一天注册的用户
    val prevRegisterUser=fields.filter(field=>FilterUtils.filterByEventTypeAndTime(field,Constant.LOG_FIRST_LOGIN,TimeUtils.getCertainDay(-1),startTime)).map(x=>(x(3),1))
    //今天登录用户
    val nowLoginUser=filterFields.filter(field=>FilterUtils.filterByEventType(field,Constant.LOG_LOGIN_IN)).map(x=>(x(3),1)).distinct()
    //昨天留存的用户
    val stayUser:Double=prevRegisterUser.join(nowLoginUser).count()
    println(stayUser)
    //留存率
    val d1r = stayUser/prevRegisterUser.count()
    println(d1r)
  }
}

