package com.codeassesment.data.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@JsonClass(generateAdapter = true)
data class User(
  val gender: String? = null,
  val name: Name? = null,
  val location: Location? = null,
  val email: String? = null,
  val login: Login? = null,
  val dob: Dob? = null,
  val registered: Dob? = null,
  val phone: String? = null,
  val cell: String? = null,
  val id: Id? = null,
  val picture: Picture? = null,
  val nat: String? = null
) : Parcelable
