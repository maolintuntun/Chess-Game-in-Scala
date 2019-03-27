import java.util
import java.util.{ArrayList, List}


class chesslist {
  private val MyList = new util.ArrayList[chessOneStep]

  def AddStep(OneStep: chessOneStep): Unit = {
    MyList.add(OneStep)
  }

  def GetSize: Int = MyList.size

  def ClearList(): Unit = {
    MyList.clear()
  }

  def RemoveLast(): Unit = {
    var OneStep = MyList.get(GetSize - 1)
    var x = OneStep.GetX
    var y = OneStep.GetY
    chessposition.MyChessPiece.SetPositionFlag(x, y, 0)
    MyList.remove(GetSize - 1)
    chessimage.CurrentStep -= 1
    if (GetSize % 2 != 0) { //说明最上面是人控制的棋
      OneStep = MyList.get(GetSize - 1)
      x = OneStep.GetX
      y = OneStep.GetY
      chessposition.MyChessPiece.SetPositionFlag(x, y, 0)
      MyList.remove(GetSize - 1)
      chessimage.CurrentStep -= 1
    }
  }
}
