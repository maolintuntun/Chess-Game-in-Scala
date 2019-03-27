import java.awt.Graphics
import scala.collection._

class chesspiece {
  private var PositionFlag =  Array.ofDim[Int](15, 15) // 表示格子上的棋子类型，0表示黑子，1表示白字

  def SetPositionFlag(x: Int, y: Int, flag: Int): Unit = {
    PositionFlag(x)(y) = flag
  }

  def SetAllFlag(NewFlag: Array[Array[Int]]): Unit = {
    PositionFlag = NewFlag
  }

  def GetPositionFlag(x: Int, y: Int): Int = this.PositionFlag(x)(y)


  def GetAllFlag: Array[Array[Int]] = this.PositionFlag

  def DrawChessPiece(g: Graphics): Unit = { // 画棋子
    var i = 0
    while ( {
      i < 15
    }) { // 扫描棋盘中所有的棋子
      var j = 0
      while ( {
        j < 15
      }) {
        val x = chessboard.Left.toInt + i * chessboard.Inc.toInt - 15
        // 把棋子在棋盘中对应的下标转化成在游戏中的坐标
        val y = 25 + j * chessboard.Inc.toInt - 15
        if (this.GetPositionFlag(i, j) == 1) { // 如果指定位置的棋子是黑色棋子
          g.drawImage(chessimage.whiteChess, x, y, null)
        }
        else if (this.GetPositionFlag(i, j) == 2) { // 如果指定位置的棋子是白色棋子，
          g.drawImage(chessimage.blackChess, x, y, null)
        }

        {
          j += 1; j - 1
        }
      }

      {
        i += 1; i - 1
      }
    }
  }
}
