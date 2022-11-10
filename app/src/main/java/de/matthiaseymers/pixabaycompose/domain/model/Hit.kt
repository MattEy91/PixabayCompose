package de.matthiaseymers.pixabaycompose.domain.model

import android.os.Parcelable
import de.matthiaseymers.pixabaycompose.data.local.entity.HitEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Hit(
    val id: Long,
    val previewURL: String,
    val tags: String,
    val user: String,
    val largeImageURL: String,
    val likes: Int,
    val comments: Int,
    val downloads: Int
) : Parcelable {
    fun toHitEntity(): HitEntity {
        return HitEntity(
            id = id,
            previewURL = previewURL,
            tags = tags,
            user = user,
            largeImageURL = largeImageURL,
            likes = likes,
            comments = comments,
            downloads = downloads
        )
    }
}