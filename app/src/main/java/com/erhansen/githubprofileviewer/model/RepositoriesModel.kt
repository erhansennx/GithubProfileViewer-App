package com.erhansen.githubprofileviewer.model

import android.os.Parcel
import android.os.Parcelable

class RepositoriesModel() : ArrayList<RepositoriesModelItem>(), Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<RepositoriesModel> {
        override fun createFromParcel(parcel: Parcel): RepositoriesModel {
            return RepositoriesModel(parcel)
        }

        override fun newArray(size: Int): Array<RepositoriesModel?> {
            return arrayOfNulls(size)
        }
    }
}