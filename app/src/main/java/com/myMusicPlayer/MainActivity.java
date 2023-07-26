package com.myMusicPlayer;

import androidx.appcompat.app.AppCompatActivity;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.Manifest;
import android.animation.AnimatorSet;
import android.media.MediaPlayer;
import android.app.Activity;
import android.widget.TextView;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import android.view.View;
import android.os.Handler;
import android.widget.Toast;
import android.view.Gravity;
import android.os.Environment;
import android.widget.SeekBar;
import java.util.concurrent.TimeUnit;
import android.widget.ViewSwitcher;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import java.util.Collections;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
	
    private static final int PERMISSION_REQUEST_CODE = 1;
	TextView t1, tempo,Durac;
	MediaPlayer mediaPlayer;
	List<String> musicPaths = new ArrayList<>();
	int currentSongIndex = 0;
	boolean lock=false;
	boolean lock2=false;
	boolean aberto=true;
	String TextoOriginal="";
	SeekBar  seekBar;
	Handler handler=new Handler();
	ObjectAnimator animator= new ObjectAnimator();
	Runnable runnable;
	int totalDuration = 0;
	String durationText ="";
	ViewSwitcher viewSwitcher,aleator;
	Random cpu=new Random();
	boolean aleaTorio=false;
	
	
	
	
	
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		// Inicializa o MediaPlayer
		mediaPlayer = new MediaPlayer();
	
	// Verifica se a permissão já foi concedida
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
			// Se a permissão não foi concedida, solicita ao usuário
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
			} else {
			// A permissão já foi concedida, você pode acessar o armazenamento externo aqui
			// Coloque o código para acessar o armazenamento externo aqui
			
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
	       //Musicas em ordem alfabeticas
		   
		  Collections.sort(musicPaths);

		
		
		
		
		  
			showToast("Bem-Vindo de Volta!");
		}
		
		viewSwitcher = findViewById(R.id.viewSwitcher);
		aleator=findViewById(R.id.aleatorio);
		
		
		
		
			t1= findViewById(R.id.musicas);
			// Obtenha a posição atual do TextView
			
			
			// Obtenha a posição atual do TextView
			float initialX = t1.getX();
			
			// Calcule a nova posição para a esquerda e para a direita (por exemplo, 200 pixels para cada lado)
			float targetXLeft = initialX + 200;
			float targetXRight = initialX - 200;
			
			// Crie a animação para mover o TextView para a esquerda e para a direita
			ObjectAnimator animator = ObjectAnimator.ofFloat(t1, "translationX", initialX,targetXLeft,targetXRight,initialX);
			
			// Defina a duração da animação (por exemplo, 1000 milissegundos)
			animator.setDuration(10000);
			
			// Defina o loop contínuo
			animator.setRepeatMode(ObjectAnimator.RESTART);
			animator.setRepeatCount(ObjectAnimator.INFINITE);
			
			// Inicie a animação
			animator.start();
			
			
		
		
		
		
		 
		
		// Configura o ouvinte para saber quando a música acabou
		mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
				public void onCompletion(MediaPlayer mp) {
					// A reprodução da música acabou, inicie a próxima música
				
				    //reprodução aleatoria
					if(aleaTorio==true){
						
						
						
						int numAle=cpu.nextInt(musicPaths.size());
						int numAle2=cpu.nextInt(musicPaths.size());
						if(currentSongIndex != numAle){
							
							currentSongIndex=numAle;
							
							
						}else if(currentSongIndex != numAle2){
							
							currentSongIndex=numAle2;
						}
						else{
							
							currentSongIndex=cpu.nextInt(musicPaths.size());
							
						}
						
					}
					
					//reprodução em ordem
					
					if(lock2==true && aleaTorio==false) {
						currentSongIndex++;
						if(currentSongIndex < musicPaths.size()){
							
							
						playNextSong();}
						
						else{
							currentSongIndex--;
						playNextSong();}
						
					} playNextSong();
				}
			});
			
			TextoOriginal =musicPaths.get(currentSongIndex );
			TextView t1=findViewById (R.id.musicas);
			 t1.setText(TextoOriginal.substring(26));
			
			
			
			
			seekBar = findViewById(R.id.seekBar);
			
			// Defina a duração máxima da SeekBar de acordo com a duração do arquivo de mídia
			
			seekBar.setMax(mediaPlayer.getDuration());
			
			
			// Obter a duração total da música e atualizar o TextView
			totalDuration = mediaPlayer.getDuration();
			durationText = formatDuration(totalDuration);
			Durac=findViewById (R.id.duracao);
			
			Durac.setText(durationText);
			
			
			// Atualize a posição do SeekBar conforme o progresso da reprodução do MediaPlayer
		mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
				public void onPrepared(MediaPlayer mp) {
					seekBar.setMax(mediaPlayer.getDuration());
					
					
				}
			});
			
			// Atualize a posição da reprodução do MediaPlayer conforme o progresso da SeekBar
			seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
					if (fromUser) {
						mediaPlayer.seekTo(progress);
					}
				}
				
				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					// Não é necessário implementar nada aqui
				}
				
				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					// Não é necessário implementar nada aqui
				}
			});
			
			
			
			// Atualizar o progresso da SeekBar enquanto a música estiver sendo reproduzida
			MainActivity.this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (mediaPlayer != null) {
						int currentPosition = mediaPlayer.getCurrentPosition();
						seekBar.setProgress(currentPosition);
						
						
					  	
						
						
						
						
						// Atualizar o texto da duração atual da música
						String currentPositionText = formatDuration(currentPosition);
						
						TextView tempo=findViewById (R.id.tempo);
						tempo.setText(currentPositionText);
						
					}
					handler.postDelayed(this, 1000); // Atualizar a cada 1 segundo (1000 ms)
				}
			});
				
		
		
		
		
		
	}
	
	// Lida com a resposta do usuário sobre a solicitação de permissão
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == PERMISSION_REQUEST_CODE) {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				// Permissão concedida, você pode acessar o armazenamento externo aqui
				// Coloque o código para acessar o armazenamento externo aqui
				
				} else {
					
					if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
						// Se a permissão não foi concedida, solicita ao usuário
						ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
					}
					
				// Permissão negada, lide com isso de acordo com a necessidade do seu aplicativo
				
			}
		}
	}
	
	private void showToast(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		
		
		
	}	
		
		
		
		
		
		
		
	
		
		
		
		
		
		
		
		
		
		
		
    



