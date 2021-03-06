package com.example.balapplat

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.core.content.ContextCompat.getSystemService
import androidx.transition.TransitionManager
import com.example.balapplat.friends.FriendsActivity
import com.example.balapplat.leaderboard.LeaderBoardActivity
import com.example.balapplat.model.HighScore
import com.example.balapplat.play.WaitingActivity
import com.example.balapplat.rank.RankActivity
import com.facebook.AccessToken
import com.facebook.Profile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.startActivity

class HomeFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    lateinit var helper : Helper


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }



    override fun onStart() {
        auth = FirebaseAuth.getInstance()
        helper = Helper()

        database = FirebaseDatabase.getInstance().reference

        val animationBounce = AnimationUtils.loadAnimation(ctx, R.anim.bounce)
        btnCustomPlay.startAnimation(animationBounce)

        btnCustomPlay.onClick {
            startActivity<WaitingActivity>()
        }

        btnRank.onClick {
            startActivity<RankActivity>()

        }

        btnPlayFriend.onClick {
            if(auth.currentUser == null)
                startActivity(intentFor<LoginActivity>().clearTask())
            else
                startActivity(intentFor<FriendsActivity>().clearTask())
        }

        btnPlayOnline.onClick {
            if(auth.currentUser == null)
                startActivity(intentFor<LoginActivity>().clearTask())
            else
                popUp(1)

        }

        btnLeaderboard.onClick {
            startActivity(intentFor<LeaderBoardActivity>().clearTask())
        }

//        cvProfile.onClick {
//            startActivity(intentFor<LoginActivity>())
//        }

        if(AccessToken.getCurrentAccessToken() == null)
            auth.signOut()

        //updateUI()

        super.onStart()
    }
//


    fun getFacebookProfilePicture(userID: String): String {
        return "https://graph.facebook.com/$userID/picture?type=large"
    }

    override fun onDestroy() {
        helper.userActive(false)
        super.onDestroy()
    }

    fun popUp(layout: Int){
//        val inflater:LayoutInflater = activity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//
//        val view = if (layout == 1)
//            inflater.inflate(R.layout.pre_enter_room,null)
//        else
//            inflater.inflate(R.layout.game_category,null)
//
//
//        // Initialize a new instance of popup window
//        val popupWindow = PopupWindow(
//            view, // Custom view to show in popup window
//            LinearLayout.LayoutParams.MATCH_PARENT, // Width of popup window
//            LinearLayout.LayoutParams.MATCH_PARENT// Window height
//        )
//
//        // Set an elevation for the popup window
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            popupWindow.elevation = 10.0F
//        }
//
//
//        // If API level 23 or higher then execute the code
////        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
////            // Create a new slide animation for popup window enter transition
////            val slideIn = Slide()
////            slideIn.slideEdge = Gravity.TOP
////            popupWindow.enterTransition = slideIn
////
////            // Slide animation for popup window exit transition
////            val slideOut = Slide()
////            slideOut.slideEdge = Gravity.RIGHT
////            popupWindow.exitTransition = slideOut
////
////        }
//
//        if (layout == 1){
//            val layoutPreEnterRoom = view.findViewById<LinearLayout>(R.id.layout_pre_enter_room)
//
//            layoutPreEnterRoom.onClick {
//                popupWindow.dismiss()
//            }
//        }
//        else{
//            val layoutGameCategory = view.findViewById<LinearLayout>(R.id.layout_game_category)
//            val btnPlay = view.findViewById<Button>(R.id.btnNormalGame)
//
//            btnPlay.onClick {
//                startActivity(intentFor<CountdownActivity>().clearTask())
//            }
//
//            layoutGameCategory.onClick {
//                popupWindow.dismiss()
//            }
//        }
//
//
//        // Finally, show the popup window on app
//        TransitionManager.beginDelayedTransition(root_layout)
//        popupWindow.showAtLocation(
//            root_layout, // Location to display popup window
//            Gravity.CENTER, // Exact position of layout to display popup
//            0, // X offset
//            0 // Y offset
//        )

    }
}
