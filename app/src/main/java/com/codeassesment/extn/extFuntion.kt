package com.codeassesment.extn

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.codeassesment.R


fun ImageView.loadImage(url: String) = Glide.with(this)
    .load(url)
    .placeholder(R.drawable.placeholder_image).into(this)


fun Context.showToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_LONG).show()


