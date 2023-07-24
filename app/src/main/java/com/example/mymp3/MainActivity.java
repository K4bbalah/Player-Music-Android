package com.example.mymp3;

import com.example.mymp3.R;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.view.View;
import android.os.Handler;
import android.widget.Toast;
import android.view.Gravity;
import android.os.Environment;

public class MainActivity extends Activity {

     TextView t1;
     TextView tempo;
      MediaPlayer mediaPlayer;
     List<String> musicPaths = new ArrayList<>();
    int currentSongIndex = 0; 
    int avancar=0;
    boolean lock=false;
    int total=0;
    boolean lock2=false;
    boolean aberto=true;
    int minutes = 0;
    int seconds = 0;
    String TextoOriginal="";
    
     Handler handler;
     Runnable runnable;
     
    
      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
    
  
    
    
    
    
    TextView tempo=findViewById(R.id.tempo);
     tempo.setText("Min: "+minutes+" : "+seconds+"s");
    
    
    
    
    
    
    
    
  
    // Inicializa o MediaPlayer
   mediaPlayer = new MediaPlayer();
    



   
       





        // Adicione o caminho da pasta que contém as músicas
        String folderPath =Environment.getExternalStorageDirectory().getAbsolutePath() + "/Music";

        // Lista todos os arquivos na pasta e armazena os caminhos dos arquivos de áudio na lista musicPaths
        File folder = new File(folderPath);
       
       File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".mp3")) {
                    musicPaths.add(file.getAbsolutePath());
                }
            }
        }
       
       total=musicPaths.size();
    

    // Configura o ouvinte para saber quando a música acabou
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // A reprodução da música acabou, inicie a próxima música
           
             
            if(lock2==true) {
             currentSongIndex++;
    if(currentSongIndex < musicPaths.size()){
       
   avancar=0;
     playNextSong();}
    
  else{ avancar =0;
   currentSongIndex--;
  playNextSong();}
              
       } playNextSong();                                  
            }
        });
    
  TextoOriginal =musicPaths.get(currentSongIndex );
   TextView t1=findViewById (R.id.musicas);               t1.setText(TextoOriginal.substring(26));
   
   
   
    

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                // Código que será executado a cada segundo
                avancar=avancar+1000;                 
                
                   //Obter o tempo atual da música em milissegundos
int currentPosition = mediaPlayer.getCurrentPosition();

// Converter para segundos


int currentSeconds = currentPosition / 1000;
minutes = currentSeconds / 60;
seconds = currentSeconds % 60;



     TextView tempo=findViewById(R.id.tempo);
     tempo.setText("Min: "+minutes+" : "+seconds+"s");
                
           //  showToast()  ;
                
                
                      
                // Agendar a próxima execução do loop após 1 segundo
                handler.postDelayed(this, 1000);
            }
        };

     
     
     
     
     
        
    }

    
   
  /* 
 
 private void showToast() {
        Toast toast = Toast.makeText(this, "Reproduzindo!", Toast.LENGTH_SHORT);

        // Definir a posição do Toast como parte superior da tela
     //   toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 1500);

       // toast.show();
    }   
  */
                     
   
   
   public void play(View v){
     mediaPlayer.start();
     lock=true;
     //tempo();
     if(aberto==true){
     handler.post(runnable);
     aberto=false;
     }
       
     }
        
     public void tempo(){
       
       //Obter o tempo atual da música em milissegundos
int currentPosition = mediaPlayer.getCurrentPosition();

// Converter para segundos
int currentSeconds = currentPosition / 1000;
int minutes = currentSeconds / 60;
int seconds = currentSeconds % 60;

     TextView tempo=findViewById(R.id.tempo);
     tempo.setText(minutes+":"+seconds);
       
       
     }
        
        
        
        
                         
           
    public void retro(View v){
   if(lock==true){
   if (avancar>0){
    avancar=avancar-10000;
        mediaPlayer.seekTo(avancar);
   }else{
        avancar=0;
        }}
           }
           
   public void avan(View v){
   if(lock==true){
     avancar=avancar+10000;
     mediaPlayer.seekTo(avancar);
     }}
      
   public void pause(View v){
     if(lock==true){
     mediaPlayer.pause();
     handler.removeCallbacks(runnable);
    aberto=true;
    lock=false;
    
     }
   }
    
  public void next(View  v){
       currentSongIndex++;
    if(currentSongIndex < musicPaths.size()){
       
   avancar=0;
     playNextSong();}
    
  else{ currentSongIndex--;
  playNextSong();}}
   
 public void back(View v) {
  currentSongIndex--;
if(currentSongIndex>=0){
avancar=0;
   playBackSong ();}else{
     
     currentSongIndex++;
     playBackSong();
   }
   
   }
  
  public  void  stop(View v)
   {
     
     mediaPlayer.stop();
     lock=false;
     lock2=false;
   handler.removeCallbacks(runnable);
     aberto=true;
     
     
   }
   
   private  void playNextSong() {
   lock2=true;
   avancar=0;
   TextoOriginal =musicPaths.get(currentSongIndex );
   TextView t1=findViewById (R.id.musicas);               t1.setText(TextoOriginal.substring(26));
   
   
   
            try {
                mediaPlayer.reset();
               mediaPlayer.setDataSource(musicPaths.get(currentSongIndex));
     mediaPlayer.prepare();
     if(lock){
     mediaPlayer.start();
      }
            }catch (IOException e) {
               e.printStackTrace();
           }
        } 
    
    
    private void playBackSong(){
    avancar=0;
     if(currentSongIndex >=0){
     
     TextoOriginal =musicPaths.get(currentSongIndex );
   TextView t1=findViewById (R.id.musicas);               t1.setText(TextoOriginal.substring(26));
   
     
     
     
     try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(musicPaths.get(currentSongIndex));
     mediaPlayer.prepare();
     if(lock){
       mediaPlayer.start();    }
     
     
   
                
            }catch (IOException e) {
               e.printStackTrace();
           }}else{
             
             
             
           }
     
     
     
     
    
   }   
   
    
    
    
    
    
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Libera recursos do MediaPlayer quando o aplicativo for encerrado
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
   handler.removeCallbacks(runnable);
        }
    }

}