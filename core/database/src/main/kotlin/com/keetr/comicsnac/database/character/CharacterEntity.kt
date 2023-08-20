package com.keetr.comicsnac.database.character

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class CharacterEntity(
    val id: Int,
    val aliases: List<String>,
    @ColumnInfo("api_detail_url") val apiDetailUrl: String,
    @ColumnInfo("count_of_issue_appearances") val countOfIssueAppearances: Int,
//    val creators: List<PersonBasic>,
    val deck: String,
    val description: String,
//    val firstAppearance: IssueBasic,
    @ColumnInfo("enemies_id") val enemiesId: List<Int>,
    @ColumnInfo("friends_id") val friendsId: List<Int>,
//    val gender: Gender,
    @ColumnInfo("image_url") val imageUrl: String,
    @ColumnInfo("movies_id") val moviesId: List<Int>,
    val name: String,
//    val origin: OriginBasic?,
//    val powers: List<PowerBasic>,
//    val publisher: PublisherBasic?,
    @ColumnInfo("real_name") val realName: String,
    @ColumnInfo("site_detail_url") val siteDetailUrl: String,
    @ColumnInfo("team_enemies_id") val teamEnemiesId: List<Int>,
    @ColumnInfo("team_friends_id") val teamFriendsId: List<Int>,
    @ColumnInfo("teams_id") val teamsId: List<Int>,
    @ColumnInfo("volume_credits_id") val volumeCreditsId: List<Int>
)

data class Character(
    @ColumnInfo("api_detail_url") val apiDetailUrl: String,
    val deck: String,
    val id: Int,
    @ColumnInfo("image_url") val imageUrl: String,
    val name: String,
    @ColumnInfo("site_detail_url") val siteDetailUrl: String
)
