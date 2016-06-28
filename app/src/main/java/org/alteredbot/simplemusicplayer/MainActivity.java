package org.alteredbot.simplemusicplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageView contentImage;
    TextView contentComposer;
    TextView contentTitle;
    TextView contentDescription;


    private ImageButton previousButton;
    private FloatingActionButton actionButton;
    private ImageButton nextButton;


    MediaPlayer mediaPlayer = null;
    private SeekBar seekbar;

    private double startTime = 0;
    private double finalTime = 0;
    private Handler myHandler = new Handler();

    private Song currentSong = null;

    List<Song> songList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentImage = (ImageView)findViewById(R.id.content_image);
        contentComposer = (TextView) findViewById(R.id.content_composer);
        contentTitle = (TextView) findViewById(R.id.content_title);
        contentDescription = (TextView) findViewById(R.id.content_description);

        seekbar = (SeekBar)findViewById(R.id.seekBar);

        previousButton = (ImageButton) findViewById(R.id.previousbutton);
        actionButton = (FloatingActionButton)findViewById(R.id.actionbutton);
        nextButton = (ImageButton) findViewById(R.id.nextbutton);


        // specify an adapter (see also next example)
        songList = getSongList();

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(mediaPlayer != null){
                    mediaPlayer.seekTo(seekBar.getProgress());
                }
            }
        });



        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(songList == null){
                    songList = getSongList();
                }

                if(currentSong == null){
                    // no song selected play the last song in the list
                    playSong(songList.size()-1);
                }else if(songList.indexOf(currentSong) == 0){
                    //
                    playSong(songList.size()-1);
                }else{

                    int previousSongIndex = songList.indexOf(currentSong)-1;
                    playSong(previousSongIndex);

                }
            }
        });

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleMediaPlayer();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(songList == null){
                    songList = getSongList();
                }
                if(currentSong == null){
                    // no song selected play the first song in the list
                    playSong(0);
                }else if(songList.indexOf(currentSong) == songList.size()-1){
                    playSong(0);
                }else{

                    int nextSongIndex = songList.indexOf(currentSong)+1;
                    playSong(nextSongIndex);
                }
            }
        });


        Intent mIntent = getIntent();
        int songIndex = mIntent.getIntExtra("songIndex", 0);

        playSong(songIndex);


    }


    @Override
    public void onPause(){
        super.onPause();
        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();

        }
    }


    private void toggleMediaPlayer(){
        if(mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                actionButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                mediaPlayer.pause();

            } else {
                mediaPlayer.start();
                actionButton.setImageResource(R.drawable.ic_pause_black_24dp);

            }
        }
    }


    private void playSong(int songIndex){

        Song song = songList.get(songIndex);

        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();

        }

        mediaPlayer = MediaPlayer.create(getApplicationContext(), getResources().getIdentifier(song.getMusicHolder(), "raw", getPackageName()));
        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                nextButton.callOnClick();
            }
        });

        finalTime = mediaPlayer.getDuration();
        startTime = mediaPlayer.getCurrentPosition();

        seekbar.setMax((int) finalTime);

        seekbar.setProgress((int)startTime);
        myHandler.postDelayed(UpdateSongTime,1000);

        contentImage.setImageResource(song.getPictureHolder());
        contentComposer.setText(song.getComposer());
        contentTitle.setText(song.getTitle());
        contentDescription.setText(song.getDescription());
        actionButton.setImageResource(R.drawable.ic_pause_black_24dp);
        currentSong = song;
    }


    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            seekbar.setProgress((int)startTime);
            myHandler.postDelayed(this, 100);
        }
    };

    private List<Song> getSongList(){
        ArrayList<Song> songs = new ArrayList<Song>();

        String chopinDescription = "The Waltz in C-sharp minor is the second work of Chopin's opus 64 and the companion to the Minute Waltz (Op. 64, No. 1). It was composed in 1847.";
        Song chopin = new Song("Waltz in C# minor, Op. 64, No. 2","Frédéric Chopin", chopinDescription,R.drawable.chopin, "chopinwaltzop64no2incsharpminor");
        songs.add(chopin);

        String beethovenDescription = "The work was premiered with Beethoven himself conducting in Vienna on 8 December 1813 at a charity concert for soldiers wounded in the Battle of Hanau. In Beethoven's address to the participants, the motives are openly named: \"We are moved by nothing but pure patriotism and the joyful sacrifice of our powers for those who have sacrificed so much for us.\"";
        Song beethoven = new Song("Symphony No. 7 in A major, Op. 92: II. Allegretto","Ludwig van Beethoven", beethovenDescription,R.drawable.beethoven, "johnmichelcellobeethovensymphony7allegretto");
        songs.add(beethoven);

        String dvorakDescription = "Popularly known as the New World Symphony, was composed by Antonín Dvořák in 1893 while he was the director of the National Conservatory of Music of America from 1892 to 1895. It is by far his most popular symphony, and one of the most popular of all symphonies. Neil Armstrong took a recording of the New World Symphony to the Moon during the Apollo 11 mission, the first Moon landing, in 1969.";
        Song dvorak = new Song("Symphony No. 9 in E minor, Op. 95: IV. Allegro Con Fuoco","Antonín Dvořák", dvorakDescription,R.drawable.dvorak, "dvoraksymphonyno9op95iv");
        songs.add(dvorak);

        String brahmsDescription = "A musical motto consisting of three notes, F–A-flat–F, was significant to Brahms. At the time of the Third Symphony, Brahms was a fifty-year-old bachelor who declared himself to be Frei aber froh, \"Free but happy\". His F–A–F motto, and some altered variations of it, can be heard throughout the symphony.";
        Song brahms = new Song("Symphony No. 3 in F major, Op. 90 - III Poco allegretto","Johannes Brahms", brahmsDescription,R.drawable.brahms, "brahmssymphony");
        songs.add(brahms);

        String shumannDescription = "No. 7, Träumerei, is one of Schumann's best known pieces; it was the title of a 1944 German biographical film on Robert Schumann. Träumerei is also the opening and closing musical theme in the 1947 Hollywood film Song of Love, starring Katharine Hepburn as Clara Wieck Schumann.";
        Song shumann = new Song("Kinderszenen, Op. 15: Traumerei","Robert Schumann", shumannDescription,R.drawable.schumann, "schumannkinderszenenop15vii");
        songs.add(shumann);

        String chopin2Description = "First published in 1833, Chopin himself believed the melody to be his most beautiful one.  Although this étude is sometimes identified by the names \"Tristesse\" (Sadness) or \"Farewell (L'Adieu),\" neither is a name given by Chopin.";
        Song chopin2 = new Song("Étude Op. 10, No. 3 in E major","Frédéric Chopin", chopin2Description,R.drawable.chopin, "chopinopus10tristesse");
        songs.add(chopin2);

        String haydnDescription = "(Franz) Joseph Haydn was a prominent and prolific Austrian composer of the Classical period.  His contributions to musical form have earned him the epithets \"Father of the Symphony\" and \"Father of the String Quartet\".  Haydn spent much of his career as a court musician for the wealthy Esterházy family at their remote estate.";
        Song haydn = new Song("Sonata in F major, Hob.XVI:23","Joseph Haydn", haydnDescription,R.drawable.haydn, "haydnsonata");
        songs.add(haydn);

        String beethoven2Description = "Commonly known as Sonata Pathétique, was written in 1798 when the composer was 27 years old, and was published in 1799. It has remained one of his most celebrated compositions.[1] Beethoven dedicated the work to his friend Prince Karl von Lichnowsky";
        Song beethoven2 = new Song("Sonata No. 8 in C minor, Op. 13","Ludwig van Beethoven", beethoven2Description,R.drawable.beethoven, "beethovensonatana");
        songs.add(beethoven2);

        String brahms2Description = "The Rhapsodies, Op. 79, for piano were written by Johannes Brahms in 1879 during his summer stay in Pörtschach, when he had reached the maturity of his career. They were inscribed to his friend, the musician and composer Elisabeth von Herzogenberg. At the suggestion of the dedicatee, Brahms reluctantly renamed the sophisticated compositions from \"klavierstücke\" (piano pieces) to \"rhapsodies\".";
        Song brahms2 = new Song("Rhapsody in G minor, Op. 79, No. 2","Johannes Brahms", brahms2Description,R.drawable.brahms, "brahms_op79");
        songs.add(brahms2);

        return songs;
    }


}
