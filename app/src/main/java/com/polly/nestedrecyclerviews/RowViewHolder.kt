package com.polly.nestedrecyclerviews

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.horde.view.*
import kotlinx.android.synthetic.main.monster.view.*

sealed class RowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class HordeViewHolder(itemView: View) : RowViewHolder(itemView) {

    val hordeRecyclerView : RecyclerView = itemView.horde_recycler_view
}

class MonsterViewHolder(itemView: View) : RowViewHolder(itemView) {

    val monsterNameText: TextView = itemView.monster_name
}