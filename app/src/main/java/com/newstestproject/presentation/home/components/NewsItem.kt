package com.newstestproject.presentation.home.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight.Companion
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newstestproject.R
import com.newstestproject.domain.model.Article
import com.skydoves.landscapist.glide.GlideImage
import org.joda.time.DateTime
import java.util.concurrent.TimeUnit
import kotlin.math.abs

@Composable
fun NewsItem(
    article: Article,
    onItemClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(vertical = 3.dp)
            .clickable() {
                onItemClick()
            },
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.White,
        elevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .padding(13.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
            ) {
                article.urlToImage?.let {
                    GlideImage(
                        modifier = Modifier
                            .padding(2.dp)
                            .width(90.dp)
                            .height(90.dp)
                            .clip(RoundedCornerShape(6.dp)),
                        imageModel = { article.urlToImage },
                        loading = {
                                  CircularProgressIndicator(Modifier.align(Alignment.Center))
                        },
                        failure = {
                            Box(
                                Modifier
                                    .background(
                                        color = Color.LightGray
                                    )
                                    .fillMaxSize()
                            ) {
                                Text(
                                    text = stringResource(R.string.image_failure_error),
                                    fontSize = 10.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.align(Alignment.Center),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(horizontal = 13.dp),
                ) {
                    Text(
                        text = article.source.name,
                        fontSize = 12.sp,
                    )
                    Text(
                        text = article.title,
                        style = MaterialTheme.typography.h6,
                        fontSize = 18.sp,
                    )
                }
            }

            article.description?.let {
                Text(
                    modifier = Modifier
                        .padding(vertical = 14.dp, horizontal = 4.dp),
                    text = it,
                    color = Color.DarkGray,
                    fontSize = 16.sp,
                )
            }
            Text(
                text = findHowLongAgo(LocalContext.current, article.publishedAt),
                color = Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(vertical = 0.dp)
            )
        }
    }
}

fun findHowLongAgo(
    context: Context,
    dateTime: DateTime,
): String {
    val currentDateTime = DateTime.now()
    val deltaMilliseconds = (currentDateTime.millis - dateTime.millis)
    var tempTime = TimeUnit.MILLISECONDS.toMinutes(deltaMilliseconds)

    if(tempTime < 60) {
        return getDeclination(
            tempTime.toInt(),
            context.getString(R.string.first_minute_declination),
            context.getString(R.string.second_minute_declination),
            context.getString(R.string.third_minute_declination))
    }
    tempTime /= 60
    if(tempTime < 24) {
        return getDeclination(
            tempTime.toInt(),
            context.getString(R.string.first_hour_declination),
            context.getString(R.string.second_hour_declination),
            context.getString(R.string.third_hour_declination))
    }
    tempTime /= 24
    if(tempTime < 29) {
        return getDeclination(
            tempTime.toInt(),
            context.getString(R.string.first_day_declination),
            context.getString(R.string.second_day_declination),
            context.getString(R.string.third_day_declination))
    }
    tempTime /= 29
    if(tempTime < 2) {
        return context.getString(R.string.first_month_declination)
    }
    return dateTime.toString("dd-MM-yyyy")
}

fun getDeclination(
    number: Int,
    firstDeclination: String,
    secondDeclination: String,
    thirdDeclination: String
): String {
    var n = abs(number)
    n %= 100
    if (n in 5..20) {
        return "$number $thirdDeclination"
    }
    n %= 10
    if (n == 1) {
        return "$number $firstDeclination"
    }
    if (n in 2..4) {
        return "$number $secondDeclination"
    }
    return "$number $thirdDeclination"
}