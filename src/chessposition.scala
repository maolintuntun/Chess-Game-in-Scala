import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.util
import java.util.Collections

import chessboard.chessboard
import javax.swing.JOptionPane


object chessposition {
  private var MyChessBoard: chessboard = _
  var MyChessPiece: chesspiece = _
  private var Mychesspanel: chesspanel = _
  var MyChessList = new chesslist
  private val INF = 1 << 30

  var CanGo = false
}

class chessposition() extends MouseAdapter {
  def this(Mychesspanel1: chesspanel, MyChessBoard1: chessboard, MyChessPiece1: chesspiece) {
    this()
    chessposition.Mychesspanel = Mychesspanel1
    chessposition.MyChessBoard = MyChessBoard1
    chessposition.MyChessPiece = MyChessPiece1
    chessposition.CanGo = true
  }

  override def mouseClicked(event: MouseEvent): Unit = {
    if (!chessposition.CanGo) return
    // 获取鼠标点击的棋盘相对位置
    val x = event.getX - chessboard.Left.asInstanceOf[Int]
    val y = event.getY - 25
    val Max = (chessboard.Inc * 14).asInstanceOf[Int] + 39
    // 棋盘最右、下边
    val Min = 0 // 棋盘最左、上边
    if ((x < Min) || (x > Max) || (y < Min) || (y > Max)) { // 无效棋子
      return
    }
    val Inc = chessboard.Inc.asInstanceOf[Int]
    val NextX = x / Inc
    val NextY = y / Inc
    new chessmusic("下棋.wav")
    if (0 != chessposition.MyChessPiece.GetPositionFlag(NextX, NextY)) {
      JOptionPane.showMessageDialog(JOptionPane.getRootFrame, "您的走法违规，该处已有棋子！", "温馨提示", JOptionPane.ERROR_MESSAGE)
      return
    }
    val OneStep = new chessOneStep(NextX, NextY, 0)
    chessposition.MyChessList.AddStep(OneStep) //保留当前下得棋

    chessposition.MyChessPiece.SetPositionFlag(NextX, NextY, 1)
    chessimage.CurrentStep += 1
    if (chessimage.CurrentStep == 225) {
      chessimage.Message = "伯仲之间 ,胜负难分!"
      chessposition.CanGo = false
    }
    if (IsOver(chessposition.MyChessPiece.GetAllFlag, NextX, NextY)) new chessmusic("取胜.wav")
    display()
    if (0 == chessimage.Rank) GetGreedNext()
    else if (1 == chessimage.Rank) GetSearchNext(1)
    else if (2 == chessimage.Rank) GetMinMaxsearchNext(3)
  }

  //计算机采用一步攻防贪心策略下棋
  def GetGreedNext(): Unit = {
    var NextX = 0
    var NextY = 0
    if (!chessposition.CanGo) return
    //完全裸下
    var MaxWei = -chessposition.INF
    var idX = -1
    var idY = -1
    var i = 0
    while ( {
      i < 15
    }) {
      var j = 0
      while ( {
        j < 15
      }) {
        if (0 == chessposition.MyChessPiece.GetPositionFlag(i, j)) {
          chessposition.MyChessPiece.SetPositionFlag(i, j, 2)
          var tmp = Evaluate(chessposition.MyChessPiece.GetAllFlag, i, j)
          if (tmp >= MaxWei) {
            MaxWei = tmp
            idX = i
            idY = j
          }
          chessposition.MyChessPiece.SetPositionFlag(i, j, 1)
          tmp = Evaluate(chessposition.MyChessPiece.GetAllFlag, i, j)
          if (tmp > MaxWei) {
            MaxWei = tmp
            idX = i
            idY = j
          }
          chessposition.MyChessPiece.SetPositionFlag(i, j, 0)
        }

        {
          j += 1; j - 1
        }
      }

      {
        i += 1; i - 1
      }
    }
    NextX = idX
    NextY = idY
    new chessmusic("下棋.wav")
    if (-1 == NextX && -1 == NextY) {
      if (chessimage.CurrentStep == 225) {
        chessimage.Message = "伯仲之间 ,胜负难分!"
        chessposition.CanGo = false
      }
      return
    }
    chessimage.CurrentStep += 1
    val OneStep = new chessOneStep(NextX, NextY, 0)
    chessposition.MyChessList.AddStep(OneStep)
    chessposition.MyChessPiece.SetPositionFlag(NextX, NextY, 2)
    if (IsOver(chessposition.MyChessPiece.GetAllFlag, NextX, NextY)) new chessmusic("取胜.wav")
    display()
  }

