package com.polly.nestedrecyclerviews

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.polly.nestedrecyclerviews.R.id.monster_name
import com.polly.nestedrecyclerviews.R.id.title_text
import kotlinx.android.synthetic.main.horde.view.*

class HordeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val hordeRecyclerView : RecyclerView = itemView.horde_recycler_view
}

class MonsterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val monsterNameText: TextView = itemView.findViewById(monster_name)
    val titleText: TextView? = itemView.findViewById(title_text)
}