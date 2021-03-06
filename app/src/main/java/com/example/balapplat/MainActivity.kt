package com.example.balapplat

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
//Christopher Hartanto
        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

        if(AccessToken.getCurrentAccessToken() != null)
            receiveInvitation()

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    loadHomeFragment(savedInstanceState)
                }
                R.id.tournament -> {
                    loadTournamentFragment(savedInstanceState)
                }
                R.id.profile -> {
                    loadProfileFragment(savedInstanceState)
                }

            }
            true
        }
        bottom_navigation.selectedItemId = R.id.home
    }

    private fun loadHomeFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, HomeFragment(), HomeFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadTournamentFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, Tournament(), Tournament::class.java.simpleName)
                .commit()
        }
    }

    private fun loadProfileFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, ProfileFragment(), ProfileFragment::class.java.simpleName)
                .commit()
        }
    }

    override fun onDestroy() {
        Log.d("destroy","masuk")
        super.onDestroy()
    }

    fun receiveInvitation(){
        val postListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    alert{
                        title = "Invitation"
                        yesButton {
                            val values: HashMap<String, Any> = hashMapOf(
                                "status" to true
                            )
                            database.child("invitation").child(Profile.getCurrentProfile().id).setValue(values).addOnSuccessListener {
                                toast("accepted game")

                                startActivity(intentFor<CountdownActivity>("status" to "player2"))

                            }.addOnFailureListener {
                                toast(""+ it.message)
                            }
                        }
                        noButton {
                            database.child("invitation").child(Profile.getCurrentProfile().id).removeValue()
                        }
                    }.show()
                }
            }

        }
        database.child("invitation").child(Profile.getCurrentProfile().id).addValueEventListener(postListener)
    }
}
