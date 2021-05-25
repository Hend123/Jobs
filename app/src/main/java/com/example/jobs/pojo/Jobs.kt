package com.example.jobs.pojo

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//@Entity(indices = [@Index(value = {"id"}, unique = true)])
@Entity(tableName = "job_favorite_table")

data class Job(
    @ColumnInfo
    @PrimaryKey
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("created_at") val created_at: String?,
    @SerializedName("company") val company: String?,
    @SerializedName("company_url") val company_url: String?,
    @SerializedName("location") val location: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("how_to_apply") val how_to_apply: String?,
    @SerializedName("company_logo") val company_logo: String?,
    var favorite:String?
): Parcelable {
   // @PrimaryKey var idCount: Int = 0

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
       // idCount = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(type)
        parcel.writeString(url)
        parcel.writeString(created_at)
        parcel.writeString(company)
        parcel.writeString(company_url)
        parcel.writeString(location)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(how_to_apply)
        parcel.writeString(company_logo)
        parcel.writeString(favorite)
       // parcel.writeInt(idCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Job> {
        override fun createFromParcel(parcel: Parcel): Job {
            return Job(parcel)
        }

        override fun newArray(size: Int): Array<Job?> {
            return arrayOfNulls(size)
        }
    }
}
