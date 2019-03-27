
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Toolkit

object chessboard {
  var Inc = .0
  var Left = .0
  var Low = .0
  var scrSize: Dimension = _


class chessboard() {
  chessboard.scrSize = Toolkit.getDefaultToolkit.getScreenSize // 获得屏幕尺寸

  chessboard.Inc = (chessboard.scrSize.height - 100) / 14.0 // 设置格子间距

  chessboard.Left = (chessboard.scrSize.width - chessboard.scrSize.height + 100) / 2.0 // 设置左右边框

  chessboard.Low = chessboard.scrSize.height - 100 //75;//棋盘下边缘

  def DrawChessBoard(g: Graphics): Unit = {
    g.setColor(new Color(chessimage.ColorOfWindows(chessimage.WitchMatch)(0), chessimage.ColorOfWindows(chessimage.WitchMatch)(1), chessimage.ColorOfWindows(chessimage.WitchMatch)(2))) //设置边框

    //窗口
    g.fillRect(0, 0, chessboard.scrSize.width - 15, 15) //上边框

    g.fillRect(0, (25 + 14 * chessboard.Inc + 1).toInt, chessboard.scrSize.width.asInstanceOf[Int], 10) //下边框

    g.fillRect(0, 0, 15, chessboard.scrSize.height - 5) //左边框

    g.fillRect(chessboard.scrSize.width - 35, 0, 15, chessboard.scrSize.height - 5) //右边框

    //棋盘
    g.fillRect((chessboard.Left - 35).toInt, 0, 15, (25 + 14 * chessboard.Inc).toInt + 2)
    g.fillRect((chessboard.Left + 14 * chessboard.Inc + 20).toInt, 0, 15, (25 + 14 * chessboard.Inc).toInt)
    //弈者
    g.fillRect(10, (10 + 14 * chessboard.Inc).toInt, (chessboard.Left - 45).toInt, 20) //左边人的下角

    g.fillRect((chessboard.Left + 14 * chessboard.Inc + 20).toInt, (10 + 14 * chessboard.Inc).toInt, (chessboard.Left - 45).toInt, 20) //右边人的下角

    g.setColor(Color.green)
    g.fillRect(0, (25 + 14 * chessboard.Inc + 11).toInt, chessboard.scrSize.width.asInstanceOf[Int], 10) //模拟任务栏下边框

    g.setColor(Color.black)
    var NumX = 0
    while ( {
      NumX < 15
    }) { // 绘制纵线
      g.drawLine((chessboard.Left + NumX * chessboard.Inc).toInt, 25, (chessboard.Left + NumX * chessboard.Inc).toInt, chessboard.scrSize.height - 75)
      g.drawLine((chessboard.Left + NumX * chessboard.Inc + 1).toInt, 25, (chessboard.Left + NumX * chessboard.Inc + 1).toInt, chessboard.scrSize.height - 75)

      {
        NumX += 1; NumX
      }
    }
    //设置双线边框
    g.drawLine((chessboard.Left - 2).toInt, 23, (chessboard.Left - 2).toInt, chessboard.scrSize.height - 73)
    g.drawLine((chessboard.Left + chessboard.Inc * 14 + 3).toInt, 23, (chessboard.Left + chessboard.Inc * 14 + 3).toInt, chessboard.scrSize.height - 73)
    g.drawLine(chessboard.Left.toInt - 1, (25 - 3).toInt, (chessboard.scrSize.width - chessboard.Left + 1).asInstanceOf[Int], (25 - 3).toInt)
    g.drawLine(chessboard.Left.toInt - 1, (25 + 14 * chessboard.Inc + 3).toInt, (chessboard.scrSize.width - chessboard.Left + 1).asInstanceOf[Int], (25 + 14 * chessboard.Inc + 3).toInt)
    var NumY = 0
    while ( {
      NumY < 15
    }) { // 绘制横线
      g.drawLine(chessboard.Left.toInt, (25 + NumY * chessboard.Inc).toInt, (chessboard.scrSize.width - chessboard.Left).asInstanceOf[Int], (25 + NumY * chessboard.Inc).toInt)
      g.drawLine(chessboard.Left.toInt, (25 + NumY * chessboard.Inc + 1).toInt, (chessboard.scrSize.width - chessboard.Left).asInstanceOf[Int], (25 + NumY * chessboard.Inc + 1).toInt)

      {
        NumY += 1; NumY
      }
    }
    //绘制五个点
    g.fillOval((chessboard.Left + 3 * chessboard.Inc - 5).toInt, (25 + 3 * chessboard.Inc - 5).toInt, 10, 10)
    g.drawOval((chessboard.Left + 3 * chessboard.Inc - 7).toInt, (25 + 3 * chessboard.Inc - 7).toInt, 15, 15)
    g.drawOval((chessboard.Left + 3 * chessboard.Inc - 8).toInt, (25 + 3 * chessboard.Inc - 8).toInt, 16, 16)
    g.fillOval((chessboard.Left + 11 * chessboard.Inc - 5).toInt, (25 + 3 * chessboard.Inc - 5).toInt, 10, 10)
    g.drawOval((chessboard.Left + 11 * chessboard.Inc - 7).toInt, (25 + 3 * chessboard.Inc - 7).toInt, 15, 15)
    g.drawOval((chessboard.Left + 11 * chessboard.Inc - 8).toInt, (25 + 3 * chessboard.Inc - 8).toInt, 16, 16)
    g.fillOval((chessboard.Left + 3 * chessboard.Inc - 5).toInt, (25 + 11 * chessboard.Inc - 5).toInt, 10, 10)
    g.drawOval((chessboard.Left + 3 * chessboard.Inc - 7).toInt, (25 + 11 * chessboard.Inc - 7).toInt, 15, 15)
    g.drawOval((chessboard.Left + 3 * chessboard.Inc - 8).toInt, (25 + 11 * chessboard.Inc - 8).toInt, 16, 16)
    g.fillOval((chessboard.Left + 11 * chessboard.Inc - 5).toInt, (25 + 11 * chessboard.Inc - 5).toInt, 10, 10)
    g.drawOval((chessboard.Left + 11 * chessboard.Inc - 7).toInt, (25 + 11 * chessboard.Inc - 7).toInt, 15, 15)
    g.drawOval((chessboard.Left + 11 * chessboard.Inc - 8).toInt, (25 + 11 * chessboard.Inc - 8).toInt, 16, 16)
    g.fillOval((chessboard.Left + 7 * chessboard.Inc - 5).toInt, (25 + 7 * chessboard.Inc - 5).toInt, 10, 10)
    g.drawOval((chessboard.Left + 7 * chessboard.Inc - 7).toInt, (25 + 7 * chessboard.Inc - 7).toInt, 15, 15)
    g.drawOval((chessboard.Left + 7 * chessboard.Inc - 8).toInt, (25 + 7 * chessboard.Inc - 8).toInt, 16, 16)
  }
}
}