  def display(): Unit = { // 用于重新显示游戏界面
    chessposition.Mychesspanel.display(chessposition.MyChessBoard, chessposition.MyChessPiece)
  }

  //添加棋子后只需判断水平、竖直、成45、135度角上是否连成5个
  def IsOver(Array: Array[Array[Int]], x: Int, y: Int): Boolean = {
    var flag = false
    var num = 1
    var k = 1
    while ( {
      x - k >= 0 && Array(x)(y) == Array(x - k)(y)
    }) {
      num += 1
      k += 1
    }
    chessimage.LineLeft = new chessOneStep(x - k + 1, y, 0)
    k = 1
    while ( {
      x + k < 15 && Array(x)(y) == Array(x + k)(y)
    }) {
      num += 1
      k += 1
    }
    chessimage.LineRight = new chessOneStep(x + k - 1, y, 0)
    if (num >= 5) flag = true
    if (!flag) {
      num = 1
      k = 1
      while ( {
        y - k >= 0 && Array(x)(y) == Array(x)(y - k)
      }) {
        num += 1
        k += 1
      }
      chessimage.LineLeft = new chessOneStep(x, y - k + 1, 0)
      k = 1
      while ( {
        y + k < 15 && Array(x)(y) == Array(x)(y + k)
      }) {
        num += 1
        k += 1
      }
      chessimage.LineRight = new chessOneStep(x, y + k - 1, 0)
      if (num >= 5) flag = true
    }
    if (!flag) {
      num = 1
      k = 1
      while ( {
        y - k >= 0 && x - k >= 0 && Array(x)(y) == Array(x - k)(y - k)
      }) {
        num += 1
        k += 1
      }
      chessimage.LineLeft = new chessOneStep(x - k + 1, y - k + 1, 0)
      k = 1
      while ( {
        y + k < 15 && x + k < 15 && Array(x)(y) == Array(x + k)(y + k)
      }) {
        num += 1
        k += 1
      }
      chessimage.LineRight = new chessOneStep(x + k - 1, y + k - 1, 0)
      if (num >= 5) flag = true
    }
    if (!flag) {
      num = 1
      k = 1
      while ( {
        y + k < 15 && x - k >= 0 && Array(x)(y) == Array(x - k)(y + k)
      }) {
        num += 1
        k += 1
      }
      chessimage.LineLeft = new chessOneStep(x - k + 1, y + k - 1, 0)
      k = 1
      while ( {
        y - k >= 0 && x + k < 15 && Array(x)(y) == Array(x + k)(y - k)
      }) {
        num += 1
        k += 1
      }
      chessimage.LineRight = new chessOneStep(x + k - 1, y - k + 1, 0)
      if (num >= 5) flag = true
    }
    if (flag) {
      chessimage.IsGameOver = true
      if (1 == Array(x)(y)) chessimage.Message = "获胜了，恭喜您!"
      else chessimage.Message = "失败了，振作点!"
      chessposition.CanGo = false
    }
    flag
  }

  def GetMax(a: Int, b: Int): Int = if (a < b) b
  else a

  // 预先设定一些规则估值,对已连成一片的
  def GetValue(flag: Int, num: Int): Int = {
    var ret = 0
    if (1 == num) ret = 0
    if (2 == num) if (0 == flag) { // 死2
      ret = 3
    }
    else if (1 == flag) { // 单活2
      ret = 50
    }
    else ret = 100 // 双活2
    else if (3 == num) if (0 == flag) { // 死3
      ret = 5
    }
    else if (1 == flag) { // 单活3
      ret = 200
    }
    else ret = 5000 // 双活3
    else if (4 == num) if (0 == flag) { // 死4
      ret = 10
    }
    else if (1 == flag) { // 单活4
      ret = 8000
    }
    else ret = 500000
    else if (5 == num) ret = 10000000
    ret
  }

  //对未连成一片但通过再下一颗子就能连成一片的局面进行估值
  def GetPredictValue(flag: Int, num: Int): Int = {
    var ret = 0
    if (0 == flag || num <= 2) ret = 0
    else if (1 == flag) if (3 == num) ret = 10
    else if (4 == num) ret = 50
    else ret = 200
    else if (3 == num) ret = 100
    else if (4 == num) ret = 5000
    else ret = 8000
    ret
  }

