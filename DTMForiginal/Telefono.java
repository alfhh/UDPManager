import java.applet.AudioClip;
import java.net.URL;
import java.net.MalformedURLException;


public class Telefono{

    SoundList soundList;
    String auFileA = "A.au";
    String auFileB = "B.au";
    String auFileC = "C.au";
    String auFileD = "D.au";
    String auFileS = "star.au";
    String auFileH = "hash.au";
    String auFile0 = "zero.au";
    String auFile1 = "one.au";
    String auFile2 = "two.au";
    String auFile3 = "three.au";
    String auFile4 = "four.au";
    String auFile5 = "five.au";
    String auFile6 = "six.au";
    String auFile7 = "seven.au";
    String auFile8 = "eight.au";
    String auFile9 = "nine.au";
    String auPause = "pause.au";
    String chosenPhone;
        
    

    AudioClip onceClip;
    URL codeBase;

        

    public Telefono() {

        startLoadingSounds();
    }


    void startLoadingSounds() {
        //Start asynchronous sound loading.
        try {
            codeBase = new URL("file:" + System.getProperty("user.dir") + "/");
        } catch (MalformedURLException e) {
            System.err.println(e.getMessage());
        }
            soundList = new SoundList(codeBase);
            soundList.startLoading(auFileA);
            soundList.startLoading(auFileB);
            soundList.startLoading(auFileC);
            soundList.startLoading(auFileD);
            soundList.startLoading(auFileS);
            soundList.startLoading(auFile0);
            soundList.startLoading(auFile1);
            soundList.startLoading(auFile2);
            soundList.startLoading(auFile3);
            soundList.startLoading(auFile4);
            soundList.startLoading(auFile5);
            soundList.startLoading(auFile6);
            soundList.startLoading(auFile7);
            soundList.startLoading(auFile8);
            soundList.startLoading(auFile9);
            soundList.startLoading(auPause);
    }

public void marcar(String chosenPhone) {
for (int i=0; i<chosenPhone.length(); i++){
long tiempo=300;

switch (chosenPhone.charAt(i)){
  case '0': onceClip = soundList.getClip(auFile0); break;
  case '1': onceClip = soundList.getClip(auFile1); break;
  case '2': onceClip = soundList.getClip(auFile2); break;
  case '3': onceClip = soundList.getClip(auFile3); break;
  case '4': onceClip = soundList.getClip(auFile4); break;
  case '5': onceClip = soundList.getClip(auFile5); break;
  case '6': onceClip = soundList.getClip(auFile6); break;
  case '7': onceClip = soundList.getClip(auFile7); break;
  case '8': onceClip = soundList.getClip(auFile8); break;
  case '9': onceClip = soundList.getClip(auFile9); break;
  case 'p': onceClip = soundList.getClip(auPause); break;
  default : tiempo=0; break;
}
   onceClip.play();  
try{
   Thread.sleep(tiempo);
} catch(InterruptedException ie){}

}//for
		
        

} //marcar

}  //Telefono
