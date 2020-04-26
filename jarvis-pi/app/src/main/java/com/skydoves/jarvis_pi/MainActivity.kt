/*
 * Designed and developed by 2020 rurimo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.skydoves.jarvis_pi

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.mp4.Mp4Extractor
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.playerView
import java.util.Locale

class MainActivity : AppCompatActivity(),
  Player.EventListener, TextToSpeech.OnInitListener {

  private val fireBaseDatabase = FirebaseDatabase.getInstance()
  private val databaseReference = fireBaseDatabase.reference
  private val tts by lazy { TextToSpeech(this, this) }

  private lateinit var player: SimpleExoPlayer

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    referenceFireBaseDatabase()

    // initialize player
    this.player = ExoPlayerFactory.newSimpleInstance(this)
    this.player.addListener(this)
    playerView.player = player

    showNormalPattern(R.raw.snow)
    doAnimation()
  }

  private fun doAnimation() {
    val animation = AnimationUtils.loadAnimation(this, R.anim.fadein)
    playerView.startAnimation(animation)
  }

  override fun onInit(status: Int) {
    if (status == TextToSpeech.SUCCESS) {
      this.tts.language = Locale.KOREAN
    }
  }

  @Suppress("CAST_NEVER_SUCCEEDS")
  private fun referenceFireBaseDatabase() {
    this.databaseReference.child("Message").removeValue()
    this.databaseReference.child("Message").addChildEventListener(object : ChildEventListener {
      override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) = Unit
      override fun onChildRemoved(dataSnapshot: DataSnapshot) = Unit
      override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) = Unit
      override fun onCancelled(databaseError: DatabaseError) = Unit
      override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
        val controlMessage = dataSnapshot.getValue(ControlMessage::class.java)
        controlMessage?.let {
          when (it.message) {
            "turn on" -> showNormalPattern(R.raw.normal)
            "turn off" -> showNormalPattern(R.raw.bye)
            "lights on" -> showNormalPattern(R.raw.magic2)
            "lights off" -> showNormalPattern(R.raw.magic2)
            "bulb" -> showNormalPattern(R.raw.magic2)
            "dancing" -> showNormalPattern(R.raw.dancing)
            "sunny" -> showNormalPattern(R.raw.sunny)
            "rainy" -> showNormalPattern(R.raw.rainy)
            "cloudy" -> showNormalPattern(R.raw.cloudy)
            else -> {
              it.message?.trim()?.let { message ->
                when {
                  message.contains("맑음") -> showNormalPattern(R.raw.sunny)
                  message.contains("비") -> showNormalPattern(R.raw.rainy)
                  else -> showNormalPattern(R.raw.cloudy)
                }
                speech(message)
              }
            }
          }
        }
      }
    })

    this.databaseReference.root.child("LED").addValueEventListener(object : ValueEventListener {
      override fun onCancelled(p0: DatabaseError) = Unit
      override fun onDataChange(data: DataSnapshot) {
        val led = data.value.toString()
      }
    })
  }

  private fun showNormalPattern(resource: Int) {
    // Produces DataSource instances through which media data is loaded.
    val uri = RawResourceDataSource.buildRawResourceUri(resource)
    val dataSource = RawResourceDataSource(this)
    dataSource.open(DataSpec(uri))
    val videoSource =
      ExtractorMediaSource(
        uri,
        DataSource.Factory { dataSource },
        Mp4Extractor.FACTORY,
        null,
        null
      )
    // Prepare the player with the source.
    player.prepare(videoSource)
    player.playWhenReady = true
  }

  override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
    if (playbackState == ExoPlayer.STATE_ENDED) {
      showNormalPattern(R.raw.snow)
    }
  }

  private fun speech(text: String) {
    this.tts.apply {
      setPitch(1.2f)
      setSpeechRate(1.0f)
      speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    this.tts.stop()
    this.tts.shutdown()
  }
}
