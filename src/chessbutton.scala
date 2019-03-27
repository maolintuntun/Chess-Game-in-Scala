import java.awt.Font
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.ItemEvent
import java.awt.event.ItemListener
import java.util.Date
import javax.swing.ButtonGroup
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JRadioButton
import scala.collection._



@SerialVersionUID(1L)
class chessbutton() extends JButton {
  Undo = new JButton("repent")
  Undo.setBounds((chessboard.Left / 4 - 60).toInt, (chessboard.Low - 40).toInt, 80, 40)
  Undo.setFont(new Font("Serif", Font.PLAIN, 10))
  Tie = new JButton("ask draw")
  Tie.setBounds((chessboard.Left / 4 * 3 - 40).toInt, (chessboard.Low - 40).toInt, 80, 40)
  Tie.setFont(new Font("Serif", Font.PLAIN, 10))
  Surrender = new JButton("give up")
  Surrender.setBounds((chessboard.Left / 4 - 60).toInt, (chessboard.Low - 100).toInt, 80, 40)
  Surrender.setFont(new Font("Serif", Font.PLAIN, 10))
  Exit = new JButton("exit")
  Exit.setBounds((chessboard.Left / 4 * 3 - 40).toInt, (chessboard.Low - 100).toInt, 80, 40)
  Exit.setFont(new Font("Serif", Font.PLAIN, 10))
  NewChess = new JButton("new game")
  NewChess.setBounds((chessboard.Left / 4 + 25).toInt, (chessboard.Low - 70).toInt, 100, 40)
  NewChess.setFont(new Font("Serif", Font.PLAIN, 10))
  val h = new MyHandle
  Undo.addActionListener(h)
  Exit.addActionListener(h)
  Tie.addActionListener(h)
  Surrender.addActionListener(h)
  NewChess.addActionListener(h)
  //设置难度等级
  MyButtonGroup = new ButtonGroup
  Simple = new JRadioButton("easy", true)
  Middle = new JRadioButton("mid")
  Difficult = new JRadioButton("hard")
  Simple.setFont(new Font("Serif", Font.PLAIN, 10))
  Middle.setFont(new Font("Serif", Font.PLAIN, 10))
  Difficult.setFont(new Font("Serif", Font.PLAIN, 10))
  MyButtonGroup.add(Simple)
  MyButtonGroup.add(Middle)
  MyButtonGroup.add(Difficult)
  Simple.setBounds((chessboard.scrSize.width - chessboard.Left + 115).toInt, (chessboard.Low - 50).toInt, 60, 30)
  Middle.setBounds((chessboard.scrSize.width - chessboard.Left + 185).toInt, (chessboard.Low - 50).toInt, 60, 30)
  Difficult.setBounds((chessboard.scrSize.width - chessboard.Left + 255).toInt, (chessboard.Low - 50).toInt, 60, 30)
  val Se = new MySelectHandle
  Simple.addItemListener(Se)
  Middle.addItemListener(Se)
  Difficult.addItemListener(Se)
  //设置背景
  MyBack = new ButtonGroup
  Back1 = new JRadioButton("original", true)
  Back2 = new JRadioButton("classic")
  Back3 = new JRadioButton("fashion")
  Back1.setFont(new Font("Serif", Font.PLAIN, 10))
  Back2.setFont(new Font("Serif", Font.PLAIN, 10))
  Back3.setFont(new Font("Serif", Font.PLAIN, 10))
  MyBack.add(Back1)
  MyBack.add(Back2)
  MyBack.add(Back3)
  Back1.setBounds((chessboard.scrSize.width - chessboard.Left + 115).toInt, (chessboard.Low - 100).toInt, 60, 30)
  Back2.setBounds((chessboard.scrSize.width - chessboard.Left + 185).toInt, (chessboard.Low - 100).toInt, 60, 30)
  Back3.setBounds((chessboard.scrSize.width - chessboard.Left + 255).toInt, (chessboard.Low - 100).toInt, 60, 30)
  val Me = new MyMusicHandle
  Back1.addItemListener(Me)
  Back2.addItemListener(Me)
  Back3.addItemListener(Me)
  //设置标签
  MyBackLabel = new JLabel("background")
  MyBackLabel.setFont(new Font("Serif", Font.PLAIN, 15))
  MyBackLabel.setBounds((chessboard.scrSize.width - chessboard.Left + 50).toInt, (chessboard.Low - 100).toInt, 90, 30)
  MyLabel = new JLabel("level")
  MyLabel.setFont(new Font("Serif", Font.PLAIN, 15))
  MyLabel.setBounds((chessboard.scrSize.width - chessboard.Left + 50).toInt, (chessboard.Low - 50).toInt, 90, 30)
  var Undo: JButton = _
  var Exit: JButton = _
  var Tie: JButton = _
  var Surrender: JButton = _
  var NewChess: JButton = _

