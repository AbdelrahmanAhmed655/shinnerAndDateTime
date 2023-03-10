package com.example.spinneranddatetime

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Spinner
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spinneranddatetime.ui.theme.Purple500
import com.example.spinneranddatetime.ui.theme.SpinnerAndDateTimeTheme
import com.example.spinneranddatetime.viewModel.SpinnerViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpinnerAndDateTimeTheme {
                SpinnerView()

            }
        }
    }
}
@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun SpinnerView(){
    val sampleList= mutableListOf("sample1","sample2","sample3","sample4","sample5")
    var sampleName: String by remember { mutableStateOf(sampleList[0]) }
    var expanded by remember { mutableStateOf(false) }
    val transitionState= remember {
        MutableTransitionState(expanded).apply {
            targetState=!expanded
        }
    }
    val transition = updateTransition(targetState = transitionState, label = "transition")
    val arrowRotationDegree by transition.animateFloat({
        tween(durationMillis = 300)
    }, label = "rotationDegree") {
        if (expanded) 180f else 0f
    }
    val context= LocalContext.current
    val viewModel:SpinnerViewModel= viewModel()
    val dateTime=viewModel.time.observeAsState()


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(Purple500)
                .padding(15.dp)
        ) {
            Text(
                text = "Spinner & DateTime",
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Column {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "spinner",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    Modifier
                        .clickable {
                            expanded = !expanded
                        }
                        .padding(8.dp), horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = sampleName,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .height(30.dp)
                    )
                    Icon(imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription ="spinner",
                        Modifier.rotate(arrowRotationDegree)
                    )
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded=false }) {
                        sampleList.forEach { data ->
                            DropdownMenuItem(onClick = {
                                expanded = false
                                sampleName=data}) {

                                Text(text = data)
                            }

                        }

                    }
                }


            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Calender Date & Time Picker",
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextButton(
                    onClick = {
                        viewModel.selectDataTime(context)
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Purple500)
                        .padding(5.dp)
                ) {
                    Text(text = "Select Date", color = Color.White)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = dateTime.value ?: "No Time Set")
            }

        }
    }

}

