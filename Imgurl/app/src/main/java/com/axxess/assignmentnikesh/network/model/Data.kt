package com.axxess.assignmentnikesh.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

data class Data (
    @SerializedName("id")
    @Expose
    var id: String? = null,

    @SerializedName("title")
    @Expose
    var title: String? = null,

    @SerializedName("description")
    @Expose
    var description: Any? = null,

    @SerializedName("datetime")
    @Expose
    var datetime: Int? = null,

    @SerializedName("cover")
    @Expose
    var cover: String? = null,

    @SerializedName("cover_width")
    @Expose
    var coverWidth: Int? = null,

    @SerializedName("cover_height")
    @Expose
    var coverHeight: Int? = null,

    @SerializedName("account_url")
    @Expose
    var accountUrl: String? = null,

    @SerializedName("account_id")
    @Expose
    var accountId: Int? = null,

    @SerializedName("privacy")
    @Expose
    var privacy: String? = null,

    @SerializedName("layout")
    @Expose
    var layout: String? = null,

    @SerializedName("views")
    @Expose
    var views: Int? = null,

    @SerializedName("link")
    @Expose
    var link: String? = null,

    @SerializedName("ups")
    @Expose
    var ups: Int? = null,

    @SerializedName("downs")
    @Expose
    var downs: Int? = null,

    @SerializedName("points")
    @Expose
    var points: Int? = null,

    @SerializedName("score")
    @Expose
    var score: Int? = null,

    @SerializedName("is_album")
    @Expose
    var isAlbum: Boolean? = null,

    @SerializedName("vote")
    @Expose
    var vote: Any? = null,

    @SerializedName("favorite")
    @Expose
    var favorite: Boolean? = null,

    @SerializedName("nsfw")
    @Expose
    var nsfw: Boolean? = null,

    @SerializedName("section")
    @Expose
    var section: String? = null,

    @SerializedName("comment_count")
    @Expose
    var commentCount: Int? = null,

    @SerializedName("favorite_count")
    @Expose
    var favoriteCount: Int? = null,

    @SerializedName("topic")
    @Expose
    var topic: String? = null,

    @SerializedName("topic_id")
    @Expose
    var topicId: Int? = null,

    @SerializedName("images_count")
    @Expose
    var imagesCount: Int? = null,

    @SerializedName("in_gallery")
    @Expose
    var inGallery: Boolean? = null,

    @SerializedName("is_ad")
    @Expose
    var isAd: Boolean? = null,

    @SerializedName("tags")
    @Expose
    var tags: List<Any> = ArrayList(),

    @SerializedName("ad_type")
    @Expose
    var adType: Int? = null,

    @SerializedName("ad_url")
    @Expose
    var adUrl: String? = null,

    @SerializedName("in_most_viral")
    @Expose
    var inMostViral: Boolean? = null,

    @SerializedName("include_album_ads")
    @Expose
    var includeAlbumAds: Boolean? = null,

    @SerializedName("images")
    @Expose
    var images: ArrayList<Image> = ArrayList(),

    @SerializedName("ad_config")
    @Expose
    var adConfig: AdConfig? = null
)