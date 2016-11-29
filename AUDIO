package Sonido;

import java.io.*;
import sun.audio.*;

public class SonidoJuego {

    
    public static void main(String[] args) 
    throws Exception        
    {
        
        String sonido = "C:/Este equipo/Escritorio/Juego poo/musica.wav";
        
        InputStream in = new FileInputStream(sonido);
        
        AudioStream audio= new AudioStream(in);
        AudioPlayer.player.start(audio);
    }
}