  var MyButtonGroup: ButtonGroup = _
  var MyBack: ButtonGroup = _
  var MyLabel: JLabel = _
  var MyBackLabel: JLabel = _
  var Simple: JRadioButton = _
  var Middle: JRadioButton = _
  var Difficult: JRadioButton = _
  var Back1: JRadioButton = _
  var Back2: JRadioButton = _
  var Back3: JRadioButton = _

class MySelectHandle extends ItemListener {
    override def itemStateChanged(select: ItemEvent): Unit = {
      if (Simple.isSelected) chessimage.Rank = 0
      else if (Middle.isSelected) chessimage.Rank = 1
      else if (Difficult.isSelected) chessimage.Rank = 2
      val tmp = new chessposition
      tmp.display()
    }
  }

class MyMusicHandle extends ItemListener {
    override def itemStateChanged(select: ItemEvent): Unit = {
      if (Back1.isSelected) {
        chessimage.WitchMatch = 0
        MainFrame.MyBackMusic.Stop()
        MainFrame.MyBackMusic = new chessmusic("高山流水 - 古筝.wav")
      }
      else if (Back2.isSelected) {
        chessimage.WitchMatch = 1
        MainFrame.MyBackMusic.Stop()
        MainFrame.MyBackMusic = new chessmusic("笑傲江湖琴箫合奏 - 女子十二乐坊.wav")
      }
      else if (Back3.isSelected) {
        chessimage.WitchMatch = 2
        MainFrame.MyBackMusic.Stop()
        MainFrame.MyBackMusic = new chessmusic("赛马 - 女子十二乐坊.wav")
      }
      val tmp = new chessposition
      tmp.display()
    }
  }

class MyHandle extends ActionListener {

    override def actionPerformed(event: ActionEvent): Unit = {
      if (event.getSource eq Exit) System.exit(0)
      else if (chessposition.CanGo && (event.getSource eq Surrender)) {
        chessimage.Message = "you are so good!"
        chessimage.IsSurrender = true
        val tmp = new chessposition
        tmp.display()
        chessposition.CanGo = false
      }
      else if (chessposition.CanGo && (event.getSource eq Tie)) {
        chessimage.Message = "nice to meet you"
        chessimage.IsSurrender = true
        val tmp = new chessposition
        tmp.display()
        chessposition.CanGo = false
      }
      else if (chessposition.CanGo && (event.getSource eq Undo)) if (chessposition.MyChessList.GetSize != 0) {
        chessposition.MyChessList.RemoveLast()
        val tmp = new chessposition
        tmp.display()
      }


      else if (event.getSource eq NewChess) {
        chessimage.begin = new Date
        var array = Array.ofDim[Int](15, 15)
       // val Array =  Array.//15 这有问题
        chessposition.MyChessPiece.SetAllFlag(NewFlag = array)
        chessimage.IsSurrender = false
        chessimage.IsTie = false
        chessimage.Message = ""
        val tmp = new chessposition
        tmp.display()
        chessposition.CanGo = true
        chessimage.CurrentStep = 0
      }
    }
  }

}
