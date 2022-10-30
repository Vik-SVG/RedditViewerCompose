package com.priesniakov.redditviewercompose.ui.home.components

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.core.content.ContextCompat.startActivity
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.priesniakov.redditviewercompose.R
import com.priesniakov.redditviewercompose.data.entities.response.DataX
import com.priesniakov.redditviewercompose.data.entities.response.getTestData
import com.priesniakov.redditviewercompose.domain.utils.formatDate
import com.priesniakov.redditviewercompose.ui.theme.TextSecondary
import com.priesniakov.redditviewercompose.ui.theme.TextTitle
import com.priesniakov.redditviewercompose.ui.theme.White


@Composable
fun PostsListItem(post: DataX?, onItemClick: () -> Unit) {
    Surface(
        color = MaterialTheme.colors.onPrimary,
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onItemClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween) {
            UpperData(post)
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "${post?.title}. ${post?.author}",
                style = MaterialTheme.typography.body1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.size(16.dp))
            CommentsAndShare(post)
        }
    }
}

@Composable
private fun UpperData(post: DataX?) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        AsyncImage(
            contentDescription = "",
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(post?.thumbnail).transformations(RoundedCornersTransformation(12f))
                .placeholder(R.drawable.redit_logo).error(R.drawable.redit_logo).build(),
            modifier = Modifier.size(64.dp),
            imageLoader = ImageLoader.Builder(LocalContext.current).build()
        )
        Column(
            modifier = Modifier
                .padding(PaddingValues(8.dp, 0.dp, 0.dp, 0.dp))
                .weight(1f)
        ) {
            Text(
                text = "${post?.subreddit_name_prefixed}",
                style = TextTitle,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = "Posted by ${post?.author} ❇ ${post?.created_utc?.let { formatDate(it) }}",
                style = TextSecondary,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
        }
        Bookmark()
        MorePopup()
    }
}

@Composable
private fun MorePopup() {
    var popupControl by rememberSaveable {
        mutableStateOf(false)
    }

    Icon(
        modifier = Modifier
            .size(24.dp)
            .clickable {
                popupControl = true
            },
        painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
        contentDescription = "More"
    )
    if (popupControl) {
        Popup(
            properties = PopupProperties(
                focusable = true,
                dismissOnBackPress = true,
            ),
            alignment = Alignment.TopEnd,
            onDismissRequest = {
                popupControl = false
            }
        ) {
            Surface(
                color = MaterialTheme.colors.onPrimary,
                elevation = 8.dp,
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = "Test case")
                    Text(text = "Test case2")
                    Text(text = "Test case3")
                }
            }
        }
    }
}

@Composable
private fun Bookmark() {
    var isBookmarked by rememberSaveable { mutableStateOf(false) }
    Icon(
        painter = painterResource(id = if (isBookmarked) R.drawable.ic_baseline_bookmark_24 else R.drawable.ic_baseline_bookmark_border_24),
        modifier = Modifier
            .size(24.dp)
            .clickable { isBookmarked = !isBookmarked },
        contentDescription = "Bookmark"
    )
}

@Composable
private fun CommentsAndShare(post: DataX?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
    ) {
        Icon(
            modifier = Modifier
                .size(20.dp),
            painter = painterResource(id = R.drawable.ic_baseline_comment_24),
            contentDescription = "Comments"
        )

        Text(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 0.dp),
            text = "${post?.num_comments}",
            style = TextSecondary,
            overflow = TextOverflow.Ellipsis
        )

        val context = LocalContext.current
        Icon(
            modifier = Modifier
                .size(20.dp)
                .clickable {
                    val sendIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "Hey check out this post: ${post?.url}"
                        )
                        type = "text/plain"
                    }
                    startActivity(context, sendIntent, null)
                },
            painter = painterResource(id = R.drawable.ic_baseline_share_24),
            contentDescription = "Share",
        )
        Text(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 0.dp),
            text = "Share",
            style = TextSecondary,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
fun UpperPreview() {
    Box(modifier = Modifier.background(White)) {
        UpperData(
            post = getTestData()
        )
    }
}