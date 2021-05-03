package com.example.shopandreview.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = ProductEntity::class,
        parentColumns = ["id"],
        childColumns = ["productId"],
        onDelete = CASCADE
    )]
)
data class ReviewEntity(
    @ColumnInfo(index = true)
    val productId: String,
    val locale: String,
    val rating: Int,
    val text: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
