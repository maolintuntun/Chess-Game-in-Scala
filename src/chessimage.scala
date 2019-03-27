import java.awt.Image
import java.io.File
import java.io.IOException
import java.util.Date
import javax.imageio.ImageIO


object chessimage { //230,230,250//169,169,169//0,206,209
  var begin: Date = _ //每局开始时间

  var cur: Date = _ //每局结束时间

  var LineLeft: chessOneStep = _ //结束端点1

  var LineRight: chessOneStep = _ //结束端点2

  var IsGameOver = false //是否只有一方获胜

  var ColorOfBackGround = Array(Array(255, 227, 132), Array(0, 255, 127), Array(218, 165, 32)) //背景颜色

  var ColorOfWindows = Array(Array(60, 179, 113), Array(245, 245, 245), Array(122, 122, 122))
  var WitchMatch = 0 //背景搭配

  var MusicOfBackGround: String = _ //背景音乐

  var MusicOfVector: String = _ //取胜

  var CurrentStep = 0 //记录当前步数

  var Rank = 0 //设置难度等级

  var IsSurrender = false //判断是否认输

  var IsTie = false
  var Message: String = _
  var IconImage: Image = _ // 图标

  var blackBoard: Image = _ //白棋盘

  var whiteBoard: Image = _ //黑棋盘

  var blackChess: Image = _ // 白棋棋子图片

  var whiteChess: Image = _
  var RightPlayer: Image = _ //白棋棋罐图片

  var LeftPlayer: Image = _ //白棋玩家头像图片

  var path = "src/" // 图片的保存路径 src/res/


  try {
    IsGameOver = false
    begin = new Date
    CurrentStep = 0
    Rank = 0
    IsSurrender = false
    IsTie = false
    WitchMatch = 0
    Message = ""
    blackBoard = ImageIO.read(new File(path + "黑棋盘.png"))
    whiteBoard = ImageIO.read(new File(path + "白棋盘.png"))
    IconImage = ImageIO.read(new File(path + "图标.jpg"))
    blackChess = ImageIO.read(new File(path + "黑棋 .png"))
    whiteChess = ImageIO.read(new File(path + "白棋.png"))
    LeftPlayer = ImageIO.read(new File(path + "弈者2.jpg"))
    RightPlayer = ImageIO.read(new File(path + "弈者1.jpg"))
  } catch {
    case e: IOException =>
      e.printStackTrace()
  }

}