  // 以下棋点为中心，查看总得分，此评判方法为贪心法
  def Evaluate(Array: Array[Array[Int]], x: Int, y: Int): Int = {
    var ret = 0
    var num = 0
    var k = 0
    var tag = 0
    var lflag = false
    var rflag = false
    //先估值一连成一片的
    // 水平线
    k = 1
    num = 1
    lflag = true
    rflag = true
    while ( {
      x - k >= 0 && Array(x)(y) == Array(x - k)(y)
    }) {
      num += 1
      k += 1
    }
    if (!(x - k >= 0 && 0 == Array(x - k)(y))) lflag = false
    k = 1
    while ( {
      x + k < 15 && Array(x)(y) == Array(x + k)(y)
    }) {
      num += 1
      k += 1
    }
    if (!(x + k < 15 && 0 == Array(x + k)(y))) rflag = false
    num = if (num < 5) num
    else 5
    if (lflag && rflag) tag = 2
    else if (lflag || rflag) tag = 1
    else tag = 0
    ret += GetValue(tag, num)
    //竖直线
    k = 1
    num = 1
    lflag = true
    rflag = true
    while ( {
      y - k >= 0 && Array(x)(y) == Array(x)(y - k)
    }) {
      num += 1
      k += 1
    }
    if (!(y - k >= 0 && 0 == Array(x)(y - k))) lflag = false
    k = 1
    while ( {
      y + k < 15 && Array(x)(y) == Array(x)(y + k)
    }) {
      num += 1
      k += 1
    }
    if (!(y + k < 15 && 0 == Array(x)(y + k))) rflag = false
    num = if (num < 5) num
    else 5
    if (lflag && rflag) tag = 2
    else if (lflag || rflag) tag = 1
    else tag = 0
    ret += GetValue(tag, num)
    //135度
    k = 1
    num = 1
    lflag = true
    rflag = true
    while ( {
      y - k >= 0 && x - k >= 0 && Array(x)(y) == Array(x - k)(y - k)
    }) {
      num += 1
      k += 1
    }
    if (!(y - k >= 0 && x - k >= 0 && 0 == Array(x - k)(y - k))) lflag = false
    k = 1
    while ( {
      y + k < 15 && x + k < 15 && Array(x)(y) == Array(x + k)(y + k)
    }) {
      num += 1
      k += 1
    }
    if (!(y + k < 15 && x + k < 15 && 0 == Array(x + k)(y + k))) rflag = false
    num = if (num < 5) num
    else 5
    if (lflag && rflag) tag = 2
    else if (lflag || rflag) tag = 1
    else tag = 0
    ret += GetValue(tag, num)
    //45度
    k = 1
    num = 1
    lflag = true
    rflag = true
    while ( {
      y + k < 15 && x - k >= 0 && Array(x)(y) == Array(x - k)(y + k)
    }) {
      num += 1
      k += 1
    }
    if (!(y + k < 15 && x - k >= 0 && 0 == Array(x - k)(y + k))) lflag = false
    k = 1
    while ( {
      y - k >= 0 && x + k < 15 && Array(x)(y) == Array(x + k)(y - k)
    }) {
      num += 1
      k += 1
    }
    if (!(y - k >= 0 && x + k < 15 && 0 == Array(x + k)(y - k))) rflag = false
    num = if (num < 5) num
    else 5
    if (lflag && rflag) tag = 2
    else if (lflag || rflag) tag = 1
    else tag = 0
    ret += GetValue(tag, num)
    //能成连成一片的
    var add = 0
    var leftadd = 0
    var rightadd = 0
    var leftflag = false
    var rightflag = false
    var lvalue = 0
    var rvalue = 0
    k = 1
    num = 1
    lflag = true
    rflag = true
    leftflag = true
    rightflag = true
    leftadd = 0
    rightadd = 0
    while ( {
      x - k >= 0 && Array(x)(y) == Array(x - k)(y)
    }) {
      num += 1
      k += 1
    }
    if (!(x - k >= 0 && 0 == Array(x - k)(y))) lflag = false
    else {
      add = k + 1 // 跳过空格

      while ( {
        (x - add >= 0) && Array(x)(y) == Array(x - add)(y)
      }) {
        leftadd += 1
        add += 1
      }
      if (!(x - add >= 0 && 0 == Array(x - add)(y))) { // 堵死了
        leftflag = false
      }
    }
    k = 1
    while ( {
      x + k < 15 && Array(x)(y) == Array(x + k)(y)
    }) {
      num += 1
      k += 1
    }
    if (!(x + k < 15 && 0 == Array(x + k)(y))) rflag = false
    else {
      add = k + 1
      while ( {
        x + add < 15 && Array(x)(y) == Array(x + add)(y)
      }) {
        rightadd += 1
        add += 1
      }
      if (!(x + add < 15 && 0 == Array(x + add)(y))) rightflag = false
    }
    if (leftflag && rflag) tag = 2
    else if (leftflag || rflag) tag = 1
    else tag = 0
    lvalue = GetPredictValue(tag, num + 1 + leftadd)
    if (lflag && rightflag) tag = 2
    else if (lflag || rightflag) tag = 1
    else tag = 0
    rvalue = GetPredictValue(tag, num + 1 + rightadd)
    ret += GetMax(lvalue, rvalue)
    // 竖直线
    k = 1
    num = 1
    lflag = true
    rflag = true
    leftflag = true
    rightflag = true
    leftadd = 0
    rightadd = 0
    while ( {
      y - k >= 0 && Array(x)(y) == Array(x)(y - k)
    }) {
      num += 1
      k += 1
    }
    if (!(y - k >= 0 && 0 == Array(x)(y - k))) lflag = false
    else {
      add = k + 1
      while ( {
        y - add >= 0 && Array(x)(y) == Array(x)(y - add)
      }) {
        leftadd += 1
        add += 1
      }
      if (!(y - add >= 0 && 0 == Array(x)(y - add))) leftflag = false
    }
    k = 1
    while ( {
      y + k < 15 && Array(x)(y) == Array(x)(y + k)
    }) {
      num += 1
      k += 1
    }
    if (!(y + k < 15 && 0 == Array(x)(y + k))) rflag = false
    else {
      add = k + 1
      while ( {
        y + add < 15 && Array(x)(y) == Array(x)(y + add)
      }) {
        rightadd += 1
        add += 1
      }
      if (!(y + add < 15 && 0 == Array(x)(y + add))) rightflag = false
    }
    if (leftflag && rflag) tag = 2
    else if (leftflag || rflag) tag = 1
    else tag = 0
    lvalue = GetPredictValue(tag, num + 1 + leftadd)
    if (lflag && rightflag) tag = 2
    else if (lflag || rightflag) tag = 1
    else tag = 0
    rvalue = GetPredictValue(tag, num + 1 + rightadd)
    ret += GetMax(lvalue, rvalue)
    // 135度
    k = 1
    num = 1
    lflag = true
    rflag = true
    leftflag = true
    rightflag = true
    leftadd = 0
    rightadd = 0
    while ( {
      y - k >= 0 && x - k >= 0 && Array(x)(y) == Array(x - k)(y - k)
    }) {
      num += 1
      k += 1
    }
    if (!(y - k >= 0 && x - k >= 0 && 0 == Array(x - k)(y - k))) lflag = false
    else {
      add = k + 1
      while ( {
        y - add >= 0 && (x - add >= 0) && Array(x)(y) == Array(x - add)(y - add)
      }) {
        rightadd += 1
        add += 1
      }
      if (!(y - add >= 0 && (x - add >= 0) && 0 == Array(x - add)(y - add))) rightflag = false
    }
    k = 1
    while ( {
      y + k < 15 && x + k < 15 && Array(x)(y) == Array(x + k)(y + k)
    }) {
      num += 1
      k += 1
    }
    if (!(y + k < 15 && x + k < 15 && 0 == Array(x + k)(y + k))) rflag = false
    else {
      add = k + 1
      while ( {
        y + add < 15 && x + add < 15 && Array(x)(y) == Array(x + add)(y + add)
      }) {
        rightadd += 1
        add += 1
      }
      if (!(y + add < 15 && x + add < 15 && 0 == Array(x + add)(y + add))) rightflag = false
    }
    if (leftflag && rflag) tag = 2
    else if (leftflag || rflag) tag = 1
    else tag = 0
    lvalue = GetPredictValue(tag, num + 1 + leftadd)
    if (lflag && rightflag) tag = 2
    else if (lflag || rightflag) tag = 1
    else tag = 0
    rvalue = GetPredictValue(tag, num + 1 + rightadd)
    ret += GetMax(lvalue, rvalue)
    k = 1
    num = 1
    leftflag = true
    rightflag = true
    leftadd = 0
    rightadd = 0
    while ( {
      y + k < 15 && x - k >= 0 && Array(x)(y) == Array(x - k)(y + k)
    }) {
      num += 1
      k += 1
    }
    if (!(y + k < 15 && x - k >= 0 && 0 == Array(x - k)(y + k))) lflag = false
    else {
      add = k + 1
      while ( {
        y + add < 15 && (x - add >= 0) && Array(x)(y) == Array(x - add)(y + add)
      }) {
        rightadd += 1
        add += 1
      }
      if (!(y + add < 15 && (x - add >= 0) && 0 == Array(x - add)(y + add))) rightflag = false
    }
    k = 1
    while ( {
      y - k >= 0 && x + k < 15 && Array(x)(y) == Array(x + k)(y - k)
    }) {
      num += 1
      k += 1
    }
    if (!(y - k >= 0 && x + k < 15 && 0 == Array(x + k)(y - k))) rflag = false
    else {
      add = k + 1
      while ( {
        y - add >= 0 && x + add < 15 && Array(x)(y) == Array(x + add)(y - add)
      }) {
        rightadd += 1
        add += 1
      }
      if (!(y - add >= 0 && x + add < 15 && 0 == Array(x + add)(y - add))) rightflag = false
    }
    if (leftflag && rflag) tag = 2
    else if (leftflag || rflag) tag = 1
    else tag = 0
    lvalue = GetPredictValue(tag, num + 1 + leftadd)
    if (lflag && rightflag) tag = 2
    else if (lflag || rightflag) tag = 1
    else tag = 0
    rvalue = GetPredictValue(tag, num + 1 + rightadd)
    ret += GetMax(lvalue, rvalue)
    ret
  }

