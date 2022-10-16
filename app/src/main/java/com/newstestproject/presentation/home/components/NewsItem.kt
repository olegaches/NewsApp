package com.newstestproject.presentation.home.components

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
    modifier: Modifier,
    article: Article,
    onItemClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .clickable(
                onClick = { onItemClick() }
            ),
    ) {
        Row {
            GlideImage(
                modifier = Modifier
                    .padding(8.dp)
                    .width(92.dp)
                    .height(92.dp),
                imageModel = { article.urlToImage },
            )
            Column(
                modifier = Modifier,
            ) {
                Text(
                    text = article.source.name,
                )
                Text(
                    text = article.title,
                    color = Color.Blue,
                    fontSize = 20.sp,
                )
            }
        }

        Text(
            text = article.description,
            color = Color.DarkGray,
            fontSize = 14.sp,
        )
        Text(
            text = findHowLongAgo(LocalContext.current, article.publishedAt),
            color = Color.Gray,
            fontSize = 14.sp,
        )
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