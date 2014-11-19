import java.applet.*;
import javax.swing.*;
import java.net.URL;
import java.net.MalformedURLException;

class SoundLoader extends Thread {
    SoundList soundList;
    URL completeURL;
    String relativeURL;

    public SoundLoader(SoundList soundList,
                       URL baseURL, String relativeURL) {
        this.soundList = soundList;
        try {
            completeURL = new URL(baseURL, relativeURL);
        } catch (MalformedURLException e){
            System.err.println(e.getMessage());
        }
        this.relativeURL = relativeURL;
        setPriority(MIN_PRIORITY);
        start();
    }

    public void run() {
        AudioClip audioClip = Applet.newAudioClip(completeURL);
        soundList.putClip(audioClip, relativeURL);
    }
}
