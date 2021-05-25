package com.example.jobs.Utils

import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jobs.R
import com.example.jobs.pojo.Job
import com.squareup.picasso.Picasso
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


@BindingAdapter("loadImage")
fun loadImage(view: ImageView, imageUrl: String?) {
    imageUrl?.let {
        Picasso.get()
            .load(imageUrl).placeholder(R.drawable.place_holder).into(view)


    }

}

@BindingAdapter("setTxt")
fun setTxt(textView: TextView, txt: String) {
    txt?.let {
        textView.setText(Html.fromHtml(txt))
    }

}

@BindingAdapter("getHyperLink")
fun getHyperLink(textView: TextView, txt: String) {
    Log.v("txt",txt)
    textView.setClickable(true)
    textView.setMovementMethod(LinkMovementMethod.getInstance())
    if (!txt.equals("not available")) {
        textView.setText(Html.fromHtml("<a href=" + "'" + txt + "'" + ">" + "Link" + "</a>"))
    } else {
        textView.setText("Not Available")
    }


}
