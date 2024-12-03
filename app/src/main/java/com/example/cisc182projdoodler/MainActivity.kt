package com.example.cisc182projdoodler

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.cisc182projdoodler.ui.theme.CISC182ProjDoodlerTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun brushButtonClick(view: View?){
            

    }

    fun colorButtonClick(view : View?){

    }

    fun undoButtonClick(view : View?){

    }
    fun redoButtonClick(view : View?){

    }

    fun clearButtonClick(view : View?){

    }

}

