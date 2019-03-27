import java.io.File

import javax.sound.sampled.AudioInputStream

import javax.sound.sampled.AudioSystem

import javax.sound.sampled.Clip

//remove if not needed
import scala.collection.JavaConversions._

class chessmusic(What: String) {

  private var clip: Clip = _

  try {
    val inputStream: AudioInputStream =
      AudioSystem.getAudioInputStream(new File(chessimage.path + What))
    clip = AudioSystem.getClip
    clip.open(inputStream)
    if (What == "下棋.wav") clip.start()
    else if (What == "取胜.wav") clip.start()
    else clip.loop(100)
  } catch {
    case e: Exception => e.printStackTrace()

  }

  def Stop(): Unit = {
    clip.stop()
    clip.close()
  }

}
