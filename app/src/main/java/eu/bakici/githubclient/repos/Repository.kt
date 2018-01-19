package eu.bakici.githubclient.repos

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import eu.bakici.githubclient.User

/**
 * Created on 13.01.18.
 */
data class Repository(val id: Long,
                      val name: String,
                      val description: String,
                      val owner: User,
                      @SerializedName("forks_count")
                      val forks: Int) : Parcelable {


    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable<User>(Repository::class.java.classLoader),
            parcel.readInt())


    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(id)
        dest.writeString(name)
        dest.writeString(description)
        dest.writeParcelable(owner, flags)
        dest.writeInt(forks)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Repository> {

        override fun createFromParcel(parcel: Parcel): Repository {
            return Repository(parcel)
        }

        override fun newArray(size: Int): Array<Repository?> {
            return arrayOfNulls(size)
        }

    }

    override fun toString(): String {
        return "Repository(id=$id, name='$name', description='$description', owner=$owner, forks=$forks)"
    }
}