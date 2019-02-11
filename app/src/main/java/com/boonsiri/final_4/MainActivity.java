package com.boonsiri.final_4;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.R.drawable;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	private static final int RECORDER_SAMPLERATE = 44100;
	private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
	private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;


	private MyFFT myfft;
	private TextView chordTV;
	private ImageButton btnSetSoundGetChord;
	private RecordingAndSetChord asyncTaskChords = null;
	private boolean started = false;
	private AudioRecord recorder = null;
	private int bufferSize = 0;


	private Drawable microphoneButton;

	private Shader textGradient;


	private static int[] coresNotas = { Color.rgb(0, 0, 0),
			Color.rgb(0, 0, 0), Color.rgb(0, 0, 0),
			Color.rgb(0, 0, 0), Color.rgb(0, 0, 0),
			Color.rgb(0, 0, 0), Color.rgb(0, 0, 0),
			Color.rgb(0, 0, 0), Color.rgb(0, 0, 0),
			Color.rgb(0, 0, 0), Color.rgb(0, 0, 0),
			Color.rgb(0, 0, 0) };

	private static String[] nomeAcordes = { "F", "F m", "F aum", "F dim", "F#",
			"F# m", "F# aum", "F# dim", "G", "G m", "G aum", "G dim", "G#",
			"G# m", "G# aum", "G# dim", "A", "A m", "A aum", "A dim", "Bb",
			"Bb m", "Bb aum", "Bb dim", "B", "B m", "B aum", "B dim", "C",
			"C m", "C aum", "C dim", "C#", "C# m", "C# aum", "C# dim", "D",
			"D m", "D aum", "D dim", "Eb", "Eb m", "Eb aum", "Eb dim", "E",
			"E m", "E aum", "E dim" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		myfft = new MyFFT();
		chordTV = (TextView) findViewById(R.id.Chord);
		chordTV.setTextSize(70);

		microphoneButton = this.getResources().getDrawable(
				drawable.ic_btn_speak_now);
		ColorFilter filter = new LightingColorFilter(Color.WHITE, Color.WHITE);
		microphoneButton.setColorFilter(filter);

		btnSetSoundGetChord = (ImageButton) findViewById(R.id.btnSetSoundGetChord);
		btnSetSoundGetChord.setImageDrawable(microphoneButton);
		btnSetSoundGetChord.setOnClickListener(btnClick);

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if (asyncTaskChords != null) {
			started = false;
			asyncTaskChords.cancel(true);
			asyncTaskChords = null;
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	private View.OnClickListener btnClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnSetSoundGetChord: {
				if (started) {
					stopRecorders();

				} else {
					playRecorders();
				}
				break;
			}
			}
		}
	};

	public void stopRecorders() {
		started = false;
		asyncTaskChords.cancel(true);
		btnSetSoundGetChord.setImageDrawable(microphoneButton);
	}

	public void playRecorders() {
		started = true;
		asyncTaskChords = null;
		asyncTaskChords = new RecordingAndSetChord();
		asyncTaskChords.execute();
		btnSetSoundGetChord.setImageResource(drawable.ic_media_pause);
	}

	private class RecordingAndSetChord extends AsyncTask<Void, byte[], Void> {

		@Override
		protected Void doInBackground(Void... params) {

			System.out.println("Do in background!!!");

			while (started) {

				bufferSize = AudioRecord.getMinBufferSize(RECORDER_SAMPLERATE,
						AudioFormat.CHANNEL_IN_MONO,
						AudioFormat.ENCODING_PCM_16BIT);

				byte data[] = new byte[bufferSize];

				recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,
						RECORDER_SAMPLERATE, RECORDER_CHANNELS,
						RECORDER_AUDIO_ENCODING, bufferSize);

				ByteArrayOutputStream baos = null;
				baos = new ByteArrayOutputStream();
				int i = recorder.getState();
				if (i == 1) {
					recorder.startRecording();
				}
				long tempoInicio = System.currentTimeMillis();
				long tempoDecorrido = 0;
				while (tempoDecorrido <= 800) {
					int result = recorder.read(data, 0, data.length);
					if (result == AudioRecord.ERROR_INVALID_OPERATION) {
						throw new RuntimeException();
					} else if (result == AudioRecord.ERROR_BAD_VALUE) {
						throw new RuntimeException();
					} else {
						baos.write(data, 0, result);
					}
					tempoDecorrido = System.currentTimeMillis() - tempoInicio;
				}
				recorder.stop();
				recorder.release();
				recorder = null;
				publishProgress(baos.toByteArray());
				try {
					baos.close();
					baos = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
		}
		@Override
		protected void onProgressUpdate(byte[]... values) {
			myfft.setByteArraySong(values[0]);
			float[] S1 = myfft.getS1();
				int numAcorde = myfft.getAcorde();
				chordTV.setText(nomeAcordes[numAcorde]);
				textGradient = new LinearGradient(0, 0, 0, 250,
						new int[] { coresNotas[(int) (numAcorde / 4)],
								Color.rgb(153, 47, 47) }, new float[] { 0, 1 },
						TileMode.CLAMP);
				chordTV.getPaint().setShader(textGradient);
			}
		}
}