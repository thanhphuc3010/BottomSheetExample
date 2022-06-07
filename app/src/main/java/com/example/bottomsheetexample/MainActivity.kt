package com.example.bottomsheetexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bottomsheetexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.openBottomSheet.post {
            val position = IntArray(2)
            // Difference between view.getY() and view.getLocationOnScreen in y value is: getY()
            // align with parent view group, getLocationOnScreen align with screen
//            position[0] = button.x.toInt()
//            position[1] = button.y.toInt()
            binding.openBottomSheet.getLocationOnScreen(position)

            Log.d(this.javaClass.name, "x axis: ${position.first()}")
            Log.d(this.javaClass.name, "y axis: ${position[1]}")
        }

        binding.openBottomSheet.setOnClickListener {
            MyBottomSheet.show(supportFragmentManager, "", true) {}
            val insets = ViewCompat.getRootWindowInsets(binding.root) ?: return@setOnClickListener
            val imeVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
            val imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            Log.d(javaClass.name, "keyboard height: $imeHeight")
        }
    }
}