package com.example.teamproject_hometrainingassistant_app.ui.community.noticeBoard.NoticeBoardDetail

data class NoticeBoardDetailData(
    val message : String,
    val name : String,
    val time: Long
){
    constructor() : this("", "", 0)
}