public  String formatDuration(int duration) {
	return String.format("%02d:%02d",
	TimeUnit.MILLISECONDS.toMinutes(duration),
	TimeUnit.MILLISECONDS.toSeconds(duration) -
	TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
	);
}




public void play(View view){
	
	if (view.getId() == R.id.Play) {
		viewSwitcher.showNext();
		mediaPlayer.start();
		totalDuration = mediaPlayer.getDuration();
		durationText = formatDuration(totalDuration);
		Durac=findViewById (R.id.duracao);
		Durac.setText(durationText);
		lock=true;
		
		
		
		
		} else if (view.getId() == R.id.Pause) {
			viewSwitcher.showPrevious();
		//	handler.removeCallbacks(runnable);
			
			mediaPlayer.pause();
			
			lock=false;
			
	}
}









public void retro(View v){
	if(lock){
		int rE=mediaPlayer.getCurrentPosition();
		mediaPlayer.seekTo(rE-10000);
	}
}

public void avan(View v){
	if(lock){
		int ava=mediaPlayer.getCurrentPosition();
		mediaPlayer.seekTo(ava+10000);
	}
	
}

public void next(View  v){
	currentSongIndex++;
	if(currentSongIndex < musicPaths.size()){
		playNextSong();}
		else{
			 currentSongIndex--;
			 playNextSong();}
	}

public void back(View v) {
	currentSongIndex--;
	if(currentSongIndex>=0){
		playBackSong ();
		}else{
			currentSongIndex++;
			playBackSong();
	}
	
}



private  void playNextSong() {
	lock2=true;
	TextoOriginal =musicPaths.get(currentSongIndex );
	TextView t1=findViewById (R.id.musicas);             
	t1.setText(TextoOriginal.substring(26));
	
	
	
	try {
		mediaPlayer.reset();
		mediaPlayer.setDataSource(musicPaths.get(currentSongIndex));
		mediaPlayer.prepare();
		if(lock){
			mediaPlayer.start();
			totalDuration = mediaPlayer.getDuration();
			durationText = formatDuration(totalDuration);
			Durac=findViewById (R.id.duracao);
			Durac.setText(durationText);
		}
		}catch (IOException e) {
		e.printStackTrace();
	}
}


private void playBackSong(){
	
	
	
	
	if(currentSongIndex >=0){
		
		TextoOriginal =musicPaths.get(currentSongIndex );
		TextView t1=findViewById (R.id.musicas);  
		t1.setText(TextoOriginal.substring(26));
		
		
		
		
		try {
			mediaPlayer.reset();
			mediaPlayer.setDataSource(musicPaths.get(currentSongIndex));
			mediaPlayer.prepare();
			if(lock){
				mediaPlayer.start();
				totalDuration = mediaPlayer.getDuration();
				durationText = formatDuration(totalDuration);
				Durac=findViewById (R.id.duracao);
				Durac.setText(durationText);    }
			
			
			
			
			}catch (IOException e) {
			e.printStackTrace();
		}}else{
		
		
		
	}
	
}	


public void ale(View view){
		
		if (view.getId() == R.id.alea) {
			aleator.showNext();
			aleaTorio=true;
			
			
			
			} else if (view.getId() == R.id.ordem) {
			aleator.showPrevious();
			aleaTorio=false;
			
			
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
