package com.thana.simplegame.data.repository.soundrepository

import android.app.Application
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import com.thana.simplegame.R
import javax.inject.Inject

class SoundRepositoryImpl @Inject constructor( application: Application) :
    SoundRepository {

    private var winAudio: ExoPlayer = ExoPlayer.Builder(application).build()
    private var loseAudio: ExoPlayer = ExoPlayer.Builder(application).build()


    override fun playWin() {
        val winUri = RawResourceDataSource.buildRawResourceUri(R.raw.win)

        winAudio.apply {
            setMediaItem(MediaItem.fromUri(winUri))
            prepare()

        }

        winAudio.play()
    }

    override fun playLose() {
        val loseUri = RawResourceDataSource.buildRawResourceUri(R.raw.lose)


        loseAudio.apply {
            setMediaItem(MediaItem.fromUri(loseUri))
            prepare()
        }
        loseAudio.play()
    }

}