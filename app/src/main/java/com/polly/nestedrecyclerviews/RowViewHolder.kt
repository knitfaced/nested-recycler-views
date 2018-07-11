package com.polly.nestedrecyclerviews

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.polly.nestedrecyclerviews.R.id.monster_name
import com.polly.nestedrecyclerviews.R.id.title_text
import kotlinx.android.synthetic.main.horde.view.*

sealed class RowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class HordeViewHolder(itemView: View) : RowViewHolder(itemView) {

    val hordeRecyclerView : RecyclerView = itemView.horde_recycler_view
}

class MonsterViewHolder(itemView: View) : RowViewHolder(itemView) {

    val monsterNameText: TextView = itemView.findViewById(monster_name)
    val titleText: TextView? = itemView.findViewById(title_text)
}