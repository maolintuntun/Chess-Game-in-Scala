import java.util.Comparator


class MyCompare extends Comparator[chessOneStep] {
  override def compare(arg0: chessOneStep, arg1: chessOneStep): Int = if (arg0.GetWeight > arg1.GetWeight) 1
  else if (arg0.GetWeight < arg1.GetWeight) -1
  else 0
}
