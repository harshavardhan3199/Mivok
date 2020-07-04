package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorsFragment extends Fragment {
    private MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;
    /**
     * This listener gets triggered whenever the audio focus changes
     * (i.e., we gain or lose audio focus because of another app or device).
     */
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mediaPlayer.pause();
                mediaPlayer.seekTo( 0 );
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };

    private MediaPlayer.OnCompletionListener mediaListener = new MediaPlayer.OnCompletionListener()
    {
        @Override
        public void onCompletion(MediaPlayer mp)
        {
            releaseMediaPlayer();
        }
    } ;

    public ColorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);
        mAudioManager = (AudioManager)getActivity(). getSystemService( Context.AUDIO_SERVICE ); //To get/request AudioFocus
        final ArrayList<Word> numbers=new ArrayList<Word>(  );
        numbers.add(new Word("Red","Weṭeṭṭi",R.drawable.color_red,R.raw.color_red));
        numbers.add(new Word("Green","Chokokki",R.drawable.color_green,R.raw.color_green));
        numbers.add(new Word("Brown","Takaakki",R.drawable.color_brown,R.raw.color_brown));
        numbers.add(new Word("Gray","Topoppi",R.drawable.color_gray,R.raw.color_gray));
        numbers.add(new Word("Black","Kululli",R.drawable.color_black,R.raw.color_black));
        numbers.add(new Word("White","Kelelli",R.drawable.color_white,R.raw.color_white));
        numbers.add(new Word("Dusty Yellow","Topiisә",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        numbers.add(new Word("Mustard Yellow","Chiwiiṭә",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));
        WordAdapter wordAdapter = new WordAdapter( getActivity(), numbers,R.color.category_colors );
        ListView listView=(ListView)rootView.findViewById( R.id.list );
        listView.setAdapter( wordAdapter );
        listView.setOnItemClickListener( new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                releaseMediaPlayer();
                Word word=numbers.get( position );
                // Request audio focus so in order to play the audio file. The app needs to play a
                // short audio file, so we will request audio focus with a short amount of time
                // with AUDIOFOCUS_GAIN_TRANSIENT.
                int result = mAudioManager.requestAudioFocus( mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT );
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create( getActivity(), word.getAudioResourceId() );
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener( mediaListener );
                }
            }
        } );
        return rootView;

    }
    @Override
    public void onStop() {
        super.onStop();
        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }
    /* *We want to release resources so that it can be used by any other component inside our app
     *For that we are creating a method called releaseMediaPlayer()
     * release() method releases the resources
     */
    private void releaseMediaPlayer()
    {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;
            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}
