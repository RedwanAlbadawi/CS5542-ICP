
import java.io.{BufferedWriter, FileWriter}
import org.apache.spark.{SparkContext, SparkConf}

/**
  * Created by Radwan Al Badawi.
  */
object SparkWordGroup {

  def main(args: Array[String]) {

    System.setProperty("hadoop.home.dir","C:\\Users\\Red\\Desktop\\CS5542\\win");

    val sparkConf = new SparkConf().setAppName("SparkWordGroup").setMaster("local[*]")

    val sc=new SparkContext(sparkConf)

    val input=sc.textFile("input")

    val gw = input
      .flatMap( line => {
        line.split(" ")
      })
      .groupBy(word => {
        word.charAt(0)
      })
      .sortByKey()
      .cache()

    val output = gw.collect()

    val gw_Writer = new BufferedWriter(new FileWriter("groupedWords.txt"))

    output.foreach(group => {
      val char = group._1

      gw_Writer.append(group._1).append(": ")

      group._2.foreach(word => {
        gw_Writer.append(word + " ")
      })

      gw_Writer.append("\n")
    })

    gw_Writer.close()

  }

}
