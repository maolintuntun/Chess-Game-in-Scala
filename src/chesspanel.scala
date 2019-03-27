import java.awt.BasicStroke
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Stroke
import java.awt.geom.Line2D
import java.util.Date

import chessboard.chessboard
import javax.swing.JPanel


@SerialVersionUID(1L)
class chesspanel(val MyChessBoard1: chessboard, val MyChessPiece1: chesspiece) extends JPanel {
  MyChessBoard = MyChessBoard1
  MyChessPiece = MyChessPiece1
  private var MyChessBoard = new chessboard
  private var MyChessPiece = new chesspiece

  def display(MyChessBoard1: chessboard, MyChessPiece1: chesspiece): Unit = {
    MyChessBoard = MyChessBoard1
    MyChessPiece = MyChessPiece1
    this.repaint()
  }

  override def paintComponent(g: Graphics): Unit = { // paint(Graphics g)//
    // {//此时遇到的问题是只有鼠标经过是才显示button
    super.paintComponent(g) // 加上这个函数

    setBackground(new Color(chessimage.ColorOfBackGround(chessimage.WitchMatch)(0), chessimage.ColorOfBackGround(chessimage.WitchMatch)(1), chessimage.ColorOfBackGround(chessimage.WitchMatch)(2))) // 设置背景色

    if (MyChessBoard != null && MyChessPiece != null) {
      MyChessBoard.DrawChessBoard(g) // 绘制棋盘背景


      MyChessPiece.DrawChessPiece(g) // 绘制盘面棋子

    }
    // 绘制两个玩家
    g.drawImage(chessimage.LeftPlayer, 15, 15, this)
    g.drawImage(chessimage.RightPlayer, (chessboard.scrSize.width - chessboard.Left + 50).toInt, 25, this)
    g.drawImage(chessimage.whiteBoard, 25, 25, this)
    g.drawImage(chessimage.blackBoard, (chessboard.scrSize.width - chessboard.Left + 250).toInt, 25, this)
    if (chessimage.Message ne "") {
      if (chessimage.IsGameOver) {
        val g2d = g.asInstanceOf[Graphics2D]
        val stroke = g2d.getStroke
        g2d.setStroke(new BasicStroke(10, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND))
        g2d.setColor(Color.pink)
        g2d.draw(new Line2D.Float((chessboard.Left + chessboard.Inc * chessimage.LineLeft.GetX).toFloat, (25 + chessboard.Inc * chessimage.LineLeft.GetY).toFloat, (chessboard.Left + chessboard.Inc * chessimage.LineRight.GetX).toFloat, (25 + chessboard.Inc * chessimage.LineRight.GetY).toFloat)) // 五子连线

        g2d.setStroke(stroke)
      }
      g.setColor(Color.red)
      g.setFont(new Font("楷体", Font.BOLD, 86))
      g.drawString(chessimage.Message, (chessboard.Left - 50).toInt, (chessboard.Low - 7 * chessboard.Inc).toInt)
    }
    // 设置游戏时间
    g.setColor(Color.blue)
    g.fillRect((chessboard.Left + 260).toInt, 0, 140, 20) // 左边人的下角

    g.setColor(Color.yellow)
    g.setFont(new Font("楷体", Font.BOLD, 20))
    chessimage.cur = new Date
    var m = chessimage.cur.getTime - chessimage.begin.getTime
    val H = m / (60 * 60 * 1000)
    m = m % (60 * 60 * 1000)
    val M = m / (60 * 1000)
    m = m % (60 * 1000)
    m = m / 1000
    val dif = "time" + H + ":" + M + ":" + m
    g.drawString(dif, (chessboard.Left + 280).toInt, 20)
  }
}