  //计算机人工智能中直接搜索下棋,向前看LookLength步
  def GetSearchNext(LookLength: Int): Unit = {
    if (!chessposition.CanGo) return
    val Option = derectSearch(chessposition.MyChessPiece.GetAllFlag, true, LookLength)
    val NextX = Option.GetX
    val NextY = Option.GetY
    new chessmusic("下棋.wav")
    if (-1 == NextX && -1 == NextY) {
      if (chessimage.CurrentStep == 225) {
        chessimage.Message = "伯仲之间 ,胜负难分!"
        chessposition.CanGo = false
      }
      return
    }
    chessimage.CurrentStep += 1
    val OneStep = new chessOneStep(NextX, NextY, 0)
    chessposition.MyChessList.AddStep(OneStep)
    chessposition.MyChessPiece.SetPositionFlag(NextX, NextY, 2)
    if (IsOver(chessposition.MyChessPiece.GetAllFlag, NextX, NextY)) new chessmusic("取胜.wav")
    display()
  }

  // 直接暴搜
  def derectSearch(Array: Array[Array[Int]], who: Boolean, deepth: Int): chessOneStep = {
    if (0 == deepth) { // 返回当前局面的评估函数值
      var MaxWei = -chessposition.INF
      var idX = -1
      var idY = -1
      var i = 0
      while ( {
        i < 15
      }) {
        var j = 0
        while ( {
          j < 15
        }) { // 5000,8000
          if (0 == Array(i)(j)) {
            Array(i)(j) = 2
            val tmp1 = Evaluate(Array, i, j)
            Array(i)(j) = 1
            val tmp2 = Evaluate(Array, i, j)
            if (tmp2 >= 10000000 && MaxWei < 10000000) { //机器未到死四且人到了活3
              MaxWei = tmp2 + 10000000
              idX = i
              idY = j
            }
            else if (tmp2 >= 500000 && MaxWei < 500000) {
              MaxWei = tmp2 + 500000
              idX = i
              idY = j
            }
            else if (tmp2 >= 10000 && MaxWei < 10000) {
              MaxWei = tmp2 + 10000
              idX = i
              idY = j
            }
            else if (tmp1 > tmp2 && tmp1 > MaxWei) {
              MaxWei = tmp1
              idX = i
              idY = j
            }
            else if (tmp2 > tmp1 && tmp2 > MaxWei) {
              MaxWei = tmp2
              idX = i
              idY = j
            }
            Array(i)(j) = 0
          }

          {
            j += 1; j - 1
          }
        }

        {
          i += 1; i - 1
        }
      }
      return new chessOneStep(idX, idY, MaxWei)
    }
    var ret = new chessOneStep(-1, -1, -chessposition.INF)
    var i = 0
    while ( {
      i < 15
    }) {
      var j = 0
      while ( {
        j < 15
      }) {
        if (0 == Array(i)(j)) {
          if (who) Array(i)(j) = 2
          else Array(i)(j) = 1
          val tmp = derectSearch(Array, !who, deepth - 1)
          Array(i)(j) = 0
          if (tmp.GetWeight > ret.GetWeight) ret = tmp
        }

        {
          j += 1; j - 1
        }
      }

      {
        i += 1; i - 1
      }
    }
    ret
  }

  //计算机人工智能中极大极小法搜索下棋,向前看LookLength步
  def GetMinMaxsearchNext(LookLength: Int): Unit = {
    val Option = MinMaxsearch(chessposition.MyChessPiece.GetAllFlag, true, LookLength)
    val NextX = Option.GetX
    val NextY = Option.GetY
    new chessmusic("下棋.wav")
    if (-1 == NextX && -1 == NextY) {
      if (chessimage.CurrentStep == 225) {
        chessimage.Message = "伯仲之间 ,胜负难分!"
        chessposition.CanGo = false
      }
      return
    }
    chessimage.CurrentStep += 1
    val OneStep = new chessOneStep(NextX, NextY, 0)
    chessposition.MyChessList.AddStep(OneStep)
    chessposition.MyChessPiece.SetPositionFlag(NextX, NextY, 2)
    if (IsOver(chessposition.MyChessPiece.GetAllFlag, NextX, NextY)) new chessmusic("取胜.wav")
    display()
  }

  // 极大极小博弈搜索
  def MinMaxsearch(Array: Array[Array[Int]], who: Boolean, deepth: Int): chessOneStep = {

    if (0 == deepth) {
      var MaxWei = -chessposition.INF
      var idX = -1
      var idY = -1
      var i = 0
      while ( {
        i < 15
      }) {
        var j = 0
        while ( {
          j < 15
        }) {
          if (0 == Array(i)(j)) {
            Array(i)(j) = 2
            var tmp = Evaluate(Array, i, j)
            if (tmp >= MaxWei) {
              MaxWei = tmp
              idX = i
              idY = j
            }
            Array(i)(j) = 1
            tmp = Evaluate(Array, i, j)
            if (tmp > MaxWei) {
              MaxWei = tmp
              idX = i
              idY = j
            }
            Array(i)(j) = 0
          }

          {
            j += 1; j - 1
          }
        }

        {
          i += 1; i - 1
        }
      }
      return new chessOneStep(idX, idY, MaxWei)
    }
    if (who) { // 轮到己方,取极大值
      var ret = new chessOneStep(-1, -1, -chessposition.INF)
      val TmpList = new util.ArrayList[chessOneStep]
      var k = 0
      while ( {
        k < 15
      }) {
        var j = 0
        while ( {
          j < 15
        }) {
          if (0 == Array(k)(j)) {
            Array(k)(j) = 2
            TmpList.add(new chessOneStep(k, j, Evaluate(Array, k, j)))
            Array(k)(j) = 0
          }

          {
            j += 1; j - 1
          }
        }

        {
          k += 1; k - 1
        }
      }
      Collections.sort(TmpList, new MyCompare)
      val num = if (TmpList.size < 5) TmpList.size
      else 5
      var i = 0
      while ( {
        i < num
      }) {
        val t = TmpList.get(i)
        Array(t.GetX)(t.GetY) = 2
        val tmp = MinMaxsearch(Array, !who, deepth - 1)
        if (tmp.GetWeight > ret.GetWeight) ret = tmp
        Array(t.GetX)(t.GetY) = 0

        {
          i += 1; i - 1
        }
      }
      ret
    }
    else { // 轮到对手，取极小值
      var ret = new chessOneStep(-1, -1, chessposition.INF)
      val TmpList = new util.ArrayList[chessOneStep]
      var z = 0
      while ( {
        z < 15
      }) {
        var j = 0
        while ( {
          j < 15
        }) {
          if (0 == Array(z)(j)) {
            Array(z)(j) = 1
            TmpList.add(new chessOneStep(z, j, Evaluate(Array, z, j)))
            Array(z)(j) = 0
          }

          {
            j += 1; j - 1
          }
        }

        {
          z += 1; z - 1
        }
      }
      Collections.sort(TmpList, new MyCompare)
      val num = if (TmpList.size < 5) TmpList.size
      else 5
      var i = 0
      while ( {
        i < num
      }) {
        val t = TmpList.get(i)
        Array(t.GetX)(t.GetY) = 1
        val tmp = MinMaxsearch(Array, !who, deepth - 1)
        if (tmp.GetWeight < ret.GetWeight) ret = tmp
        Array(t.GetX)(t.GetY) = 0

        {
          i += 1; i - 1
        }
      }
      ret
    }
  }
}

